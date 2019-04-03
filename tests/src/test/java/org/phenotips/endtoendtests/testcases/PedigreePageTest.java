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
import org.phenotips.endtoendtests.pageobjects.PatientRecordEditPage;
import org.phenotips.endtoendtests.pageobjects.HomePage;
import org.phenotips.endtoendtests.pageobjects.PedigreeEditorPage;
import org.phenotips.endtoendtests.pageobjects.ViewPatientPage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Tests for the Pedigree Editor page and the sync with the patient info page. There are cases for creation of a patient
 * and input information both via Pedigree Editor This should be run as an entire class due to the pedigree editor
 * requiring certain kinds of patients to be present for the selectors to work.
 */
public class PedigreePageTest extends BaseTest implements CommonInfoEnums
{
    final private HomePage aHomePage = new HomePage(theDriver);

    final private PedigreeEditorPage aPedigreeEditorPage = new PedigreeEditorPage(theDriver);

    final private PatientRecordEditPage aPatientRecordEditPage = new PatientRecordEditPage(theDriver);

    final private ViewPatientPage aViewPatientPage = new ViewPatientPage(theDriver);

    final private String randomChars = RandomStringUtils.randomAlphanumeric(5);

    /**
     * Creates a patient with phenotypes and genes. Asserts that they are reflected in the pedigree editor after a save.
     * This tests the pedigree editor when one patient/node is present. Checks that Patient Form Info -> Pedigree Editor
     * Info
     */
    @Test()
    public void basicPedigree()
    {
        final List<String> checkPhenotypes = new ArrayList<>(Arrays.asList(
            "Prominent nose", "Macrocephaly at birth", "Narcolepsy", "Large elbow", "Terminal insomnia", "Small hand"));
        final List<String> checkCandidateGenes = new ArrayList<>(Arrays.asList("IV", "IVD"));
        final List<String> checkConfirmedCausalGenes = new ArrayList<>(Arrays.asList("IVL"));
        final List<String> checkCarrierGenes = new ArrayList<>(Arrays.asList("OR6B1"));

        this.aHomePage.navigateToLoginPage()
            .loginAsUser()
            .navigateToCreateANewPatientPage()
            .toggleDefaultUncheckedConsentBoxes()
            .updateConsent()
            .setIdentifier("Pedigree Editor " + this.randomChars)
            .setDOB("05", "2000")
            .setGender("Female")
            .setOnset("Congenital onset ")
            .expandSection(SECTIONS.ClinicalSymptomsSection)
            .addPhenotypes(checkPhenotypes)
            .expandSection(SECTIONS.ClinicalSymptomsSection)
            .expandSection(SECTIONS.GenotypeInfoSection)
            .addGene("IV", "Candidate", "Sequencing")
            .addGene("IVD", "Candidate", "Sequencing")
            .addGene("IVL", "Confirmed causal", "Sequencing")
            .addGene("OR6B1", "Carrier", "Sequencing")
            .expandSection(SECTIONS.FamilyHistorySection)
            .navigateToPedigreeEditor("")
            .openNthEditModal(1);

        List<String> loPhenotypesFound = this.aPedigreeEditorPage.getPhenotypes();
        List<String> loCandidateGenesFound = this.aPedigreeEditorPage.getGenes("Candidate");
        List<String> loConfirmedCausalGenesFound = this.aPedigreeEditorPage.getGenes("Confirmed Causal");
        List<String> loCarrierGenesFound = this.aPedigreeEditorPage.getGenes("Carrier");
        String patientGender = this.aPedigreeEditorPage.getGender();

        Assert.assertEquals(loPhenotypesFound, checkPhenotypes);
        Assert.assertEquals(loCandidateGenesFound, checkCandidateGenes);
        Assert.assertEquals(loConfirmedCausalGenesFound, checkConfirmedCausalGenes);
        Assert.assertEquals(loCarrierGenesFound, checkCarrierGenes);
        Assert.assertEquals(patientGender, "Female");

        this.aPedigreeEditorPage.closeEditor("Save")
            .saveAndViewSummary()
            .logOut();
    }

    /**
     * Creates a child for the most recently created patient via the Pedigree editor. Asserts that two new patient nodes
     * are created and the total number of nodes.
     */
    @Test()
    public void createChild()
    {
        this.aHomePage.navigateToLoginPage()
            .loginAsAdmin()
            .navigateToAllPatientsPage()
            .sortPatientsDateDesc()
            .viewFirstPatientInTable()
            .editThisPatient()
            .expandSection(SECTIONS.FamilyHistorySection)
            .navigateToPedigreeEditor("");
        this.aPedigreeEditorPage.createChild("male");

        Assert.assertEquals(this.aPedigreeEditorPage.getNumberOfTotalPatientsInTree(), 3);
        Assert.assertEquals(this.aPedigreeEditorPage.getNumberOfPartnerLinks(), 1);

        this.aPedigreeEditorPage.createSibling(3);

        Assert.assertEquals(this.aPedigreeEditorPage.getNumberOfTotalPatientsInTree(), 4);
        Assert.assertEquals(this.aPedigreeEditorPage.getNumberOfPartnerLinks(), 1);

        this.aPedigreeEditorPage.closeEditor("Don't Save")
            .saveAndViewSummary()
            .logOut();
    }

    /**
     * Ensure changes made through pedigree editor are reflected on the View Patient Form after a save. Asserts: -
     * Correct number of nodes (note, does not check the type/gender, just counts the hoverboxes) - Phenotypes
     * correspond on the view Patient Info Form
     */
    @Test()
    public void editorToPatientForm()
    {
        List<String> loPhenotypesToAdd = new ArrayList<>(Arrays.asList("Small hand", "Large knee", "Acne"));

        this.aHomePage.navigateToLoginPage()
            .loginAsUser()
            .navigateToCreateANewPatientPage()
            .toggleDefaultUncheckedConsentBoxes()
            .updateConsent()
            .setGender("Female")
            .expandSection(SECTIONS.FamilyHistorySection)
            .navigateToPedigreeEditor("");

        Assert.assertEquals(this.aPedigreeEditorPage.getNumberOfTotalPatientsInTree(), 1);
        Assert.assertEquals(this.aPedigreeEditorPage.getNumberOfPartnerLinks(), 0);

        this.aPedigreeEditorPage.openNthEditModal(1)
            .addPhenotypes(loPhenotypesToAdd)
            .closeEditor("Save")
            .expandSection(SECTIONS.ClinicalSymptomsSection);

        List<String> foundPhenotypesOnPatientForm = this.aPatientRecordEditPage.getAllPhenotypes();

        System.out.println("Before: " + foundPhenotypesOnPatientForm);
        System.out.println("Before loAdding: " + loPhenotypesToAdd);
        // Must sort alphabetical first before comparison, they will be of a different order.
        loPhenotypesToAdd.sort(String::compareTo);
        foundPhenotypesOnPatientForm.sort(String::compareTo);

        Assert.assertEquals(foundPhenotypesOnPatientForm, loPhenotypesToAdd);
        System.out.println("After: " + foundPhenotypesOnPatientForm);
        System.out.println("After loAdding: " + loPhenotypesToAdd);

        this.aPatientRecordEditPage
            .saveAndViewSummary()
            .logOut();
    }

    /**
     * Creates a new patient via the pedigree editor, using an existing pedigree created in the previous test Asserts: -
     * Correct number of Patients and Partner Links before and after creating a male sibling. - Asserts that the
     * phenotypes and genotype information on the modal corresponds to info on the patient's main profile page. This
     * includes gene names, their status, strategies and comments.
     */
    @Test()
    public void createNewPatientViaEditor()
    {
        List<String> loPhenotypesToAdd = new ArrayList<>(Arrays.asList("Small hand", "Large knee", "Acne"));

        this.aHomePage.navigateToLoginPage()
            .loginAsUser()
            .navigateToAllPatientsPage()
            .sortPatientsDateDesc()
            .viewFirstPatientInTable()
            .editThisPatient()
            .expandSection(SECTIONS.FamilyHistorySection)
            .navigateToPedigreeEditor("");

        Assert.assertEquals(this.aPedigreeEditorPage.getNumberOfTotalPatientsInTree(), 1);
        Assert.assertEquals(this.aPedigreeEditorPage.getNumberOfPartnerLinks(), 0);

        this.aPedigreeEditorPage.createSibling(1);

        Assert.assertEquals(this.aPedigreeEditorPage.getNumberOfTotalPatientsInTree(), 4);
        Assert.assertEquals(this.aPedigreeEditorPage.getNumberOfPartnerLinks(), 1);

        this.aPedigreeEditorPage.openNthEditModal(5)
            .linkPatient("New");

        String createdPatient = this.aPedigreeEditorPage.getPatientIDFromModal();

        this.aPedigreeEditorPage.addPhenotypes(loPhenotypesToAdd)
            .addGene("LIN7C", "Candidate")
            .addGene("TAOK3", "Confirmed Causal")
            .addGene("YKT6", "Carrier")
            .closeEditor("Save")
            .saveAndViewSummary()
            .navigateToAllPatientsPage()
            .filterByPatientID(createdPatient)
            .viewFirstPatientInTable()
            .editThisPatient()
            .toggleDefaultUncheckedConsentBoxes()
            .updateConsent()
            .expandSection(SECTIONS.ClinicalSymptomsSection);

        List<String> foundPhenotypesFromPatientPage = this.aPatientRecordEditPage.getAllPhenotypes();

        System.out.println("Before: " + foundPhenotypesFromPatientPage);
        System.out.println("Before loAdding: " + loPhenotypesToAdd);
        // Must sort alphabetical first before comparison, they will be of a different order.
        loPhenotypesToAdd.sort(String::compareTo);
        foundPhenotypesFromPatientPage.sort(String::compareTo);

        Assert.assertEquals(foundPhenotypesFromPatientPage, loPhenotypesToAdd);
        System.out.println("After: " + foundPhenotypesFromPatientPage);
        System.out.println("After loAdding: " + loPhenotypesToAdd);

        this.aPatientRecordEditPage.saveAndViewSummary();

        List<String> foundGeneNamesOnPatientForm = this.aViewPatientPage.getGeneNames();
        List<String> checkGeneNames = new ArrayList<>(Arrays.asList("LIN7C", "TAOK3", "YKT6"));

        List<String> foundGeneStatusesOnPatientForm = this.aViewPatientPage.getGeneStatus();
        List<String> checkGeneStatuses = new ArrayList<>(Arrays.asList("Candidate", "Confirmed causal", "Carrier"));

        List<String> foundGeneStrategiesOnPatientForm = this.aViewPatientPage.getGeneStrategies();
        List<String> checkGeneStrategies = new ArrayList<>(Arrays.asList("", "", ""));

        List<String> foundGeneCommentsOnPatientForm = this.aViewPatientPage.getGeneComments();
        List<String> checkGeneComments = new ArrayList<>(Arrays.asList("", "", ""));

        Assert.assertEquals(foundGeneNamesOnPatientForm, checkGeneNames);
        Assert.assertEquals(foundGeneStatusesOnPatientForm, checkGeneStatuses);
        Assert.assertEquals(foundGeneStrategiesOnPatientForm, checkGeneStrategies);
        Assert.assertEquals(foundGeneCommentsOnPatientForm, checkGeneComments);

        this.aViewPatientPage.logOut();
    }

    /**
     * Opens the pedigree editor for the previously created patient (in the test above) and edit the patient's genotype.
     * Asserts: - On trying to click "Close", that there is a js warning dialogue to prompt saving before navigating
     * away.
     */
    @Test()
    public void warningDialoguePresent()
    {
        this.aHomePage.navigateToLoginPage()
            .loginAsUser()
            .navigateToAllPatientsPage()
            .sortPatientsDateDesc()
            .viewFirstPatientInTable()
            .editThisPatient()
            .expandSection(SECTIONS.FamilyHistorySection)
            .navigateToPedigreeEditor("")
            .openNthEditModal(1)
            .addGene("FOXP2", "Carrier");

        Assert.assertTrue(this.aPedigreeEditorPage.doesWarningDialogueAppear());

        this.aPatientRecordEditPage.saveAndViewSummary().logOut();
    }
}
