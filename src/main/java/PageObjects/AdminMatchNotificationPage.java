package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * The admin page where match notifications can be sent.
 * Administration -> PhenoTips -> Matching Notification in the left accordion menu
 * i.e. http://localhost:8083/admin/XWiki/XWikiPreferences?editor=globaladmin&section=Matching+Notification
 */
public class AdminMatchNotificationPage extends AdminSettingsPage
{
    By patientIDContainsBox = By.id("external-id-filter");

    By reloadMatchesBtn = By.id("show-matches-button");

    By firstRowFirstEmailBox = By.cssSelector("#matchesTable > tbody > tr > td[name=\"referenceEmails\"] > input");

    By firstRowSecondEmailBox = By.cssSelector("#matchesTable > tbody > tr > td[name=\"matchedEmails\"] > input");

    By sendNotificationsBtn = By.id("send-notifications-button");

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

}
