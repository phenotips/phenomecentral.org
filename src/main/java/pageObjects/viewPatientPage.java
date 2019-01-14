package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

// Represents the page http://localhost:8083/AllData
public class viewPatientPage extends commonInfoSelectors {
    public viewPatientPage(WebDriver aDriver) { super(aDriver); }

    private final By patientID = By.cssSelector("#document-title > h1:nth-child(1)");
    private final By editBtn = By.id("prActionEdit");
    private final By similarityTable = By.cssSelector(".similarity-results");


    public String getPatientID() {
        waitForElementToBePresent(patientID);
        return superDriver.findElement(patientID).getText();
    }

    public createPatientPage editThisPatient() {
        clickOnElement(editBtn);
        return new createPatientPage(superDriver);
    }

}
