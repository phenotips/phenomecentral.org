package org.phenotips.pageobjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * This class corresponds to the admin's Pending Users page, where users can be approved for
 * access to PC. I.e. http://localhost:8083/admin/XWiki/XWikiPreferences?editor=globaladmin&section=PendingUsers
 */
public class AdminPendingUsersPage extends AdminSettingsPage
{
    private final By approveUserBtns = By.cssSelector("#admin-page-content td.manage > img:nth-child(1)");
    private final By usernamesLinks = By.cssSelector("#userstable td.username > a");

    public AdminPendingUsersPage(WebDriver aDriver)
    {
        super(aDriver);
    }

    /**
     * Approves the nth pending user on the pending users table.
     * Requires there is at least one pending user waiting to be approved.
     * @param n is the Nth user in the table, should be >= 1.
     * @return Stay on the same page so return the same object.
     */
    public AdminPendingUsersPage approveNthPendingUser(int n)
    {
        waitForElementToBePresent(approveUserBtns); // Should wait for first button to appear.
        clickOnElement(superDriver.findElements(approveUserBtns).get(n - 1));

        superDriver.switchTo().alert().accept();
        superDriver.switchTo().defaultContent();

        return this;
    }

    /**
     * Approves the pending user that is specified by the username.
     * Requires: That the username is valid and exists within the table of pending users.
     * @param userName is username of pending user as a String.
     * @return Stay on the same page so return the same object.
     */
    public AdminPendingUsersPage approvePendingUser(String userName)
    {
        waitForElementToBePresent(approveUserBtns);

        List<String> usernamesFound = new ArrayList<>();
        superDriver.findElements(usernamesLinks).forEach(x -> usernamesFound.add(x.getText()));

        int indexOfUsername = usernamesFound.indexOf(userName);

        clickOnElement(superDriver.findElements(approveUserBtns).get(indexOfUsername));

        superDriver.switchTo().alert().accept();
        superDriver.switchTo().defaultContent();

        return this;
    }

}
