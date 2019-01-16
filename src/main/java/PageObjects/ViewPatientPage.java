package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Represents viewing a specifc patient's full information page.
 * Ex. http://localhost:8083/P0000005
 */
public class ViewPatientPage extends CommonInfoSelectors
{
    private final By patientID = By.cssSelector("#document-title > h1:nth-child(1)");

    private final By editBtn = By.id("prActionEdit");

    private final By similarityTable = By.cssSelector(".similarity-results");

    public ViewPatientPage(WebDriver aDriver)
    {
        super(aDriver);
    }

    /**
     * Returns a string representing the current patient's ID number. It is the string
     * at the top left corner of the page.
     * @return a String in the form of Pxxxxxxx
     */
    public String getPatientID()
    {
        waitForElementToBePresent(patientID);
        return superDriver.findElement(patientID).getText();
    }

    /**
     * Clicks on the "Edit" link to edit the patient
     * @return new patient editor page object as we navigate to the patient editing page
     */
    public CreatePatientPage editThisPatient()
    {
        clickOnElement(editBtn);
        return new CreatePatientPage(superDriver);
    }
}
