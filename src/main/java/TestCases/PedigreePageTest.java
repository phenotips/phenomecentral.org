package TestCases;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import PageObjects.HomePage;
import PageObjects.PedigreeEditorPage;
import net.bytebuddy.utility.RandomString;

/**
 * Tests for the Pedigree Editor page and the sync with the patient info page.
 */
public class PedigreePageTest extends BaseTest implements CommonInfoEnums
{
    final private HomePage aHomePage = new HomePage(theDriver);

    final private PedigreeEditorPage aPedigreeEditorPage = new PedigreeEditorPage(theDriver);

    final private String randomChars = RandomString.make(5);

    // Creates a patient with phenotypes and genes. Asserts that they are reflected
    //   in the pedigree editor after a save.
    @Test
    public void basicPedigree()
    {
        final List<String> checkPhenotypes = new ArrayList<String>(Arrays.asList(
            "Prominent nose", "Macrocephaly at birth", "Narcolepsy", "Large elbow", "Terminal insomnia", "Small hand"));
        final List<String> checkCandidateGenes = new ArrayList<String>(Arrays.asList(
            "IV", "IVD"));

        aHomePage.navigateToLoginPage()
            .loginAsUser()
            .navigateToCreateANewPatientPage()
            .toggleNthConsentBox(1)
            .toggleNthConsentBox(2)
            .toggleNthConsentBox(3)
            .toggleNthConsentBox(4)
            .updateConsent()
            .setIdentifer("Pedigree Editor " + randomChars)
            .setDOB("05", "2000")
            .setGender("Female")
            .setOnset("Congenital onset ")
            .expandSection(SECTIONS.ClinicalSymptomsSection)
            .addPhenotype("Prominent nose")
            .addPhenotype("Large elbow")
            .addPhenotype("Small hand")
            .addPhenotype("Macrocephaly at birth")
            .addPhenotype("Narcolepsy")
            .addPhenotype("Terminal insomnia ")
            .expandSection(SECTIONS.ClinicalSymptomsSection)
            .expandSection(SECTIONS.GenotypeInfoSection)
            .addGene("IV", "Candidate", "Sequencing")
            .addGene("IVD", "Candidate", "Sequencing")
            .addGene("IVL", "Confirmed causal", "Sequencing")
            .addGene("OR6B1", "Carrier", "Sequencing")
            .saveAndViewSummary()
            .editThisPatient()
            .expandSection(SECTIONS.FamilyHistorySection)
            .navigateToPedigreeEditor("")
            .openEditModal();

        List<String> loPhenotypesFound = aPedigreeEditorPage.getPhenotypes();
        List<String> loCandidateGenesFound = aPedigreeEditorPage.getGenes("Candidate");
        String patientGender = aPedigreeEditorPage.getGender();

        Assert.assertEquals(loPhenotypesFound, checkPhenotypes);
        Assert.assertEquals(loCandidateGenesFound, checkCandidateGenes);
        Assert.assertEquals(patientGender, "Female");

        aPedigreeEditorPage.closeEditor("Save")
            .saveAndViewSummary()
            .editThisPatient()
            .expandSection(SECTIONS.FamilyHistorySection)
            .navigateToPedigreeEditor("")
            .closeEditor("")
            .saveAndViewSummary()
            .logOut();
    }

    // Creates a child for the most recently created patient via the Pedigree editor.
    // Asserts that two new patient nodes are created.
    @Test
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
        aPedigreeEditorPage.createChild("male");

        Assert.assertEquals(aPedigreeEditorPage.getNumberOfTotalPatientsInTree(), 3);
        Assert.assertEquals(aPedigreeEditorPage.getNumberOfPartnerLinks(), 1);

        aPedigreeEditorPage.createSibling(3)
            .createPartnership(3, 5)
            .closeEditor("Don't Save")
            .logOut();
    }

}
