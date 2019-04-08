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

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;

import io.qameta.allure.Step;

/**
 * This class corresponds to the admin's Pending Users page, where users can be approved for access to PC. I.e.
 * http://localhost:8083/admin/XWiki/XWikiPreferences?editor=globaladmin&section=PendingUsers
 */
public class AdminPendingUsersPage extends AdminSettingsPage
{
    private final By approveUserBtns = By.cssSelector("#admin-page-content td.manage > img:nth-child(1)");

    private final By usernamesLinks = By.cssSelector("#userstable td.username > a");

    public AdminPendingUsersPage()
    {
        super();
    }

    /**
     * Approves the nth pending user on the pending users table. Requires there is at least one pending user waiting to
     * be approved.
     *
     * @param n is the Nth user in the table, should be >= 1.
     * @return Stay on the same page so return the same object.
     */
    @Step("Approve the {0}th pending user on the list")
    public AdminPendingUsersPage approveNthPendingUser(int n)
    {
        waitForElementToBePresent(this.approveUserBtns); // Should wait for first button to appear.
        clickOnElement(this.superDriver.findElements(this.approveUserBtns).get(n - 1));

        this.superDriver.switchTo().alert().accept();
        this.superDriver.switchTo().defaultContent();

        return this;
    }

    /**
     * Approves the pending user that is specified by the username. Requires: That the username is valid and exists
     * within the table of pending users.
     *
     * @param userName is username of pending user as a String.
     * @return Stay on the same page so return the same object.
     */
    @Step("Approve pending user with username: {0}")
    public AdminPendingUsersPage approvePendingUser(String userName)
    {
        waitForElementToBePresent(this.approveUserBtns);

        List<String> usernamesFound = new ArrayList<>();
        this.superDriver.findElements(this.usernamesLinks).forEach(x -> usernamesFound.add(x.getText()));

        int indexOfUsername = usernamesFound.indexOf(userName);

        clickOnElement(this.superDriver.findElements(this.approveUserBtns).get(indexOfUsername));

        this.superDriver.switchTo().alert().accept();
        this.superDriver.switchTo().defaultContent();

        return this;
    }
}
