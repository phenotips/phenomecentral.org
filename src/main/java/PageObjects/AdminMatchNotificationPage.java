package PageObjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * The admin page where match notifications can be sent.
 * Administration -> PhenoTips -> Matching Notification in the left accordion menu
 * i.e. http://localhost:8083/admin/XWiki/XWikiPreferences?editor=globaladmin&section=Matching+Notification
 */
public class AdminMatchNotificationPage extends AdminSettingsPage
{
    By patientIDContainsBox = By.id("external-id-filter");

    By reloadMatchesBtn = By.id("show-matches-button");

    By firstRowFirstEmailBox = By.cssSelector("#matchesTable td[name=referenceEmails] > input.notify");

    By firstRowSecondEmailBox = By.cssSelector("#matchesTable td[name=matchedEmails] > input.notify");

    By sendNotificationsBtn = By.id("send-notifications-button");

    By referencePatientLink = By.cssSelector("#referencePatientTd > a.patient-href");

    By matchedPatientLink = By.cssSelector("#matchedPatientTd > a.patient-href");

    By contactedStatusCheckbox = By.cssSelector("input[name=notified-filter][value=notified]");

    By notContactedStatusCheckbox = By.cssSelector("input[name=notified-filter][value=unnotified]");

    public AdminMatchNotificationPage(WebDriver aDriver)
    {
        super(aDriver);
    }

    /**
     * Filters the table by inputting a string into "Patient ID contains:" box and clicking "Refresh Matches"
     * @param identifier String to search by. Usually an identifier or the Patient ID itself
     * @return the same object as we are still on the same page, just with the table filtered
     */
    public AdminMatchNotificationPage filterByID(String identifier)
    {
        clickAndTypeOnElement(patientIDContainsBox, identifier);
        clickOnElement(reloadMatchesBtn);
        unconditionalWaitNs(2);
        return this;
    }

    /**
     * Notifies the two users on the first row of a match. Requires that there is at least one visible row on
     * the table. Waits 10 seconds before returning as it is difficult to check css state.
     * @return the same (current) object, as we stay on the same page.
     */
    // TODO: Requires MockMock to be working and configured!!
    public AdminMatchNotificationPage emailFirstRowUsers()
    {
        clickOnElement(firstRowFirstEmailBox);
        clickOnElement(firstRowSecondEmailBox);
        clickOnElement(sendNotificationsBtn);
        unconditionalWaitNs(10);
        return this;
    }

    /**
     * Emails the specified patients in the matching table. Each patient must be in their respective column.
     * Searches for the patient using the same behaviour as the "Patient ID contains:" filter box. i.e. can pass
     * a substring of the patient ID or Patient Name. Will take the first match where the respective substrings appear.
     * @param referencePatient The patient name, or ID number, in the Reference Column
     * @param matchedPatient The patient name, or ID number, in the Matched Column, on the same row as referencePatient.
     * @return Stay on the same page so return the same object.
     */
    public AdminMatchNotificationPage emailSpecificPatients(String referencePatient, String matchedPatient)
    {
        filterByID(referencePatient);
        unconditionalWaitNs(3); // maybe wait on something?

        List<WebElement> loFoundReferencePatients = superDriver.findElements(referencePatientLink);
        List<WebElement> loFoundMatchedPatients = superDriver.findElements(matchedPatientLink);
        List<WebElement> loFoundReferenceEmailBoxes = superDriver.findElements(firstRowFirstEmailBox);
        List<WebElement> loFoundMatchedEmailBoxes = superDriver.findElements(firstRowSecondEmailBox);

        System.out.println("Found reference email boxes number: " + loFoundReferenceEmailBoxes.size());
        System.out.println("Found matched email boxes number: " + loFoundMatchedEmailBoxes.size());

        for (int i = 0; i < loFoundMatchedPatients.size(); ++i)
        {
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

        return this;
    }

    /**
     * Toggles the "Contacted status: contacted" filter checkbox.
     * @return Stay on the same page so return the same object.
     */
    public AdminMatchNotificationPage toggleContactedStatusCheckbox()
    {
        clickOnElement(contactedStatusCheckbox);
        return this;
    }

    /**
     * Toggles the "Contacted status: not contacted" filter checkbox.
     * @return Stay on the same page so return the same object.
     */
    public AdminMatchNotificationPage toggleNotContactedStatusCheckbox()
    {
        clickOnElement(notContactedStatusCheckbox);
        return this;
    }

}
