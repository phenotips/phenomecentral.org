/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/
 */
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
        clickOnElement(this.matchingNotificationMenu);
        return new AdminMatchNotificationPage(this.superDriver);
    }

    /**
     * Navigates to the "Refresh matches" page. On the left accordion menu: PhenoTips -> Refresh matches
     *
     * @return a AdminRefreshMatches page object as we navigate there.
     */
    @Step("Navigate to Admin Refresh Matches Page")
    public AdminRefreshMatchesPage navigateToRefreshMatchesPage()
    {
        clickOnElement(this.refreshMatchesMenu);
        return new AdminRefreshMatchesPage(this.superDriver);
    }

    /**
     * Navigates to the Users page. From the accordion menu on the left: Users & Groups -> Users
     *
     * @return an AdminUsersPage object as we navigate there.
     */
    @Step("Navigate to Admin's Users Page")
    public AdminUsersPage navigateToAdminUsersPage()
    {
        clickOnElement(this.usersMenu);
        return new AdminUsersPage(this.superDriver);
    }

    /**
     * Navigates to the Pending Users page. From the accordion menu on the left: Users & Groups -> Pending Users
     *
     * @return an AdminPendingUsersPage as we navigate there.
     */
    @Step("Navigate to Admin's Pending Users Page")
    public AdminPendingUsersPage navigateToPendingUsersPage()
    {
        clickOnElement(this.pendingUsersMenu);
        return new AdminPendingUsersPage(this.superDriver);
    }

    /**
     * Navigates to the Mail Sending settings page. From the left accordion menu: Email -> Mail Sending
     *
     * @return an AdminEmailSendingSettingsPage instance as we navigate there.
     */
    @Step("Navigate to Admin Mail Sending Settings Page")
    public AdminEmailSendingSettingsPage navigateToMailSendingSettingsPage()
    {
        clickOnElement(this.mailSendingMenu);
        return new AdminEmailSendingSettingsPage(this.superDriver);
    }
}
