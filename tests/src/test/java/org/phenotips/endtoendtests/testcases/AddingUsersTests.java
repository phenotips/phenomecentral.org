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

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test cases for adding users to the instance. These tests can be run individually, or as a class. There are functional
 * tests for ensuring the user approval workflow and ensuring that the input fields on the "Request Account" form have
 * error checking.
 */
public class AddingUsersTests extends BaseTest
{
    // Strings of messages to verify:

    private static final String CONFIRMATION_MESSAGE_CHECK =
        "Thank you for your interest in PhenomeCentral. We took note of your request and we will process it shortly.";

    private static final String PENDING_APPROVAL_MESSAGE_CHECK =
        "Please wait for your account to be approved. Thank you.";

    private static final String RANDOM_CHARS = RandomStringUtils.randomAlphanumeric(5);

    // Common Strings for creation of patients:

    private static final String PASSWORD = "123456";

    private static final String AFFILIATION = "someaffilation";

    private static final String REFERRER = "some referrer";

    private static final String JUSTIFICATION = "some reason";

    private static final List<String> loSectionTitlesCheck =
        new ArrayList<>(Arrays.asList("MY MATCHES", "MY PATIENTS\n ",
            "PATIENTS SHARED WITH ME\n ", "MY GROUPS\n ", "PUBLIC DATA\n "));

    private final HomePage aHomePage = new HomePage(theDriver);

    private final UserSignUpPage aUserSignUpPage = new UserSignUpPage(theDriver);

    // Adds a user through the admin's Users page. Approve the user and login and ensure that dashboard widgets (i.e.
    // My Patients, My Families, etc. are visible.
    @Test
    public void adminAddAndApproveUser()
    {
        this.aHomePage.navigateToLoginPage().loginAsAdmin()
            .navigateToAdminSettingsPage().navigateToAdminUsersPage()
            .addUser("AutoAdded1" + RANDOM_CHARS, "AutoAdded1Last", PASSWORD,
                "AutoAdded1" + RANDOM_CHARS + "@somethingsomething.cjasdfj", AFFILIATION, REFERRER, JUSTIFICATION)
            .navigateToPendingUsersPage()
            .approvePendingUser("AutoAdded1");

        this.aHomePage.logOut()
            .loginAs("AutoAdded1" + RANDOM_CHARS + "AutoAdded1Last", PASSWORD)
            .navigateToHomePage();

        Assert.assertEquals(this.aHomePage.getSectionTitles(), loSectionTitlesCheck);

        this.aHomePage.logOut();
    }

    // User signs up and is pending approval. Asserts that the user sees the Pending Approval message on a few pages.
    @Test
    public void userSignUpNotApproved()
    {
        final String firstname = "PublicSignUp" + RANDOM_CHARS;
        final String lastname = "Auto" + RANDOM_CHARS;

        this.aHomePage.navigateToSignUpPage()
            .requestAccount(firstname, lastname, PASSWORD, PASSWORD, firstname + "@akjsjdf.cjsjdfn", AFFILIATION,
                REFERRER, JUSTIFICATION);

        Assert.assertEquals(this.aUserSignUpPage.getConfirmationMessage(), CONFIRMATION_MESSAGE_CHECK);
        System.out.println("Request Received Msg: " + this.aUserSignUpPage.getConfirmationMessage());

        this.aHomePage.navigateToLoginPage().loginAs(firstname + lastname, PASSWORD);

        System.out.println("Approval Pending Msg: " + this.aHomePage.getApprovalPendingMessage());
        Assert.assertEquals(this.aHomePage.getApprovalPendingMessage(), PENDING_APPROVAL_MESSAGE_CHECK);
        Assert.assertEquals(this.aHomePage.navigateToAllPatientsPage().getApprovalPendingMessage(),
            PENDING_APPROVAL_MESSAGE_CHECK);
        Assert.assertEquals(this.aHomePage.navigateToCreateANewPatientPage().getApprovalPendingMessage(),
            PENDING_APPROVAL_MESSAGE_CHECK);

        this.aHomePage.logOut();
    }

    // User signs up via public "Request An Account" form, approve account, and then login to ensure that dashboard
    // widgets
    // Ex. Patients, Families sections are visible.
    @Test
    public void userSignUpApproved()
    {
        String firstName = "PublicSignUpApproved" + RANDOM_CHARS;
        String lastName = "Auto" + RANDOM_CHARS;
        String username = firstName + lastName;

        this.aHomePage.navigateToSignUpPage().requestAccount(firstName, lastName, PASSWORD, PASSWORD,
            firstName + "@akjsjdf.cjsjdfn", AFFILIATION, REFERRER, JUSTIFICATION);

        Assert.assertEquals(this.aUserSignUpPage.getConfirmationMessage(), CONFIRMATION_MESSAGE_CHECK);

        this.aHomePage.navigateToLoginPage().loginAs(username, PASSWORD);

        System.out.println("Approval Pending Msg: " + this.aHomePage.getApprovalPendingMessage());
        Assert.assertEquals(this.aHomePage.getApprovalPendingMessage(), PENDING_APPROVAL_MESSAGE_CHECK);

        this.aHomePage.logOut();
        this.aHomePage.navigateToLoginPage()
            .loginAsAdmin()
            .navigateToAdminSettingsPage()
            .navigateToPendingUsersPage()
            .approvePendingUser(username)
            .logOut()
            .loginAs(username, PASSWORD)
            .navigateToHomePage();

        System.out.println("Titles Found: " + this.aHomePage.getSectionTitles());
        Assert.assertEquals(this.aHomePage.getSectionTitles(), loSectionTitlesCheck);
    }

    // Error check the input fields, ensures that a request is not sent if any one of the required fields are missing or
    // invalid.
    // This test keeps trying to create the user on the same request form, so it will fail if there was no error check
    // and we
    // navigate away from that page.
    @Test
    public void errorCheckFields()
    {
        this.aHomePage.navigateToSignUpPage()
            .requestAccount("PublicDupe" + RANDOM_CHARS, "Duped" + RANDOM_CHARS,
                "123456", "123456",
                "PublicSignUpDupe" + RANDOM_CHARS + "@something.something", "none",
                "Test server", "Some reason");

        // Duplicate Username
        this.aHomePage.navigateToHomePage()
            .navigateToSignUpPage()
            .requestAccount("PublicDupe" + RANDOM_CHARS, "Duped" + RANDOM_CHARS,
                "123456", "123456",
                "PublicSignUpDupe" + RANDOM_CHARS + "@something.something", "none",
                "Test server", "Some reason")

            // Mismatching passwords
            .requestAccount("MisMatched PassWord" + RANDOM_CHARS, "Mismatch", "123456", "123456 ",
                "PublicSignUpDupe" + RANDOM_CHARS + "@something.something", "none",
                "Test server", "Some reason")

            // Invalid Email
            .requestAccount("Invalid Email" + RANDOM_CHARS, "Invalidated", "123456", "123456",
                "PublicSignUpDupe" + RANDOM_CHARS + "@NoExtension", "none",
                "Test server", "Some reason")

            // Lacking first name
            .requestAccount("", "First Name Missing", "123456", "123456",
                "PublicSignUpDupe" + RANDOM_CHARS + "@something.something", "none",
                "Test server", "Some reason")

            // Lacking last name
            .requestAccount("LastName Missing", "", "123456", "123456",
                "PublicSignUpDupe" + RANDOM_CHARS + "@something.something", "none",
                "Test server", "Some reason")

            // Lacking password
            .requestAccount("MisMatched PassWord" + RANDOM_CHARS, "Mismatch", "", "",
                "PublicSignUpDupe" + RANDOM_CHARS + "@something.something", "none",
                "Test server", "Some reason")

            // Lacking email
            .requestAccount("Email Missing", "Missings", "123456", "123456",
                "", "none",
                "Test server", "Some reason")

            // Lack of Affiliation
            .requestAccount("No Affiliation" + RANDOM_CHARS, "Reasoning", "123456", "123456",
                "PublicSignUpDupe" + RANDOM_CHARS + "@something.something", "",
                "Test server", "Some reason")
            .cancelRequestingAccount()
            .navigateToLoginPage();
    }
}
