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
        this.aHomePage.navigateToLoginPage()
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
        this.aHomePage.navigateToLoginPage()
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
