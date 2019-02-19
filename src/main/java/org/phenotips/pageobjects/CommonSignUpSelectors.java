package org.phenotips.pageobjects;

import org.openqa.selenium.By;

/**
 * This interface contains selectors that are used on a sign up page. These appear on both the sign up page that a
 * user sees and when an Admin tries to add a user manually.
 * i.e. http://localhost:8083/register/PhenomeCentral/WebHome and
 *      http://localhost:8083/admin/XWiki/XWikiPreferences?editor=globaladmin&section=Users#
 */
public interface CommonSignUpSelectors
{
    By newUserBtn = By.id("addNewUser");
    By firstNameBox = By.id("register_first_name");
    By lastNameBox = By.id("register_last_name");
    By userNameBox = By.id("xwikiname");
    By passwordBox = By.id("register_password");
    By confirmPasswordBox = By.id("register2_password");
    By emailBox = By.id("register_email");
    By affiliationBox = By.id("register_affiliation");
    By referralBox = By.id("register_referral"); // "How did you hear about / Who referred you to PhenomeCentral?"
    By reasoningBox = By.id("register_comment"); // Why are you requesting access to PhenomeCentral?

    // Checkboxes
    By professionalCheckbox = By.id("confirmation_clinician");
    By liabilityCheckbox = By.id("confirmation_research");
    By nonIdentificationCheckbox = By.id("confirmation_identify");
    By cooperationCheckbox = By.id("confirmation_publication");
    By acknoledgementCheckbox = By.id("confirmation_funding");

}
