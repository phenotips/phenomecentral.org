package TestCases;

import org.testng.annotations.Test;

import PageObjects.HomePage;

/**
 * This class will contain tests for the match notification table. It will ensure the appropriate
 * patients are present. They show up when needed, for instance.
 */
public class MatchNotificationPageTests extends BaseTest
{
    HomePage aHomePage = new HomePage(theDriver);

    @Test
    public void filterAndEmailTemp()
    {
        aHomePage.navigateToLoginPage()
            .loginAsAdmin()
            .navigateToAdminSettingsPage()
            .navigateToMatchingNotificationPage()
            .toggleContactedStatusCheckbox()
            .emailSpecificPatients("P0000001", "P0000002 : Auto YnoP6 Patient");
        aHomePage.unconditionalWaitNs(15);
    }
}
