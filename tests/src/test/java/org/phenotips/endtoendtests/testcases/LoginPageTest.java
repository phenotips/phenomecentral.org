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
    HomePage currentPage = new HomePage(theDriver);

    LoginPage aLoginPage = new LoginPage(theDriver);

    /**
     * Login as Admin, assert that both Administrator Settings link and About link are visible
     */
    @Test
    public void loginAdminTest()
    {
        this.currentPage.navigateToLoginPage()
            .loginAsAdmin();

        // This is a basic test so we touch a selector directly.
        // But for all other tests, don't touch selectors directly - abstract the action out to a method in a
        // pageObject.
        // Ex. isAdminLinkVisible()
        Assert.assertTrue(this.currentPage.isElementPresent(this.currentPage.adminLink));
        Assert.assertTrue(this.currentPage.isElementPresent(this.currentPage.aboutLink));

        this.currentPage.logOut();
    }

    /**
     * Login as User, assert that Admin Settings link is not visible
     */
    @Test
    public void loginUserTest()
    {
        this.currentPage.navigateToLoginPage()
            .loginAsUser();

        Assert.assertFalse(this.currentPage.isElementPresent(this.currentPage.adminLink));
        Assert.assertTrue(this.currentPage.isElementPresent(this.currentPage.aboutLink));

        this.currentPage.logOut();
    }

    /**
     * Test for invalid credentials. This implicitly tests for it as we should have stayed on the login page rather than
     * being redirected to the homepage. If we got logged in at any point, test fails as it cannot login again without
     * having logging out.
     */
    @Test
    public void invalidCredentials()
    {
        this.currentPage.navigateToLoginPage()
            .loginAs("TestUser1Uno", "BadPassword"); // Valid username but invalid password.
        this.aLoginPage.loginAs("", "123"); // Empty username
        this.aLoginPage.loginAs("SomeoneLikeYou", ""); // Empty password

        this.aLoginPage.loginAsAdmin().logOut();
    }
}
