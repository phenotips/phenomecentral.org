package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Represents the http://localhost:8083/AllData page, where "Browse... -> Browse patients" is clicked on
 */
public class AllPatientsPage extends BasePage
{
    private final By importJSONLink = By.id("phenotips_json_import");

    private final By JSONBox = By.id("import");

    private final By importBtn = By.id("import_button");

    private final By sortCreationDate = By.cssSelector(
        "th.xwiki-livetable-display-header-text:nth-child(4) > a:nth-child(1)"); // 4th column

    private final By firstPatientRowLink = By.cssSelector(
        "#patients-display > tr:nth-child(1) > td:nth-child(1) > a:nth-child(1)");

    public AllPatientsPage(WebDriver aDriver)
    {
        super(aDriver);
    }

    /**
     * Imports a patient via the passed JSON string. Waits 5 seconds before returning, difficult to detect
     * when import is sucessful.
     * @param theJSON a long string which represents the JSON. Ensure that backslashes are escaped.
     * @return the same object, we stay on the same page.
     */
    public AllPatientsPage importJSONPatient(String theJSON)
    {
        clickOnElement(importJSONLink);
        clickAndTypeOnElement(JSONBox, theJSON);
        clickOnElement(importBtn);
        unconditionalWaitNs(5);
        return this;
    }

    /**
     * Sorts the list of patients in descending order. Assumes that the default sort (i.e. what comes up
     * when the page is first visited) is the starting state. Clicks on the sort twice, needs to do that
     * for some reason to sort descending.
     * @return same object as it is the same page.
     */
    public AllPatientsPage sortPatientsDateDesc()
    {
        clickOnElement(sortCreationDate);
        clickOnElement(sortCreationDate);
        unconditionalWaitNs(5);
        return this;
    }

    /**
     * Click on the first patient in the table to view its full profile.
     * @return the patient's full info page which is called the ViewPatientPage
     */
    public ViewPatientPage viewFirstPatientInTable()
    {
        clickOnElement(firstPatientRowLink);
        return new ViewPatientPage(superDriver);
    }
}
