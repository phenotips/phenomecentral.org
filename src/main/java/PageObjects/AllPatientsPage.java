package PageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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

    private final By deleteBtns = By.cssSelector("tbody[id=patients-display] > tr > td.actions > a.fa-remove");

    private final By deleteYesConfirmBtn = By.cssSelector("input[value=Yes]");

    private final By patientIDFilterBox = By.cssSelector("input[title=\"Filter for the Identifier column\"]");

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

    /**
     * Deletes all the patients in the table if there are any, only for the first page
     * @return stay on the same page, so return the same object.
     */
    public AllPatientsPage deleteAllPatients() {
        // We need to somehow wait for rows to load, maybe empty table so we can't search for elements of the table
        unconditionalWaitNs(3);
        List<WebElement> loDeleteBtns = superDriver.findElements(deleteBtns);

        for (WebElement theElement : loDeleteBtns) { // Use the original number of rows a counter
            // theElement.click(); // Table pagintes upwards
            clickOnElement(deleteBtns);
            clickOnElement(deleteYesConfirmBtn);
            unconditionalWaitNs(3);
        }

        return this;
    }

    /**
     * Filters by the patient ID by sending keys to the "type to filter" box under the identifier column
     * @param patientID the patient ID to enter, should be in Pxxxxxxx format.
     * @return stay on the same page so return the same object.
     */
    public AllPatientsPage filterByPatientID(String patientID) {
        clickAndTypeOnElement(patientIDFilterBox, patientID);
        unconditionalWaitNs(2);
        return this;
    }
}
