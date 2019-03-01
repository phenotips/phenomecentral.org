package org.phenotips.endtoendtests;

import org.phenotips.endtoendtests.pageobjects.HomePage;
import org.phenotips.endtoendtests.testcases.BaseTest;

import org.testng.annotations.Test;

/**
 * This class sets up the state of the PC instance for testing purposes. Notably, it creates the two main users that all
 * other tests will be using, along with setting an outgoing email port corresponding to the one that the fake SMTP
 * service is listening to.
 */
public class SetupUsers extends BaseTest
{
    HomePage aHomePage = new HomePage(theDriver);

    @Test()
    public void setEmailPort()
    {
        aHomePage.navigateToLoginPage()
            .loginAsAdmin()
            .navigateToAdminSettingsPage()
            .navigateToMailSendingSettingsPage()
            .setEmailPort(1025)
            .navigateToHomePage()
            .logOut();
    }

    // Creates the two users used by the automation.
    @Test()
    public void setupAutomationUsers()
    {
        aHomePage.navigateToLoginPage()
            .loginAsAdmin()
            .navigateToAdminSettingsPage()
            .navigateToAdminUsersPage()
            .addUser("TestUser1", "Uno", "123456",
                "testuser1uno@jksjfljsdlfj.caksjdfjlkg", "none",
                "Test server", "Some reason")
            .addUser("TestUser2", "Dos", "123456",
                "testuser2dos@kljaskljdfljlfd.casdfjjg", "none",
                "Test server", "Some reason")
            .navigateToPendingUsersPage()
            .approvePendingUser("TestUser1Uno")
            .approvePendingUser("TestUser2Dos")
            .logOut()
            .loginAsUser()
            .logOut()
            .loginAsUserTwo()
            .logOut();
        System.out.println("Created Two users for automation");
    }
}
