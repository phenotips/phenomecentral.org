package org.phenotips.endtoendtests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import io.qameta.allure.Step;

/**
 * This is the main Global Administrator settings page. Reached by clicking on "Administrator" (gear icon) link on the
 * top left of the navbar. Ex. http://localhost:8083/admin/XWiki/XWikiPreferences
 */
public class AdminSettingsPage extends BasePage
{
    private final By matchingNotificationMenu = By.id("vertical-menu-Matching Notification");

    private final By refreshMatchesMenu = By.id("vertical-menu-Refresh matches");

    private final By usersMenu = By.id("vertical-menu-Users");

    private final By pendingUsersMenu = By.id("vertical-menu-PendingUsers");

    private final By mailSendingMenu = By.id("vertical-menu-emailSend");

    public AdminSettingsPage(WebDriver aDriver)
    {
        super(aDriver);
    }

    /**
     * Navigates to the "Matching Notification" page. On the left accordion menu: PhenoTips -> Matching Notification
     *
     * @return a MatchNotification page object.
     */
    @Step("Navigate to Admin Matching Notification Page")
    public AdminMatchNotificationPage navigateToMatchingNotificationPage()
    {
        clickOnElement(matchingNotificationMenu);
        return new AdminMatchNotificationPage(superDriver);
    }

    /**
     * Navigates to the "Refresh matches" page. On the left accordion menu: PhenoTips -> Refresh matches
     *
     * @return a AdminRefreshMatches page object as we navigate there.
     */
    @Step("Navigate to Admin Refresh Matches Page")
    public AdminRefreshMatchesPage navigateToRefreshMatchesPage()
    {
        clickOnElement(refreshMatchesMenu);
        return new AdminRefreshMatchesPage(superDriver);
    }

    /**
     * Navigates to the Users page. From the accordion menu on the left: Users & Groups -> Users
     *
     * @return an AdminUsersPage object as we navigate there.
     */
    @Step("Navigate to Admin's Users Page")
    public AdminUsersPage navigateToAdminUsersPage()
    {
        clickOnElement(usersMenu);
        return new AdminUsersPage(superDriver);
    }

    /**
     * Navigates to the Pending Users page. From the accordion menu on the left: Users & Groups -> Pending Users
     *
     * @return an AdminPendingUsersPage as we navigate there.
     */
    @Step("Navigate to Admin's Pending Users Page")
    public AdminPendingUsersPage navigateToPendingUsersPage()
    {
        clickOnElement(pendingUsersMenu);
        return new AdminPendingUsersPage(superDriver);
    }

    /**
     * Navigates to the Mail Sending settings page. From the left accordion menu: Email -> Mail Sending
     *
     * @return an AdminEmailSendingSettingsPage instance as we navigate there.
     */
    @Step("Navigate to Admin Mail Sending Settings Page")
    public AdminEmailSendingSettingsPage navigateToMailSendingSettingsPage()
    {
        clickOnElement(mailSendingMenu);
        return new AdminEmailSendingSettingsPage(superDriver);
    }
}
