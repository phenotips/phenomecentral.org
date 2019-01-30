package TestCases;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import PageObjects.CreatePatientPage;
import PageObjects.HomePage;
import PageObjects.PedigreeEditorPage;
import PageObjects.ViewPatientPage;
import net.bytebuddy.utility.RandomString;

/**
 * Tests for the Pedigree Editor page and the sync with the patient info page.
 * There are cases for creation of a patient and input information both via Pedigree Editor
 * This should be run as an entire class due to the pedigree editor requiring certain kinds of patients
 * to be present for the selectors to work.
 */
public class PedigreePageTest extends BaseTest implements CommonInfoEnums
{
    final private HomePage aHomePage = new HomePage(theDriver);

    final private PedigreeEditorPage aPedigreeEditorPage = new PedigreeEditorPage(theDriver);

    final private CreatePatientPage aCreatePatientPage = new CreatePatientPage(theDriver);

    final private ViewPatientPage aViewPatientPage = new ViewPatientPage(theDriver);

    final private String randomChars = RandomString.make(5);

    // Creates a patient with phenotypes and genes. Asserts that they are reflected
    //   in the pedigree editor after a save. This tests the pedigree editor when one patient/node is present.
    //   Checks that Patient Form Info -> Pedigree Editor Info
    @Test(priority = 1)
    public void basicPedigree()
    {
        final List<String> checkPhenotypes = new ArrayList<String>(Arrays.asList(
            "Prominent nose", "Macrocephaly at birth", "Narcolepsy", "Large elbow", "Terminal insomnia", "Small hand"));
        final List<String> checkCandidateGenes = new ArrayList<String>(Arrays.asList("IV", "IVD"));
        final List<String> checkConfirmedCausalGenes = new ArrayList<String>(Arrays.asList("IVL"));
        final List<String> checkCarrierGenes = new ArrayList<String>(Arrays.asList("OR6B1"));

        aHomePage.navigateToLoginPage()
            .loginAsUser()
            .navigateToCreateANewPatientPage()
            .toggleFirstFourConsentBoxes()
            .updateConsent()
            .setIdentifer("Pedigree Editor " + randomChars)
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

        List<String> loPhenotypesFound = aPedigreeEditorPage.getPhenotypes();
        List<String> loCandidateGenesFound = aPedigreeEditorPage.getGenes("Candidate");
        List<String> loConfirmedCausalGenesFound = aPedigreeEditorPage.getGenes("Confirmed Causal");
        List<String> loCarrierGenesFound = aPedigreeEditorPage.getGenes("Carrier");
        String patientGender = aPedigreeEditorPage.getGender();

        Assert.assertEquals(loPhenotypesFound, checkPhenotypes);
        Assert.assertEquals(loCandidateGenesFound, checkCandidateGenes);
        Assert.assertEquals(loConfirmedCausalGenesFound, checkConfirmedCausalGenes);
        Assert.assertEquals(loCarrierGenesFound, checkCarrierGenes);
        Assert.assertEquals(patientGender, "Female");

        aPedigreeEditorPage.closeEditor("Save")
            .saveAndViewSummary()
            .logOut();
    }

    // Creates a child for the most recently created patient via the Pedigree editor.
    // Asserts that two new patient nodes are created.
    @Test(priority = 2)
    public void createChild()
    {
        aHomePage.navigateToLoginPage()
            .loginAsAdmin()
            .navigateToAllPatientsPage()
            .sortPatientsDateDesc()
            .viewFirstPatientInTable()
            .editThisPatient()
            .expandSection(SECTIONS.FamilyHistorySection)
            .navigateToPedigreeEditor("");
        aPedigreeEditorPage.openNthEditModal(1)
            .createChild("male");

        Assert.assertEquals(aPedigreeEditorPage.getNumberOfTotalPatientsInTree(), 3);
        Assert.assertEquals(aPedigreeEditorPage.getNumberOfPartnerLinks(), 1);

        aPedigreeEditorPage.openNthEditModal(3).createSibling(3);

        Assert.assertEquals(aPedigreeEditorPage.getNumberOfTotalPatientsInTree(), 4);
        Assert.assertEquals(aPedigreeEditorPage.getNumberOfPartnerLinks(), 1);

        aPedigreeEditorPage.closeEditor("Don't Save")
            .logOut();
    }

    @Test(priority = 3)
    public void editorToPatientForm()
    {
        List<String> loPhenotypesToAdd = new ArrayList<>(Arrays.asList("Small hand", "Large knee", "Acne"));

        aHomePage.navigateToLoginPage()
            .loginAsUser()
            .navigateToCreateANewPatientPage()
            .toggleFirstFourConsentBoxes()
            .updateConsent()
            .expandSection(SECTIONS.FamilyHistorySection)
            .navigateToPedigreeEditor("");

        Assert.assertEquals(aPedigreeEditorPage.getNumberOfTotalPatientsInTree(), 1);
        Assert.assertEquals(aPedigreeEditorPage.getNumberOfPartnerLinks(), 0);

        aPedigreeEditorPage.openNthEditModal(1)
            .addPhenotypes(loPhenotypesToAdd)
            .closeEditor("Save")
            .expandSection(SECTIONS.ClinicalSymptomsSection);

        List<String> foundPhenotypesOnPatientForm = aCreatePatientPage.getPresentPhenotypes();

        System.out.println("Before: " + foundPhenotypesOnPatientForm);
        System.out.println("Before loAdding: " + loPhenotypesToAdd);
        // Must sort alphabetical first before comparison, they will be of a different order.
        loPhenotypesToAdd.sort(String::compareTo);
        foundPhenotypesOnPatientForm.sort(String::compareTo);

        Assert.assertEquals(foundPhenotypesOnPatientForm, loPhenotypesToAdd);
        System.out.println("After: " + foundPhenotypesOnPatientForm);
        System.out.println("After loAdding: " + loPhenotypesToAdd);

        aCreatePatientPage
            .saveAndViewSummary()
            .logOut();
    }

    @Test(priority = 4)
    public void createNewPatientViaEditor()
    {
        List<String> loPhenotypesToAdd = new ArrayList<>(Arrays.asList("Small hand", "Large knee", "Acne"));

        aHomePage.navigateToLoginPage()
            .loginAsUser()
            .navigateToAllPatientsPage()
            .sortPatientsDateDesc()
            .viewFirstPatientInTable()
            .editThisPatient()
            .expandSection(SECTIONS.FamilyHistorySection)
            .navigateToPedigreeEditor("");

        Assert.assertEquals(aPedigreeEditorPage.getNumberOfTotalPatientsInTree(), 1);
        Assert.assertEquals(aPedigreeEditorPage.getNumberOfPartnerLinks(), 0);

        aPedigreeEditorPage.createSibling(1);

        Assert.assertEquals(aPedigreeEditorPage.getNumberOfTotalPatientsInTree(), 4);
        Assert.assertEquals(aPedigreeEditorPage.getNumberOfPartnerLinks(), 1);

        aPedigreeEditorPage.openNthEditModal(5)
            .linkPatient("New");

        String createdPatient = aPedigreeEditorPage.getPatientIDFromModal();

        aPedigreeEditorPage.addPhenotypes(loPhenotypesToAdd)
            .addGene("Candidate", "LIN7C")
            .addGene("Confirmed Causal", "TAOK3")
            .addGene("Carrier", "YKT6")
            .closeEditor("Save")
            .saveAndViewSummary()
            .navigateToAllPatientsPage()
            .filterByPatientID(createdPatient)
            .viewFirstPatientInTable()
            .editThisPatient()
            .toggleFirstFourConsentBoxes()
            .updateConsent()
            .expandSection(SECTIONS.ClinicalSymptomsSection);

        List<String> foundPhenotypesFromPatientPage = aCreatePatientPage.getPresentPhenotypes();

        System.out.println("Before: " + foundPhenotypesFromPatientPage);
        System.out.println("Before loAdding: " + loPhenotypesToAdd);
        // Must sort alphabetical first before comparison, they will be of a different order.
        loPhenotypesToAdd.sort(String::compareTo);
        foundPhenotypesFromPatientPage.sort(String::compareTo);

        Assert.assertEquals(foundPhenotypesFromPatientPage, loPhenotypesToAdd);
        System.out.println("After: " + foundPhenotypesFromPatientPage);
        System.out.println("After loAdding: " + loPhenotypesToAdd);

        aCreatePatientPage.saveAndViewSummary();

        List<String> foundGeneNamesOnPatientForm = aViewPatientPage.getGeneNames();
        List<String> checkGeneNames = new ArrayList<>(Arrays.asList("LIN7C", "TAOK3", "YKT6"));

        List<String> foundGeneStatusesOnPatientForm = aViewPatientPage.getGeneStatus();
        List<String> checkGeneStatuses = new ArrayList<>(Arrays.asList("Candidate", "Confirmed Causal", "Carrier"));

        List<String> foundGeneStrategiesOnPatientForm = aViewPatientPage.getGeneStrategies();
        List<String> checkGeneStrategies = new ArrayList<>(Arrays.asList("", "", ""));

        List<String> foundGeneCommentsOnPatientForm = aViewPatientPage.getGeneComments();
        List<String> checkGeneComments = new ArrayList<>(Arrays.asList("", "", ""));

        Assert.assertEquals(foundGeneNamesOnPatientForm, checkGeneNames);
        Assert.assertEquals(foundGeneStatusesOnPatientForm, checkGeneStatuses);
        Assert.assertEquals(foundGeneStrategiesOnPatientForm, checkGeneStrategies);
        Assert.assertEquals(foundGeneCommentsOnPatientForm, checkGeneComments);

        aViewPatientPage.logOut();
    }

}
