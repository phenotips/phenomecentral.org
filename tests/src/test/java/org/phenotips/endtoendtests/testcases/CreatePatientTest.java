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
import org.phenotips.endtoendtests.common.PatientMeasurementEntry;
import org.phenotips.endtoendtests.pageobjects.HomePage;
import org.phenotips.endtoendtests.pageobjects.PatientRecordEditPage;
import org.phenotips.endtoendtests.pageobjects.ViewPatientPage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test patient creation and data entry to patient form. No matching is being tested.
 */
public class CreatePatientTest extends BaseTest implements CommonInfoEnums
{
    final private HomePage aHomePage = new HomePage();

    final private ViewPatientPage aViewPatientPage = new ViewPatientPage();

    final private PatientRecordEditPage aPatientRecordEditPage = new PatientRecordEditPage();

    final private SECTIONS[] checkForTheseSections = {
        SECTIONS.PatientInfoSection,
        SECTIONS.ClinicalSymptomsSection,
        SECTIONS.SuggestedGenesSection,
        SECTIONS.GenotypeInfoSection,
        SECTIONS.SimilarCasesSection
    };

    final private String randomChars = RandomStringUtils.randomAlphanumeric(5);

    final private String patientUniqueIdentifier = "Auto " + this.randomChars + " Patient";

    final private String JSONToImport =
        "[{\"allergies\":[],\"date\":\"2019-01-11T17:26:01.000Z\",\"apgar\":{},\"notes\":{\"family_history\":\"\",\"prenatal_development\":\"\",\"indication_for_referral\":\"\",\"genetic_notes\":\"\",\"medical_history\":\"\",\"diagnosis_notes\":\"\"},\"ethnicity\":{\"maternal_ethnicity\":[],\"paternal_ethnicity\":[]},\"date_of_birth\":{\"month\":3,\"year\":2011},\"global_mode_of_inheritance\":[],\"solved\":{\"status\":\"unsolved\"},\"external_id\":\""
            + this.patientUniqueIdentifier +
            "\",\"variants\":[],\"clinicalStatus\":\"affected\",\"disorders\":[],\"features\":[{\"id\":\"HP:0000385\",\"label\":\"Small earlobe\",\"type\":\"phenotype\",\"observed\":\"yes\"},{\"id\":\"HP:0000505\",\"label\":\"Visual impairment\",\"type\":\"phenotype\",\"observed\":\"yes\"},{\"id\":\"HP:0000618\",\"label\":\"Blindness\",\"type\":\"phenotype\",\"observed\":\"yes\"},{\"id\":\"HP:0001250\",\"label\":\"Seizures\",\"type\":\"phenotype\",\"observed\":\"yes\"},{\"id\":\"HP:0002121\",\"label\":\"Absence seizures\",\"type\":\"phenotype\",\"observed\":\"yes\"},{\"id\":\"HP:0006266\",\"label\":\"Small placenta\",\"type\":\"phenotype\",\"observed\":\"yes\"},{\"id\":\"HP:0007875\",\"label\":\"Congenital blindness\",\"type\":\"phenotype\",\"observed\":\"yes\"},{\"id\":\"HP:0011146\",\"label\":\"Dialeptic seizures\",\"type\":\"phenotype\",\"observed\":\"yes\"},{\"id\":\"HP:0011147\",\"label\":\"Typical absence seizures\",\"type\":\"phenotype\",\"observed\":\"yes\"},{\"id\":\"HP:0200055\",\"label\":\"Small hand\",\"type\":\"phenotype\",\"observed\":\"yes\"}],\"date_of_death\":\"\",\"last_modification_date\":\"2019-01-11T17:31:13.000Z\",\"nonstandard_features\":[],\"prenatal_perinatal_history\":{\"multipleGestation\":null,\"icsi\":null,\"ivf\":null,\"assistedReproduction_donoregg\":null,\"assistedReproduction_iui\":null,\"twinNumber\":\"\",\"assistedReproduction_fertilityMeds\":null,\"gestation\":null,\"assistedReproduction_surrogacy\":null,\"assistedReproduction_donorsperm\":null},\"family_history\":{\"miscarriages\":null,\"consanguinity\":null,\"affectedRelatives\":null},\"genes\":[{\"gene\":\"PLS1\",\"id\":\"ENSG00000120756\",\"strategy\":[\"sequencing\"],\"status\":\"candidate\"},{\"gene\":\"PLS3\",\"id\":\"ENSG00000102024\",\"strategy\":[\"sequencing\"],\"status\":\"candidate\"},{\"gene\":\"QSOX1\",\"id\":\"ENSG00000116260\",\"strategy\":[\"sequencing\"],\"status\":\"solved\"},{\"gene\":\"TXNL1\",\"id\":\"ENSG00000091164\",\"strategy\":[\"sequencing\"],\"status\":\"carrier\"}],\"life_status\":\"alive\",\"sex\":\"M\",\"clinical-diagnosis\":[],\"reporter\":\"TestUser1Uno\",\"last_modified_by\":\"TestUser1Uno\",\"global_age_of_onset\":[{\"id\":\"HP:0003577\",\"label\":\"Congenital onset\"}],\"report_id\":\"P0000009\",\"medical_reports\":[]}\n"
            +
            "]";

    final private PatientMeasurementEntry fullMeasurement =
        new PatientMeasurementEntry()
            .withWeight(1f)
            .withArmSpan(2f)
            .withHeadCircumference(3f)
            .withOuterCanthalDistance(4f)
            .withLeftHandLength(5f)
            .withRightHandLength(6f)
            .withHeight(7f)
            .withSittingHeight(8f)
            .withPhiltrumLength(9f)
            .withInnercanthalDistance(10f)
            .withLeftPalmLength(11f)
            .withRightPalmLength(12f)
            .withLeftEarLength(13f)
            .withRightEarLength(14f)
            .withPalpebralFissureLength(15f)
            .withLeftFootLength(16f)
            .withRightFootLength(17f)
            .withInterpupilaryDistance(18f);

    final private PatientMeasurementEntry partialMeasurement =
        new PatientMeasurementEntry().withArmSpan(3f)
            .withHeight(88f)
            .withLeftEarLength(3f)
            .withHeadCircumference(9f)
            .withLeftEarLength(3f);

    // Create a patient as User 1 with basic information. Asserts that this info is visible after save.
    @Test
    public void createPatientInformation()
    {
        this.aHomePage.navigateToLoginPage()
            .loginAsUser()
            .navigateToCreateANewPatientPage()
            .toggleDefaultUncheckedConsentBoxes()
            .updateConsent()
            .setIdentifier("Basic Patient Information " + randomChars)
            .setLifeStatus("Deceased")
            .setDOB("03", "1998")
            .setDateOfDeath("08", "2006")
            .setGender("Male")
            .setOnset("Congenital Onset")
            .setIndicationForReferral("Not a text-mining test")
            .saveAndViewSummary();

        Assert.assertEquals(this.aViewPatientPage.getPatientIdentifier(),
            "Basic Patient Information " + randomChars);
        Assert.assertEquals(this.aViewPatientPage.getDateOfBirth(), "03 1998");
        Assert.assertEquals(this.aViewPatientPage.getIndicationForReferral(), "Not a text-mining test");

        this.aViewPatientPage.getPatientID();
        this.aViewPatientPage.logOut();
    }

    // Create a patient as User 1 with various genes and phenotypes.
    // Assert that the required section titles in checkForTheseSections are
    // visible and the genes and phenotypes are present after save.
    @Test
    public void createPatientGenesAndPhenotypes()
    {
        List<String> loPhenotypesToAdd = new ArrayList<>(Arrays.asList(
            "Blindness", "Visual impairment", "Small earlobe", "Small hand", "Absence seizures",
            "Diapleptic Seizures", "Typical absence seizures", "Seizures", "Small placenta"));

        this.aHomePage.navigateToLoginPage()
            .loginAsUser()
            .navigateToCreateANewPatientPage()
            .toggleDefaultUncheckedConsentBoxes()
            .updateConsent()
            .setIdentifier(this.patientUniqueIdentifier)
            .setDOB("02", "2012")
            .setGender("Male")
            .setOnset("Congenital onset ")
            .expandSection(SECTIONS.ClinicalSymptomsSection)
            .addPhenotypes(loPhenotypesToAdd)
            .expandSection(SECTIONS.ClinicalSymptomsSection)
            .expandSection(SECTIONS.GenotypeInfoSection)
            .addGene("PLS1", "Candidate", "Sequencing")
            .addGene("PLS3", "Candidate", "Sequencing")
            .addGene("QSOX1", "Confirmed causal", "Sequencing")
            .addGene("TXNL1", "Carrier", "Sequencing")
            .saveAndViewSummary();

        System.out.println("We just edited: " + this.aViewPatientPage.getPatientID());
        Assert.assertTrue(this.aViewPatientPage.checkForVisibleSections(this.checkForTheseSections));
        Assert.assertEquals(this.aViewPatientPage.getPatientIdentifier(), this.patientUniqueIdentifier);
        Assert.assertEquals(this.aViewPatientPage.getDateOfBirth(), "02 2012");
        Assert.assertEquals(this.aViewPatientPage.getGeneNames(), Arrays.asList("PLS1", "PLS3", "QSOX1", "TXNL1"));

        this.aViewPatientPage.logOut();
    }

    // Creates an identical patient as User 2 via JSON import. Asserts that the section titles are visible.
    // Asserts that information is corroborated after consents are enabled.
    @Test
    public void importingJSONPatient()
    {
        this.aHomePage.navigateToLoginPage()
            .loginAsUserTwo()
            .navigateToAllPatientsPage()
            .importJSONPatient(this.JSONToImport)
            .sortPatientsDateDesc()
            .viewFirstPatientInTable()
            .editThisPatient()
            .toggleDefaultUncheckedConsentBoxes()
            .updateConsent()
            .setIdentifier(this.patientUniqueIdentifier + " JSON")
            .saveAndViewSummary();

        System.out.println("We just edited: " + this.aViewPatientPage.getPatientID());
        Assert.assertTrue(this.aViewPatientPage.checkForVisibleSections(this.checkForTheseSections));
        Assert.assertEquals(this.aViewPatientPage.getPatientIdentifier(),
            this.patientUniqueIdentifier + " JSON");
        Assert.assertEquals(this.aViewPatientPage.getDateOfBirth(), "03 2011");

        this.aViewPatientPage.logOut();
    }

    // User Two Creates a publicly visible patient. Ensure that User 1 is able to view.
    @Test
    public void publicVisibilityTest()
    {
        this.aHomePage.navigateToLoginPage()
            .loginAsUserTwo()
            .navigateToCreateANewPatientPage()
            .toggleDefaultUncheckedConsentBoxes()
            .updateConsent()
            .setIdentifier("Public Visibility " + randomChars)
            .setIndicationForReferral("This test checks that a public patient is visible by another user")
            .saveAndViewSummary();

        String ID1 = this.aViewPatientPage.getPatientID();

        this.aViewPatientPage.setGlobalVisibility("public");

        this.aViewPatientPage.logOut()
            .loginAsUser()
            .navigateToAllPatientsPage()
            .filterByPatientID(ID1)
            .viewFirstPatientInTable();

        String ID2 = this.aViewPatientPage.getPatientID();

        Assert.assertEquals(ID1, ID2);

        this.aViewPatientPage.logOut();
    }

    // Adds a collaborator to a patient belong to User 2. Asserts that User 1 can then access it.
    @Test
    public void collaboratorVisibilityTest()
    {
        this.aHomePage
            .navigateToLoginPage()
            .loginAsUser()
            .navigateToCreateANewPatientPage()
            .toggleDefaultUncheckedConsentBoxes()
            .updateConsent()
            .setIdentifier("Collaborator Visibility " + randomChars)
            .setIndicationForReferral("Tests that User Two can see this patient")
            .saveAndViewSummary()
            .addCollaboratorToPatient("TestUser2Dos", PRIVILEGE.CanViewAndModifyAndManageRights);

        String patientIDThroughUser1 = this.aViewPatientPage.getPatientID();

        this.aViewPatientPage.logOut()
            .loginAsUserTwo()
            .navigateToAllPatientsPage()
            .filterByPatientID(patientIDThroughUser1)
            .viewFirstPatientInTable();

        String patientIDThroughUser2 = this.aViewPatientPage.getPatientID();

        Assert.assertEquals(patientIDThroughUser1, patientIDThroughUser2);

        this.aViewPatientPage.logOut();
    }

    // Adds partialMeasurement to User 1's patient, ensures that they are saved and viewable on the view patient form.
    // Does not enter a birthday.
    @Test
    public void addMeasurements()
    {
        this.aHomePage.navigateToLoginPage()
            .loginAsUser()
            .navigateToCreateANewPatientPage()
            .toggleDefaultUncheckedConsentBoxes()
            .updateConsent()
            .setIdentifier("Add Measurements " + randomChars)
            .expandSection(SECTIONS.MeasurementSection)
            .addMeasurement(this.partialMeasurement)
            .changeMeasurementDate(1, "15", "April", "2005")
            .saveAndViewSummary()
            .editThisPatient()
            .expandSection(SECTIONS.MeasurementSection);

        PatientMeasurementEntry foundMeasurementOnPatientForm = this.aPatientRecordEditPage.getNthMeasurement(1);
        System.out.println(foundMeasurementOnPatientForm);
        Assert.assertEquals(foundMeasurementOnPatientForm, this.partialMeasurement);

        this.aPatientRecordEditPage
            .saveAndViewSummary()
            .logOut();
    }

    // Creates a patient with a complete measurement data entry. Asserts that phenotypes are automatically added
    // (lightning bolt symbol) and the manually added phenotype doesn't have that symbol.
    @Test
    public void checkPhenotypesDueToMeasurements()
    {
        final List<String> automaticallyAddedPhenotypesToCheck = new ArrayList<>(
            Arrays.asList("Decreased body weight", "Short stature", "Microcephaly",
                "Obesity", "Long philtrum", "Long palpebral fissure",
                "Hypertelorism", "Macrotia", "Small hand", "Short foot"));

        final List<String> manuallyAddedPhenotypesToCheck = new ArrayList<>(Arrays.asList("Blue irides"));

        this.aHomePage.navigateToLoginPage()
            .loginAsUser()
            .navigateToCreateANewPatientPage()
            .toggleDefaultUncheckedConsentBoxes()
            .updateConsent()
            .setIdentifier("Phenotypes due to Measurement Data " + randomChars)
            .setIndicationForReferral("Ensure that phenotypes are automatically generated due to measurement data")
            .setDOB("10", "1992")
            .expandSection(SECTIONS.MeasurementSection)
            .addMeasurement(this.fullMeasurement)
            .changeMeasurementDate(1, "11", "March", "2015");

        // Have to wait for AJAX calls to finish. Better way would be to wait for standard dev calc element to appear.
        this.aPatientRecordEditPage.unconditionalWaitNs(5);

        this.aPatientRecordEditPage
            .expandSection(SECTIONS.ClinicalSymptomsSection)
            .addPhenotype("Blue irides");

        List<String> automaticallyAddedPhenotypesFound = this.aPatientRecordEditPage.getPhenotypesLightning();
        System.out.println(automaticallyAddedPhenotypesFound);

        List<String> manuallyAddedPhenotypesFound = this.aPatientRecordEditPage.getPhenotypesNonLightning();
        System.out.println(manuallyAddedPhenotypesFound);

        Assert.assertEquals(manuallyAddedPhenotypesFound, manuallyAddedPhenotypesToCheck);

        // Phenotype order depends on when AJAX completes for each one. No definite order.
        Assert.assertEqualsNoOrder(automaticallyAddedPhenotypesFound.toArray(),
            automaticallyAddedPhenotypesToCheck.toArray());

        this.aPatientRecordEditPage
            .saveAndViewSummary()
            .logOut();
    }

    // Enters information to the diagnosis form. Asserts that the diagnosis section has the appropriate fields
    // as seen on the view patient form.
    @Test
    public void checkDiagnosisSection()
    {
        final List<String> clinicalDiagnosisCheck = new ArrayList<>(
            Arrays.asList("1164 Allergic bronchopulmonary aspergillosis", "52530 Pseudo-von Willebrand disease"));

        final List<String> finalDiagnosisCheck = new ArrayList<>(
            Arrays.asList("#607154 ALLERGIC RHINITIS", "#304340 PETTIGREW SYNDROME"));

        final String additionalCommentCheck = "Comment in Additional Comments";

        final String resolutionNoteCheck = "Resolved";

        // This array will be sorted as PMIDs do not load in deterministic order.
        List<String> pubMedIDsCheck = new ArrayList<>(
            Arrays.asList("PMID: 30700955", "PMID: 30699054", "PMID: 30699052"));

        this.aHomePage.navigateToLoginPage()
            .loginAsUser()
            .navigateToCreateANewPatientPage()
            .toggleDefaultUncheckedConsentBoxes()
            .updateConsent()
            .setIdentifier("Check diagnosis section " + randomChars)
            .setIndicationForReferral("Ensure that proper diagnosis appear on patient form")
            .expandSection(SECTIONS.DiagnosisSection)
            .cycleThroughDiagnosisBoxes()
            .addClinicalDiagnosis("Pseudo-von Willebrand disease")
            .addFinalDiagnosis("PETTIGREW SYNDROME")
            .toggleNthFinalDiagnosisCheckbox(2)
            .toggleNthClinicalDiagnosisCheckbox(2)
            .addPubMedID("30700955")
            .saveAndViewSummary();

        System.out.println("Clinical Diagnosis Found: " + this.aViewPatientPage.getClinicalDiagnosisNames());
        System.out.println("Final Diagnosis Found: " + this.aViewPatientPage.getFinalDiagnosisNames());
        System.out.println("Additional Comments Found: " + this.aViewPatientPage.getAdditionalComments());
        System.out.println("PubMed IDs Found: " + this.aViewPatientPage.getExistingPubMedIDs());
        System.out.println("Resolution Notes Found: " + this.aViewPatientPage.getResolutionNotes());

        // Sort the PubMed IDs as they do not load/display in a deterministic order
        List<String> sortedPubMedIDsFound = this.aViewPatientPage.getExistingPubMedIDs();
        sortedPubMedIDsFound.sort(String::compareTo);
        pubMedIDsCheck.sort(String::compareTo);

        Assert.assertEquals(this.aViewPatientPage.getClinicalDiagnosisNames(), clinicalDiagnosisCheck);
        Assert.assertEquals(this.aViewPatientPage.getFinalDiagnosisNames(), finalDiagnosisCheck);
        Assert.assertEquals(this.aViewPatientPage.getAdditionalComments(), additionalCommentCheck);
        Assert.assertEquals(sortedPubMedIDsFound, pubMedIDsCheck);
        Assert.assertEquals(this.aViewPatientPage.getResolutionNotes(), resolutionNoteCheck);

        this.aViewPatientPage.logOut();
    }
}
