# Integration Tests for PC

Jira Link: https://phenotips.atlassian.net/browse/PC-325

This is WIP e-2-e testing of PC.

## Maven Dependencies
- Selenium WebDriver
- TestNG

## Requirements
- Certain tests check for emails. Ensure that the PC instance is setup to allow for emails to be sent. 

## Usage
- Clone this repository
- cd into directory and verify `pom.xml` is there
- Import the project on intelliJ as a Maven project.
- Start MockMock fakeSMTP by running `java -jar MockMock.jar -p 1025 -h 8085` in the root folder. Change 1025 and 8085 to the outgoing port on PC and the port to access the email UI respectively
- Modify `src/main/java/org.phenotips.endtoendtests.pageobjects/BasePage.java` to specify the login credentials and the address of the PC instance (`HOMEPAGE_URL`, `ADMIN_USERNAME`, `ADMIN_PASS`, etc.).
- Navigate to `src/main/java/org.phenotips.endtoendtests.testcases.java` and run either `CreatePatientTest` or `LoginPageTest`. The run button is located on the class declaration line beside the line number.
	- Alternatively, right click on `MultipleClasses.xml` and then run to run both test classes

## Limitations
- UI/UX issues can't be detected  effectively. Ex. Element is present but is in 1 pt font or hard to see.
- Can't handle native operating system dialoge boxes. Ex. Browser produces a non-web based warning dialgoue. Selenium has no control over those.
- Need to use a third party SMTP browser based service to test emails. Ex. https://github.com/tweakers/MockMock might work.
	- Clone and in release folder run `java -jar MockMock.jar -p 1025 -h 8085`
- A seperate machine or server is highly recommended for test stability. 
