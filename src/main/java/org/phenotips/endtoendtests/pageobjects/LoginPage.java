package org.phenotips.endtoendtests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import io.qameta.allure.Step;

/**
 * Represents the page http://localhost:8083/PhenomeCentral/login
 * This is the user login page
  */
public class LoginPage extends BasePage
{
    private final By userNameField = By.id("j_username");

    private final By passField = By.id("j_pasword"); // TODO: There might be a typo there

    private final By loginButton = By.cssSelector("input.button");

    public LoginPage(WebDriver aDriver)
    {
        super(aDriver);
    }

    /**
     * Logs in using username and password. Assumes that login will be sucessful.
     * @param username non-empty case sensitive user ID
     * @param password non-empty and case sensitive password
     * @return a homepage object as we navigate there on successful login.
     */
    @Step("Login with credentials username {0} and password {1}")
    public HomePage loginAs(String username, String password)
    {
        clickAndTypeOnElement(userNameField, username);
        clickAndTypeOnElement(passField, password);

        clickOnElement(loginButton);

        return new HomePage(superDriver);
    }

    /**
     * Logs in with the default admin credentials
     * @return a homepage object as we navigate there upon sucessful login
     */
    @Step("Login as an admin")
    public HomePage loginAsAdmin()
    {
        return loginAs(ADMIN_USERNAME, ADMIN_PASS);
    }

    /**
     * Logs in with a regular user's credentials.
     * @return a homepage object as we navigate there upon sucessful login
     */
    @Step("Login as User 1")
    public HomePage loginAsUser()
    {
        return loginAs(USER_USERNAME, USER_PASS);
    }

    /**
     * Logs in with the second user's credentials.
     * @return a homepage object as we navigate there upon sucessful login
     */
    @Step("Login as User 2")
    public HomePage loginAsUserTwo()
    {
        return loginAs(USER_USERNAME2, USER_PASS2);
    }
}
