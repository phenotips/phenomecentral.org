package org.phenotips.endtoendtests.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.qameta.allure.Step;

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
     * Imports a patient via the passed JSON string. Waits 5 seconds before returning, difficult to detect when import
     * is sucessful.
     *
     * @param theJSON a long string which represents the JSON. Ensure that backslashes are escaped.
     * @return the same object, we stay on the same page.
     */
    @Step("Import a patient via JSON {0}")
    public AllPatientsPage importJSONPatient(String theJSON)
    {
        clickOnElement(importJSONLink);
        clickAndTypeOnElement(JSONBox, theJSON);
        clickOnElement(importBtn);
        waitForInProgressMsgToDisappear();
        waitForLoadingBarToDisappear();
        return this;
    }

    /**
     * Sorts the list of patients in descending order. Assumes that the default sort (i.e. what comes up when the page
     * is first visited) is the starting state. Clicks on the sort twice, needs to do that for some reason to sort
     * descending.
     *
     * @return same object as it is the same page.
     */
    @Step("Sort patients in descending date order")
    public AllPatientsPage sortPatientsDateDesc()
    {
        clickOnElement(sortCreationDate);
        clickOnElement(sortCreationDate);
        waitForLoadingBarToDisappear();
        return this;
    }

    /**
     * Click on the first patient in the table to view its full profile.
     *
     * @return the patient's full info page which is called the ViewPatientPage
     */
    @Step("View first patient in table")
    public ViewPatientPage viewFirstPatientInTable()
    {
        clickOnElement(firstPatientRowLink);
        return new ViewPatientPage(superDriver);
    }

    /**
     * Deletes all the patients in the table if there are any, only for the first page
     *
     * @return stay on the same page, so return the same object.
     */
    @Step("Delete all patients on the table")
    public AllPatientsPage deleteAllPatients()
    {
        waitForLoadingBarToDisappear();

        List<WebElement> loDeleteBtns = superDriver.findElements(deleteBtns);

        // theElement just acts as an iterator for the array size, we don't use it.
        for (WebElement theElement : loDeleteBtns) { // Use the original number of rows a counter
            // theElement.click(); // Table pagintes upwards
            clickOnElement(deleteBtns);
            clickOnElement(deleteYesConfirmBtn);
            waitForInProgressMsgToDisappear();
            waitForLoadingBarToDisappear();
        }

        return this;
    }

    /**
     * Filters by the patient ID by sending keys to the "type to filter" box under the identifier column
     *
     * @param patientID the patient ID to enter, should be in Pxxxxxxx format.
     * @return stay on the same page so return the same object.
     */
    @Step("Filter patient {0} by their identifier or patient ID")
    public AllPatientsPage filterByPatientID(String patientID)
    {
        clickAndTypeOnElement(patientIDFilterBox, patientID);
        waitForLoadingBarToDisappear();
        return this;
    }
}
