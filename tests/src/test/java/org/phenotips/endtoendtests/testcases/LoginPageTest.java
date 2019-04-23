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
package org.phenotips.endtoendtests.testcases;

import org.phenotips.endtoendtests.pageobjects.HomePage;
import org.phenotips.endtoendtests.pageobjects.LoginPage;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test the login functionality. Each test can be run separately.
 */
public class LoginPageTest extends BaseTest
{
    HomePage aHomePage = new HomePage();

    LoginPage aLoginPage = new LoginPage();

    /**
     * Login as Admin, assert that both Administrator Settings link and About link are visible
     */
    @Test
    public void loginAdminTest()
    {
        this.aHomePage.navigateToLoginPage()
            .loginAsAdmin();

        Assert.assertTrue(this.aHomePage.isAdminLinkClickable());
        Assert.assertTrue(this.aHomePage.isAboutLinkClickable());

        this.aHomePage.logOut();
    }

    /**
     * Login as User, assert that Admin Settings link is not visible
     */
    @Test
    public void loginUserTest()
    {
        this.aHomePage.navigateToLoginPage()
            .loginAsUser();

        Assert.assertFalse(this.aHomePage.isAdminLinkClickable());
        Assert.assertTrue(this.aHomePage.isAboutLinkClickable());

        this.aHomePage.logOut();
    }

    /**
     * Test for invalid credentials - bad password. Assert that the appropriate error message appears.
     */
    @Test
    public void badPasswordTest()
    {
        // Valid username but invalid password.
        this.aHomePage.navigateToLoginPage()
            .loginAs(USER_1_USERNAME, "BadPassword");

        Assert.assertEquals(this.aLoginPage.getErrorMessage(), "Invalid credentials");

        this.aLoginPage.cancelAndReturnToHomepage();
    }

    /**
     * Test for invalid credentials - non-existent username. Assert that the appropriate error message appears.
     */
    @Test
    public void invalidUsernameTest()
    {
        this.aHomePage.navigateToLoginPage()
            .loginAs("Non-existent user[{}]", USER_1_PASS);

        Assert.assertEquals(this.aLoginPage.getErrorMessage(), "Invalid credentials");

        this.aLoginPage.cancelAndReturnToHomepage();
    }

    /**
     * Test for invalid credentials - empty username. Assert that the appropriate error message appears.
     */
    @Test
    public void emptyUsernameTest()
    {
        this.aHomePage.navigateToLoginPage()
            .loginAs("", "123");

        Assert.assertEquals(this.aLoginPage.getErrorMessage(), "No user name given");

        this.aLoginPage.cancelAndReturnToHomepage();
    }

    /**
     * Test for invalid credentials - empty password. Assert that the appropriate error message appears.
     */
    @Test
    public void emptyPasswordTest()
    {
        this.aHomePage.navigateToLoginPage()
            .loginAs("EmptyPasswordTest", "");

        Assert.assertEquals(this.aLoginPage.getErrorMessage(), "No password given");

        this.aLoginPage.cancelAndReturnToHomepage();
    }
}
