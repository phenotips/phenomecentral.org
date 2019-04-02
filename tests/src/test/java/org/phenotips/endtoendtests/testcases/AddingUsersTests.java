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
import org.phenotips.endtoendtests.pageobjects.UserSignUpPage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import net.bytebuddy.utility.RandomString;

/**
 * Test cases for adding users to the instance. These tests can be run individually, or as a class. There are functional
 * tests for ensuring the user approval workflow and ensuring that the input fields on the "Request Account" form have
 * error checking.
 */
public class AddingUsersTests extends BaseTest
{
    final HomePage aHomePage = new HomePage(theDriver);

    final UserSignUpPage aUserSignUpPage = new UserSignUpPage(theDriver);

    // Strings of messages to verify:

    final String confirmationMessageCheck =
        "Thank you for your interest in PhenomeCentral. We took note of your request and we will process it shortly.";

    final String pendingApprovalMessageCheck = "Please wait for your account to be approved. Thank you.";

    final String randomChars = RandomString.make(5);

    // Common Strings for creation of patients:

    final String password = "123456";

    final String affiliation = "someaffilation";

    final String referrer = "some referrer";

    final String justification = "some reason";

    List<String> loSectionTitlesCheck = new ArrayList<>(Arrays.asList("MY MATCHES", "MY PATIENTS\n ",
        "PATIENTS SHARED WITH ME\n ", "MY GROUPS\n ", "PUBLIC DATA\n "));

    // Adds a user through the admin's Users page. Approve the user and login and ensure that dashboard widgets (i.e.
    // My Patients, My Families, etc. are visible.
    @Test
    public void adminAddAndApproveUser()
    {
        this.aHomePage.navigateToLoginPage().loginAsAdmin()
            .navigateToAdminSettingsPage().navigateToAdminUsersPage()
            .addUser("AutoAdded1" + this.randomChars, "AutoAdded1Last", this.password,
                "AutoAdded1" + this.randomChars + "@somethingsomething.cjasdfj", this.affiliation, this.referrer,
                this.justification)
            .navigateToPendingUsersPage()
            .approveNthPendingUser(1);

        this.aHomePage.logOut()
            .loginAs("AutoAdded1" + this.randomChars + "AutoAdded1Last", this.password)
            .navigateToHomePage();

        Assert.assertEquals(this.aHomePage.getSectionTitles(), this.loSectionTitlesCheck);

        this.aHomePage.logOut();
    }

    // User signs up and is pending approval. Asserts that the user sees the Pending Approval message on a few pages.
    @Test
    public void userSignUpNotApproved()
    {
        final String firstname = "PublicSignUp" + this.randomChars;
        final String lastname = "Auto" + this.randomChars;

        this.aHomePage.navigateToSignUpPage()
            .requestAccount(firstname, lastname, this.password, this.password,
                firstname + "@akjsjdf.cjsjdfn", this.affiliation, this.referrer, this.justification);

        Assert.assertEquals(this.aUserSignUpPage.getConfirmationMessage(), this.confirmationMessageCheck);
        System.out.println("Request Recieved Msg: " + this.aUserSignUpPage.getConfirmationMessage());

        this.aHomePage.navigateToLoginPage()
            .loginAs(firstname + lastname, this.password);

        System.out.println("Approval Pending Msg: " + this.aHomePage.getApprovalPendingMessage());
        Assert.assertEquals(this.aHomePage.getApprovalPendingMessage(), this.pendingApprovalMessageCheck);
        Assert.assertEquals(this.aHomePage.navigateToAllPatientsPage().getApprovalPendingMessage(),
            this.pendingApprovalMessageCheck);
        Assert.assertEquals(this.aHomePage.navigateToCreateANewPatientPage().getApprovalPendingMessage(),
            this.pendingApprovalMessageCheck);

        this.aHomePage.logOut();
    }

    // User signs up via public "Request An Account" form, approve account, and then login to ensure that dashboard
    // widgets
    // Ex. Patients, Families sections are visible.
    @Test
    public void userSignUpApproved()
    {
        String firstName = "PublicSignUpApproved" + this.randomChars;
        String lastName = "Auto" + this.randomChars;
        String username = firstName + lastName;

        this.aHomePage.navigateToSignUpPage()
            .requestAccount(firstName, lastName, this.password, this.password,
                firstName + "@akjsjdf.cjsjdfn", this.affiliation, this.referrer, this.justification);

        Assert.assertEquals(this.aUserSignUpPage.getConfirmationMessage(), this.confirmationMessageCheck);

        this.aHomePage.navigateToLoginPage()
            .loginAs(username, this.password);

        System.out.println("Approval Pending Msg: " + this.aHomePage.getApprovalPendingMessage());
        Assert.assertEquals(this.aHomePage.getApprovalPendingMessage(), this.pendingApprovalMessageCheck);

        this.aHomePage.logOut();
        this.aHomePage.navigateToLoginPage()
            .loginAsAdmin()
            .navigateToAdminSettingsPage()
            .navigateToPendingUsersPage()
            .approvePendingUser(username)
            .logOut()
            .loginAs(username, this.password)
            .navigateToHomePage();

        System.out.println("Titles Found: " + this.aHomePage.getSectionTitles());
        Assert.assertEquals(this.aHomePage.getSectionTitles(), this.loSectionTitlesCheck);
    }

    // Error check the input fields, ensures that a request is not sent if any one of the required fields are missing or invalid.
    // This test keeps trying to create the user on the same request form, so it will fail if there was no error check and we
    // navigate away from that page.
    @Test
    public void errorCheckFields()
    {
        this.aHomePage.navigateToSignUpPage()
            .requestAccount("PublicDupe" + this.randomChars, "Duped" + this.randomChars, "123456", "123456",
                "PublicSignUpDupe" + this.randomChars + "@something.something", "none",
                "Test server", "Some reason");

        // Duplicate Username
        this.aHomePage.navigateToHomePage()
            .navigateToSignUpPage()
            .requestAccount("PublicDupe" + this.randomChars, "Duped" + this.randomChars, "123456", "123456",
                "PublicSignUpDupe" + this.randomChars + "@something.something", "none",
                "Test server", "Some reason")

            // Mismatching passwords
            .requestAccount("MisMatched PassWord" + this.randomChars, "Mismatch", "123456", "123456 ",
                "PublicSignUpDupe" + this.randomChars + "@something.something", "none",
                "Test server", "Some reason")

            // Invalid Email
            .requestAccount("Invalid Email" + this.randomChars, "Invalidated", "123456", "123456",
                "PublicSignUpDupe" + this.randomChars + "@NoExtension", "none",
                "Test server", "Some reason")

            // Lacking first name
            .requestAccount("", "First Name Missing", "123456", "123456",
                "PublicSignUpDupe" + this.randomChars + "@something.something", "none",
                "Test server", "Some reason")

            // Lacking last name
            .requestAccount("LastName Missing", "", "123456", "123456",
                "PublicSignUpDupe" + this.randomChars + "@something.something", "none",
                "Test server", "Some reason")

            // Lacking password
            .requestAccount("MisMatched PassWord" + this.randomChars, "Mismatch", "", "",
                "PublicSignUpDupe" + this.randomChars + "@something.something", "none",
                "Test server", "Some reason")

            // Lacking email
            .requestAccount("Email Missing", "Missings", "123456", "123456",
                "", "none",
                "Test server", "Some reason")

            // Lack of Affiliation
            .requestAccount("No Affiliation" + this.randomChars, "Reasoning", "123456", "123456",
                "PublicSignUpDupe" + this.randomChars + "@something.something", "",
                "Test server", "Some reason")
            .cancelRequestingAccount()
            .navigateToLoginPage();
    }
}
