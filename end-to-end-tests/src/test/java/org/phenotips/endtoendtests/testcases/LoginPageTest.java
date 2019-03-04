package org.phenotips.endtoendtests.testcases;

import org.phenotips.endtoendtests.pageobjects.HomePage;
import org.phenotips.endtoendtests.pageobjects.LoginPage;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test the login functionality.
 *
 * Each test can be run separately.
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
        currentPage.navigateToLoginPage()
            .loginAsAdmin();

        // This is a basic test so we touch a selector directly.
        //  But for all other tests, don't touch selectors directly - abstract the action out to a method in a pageObject.
        //  Ex. isAdminLinkVisible()
        Assert.assertTrue(currentPage.isElementPresent(currentPage.adminLink));
        Assert.assertTrue(currentPage.isElementPresent(currentPage.aboutLink));

        currentPage.logOut();
    }

    /**
     * Login as User, assert that Admin Settings link is not visible
     */
    @Test
    public void loginUserTest()
    {
        currentPage.navigateToLoginPage()
            .loginAsUser();

        Assert.assertFalse(currentPage.isElementPresent(currentPage.adminLink));
        Assert.assertTrue(currentPage.isElementPresent(currentPage.aboutLink));

        currentPage.logOut();
    }

    /**
     * Test for invalid credentials. This implicitly tests for it as we should have stayed on the login page rather than
     * being redirected to the homepage. If we got logged in at any point, test fails as it cannot login again without
     * having logging out.
     */
    @Test
    public void invalidCredentials()
    {
        currentPage.navigateToLoginPage()
            .loginAs("TestUser1Uno", "BadPassword"); // Valid username but invalid password.
        aLoginPage.loginAs("", "123"); // Empty username
        aLoginPage.loginAs("SomeoneLikeYou", ""); // Empty password

        aLoginPage.loginAsAdmin().logOut();
    }
}
