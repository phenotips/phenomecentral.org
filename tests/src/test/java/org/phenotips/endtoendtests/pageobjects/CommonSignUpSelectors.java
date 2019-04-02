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
package org.phenotips.endtoendtests.pageobjects;

import org.openqa.selenium.By;

/**
 * This interface contains selectors that are used on a sign up page. These appear on both the sign up page that a user
 * sees and when an Admin tries to add a user manually. i.e. http://localhost:8083/register/PhenomeCentral/WebHome and
 * http://localhost:8083/admin/XWiki/XWikiPreferences?editor=globaladmin&section=Users#
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
