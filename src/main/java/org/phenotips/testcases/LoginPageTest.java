package org.phenotips.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import org.phenotips.pageobjects.HomePage;

/**
 * Test the login functionality.
 *
 * Each test can be run separately.
 */
public class LoginPageTest extends BaseTest
{
    HomePage currentPage = new HomePage(theDriver);

    /**
     * Login as Admin, assert that both Administrator Settings link and About link are visible
      */
    @Test
    public void loginAdminTest()
    {
        currentPage.navigateToLoginPage()
            .loginAsAdmin();

        // TODO: maybe we should avoid putting a selector here at all
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
}
