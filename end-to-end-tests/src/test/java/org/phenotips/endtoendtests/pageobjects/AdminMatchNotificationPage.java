package org.phenotips.endtoendtests.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import io.qameta.allure.Step;

/**
 * The admin page where match notifications can be sent. Administration -> PhenoTips -> Matching Notification in the
 * left accordion menu i.e. http://localhost:8083/admin/XWiki/XWikiPreferences?editor=globaladmin&section=Matching+Notification
 */
public class AdminMatchNotificationPage extends AdminSettingsPage
{
    private final By patientIDContainsBox = By.id("external-id-filter");

    private final By reloadMatchesBtn = By.id("show-matches-button");

    private final By firstRowFirstEmailBox = By.cssSelector("#matchesTable td[name=referenceEmails] > input.notify");

    private final By firstRowSecondEmailBox = By.cssSelector("#matchesTable td[name=matchedEmails] > input.notify");

    private final By sendNotificationsBtn = By.id("send-notifications-button");

    private final By referencePatientLink = By.cssSelector("#referencePatientTd > a.patient-href");

    private final By matchedPatientLink = By.cssSelector("#matchedPatientTd > a.patient-href");

    private final By contactedStatusCheckbox = By.cssSelector("input[name=notified-filter][value=notified]");

    private final By notContactedStatusCheckbox = By.cssSelector("input[name=notified-filter][value=unnotified]");

    private final By sendingNotificationMessage = By.cssSelector("#send-notifications-messages > div");

    private final By matchesGenotypeScoreSlider = By.cssSelector("#show-matches-gen-score > div.handle");

    private final By matchesAverageScoreSlider = By.cssSelector("#show-matches-score > div.handle");

    public AdminMatchNotificationPage(WebDriver aDriver)
    {
        super(aDriver);
    }

    /**
     * Filters the table by inputting a string into "Patient ID contains:" box and clicking "Refresh Matches"
     *
     * @param identifier String to search by. Usually an identifier or the Patient ID itself
     * @return the same object as we are still on the same page, just with the table filtered
     */
    @Step("Filter matches by identifier: {0}")
    public AdminMatchNotificationPage filterByID(String identifier)
    {
        clickAndTypeOnElement(patientIDContainsBox, identifier);
        clickOnElement(reloadMatchesBtn);
        waitForLoadingBarToDisappear();
        return this;
    }

    /**
     * Notifies the two users on the first row of a match. Requires that there is at least one visible row on the table.
     * Waits until the green "Sending request..." text beside the Send Notifications button disappears Note that the PC
     * instance should have email configured as this does not check the actual text that appears after clicking on the
     * Send Notifcations button.
     *
     * @return the same (current) object, as we stay on the same page.
     */
    @Step("Send an email to the matched patients in the first row")
    public AdminMatchNotificationPage emailFirstRowUsers()
    {
        clickOnElement(firstRowFirstEmailBox);
        clickOnElement(firstRowSecondEmailBox);
        clickOnElement(sendNotificationsBtn);
        waitForElementToBePresent(sendingNotificationMessage);
        waitForElementToBeGone(sendingNotificationMessage);
        return this;
    }

    /**
     * Emails the specified patients in the matching table. Each patient must be in their respective column. Searches
     * for the patient using the same behaviour as the "Patient ID contains:" filter box. i.e. can pass a substring of
     * the patient ID or Patient Name. Will take the first match where the respective substrings appear.
     *
     * @param referencePatient The patient name, or ID number, in the Reference Column
     * @param matchedPatient The patient name, or ID number, in the Matched Column, on the same row as
     * referencePatient.
     * @return Stay on the same page so return the same object.
     */
    @Step("Find and email specific matched patients. Reference Patient ID: {0} with Matched Patient ID: {1}")
    public AdminMatchNotificationPage emailSpecificPatients(String referencePatient, String matchedPatient)
    {
        filterByID(referencePatient);
        waitForLoadingBarToDisappear();

        List<WebElement> loFoundReferencePatients = superDriver.findElements(referencePatientLink);
        List<WebElement> loFoundMatchedPatients = superDriver.findElements(matchedPatientLink);
        List<WebElement> loFoundReferenceEmailBoxes = superDriver.findElements(firstRowFirstEmailBox);
        List<WebElement> loFoundMatchedEmailBoxes = superDriver.findElements(firstRowSecondEmailBox);

        System.out.println("Found reference email boxes number: " + loFoundReferenceEmailBoxes.size());
        System.out.println("Found matched email boxes number: " + loFoundMatchedEmailBoxes.size());

        for (int i = 0; i < loFoundMatchedPatients.size(); ++i) {
            System.out.println("For loop: Reference: " + loFoundMatchedPatients.get(i).getText() +
                "Matched patient: " + loFoundReferencePatients.get(i).getText());
            if (loFoundMatchedPatients.get(i).getText().contains(matchedPatient) &&
                loFoundReferencePatients.get(i).getText().contains(referencePatient))
            {
                clickOnElement(loFoundReferenceEmailBoxes.get(i));
                clickOnElement(loFoundMatchedEmailBoxes.get(i));
                System.out.println("Found a match");
                break;
            }
        }

        clickOnElement(sendNotificationsBtn);

        // Wait for the green "Sending emails..." message to disappear.
        waitForElementToBeGone(sendingNotificationMessage);

        return this;
    }

    /**
     * Determines if the two specified patients appear on the match table, matching to each other.
     *
     * @param referencePatient The reference patient, either PatientID or unique identifier, can be substring.
     * @param matchedPatient The matched patient, either patientID or unique identifier, can be substring.
     * @return boolean, true when there is a referencePatient matching to the matchedPatient, false if match not found.
     */
    @Step("Does match exist between Reference Patient ID: {0} and Matched patient ID: {0}")
    public boolean doesMatchExist(String referencePatient, String matchedPatient)
    {
        filterByID(referencePatient);
        waitForLoadingBarToDisappear();

        List<WebElement> loFoundReferencePatients = superDriver.findElements(referencePatientLink);
        List<WebElement> loFoundMatchedPatients = superDriver.findElements(matchedPatientLink);

        for (int i = 0; i < loFoundMatchedPatients.size(); ++i) {
            System.out.println("For loop: Reference: " + loFoundMatchedPatients.get(i).getText() +
                "Matched patient: " + loFoundReferencePatients.get(i).getText());
            if (loFoundMatchedPatients.get(i).getText().contains(matchedPatient) &&
                loFoundReferencePatients.get(i).getText().contains(referencePatient))
            {
                return true;
            }
        }

        return false; // didn't find the specified patients while looping through match table
    }

    /**
     * Toggles the "Contacted status: contacted" filter checkbox.
     *
     * @return Stay on the same page so return the same object.
     */
    @Step("Toggle contacted status checkbox")
    public AdminMatchNotificationPage toggleContactedStatusCheckbox()
    {
        clickOnElement(contactedStatusCheckbox);
        return this;
    }

    /**
     * Toggles the "Contacted status: not contacted" filter checkbox.
     *
     * @return Stay on the same page so return the same object.
     */
    @Step("Toggle not contacted status checkbox")
    public AdminMatchNotificationPage toggleNotContactedStatusCheckbox()
    {
        clickOnElement(notContactedStatusCheckbox);
        return this;
    }

    /**
     * Sets the genotype slider to 0 by dragging all the way to the left.
     *
     * @return Stay on the same page so return the same object.
     */
    @Step("Set genotype slider filter to zero")
    public AdminMatchNotificationPage setGenotypeSliderToZero()
    {
        waitForElementToBePresent(matchesGenotypeScoreSlider);

        Actions actionBuilder = new Actions(superDriver);
        actionBuilder.dragAndDropBy(superDriver.findElement(matchesGenotypeScoreSlider), -50, 0)
            .build().perform();
        System.out.println("Dragging Genotype score slider to 0.");

        clickOnElement(reloadMatchesBtn);

        return this;
    }

    /**
     * Sets the average score to the minimum value by sliding the average score slider all the way to the left.
     *
     * @return Stay on the same page so return the same object.
     */
    @Step("Set the Average Score slider filter to the minimum value (hard left)")
    public AdminMatchNotificationPage setAverageScoreSliderToMinimum()
    {
        waitForElementToBePresent(matchesAverageScoreSlider);

        Actions actionBuilder = new Actions(superDriver);
        actionBuilder.dragAndDropBy(superDriver.findElement(matchesAverageScoreSlider), -50, 0)
            .build().perform();
        System.out.println("Dragging Average Score slider to 0.1");

        clickOnElement(reloadMatchesBtn);

        return this;
    }
}
