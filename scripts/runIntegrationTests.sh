#!/usr/bin/env bash

# PC and SMTP UI ports. These can be changed as needed.
browser="chrome" # One of: chrome, firefox, edge, ie, safari
PCInstancePort="8083"
PCInstanceStopPort="8084"
emailUIPort="8085" # Port to access Fake SMTP (MockMock) email inbox UI
outGoingEmailPort="1025" # PC instance sends emails to this port. Fake SMTP (MockMock) listens to this port.

PCInstanceURL="http://localhost:$PCInstancePort"
emailUIURL="http://localhost:$emailUIPort"

curDate=$(date +'%s') # Set once
zipExtract="PCInstance_$curDate/" # Directory to create and will contain the contents of extracted zip file


distPath="../../standalone/target/" # Path to phenomecentral.org's distribution folder, where standalone zip is located
distZip="phenomecentral-standalone*.zip"
PCZipName="" # The exact name of the zip file found, to be set in extractZip()


logFile="logFile$curDate.txt"
startPCInstanceCommand="./start.sh"
stopPCInstanceCommand="./stop.sh"
startSMTPCommand="java -jar MockMock.jar"
SMTPPID="" # PID of the FakeSMTP that is to be set in startSMTP()
startingMessage="Phenotips is initializing"
readyMessage="About PhenomeCentral"
# TODO: Ensure that these remain correct
mavenPOMLocation="../../../pom.xml"
mavenTestNGXMLLocation="src/main/java/org/phenotips/testcases/xml/AllTests.xml"

# cd into standalone directory, locate the zip, and extract it to where we were previously. If 0 or more than 1 standalone zip located, exits
extractZip() {
	# Go to distribution folder of PC and check for zips there.
	# Should do this because ls might give full path of file (instead of just filename) if we do not cd into the directory. Dependent on unix flavour.
	cd "$distPath"

	# ls giving filenames only sorted by most recently modified descending
	local numberOfZipsFound=$(ls $distZip -t1 | wc -l)
	PCZipName=$(ls $distZip -t1 | head -n 1)

	if [[ $numberOfZipsFound -eq 0 ]]; then
		echo "No zips following pattern of $distZip were found in $distPath. Exiting." | tee -a $logFile
		exit 1
	elif [[ $numberOfZipsFound -gt 1 ]]; then
		echo "More than one zip following pattern of $distZip were found in $distPath. Not sure which one to use. Exiting." | tee -a $logFile
		exit 2
	else
		echo "Found $PCZipName in $distPath" | tee -a $logFile
		# Return to where we were
		cd -

		mkdir "$zipExtract"
		unzip $distPath$PCZipName -d "$zipExtract" 2>&1 | tee -a $logFile

		echo "Extracted $PCZipName to $zipExtract" | tee -a $logFile
	fi
}

startInstance() {
	zipSubdir=${PCZipName%????} # Cut off last 4 chars of PCZipName (remove the .zip extension as this is the folder name)
	cd $zipExtract$zipSubdir
	echo "Starting server on port $PCInstancePort and stop port $PCInstanceStopPort" | tee -a $logFile
	$startPCInstanceCommand $PCInstancePort $PCInstanceStopPort 2>&1 | tee -a $logFile &
	sleep 30
	echo "Waited 30 seconds for server to start. Now check with curl command" | tee -a $logFile
}

# Checks if the instance has started, recursivly calls itself to check again if the "Phenotips is initializing" message is still there after waiting.
checkForStart() {
	local curlResult
	local curlReturn
	curlResult=$(curl "$PCInstanceURL")
	curlReturn=$? # "local" affects the return code of above curl command. Declarae vars first.

	if test "$curlReturn" != "0"; then
		echo "Curl to $PCInstanceURL has failed. Wait 10 secs on try again" | tee -a $logFile
		sleep 10
		checkForStart
	else
		echo "Response recieved on curl to $PCInstanceURL." | tee -a $logFile
		# local startingMessageFound=$(curl "$PCInstanceURL" | grep -c "$startingMessage")
		local readyMessageFound=$(echo "$curlResult" | grep -c "$readyMessage")

		if [[ "$readyMessageFound" -gt 0 ]]; then
			echo "It seems instance has sucessfully started and is ready." | tee -a $logFile
		else
			echo "Instance is not ready yet. The string $readyMessage was not found on page. Wait 10 seconds and ping again" | tee -a $logFile
			sleep 10
			checkForStart
		fi
	fi
}

runTests() {
	echo "Compiling and running e2e testing framework with maven. Should see maven messages and browser soon"
	mvn test -f $mavenPOMLocation -Dsurefire.suiteXmlFiles=$mavenTestNGXMLLocation -Dbrowser=$browser -DhomePageURL=$PCInstanceURL -DemailUIPageURL=$emailUIURL 
}

stopInstance() {
	echo "Stopping instance that was started on port $PCInstancePort and stop port $PCInstanceStopPort" | tee -a $logFile
	$stopPCInstanceCommand $PCInstanceStopPort | tee -a $logFile
}

startSMTP() {
	echo "Starting SMTP (MockMock). Listening on $outGoingEmailPort. Email UI is at $emailUIPort" | tee -a $logFile
	$startSMTPCommand -p $outGoingEmailPort -h $emailUIPort &
	SMTPPID=$!
	echo "DEBUG: PID of SMTP is: $SMTPPID" | tee -a $logFile
	sleep 10
}

stopSMTP() {
	echo "Killing SMTP" | tee -a $logFile
	while kill INT $SMTPPID 2>/dev/null; do 
    	sleep 1
	done
}

onCtrlC() {
	echo "Ctrl C recieved. Stopping script" | tee -a $logFile
	stopInstance
	stopSMTP
	exit 3 # TODO: check that this does not exit before above two finishes
}

##################
# main
##################
# Taken from start.sh
# Ensure that the commands below are always started in the directory where this script is
# located. To do this we compute the location of the current script.
PRG="$0"
while [ -h "$PRG" ]; do
  ls=`ls -ld "$PRG"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
  else
    PRG=`dirname "$PRG"`/"$link"
  fi
done
PRGDIR=`dirname "$PRG"`
cd "$PRGDIR"

echo "ProgramDir is calculated as: $PRGDIR"

# If a browser argument is passed, then set browser argument
# TODO: Make arguments to allow stop/start ports. Perhaps argument parser needed.
if [ -n "$1" ]; then
	browser=$1
	echo "Browser is being specified as: $browser" | tee -a $logFile
fi

# Create a debug log file.
touch $logFile
pwdir="$(pwd)"
logFile="$pwdir/$logFile" #Specify absolute path to logfile

extractZip

trap onCtrlC SIGINT # If we break (ctrl+c) from here on, we should stop SMTP and PC instance

startSMTP
startInstance
checkForStart
runTests | tee -a $logFile
stopInstance
stopSMTP

