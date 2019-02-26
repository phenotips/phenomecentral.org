#!/usr/bin/env bash

# This script does the following:
# 	- Extract zip file for standalone PC instance
# 	- Start the local PC instance. After 30 seconds, pings to check for $READY_MESSAGE.
#	- Start a fake SMTP server (MockMock SMTP)
#	- Run all integration tests (AllTests.xml)
#	- Stop the SMTP and PC instances
#	- Generate an Allure report with the test run results.

###########################
# Variables
###########################

# PC and SMTP UI ports. These can be changed as needed.
BROWSER="chrome" # One of: chrome, firefox, edge, ie, safari
PC_INSTANCE_PORT="8083"
PC_INSTANCE_STOP_PORT="8084"
EMAIL_UI_PORT="8085" # Port to access Fake SMTP (MockMock) email inbox UI
OUTGOING_EMAIL_PORT="1025" # PC instance sends emails to this port. Fake SMTP (MockMock) listens to this port.

PC_INSTANCE_URL="http://localhost:$PC_INSTANCE_PORT"
EMAIL_UI_URL="http://localhost:$EMAIL_UI_PORT"

CUR_DATE=$(date '+%Y-%m-%d_%H-%M-%S') # Set once
ZIP_EXTRACT="PCInstance_$CUR_DATE" # Directory to create and will contain the contents of extracted zip file
ALL_PC_INSTANCES_FOLDER="instances" # Parent folder after scripts/ for where extracted folders should go


BUILT_ZIP_LOCATION="../../standalone/target" # Path to phenomecentral.org's build standalone/target folder, where standalone zip is located
BUILT_ZIP_REGEX="phenomecentral-standalone*.zip"
BUILT_ZIP_EXACT_NAME="" # The exact name of the zip file found, to be set in extractZip()


OUTPUT_LOG_FILE="outputLog_$CUR_DATE.txt" # Mutate to absolute dir in main
START_PC_COMMAND="./start.sh"
STOP_PC_COMMAND="./stop.sh"
START_SMTP_COMMAND="java -jar smtp-server/MockMock.jar"
SMTP_PID="" # PID of the FakeSMTP that is to be set in startSMTP()
READY_MESSAGE="About PhenomeCentral"
# Ensure that these relative paths remain when changing file structure of project
POM_LOCATION="../../../../pom.xml" # Relative to the extracted from inside the extracted PC instance folder (scripts/$ALL_PC_INSTANCES_FOLDER/$ZIP_EXTRACT/phenomecentral-standalone-*/)
TESTNG_XML_LOCATION="src/test/java/org/phenotips/endtoendtests/testcases/xml/AllTests.xml"

###########################
# Functions
###########################

# cd into standalone directory, locate the zip, and extract it to where we were previously. If 0 or more than 1 standalone zip located, exits
extractZip() {
	echo -e "\n====================== Extract Zip File ======================"
	# Go to distribution folder of PC and check for zips there.
	# Should do this because ls might give full path of file (instead of just filename) if we do not cd into the directory. Dependent on unix flavour.
	cd "$BUILT_ZIP_LOCATION"

	# ls giving filenames only sorted by most recently modified descending
	local ZIPS_FOUND_COUNT=$(ls $BUILT_ZIP_REGEX -t1 | wc -l)
	BUILT_ZIP_EXACT_NAME=$(ls $BUILT_ZIP_REGEX -t1 | head -n 1)

	if [[ $ZIPS_FOUND_COUNT -eq 0 ]]; then
		echo "No zips following pattern of $BUILT_ZIP_REGEX were found in $BUILT_ZIP_LOCATION. Exiting." 
		exit 1
	elif [[ $ZIPS_FOUND_COUNT -gt 1 ]]; then
		echo "More than one zip following pattern of $BUILT_ZIP_REGEX were found in $BUILT_ZIP_LOCATION. Not sure which one to use. Exiting." 
		exit 2
	else
		echo "Found $BUILT_ZIP_EXACT_NAME in $BUILT_ZIP_LOCATION" 
		# Return to where we were
		cd -

		mkdir -p "$ALL_PC_INSTANCES_FOLDER/$ZIP_EXTRACT"
		unzip "$BUILT_ZIP_LOCATION/$BUILT_ZIP_EXACT_NAME" -d "$ALL_PC_INSTANCES_FOLDER/$ZIP_EXTRACT"

		echo "Extracted $BUILT_ZIP_EXACT_NAME to $ALL_PC_INSTANCES_FOLDER/$ZIP_EXTRACT" 
	fi
}

startInstance() {
	echo -e "\n====================== Start PC Instance ======================"

	ZIP_SUBDIR=${BUILT_ZIP_EXACT_NAME%????} # Cut off last 4 chars of BUILT_ZIP_EXACT_NAME (remove the .zip extension as this is the folder name)
	cd $ALL_PC_INSTANCES_FOLDER/$ZIP_EXTRACT/$ZIP_SUBDIR
	echo "Starting server on port $PC_INSTANCE_PORT and stop port $PC_INSTANCE_STOP_PORT" 
	$START_PC_COMMAND $PC_INSTANCE_PORT $PC_INSTANCE_STOP_PORT &
	sleep 30
	echo "Waited 30 seconds for server to start. Now check with curl command" 
}

# Checks if the instance has started, recursivly calls itself to check again if the "Phenotips is initializing" message is still there after waiting.
checkForStart() {
	echo -e "\n====================== Check PC Instance Start Status ======================"
	local CURL_RESULT
	local CURL_RETURN
	CURL_RESULT=$(curl "$PC_INSTANCE_URL")
	CURL_RETURN=$? # "local" affects the return code of above curl command. Declarae vars first.

	if test "$CURL_RETURN" != "0"; then
		echo "Curl to $PC_INSTANCE_URL has failed. Wait 10 secs on try again" 
		sleep 10
		checkForStart
	else
		echo "Response recieved on curl to $PC_INSTANCE_URL." 
		local READY_MESSAGE_FOUND=$(echo "$CURL_RESULT" | grep -c "$READY_MESSAGE")

		if [[ "$READY_MESSAGE_FOUND" -gt 0 ]]; then
			echo "It seems instance has sucessfully started and is ready since '$READY_MESSAGE' is visible on the page" 
		else
			echo "Instance is not ready yet. The string $READY_MESSAGE was not found on page. Wait 10 seconds and ping again" 
			sleep 10
			checkForStart
		fi
	fi
}

runTests() {
	echo -e "\n====================== Running Selenium Tests ======================"
	echo "Compiling and running e2e testing framework with maven. Should see maven messages and browser soon"
	mvn test -f $POM_LOCATION -Dsurefire.suiteXmlFiles=$TESTNG_XML_LOCATION -Dbrowser=$BROWSER -DhomePageURL=$PC_INSTANCE_URL -DemailUIPageURL=$EMAIL_UI_URL 
}

stopInstance() {
	echo -e "\n====================== Stopping PC Instance ======================"
	echo "Stopping instance that was started on port $PC_INSTANCE_PORT and stop port $PC_INSTANCE_STOP_PORT" 
	$STOP_PC_COMMAND $PC_INSTANCE_STOP_PORT 
}

startSMTP() {
	echo -e "\n====================== Start Mock SMTP ======================"
	echo "Starting SMTP (MockMock). Listening on $OUTGOING_EMAIL_PORT. Email UI is at $EMAIL_UI_PORT" 
	$START_SMTP_COMMAND -p $OUTGOING_EMAIL_PORT -h $EMAIL_UI_PORT &
	SMTP_PID=$!
	echo "DEBUG: PID of SMTP is: $SMTP_PID" 
	sleep 10
}

stopSMTP() {
	echo -e "\n====================== Stop SMTP ======================"
	echo "Killing SMTP with PID of $SMTP_PID" 
	while kill INT $SMTP_PID 2>/dev/null; do 
    	sleep 1
	done
}

onCtrlC() {
	echo "Ctrl C recieved. Stopping script" 
	stopInstance
	stopSMTP
	exit 3
}

checkPWD() {
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
}

printHelp() {
	echo "Usage: $0 [--argName argValue]"
	echo "Example: $0 --browser chrome --start 8083 --stop 8084 --emailUI 8085 --emailListen 1025"
	echo "Arguments can be passed in any order and any number of them can be used"
	echo "The example shows the default values if the arg is not supplied."
	echo ""
	echo "Possible Arguments:"
	echo "--help (or -h)     display this help message"
	echo "--browser          Run tests with browser specified. Must be one of: chrome, firefox, edge, ie, safari"
	echo "--start            Start port of the PC instance"
	echo "--stop             Stop port of the PC instance"
	echo "--emailUI          Email UI (MockMock SMTP) access port"
	echo "--emailListen      Port that the mock SMTP listens to for messages. This is the port that the PC instance sends outgoing emails."
}

# Help from https://stackoverflow.com/questions/16483119/an-example-of-how-to-use-getopts-in-bash
parseArgs() {
	echo -e "\n====================== Parsing Arguments ======================"

	OPTSPEC=":h-:"
	while getopts "$OPTSPEC" OPTCHAR; do
	    case "${OPTCHAR}" in
	        -)
                VAL="${!OPTIND}"; OPTIND=$(( $OPTIND + 1 ))
                echo "Parsing option: '--${OPTARG}', value: '${VAL}'" >&2;
	            case "${OPTARG}" in
	                help)
	                    printHelp
	                    exit 0
	                    ;;
	                browser)
	                    BROWSER=${VAL}
	    				echo "Browser is being specified as: $BROWSER" 
	                    ;;
	                start)
	                    PC_INSTANCE_PORT=${VAL}
						echo "PC instance start port is being specified as: $PC_INSTANCE_PORT" 
	                    ;;
	                stop)
	                    PC_INSTANCE_STOP_PORT=${VAL}
	    				echo "PC instance stop port is being specified as: $PC_INSTANCE_STOP_PORT"
	                    ;;
	                emailUI)
	                    EMAIL_UI_PORT=${VAL}
	    				echo "Email UI port is being specified as: $EMAIL_UI_PORT"
	                    ;;
	                emailListen)
	                    OUTGOING_EMAIL_PORT=${VAL}
	    				echo "Email listening port is being specified as: $OUTGOING_EMAIL_PORT"
	                    ;;
	                *)
	                    if [ "$OPTERR" = 1 ] && [ "${OPTSPEC:0:1}" != ":" ]; then
	                        echo "Unknown option --${OPTARG}" >&2
	                    fi
	                    ;;
	            esac;;
	        h)
	            printHelp
	            exit 0
	            ;;
	        *)
	            if [ "$OPTERR" != 1 ] || [ "${OPTSPEC:0:1}" = ":" ]; then
	                echo "Non-option argument: '-${OPTARG}'" >&2
	            fi
	            ;;
	    esac
	done

}



##################
# main
##################
echo -e "\n====================== $0 Start ======================"

checkPWD
parseArgs $@ # Pass command line params to parseArgs... important

# Argument parsing might have changed these.
PC_INSTANCE_URL="http://localhost:$PC_INSTANCE_PORT"
EMAIL_UI_URL="http://localhost:$EMAIL_UI_PORT"

mkdir -p $ALL_PC_INSTANCES_FOLDER

# Create a debug log file.
OUTPUT_LOG_FILE="$PWD/$ALL_PC_INSTANCES_FOLDER/$OUTPUT_LOG_FILE" #Specify absolute path to OUTPUT_LOG_FILE
touch $OUTPUT_LOG_FILE

# Capture both stderr and stout to OUTPUT_LOG_FILE.
# Note: This is process substitution, it might not work on non-bash shells such as ksh or sh.
exec >  >(tee -ia $OUTPUT_LOG_FILE)
exec 2> >(tee -ia $OUTPUT_LOG_FILE >&2)

extractZip

trap onCtrlC SIGINT # If we break (ctrl+c) from here on, we should stop SMTP and PC instance

startSMTP
startInstance
checkForStart
runTests 
stopInstance
stopSMTP

echo -e "\n====================== Generate Allure Report ======================"
mvn -f $POM_LOCATION io.qameta.allure:allure-maven:report

echo -e "\n====================== $0 End ======================"
