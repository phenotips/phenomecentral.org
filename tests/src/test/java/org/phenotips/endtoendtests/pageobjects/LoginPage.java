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

import io.qameta.allure.Step;

/**
 * Represents the user login page, {@code http://localhost:8083/PhenomeCentral/login}.
 */
public class LoginPage extends BasePage
{
    private final By userNameField = By.id("j_username");

    private final By passField = By.id("j_password");

    private final By loginButton = By.cssSelector("input.button[value='Sign in']");

    public LoginPage()
    {
        super();
    }

    /**
     * Logs in using username and password. Assumes that login will be successful.
     *
     * @param username non-empty case sensitive user ID
     * @param password non-empty and case sensitive password
     * @return a homepage object as we navigate there on successful login.
     */
    @Step("Login with credentials username {0} and password {1}")
    public HomePage loginAs(String username, String password)
    {
        clickAndTypeOnElement(this.userNameField, username);
        clickAndTypeOnElement(this.passField, password);

        clickOnElement(this.loginButton);

        return new HomePage();
    }

    /**
     * Logs in with the default admin credentials.
     *
     * @return a homepage object as we navigate there upon successful login
     */
    @Step("Login as an admin")
    public HomePage loginAsAdmin()
    {
        return loginAs(ADMIN_USERNAME, ADMIN_PASS);
    }

    /**
     * Logs in with a regular user's credentials.
     *
     * @return a homepage object as we navigate there upon successful login
     */
    @Step("Login as User 1")
    public HomePage loginAsUser()
    {
        return loginAs(USER_1_USERNAME, USER_1_PASS);
    }

    /**
     * Logs in with the second user's credentials.
     *
     * @return a homepage object as we navigate there upon successful login
     */
    @Step("Login as User 2")
    public HomePage loginAsUserTwo()
    {
        return loginAs(USER_2_USERNAME, USER_2_PASS);
    }
}
