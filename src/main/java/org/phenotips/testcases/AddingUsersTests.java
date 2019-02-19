package org.phenotips.testcases;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import org.phenotips.pageobjects.HomePage;
import org.phenotips.pageobjects.UserSignUpPage;
import net.bytebuddy.utility.RandomString;

/**
 * Test cases for adding users to the instance. These tests can be run individually, or as a class.
 * There are functional tests for ensuring the user approval workflow and ensuring that the input fields on
 * the "Request Account" form have error checking.
 */
public class AddingUsersTests extends BaseTest
{
    final HomePage aHomePage = new HomePage(theDriver);

    final UserSignUpPage aUserSignUpPage = new UserSignUpPage(theDriver);

    // Strings of messages to verify:

    List<String> loSectionTitlesCheck = new ArrayList<>(Arrays.asList("MY MATCHES", "MY PATIENTS\n ",
        "PATIENTS SHARED WITH ME\n ", "MY GROUPS\n ", "PUBLIC DATA\n "));

    final String confirmationMessageCheck = "Thank you for your interest in PhenomeCentral. We took note of your request and we will process it shortly.";

    final String pendingApprovalMessageCheck = "Please wait for your account to be approved. Thank you.";

    // Common Strings for creation of patients:

    final String randomChars = RandomString.make(5);

    final String password = "123456";

    final String affiliation = "someaffilation";

    final String referrer = "some referrer";

    final String justification = "some reason";


    // Adds a user through the admin's Users page. Approve the user and login and ensure that dashboard widgets (i.e.
    // My Patients, My Families, etc. are visible.
    @Test
    public void adminAddAndApproveUser()
    {
        aHomePage.navigateToLoginPage().loginAsAdmin()
            .navigateToAdminSettingsPage().navigateToAdminUsersPage()
            .addUser("AutoAdded1" + randomChars, "AutoAdded1Last", password,
                "AutoAdded1" + randomChars + "@somethingsomething.cjasdfj", affiliation, referrer, justification)
            .navigateToPendingUsersPage()
            .approveNthPendingUser(1);

        aHomePage.logOut()
            .loginAs("AutoAdded1" + randomChars + "AutoAdded1Last", password)
            .navigateToHomePage();

        Assert.assertEquals(aHomePage.getSectionTitles(), loSectionTitlesCheck);

        aHomePage.logOut();
    }

    // User signs up and is pending approval. Asserts that the user sees the Pending Approval message on a few pages.
    @Test
    public void userSignUpNotApproved()
    {
        final String firstname = "PublicSignUp" + randomChars;
        final String lastname = "Auto" + randomChars;

        aHomePage.navigateToSignUpPage()
            .requestAccount(firstname, lastname, password, password,
            firstname + "@akjsjdf.cjsjdfn", affiliation, referrer, justification);

        Assert.assertEquals(aUserSignUpPage.getConfirmationMessage(), confirmationMessageCheck);
        System.out.println("Request Recieved Msg: " + aUserSignUpPage.getConfirmationMessage());

        aHomePage.navigateToLoginPage()
            .loginAs(firstname + lastname, password);

        System.out.println("Approval Pending Msg: " + aHomePage.getApprovalPendingMessage());
        Assert.assertEquals(aHomePage.getApprovalPendingMessage(), pendingApprovalMessageCheck);
        Assert.assertEquals(aHomePage.navigateToAllPatientsPage().getApprovalPendingMessage(), pendingApprovalMessageCheck);
        Assert.assertEquals(aHomePage.navigateToCreateANewPatientPage().getApprovalPendingMessage(), pendingApprovalMessageCheck);

        aHomePage.logOut();

    }

    // User signs up via public "Request An Account" form, approve account, and then login to ensure that dashboard widgets
    // Ex. Patients, Families sections are visible.
    @Test
    public void userSignUpApproved()
    {
        String firstName = "PublicSignUpApproved" + randomChars;
        String lastName = "Auto" + randomChars;
        String username = firstName + lastName;


        aHomePage.navigateToSignUpPage()
            .requestAccount(firstName , lastName, password, password,
                firstName + "@akjsjdf.cjsjdfn", affiliation, referrer, justification);

        Assert.assertEquals(aUserSignUpPage.getConfirmationMessage(), confirmationMessageCheck);

        aHomePage.navigateToLoginPage()
            .loginAs(username, password);

        System.out.println("Approval Pending Msg: " + aHomePage.getApprovalPendingMessage());
        Assert.assertEquals(aHomePage.getApprovalPendingMessage(), pendingApprovalMessageCheck);

        aHomePage.logOut();
        aHomePage.navigateToLoginPage()
            .loginAsAdmin()
            .navigateToAdminSettingsPage()
            .navigateToPendingUsersPage()
            .approvePendingUser(username)
            .logOut()
            .loginAs(username, password)
            .navigateToHomePage();

        System.out.println("Titles Found: " + aHomePage.getSectionTitles());
        Assert.assertEquals(aHomePage.getSectionTitles(), loSectionTitlesCheck);

    }

    // Error check the input fields, ensures that a request is not sent if any one of the required fields are missing or invalid.
    // This test keeps trying to create the user on the same request form, so it will fail if there was no error check and we
    // navigate away from that page.
    @Test
    public void errorCheckFields()
    {
        aHomePage.navigateToSignUpPage()
            .requestAccount("PublicDupe" + randomChars , "Duped" + randomChars, "123456", "123456",
                "PublicSignUpDupe" + randomChars + "@something.something", "none",
                "Test server", "Some reason");

        // Duplicate Username
        aHomePage.navigateToHomePage()
            .navigateToSignUpPage()
            .requestAccount("PublicDupe" + randomChars , "Duped" + randomChars, "123456", "123456",
                "PublicSignUpDupe" + randomChars + "@something.something", "none",
                "Test server", "Some reason")

            // Mismatching passwords
            .requestAccount("MisMatched PassWord" + randomChars, "Mismatch", "123456", "123456 ",
        "PublicSignUpDupe" + randomChars + "@something.something", "none",
        "Test server", "Some reason")

            // Invalid Email
            .requestAccount("Invalid Email" + randomChars, "Invalidated", "123456", "123456",
                "PublicSignUpDupe" + randomChars + "@NoExtension", "none",
                "Test server", "Some reason")

            // Lacking first name
            .requestAccount("", "First Name Missing", "123456", "123456",
                "PublicSignUpDupe" + randomChars + "@something.something", "none",
                "Test server", "Some reason")

            // Lacking last name
            .requestAccount("LastName Missing", "", "123456", "123456",
                "PublicSignUpDupe" + randomChars + "@something.something", "none",
                "Test server", "Some reason")

            // Lacking password
            .requestAccount("MisMatched PassWord" + randomChars, "Mismatch", "", "",
                "PublicSignUpDupe" + randomChars + "@something.something", "none",
                "Test server", "Some reason")

            // Lacking email
            .requestAccount("Email Missing", "Missings", "123456", "123456",
                "", "none",
                "Test server", "Some reason")

            // Lack of Affiliation
            .requestAccount("No Affiliation" + randomChars, "Reasoning", "123456", "123456",
                "PublicSignUpDupe" + randomChars + "@something.something", "",
                "Test server", "Some reason")
            .cancelRequestingAccount()
            .navigateToLoginPage();
    }

}
