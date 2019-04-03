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

import org.phenotips.endtoendtests.common.CommonInfoEnums;
import org.phenotips.endtoendtests.pageobjects.AdminMatchNotificationPage;
import org.phenotips.endtoendtests.pageobjects.AdminRefreshMatchesPage;
import org.phenotips.endtoendtests.pageobjects.HomePage;
import org.phenotips.endtoendtests.pageobjects.ViewPatientPage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * This class tests that when permissions are modified, patients matching behaviour is modified. These tests must be run
 * as a class.
 */
public class PermissionsTests extends BaseTest
{
    final private String randomChars = RandomStringUtils.randomAlphanumeric(5);

    HomePage aHomePage = new HomePage(theDriver);

    ViewPatientPage aViewPatientPage = new ViewPatientPage(theDriver);

    AdminRefreshMatchesPage anAdminRefreshMatchesPage = new AdminRefreshMatchesPage(theDriver);

    AdminMatchNotificationPage anAdminMatchNotificationPage = new AdminMatchNotificationPage(theDriver);

    /**
     * Creates two patients with identical phenotypes and genotypes. One is "Private" and the other is "Matchable".
     * Assert that: - 1 Patients Processed during refresh of matches - No match is found on Admin's match page
     */
    @Test()
    public void noMatchPrivatePatient()
    {
        List<String> loPhenotypesToAdd = new ArrayList<>(Arrays.asList(
            "Perimembranous ventricular septal defect", "Postaxial polysyndactyly of foot",
            "Delayed ability to stand"));

        final String patientUniqueIdentifier = "NoPermissionForMatch " + this.randomChars;

        String createdPatient1;
        String createdPatient2;

        this.aHomePage.navigateToLoginPage()
            .loginAsAdmin()
            .navigateToAdminSettingsPage()
            .navigateToRefreshMatchesPage()
            .refreshMatchesSinceLastUpdate() // Refresh Matches so that "Since last update" goes to 0 first
            .logOut()

            .loginAsUser()
            .navigateToCreateANewPatientPage()
            .toggleDefaultUncheckedConsentBoxes()
            .updateConsent()
            .setIdentifier(patientUniqueIdentifier)
            .setDOB("01", "2001")
            .setGender("Female")
            .expandSection(CommonInfoEnums.SECTIONS.ClinicalSymptomsSection)
            .addPhenotypes(loPhenotypesToAdd)
            .expandSection(CommonInfoEnums.SECTIONS.ClinicalSymptomsSection)
            .expandSection(CommonInfoEnums.SECTIONS.GenotypeInfoSection)
            .addGene("CEP85", "Confirmed causal", "Sequencing")
            .saveAndViewSummary();

        this.aViewPatientPage.setGlobalVisibility("Private");

        createdPatient1 = this.aViewPatientPage.getPatientID();

        this.aViewPatientPage.logOut()
            .loginAsUserTwo()
            .navigateToCreateANewPatientPage()
            .toggleDefaultUncheckedConsentBoxes()
            .updateConsent()
            .setIdentifier(patientUniqueIdentifier + "Matchee")
            .setDOB("05", "2005")
            .setGender("Male")
            .expandSection(CommonInfoEnums.SECTIONS.ClinicalSymptomsSection)
            .addPhenotypes(loPhenotypesToAdd)
            .expandSection(CommonInfoEnums.SECTIONS.ClinicalSymptomsSection)
            .expandSection(CommonInfoEnums.SECTIONS.GenotypeInfoSection)
            .addGene("CEP85", "Confirmed causal", "Sequencing")
            .saveAndViewSummary();

        createdPatient2 = this.aViewPatientPage.getPatientID();

        this.aViewPatientPage.logOut()
            .loginAsAdmin()
            .navigateToAdminSettingsPage()
            .navigateToRefreshMatchesPage()
            .refreshMatchesSinceLastUpdate();

        Assert.assertEquals(this.anAdminRefreshMatchesPage.getNumberOfLocalPatientsProcessed(), "1");

        this.anAdminRefreshMatchesPage.navigateToAdminSettingsPage()
            .navigateToMatchingNotificationPage()
            .filterByID(createdPatient1);

        Assert.assertFalse(this.anAdminMatchNotificationPage.doesMatchExist(createdPatient1, createdPatient2));

        this.anAdminRefreshMatchesPage.logOut();
    }

    /**
     * Ensure that the matchable patient created by User2Dos cannot be seen by User1Uno. Asserts that the unauthorized
     * action error message page is presented.
     */
    @Test()
    public void cannotSeeOtherPatients()
    {
        String unauthorizedActionMsgCheck = "You are not allowed to view this page or perform this action.";

        this.aHomePage.navigateToLoginPage()
            .loginAsUserTwo()
            .navigateToAllPatientsPage()
            .sortPatientsDateDesc()
            .viewFirstPatientInTable()
            .logOut()
            .loginAsUser();

        Assert.assertEquals(this.aHomePage.getUnauthorizedErrorMessage(), unauthorizedActionMsgCheck);

        this.anAdminRefreshMatchesPage.logOut();
    }
}
