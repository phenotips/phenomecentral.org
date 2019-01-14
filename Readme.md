# Integration Tests for PC

This is WIP stuff for e-2-e testing of PC. This can be used as a proof-of-concept for developing a testing framework.

## Maven Dependencies
- Selenium WebDriver
- TestNG

## Usage
- Clone this repository
- cd into directory and verify the pom.xml
- Import the project on intelliJ as a Maven project.
- Navigate to src/main/java/testCases and run either `createPatientTest` or `loginPageTest`. The run button is located on the class declaration line right beside the line number.

## Limitations
- UI/UX issues can't be detected  effectively. Ex. Element is present but is in 1 pt font or hard to see.
- Can't handle native operating system dialoge boxes. Ex. Browser produces a non-web based warning dialgoue. Selenium has no control over those.
- Need to use a third party SMTP browser based service to test emails. Ex. https://github.com/tweakers/MockMock might work.
	- Clone and in release folder run `java -jar MockMock.jar -p 1025 -h 8085`
