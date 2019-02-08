package TestCases;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import PageObjects.AdminRefreshMatchesPage;
import PageObjects.EmailUIPage;
import PageObjects.HomePage;
import PageObjects.ViewPatientPage;
import net.bytebuddy.utility.RandomString;

/**
 * This class will contain tests for the match notification table. It will ensure the appropriate
 * patients are present and show up when matched. For instance, match to genes, phenotype, and both.
 * Requires MockMock email UI to be setup with the PC instance that is being tested.
 */
public class MatchNotificationPageTests extends BaseTest implements CommonInfoEnums
{
    HomePage aHomePage = new HomePage(theDriver);

    EmailUIPage aEmailUIPage = new EmailUIPage(theDriver);

    ViewPatientPage aViewPatientPage = new ViewPatientPage(theDriver);

    AdminRefreshMatchesPage anAdminRefreshMatchesPage = new AdminRefreshMatchesPage(theDriver);

    final private String randomChars = RandomString.make(5);

    /**
     * Refresh "matches since last modified" so that the number goes to zero before beginning these tests.
     * This ensures that the assertions for "Number of Patients Processed" will pass as we check for exact match.
     * It then navigates to MockMock's UI page to clear the email inbox before any of these tests run.
     * This method runs once before the tests in this class begin running.
     */
    @BeforeClass
    public void refreshMatchesFirst()
    {
        aHomePage.navigateToLoginPage()
            .loginAsAdmin()
            .navigateToAdminSettingsPage()
            .navigateToRefreshMatchesPage()
            .refreshMatchesSinceLastUpdate()
            .navigateToEmailInboxPage()
            .deleteAllEmails()
            .navigateToHomePage()
            .logOut();
    }

    /**
     * Creates two new patients with 3/4 same phenotypes and no identical genes.
     * Asserts that:
     * - Two new patients are processed after a match refresh since last update
     * - A match for the two patients is found.
     */
    @Test(priority = 1)
    public void matchPhenotypeOnly()
    {
        List<String> loPhenotypesToAdd1 = new ArrayList<String>(Arrays.asList(
            "Unilateral deafness", "Poikilocytosis", "Swollen lip", "Narcolepsy", "Eye poking"));

        List<String> loPhenotypesToAdd2 = new ArrayList<String>(Arrays.asList(
            "Nausea and vomiting", "Poikilocytosis", "Swollen lip", "Narcolepsy", "Eye poking"));

        final String patientUniqueIdentifier = "PhenoOnlyMatch " + randomChars;

        String createdPatient1;
        String createdPatient2;
        List<String> patientsEmailTitleCheck;

        aHomePage.navigateToLoginPage()
            .loginAsUser()
            .navigateToCreateANewPatientPage()
            .toggleFirstFourConsentBoxes()
            .updateConsent()
            .setIdentifer(patientUniqueIdentifier)
            .setDOB("05", "2005")
            .setGender("Female")
            .expandSection(SECTIONS.ClinicalSymptomsSection)
            .addPhenotypes(loPhenotypesToAdd1)
            .expandSection(SECTIONS.ClinicalSymptomsSection)
            .expandSection(SECTIONS.GenotypeInfoSection)
            .addGene("IBD3", "Candidate", "Sequencing")
            .addGene("RNU1-4", "Rejected candidate", "Sequencing")
            .addGene("DAW1", "Confirmed causal", "Sequencing")
            .addGene("QRICH2", "Carrier", "Sequencing")
            .addGene("SCN5A", "Tested negative", "Sequencing")
            .saveAndViewSummary();

        createdPatient1 = aViewPatientPage.getPatientID();

        aViewPatientPage.logOut()
            .loginAsUserTwo()
            .navigateToCreateANewPatientPage()
            .toggleFirstFourConsentBoxes()
            .updateConsent()
            .setIdentifer(patientUniqueIdentifier + "Matchee")
            .setDOB("09", "2008")
            .setGender("Male")
            .expandSection(SECTIONS.ClinicalSymptomsSection)
            .addPhenotypes(loPhenotypesToAdd2)
            .expandSection(SECTIONS.ClinicalSymptomsSection)
            .expandSection(SECTIONS.GenotypeInfoSection)
            .addGene("NUDT12", "Candidate", "Sequencing")
            .addGene("MIR5685", "Rejected candidate", "Sequencing")
            .addGene("QRFP", "Confirmed causal", "Sequencing")
            .addGene("LINC01854", "Carrier", "Sequencing")
            .addGene("PRKCZ", "Tested negative", "Sequencing")
            .saveAndViewSummary();

        createdPatient2 = aViewPatientPage.getPatientID();

        aViewPatientPage.logOut()
            .loginAsAdmin()
            .navigateToAdminSettingsPage()
            .navigateToRefreshMatchesPage()
            .refreshMatchesSinceLastUpdate();

        Assert.assertEquals(anAdminRefreshMatchesPage.getNumberOfLocalPatientsProcessed(), "2");

        anAdminRefreshMatchesPage.navigateToAdminSettingsPage()
            .navigateToMatchingNotificationPage()
            .setAverageScoreSliderToMinimum()
            .setGenotypeSliderToZero()
            .filterByID(createdPatient1)
            .emailSpecificPatients(createdPatient1, createdPatient2)
            .navigateToEmailInboxPage();

        patientsEmailTitleCheck = new ArrayList<>(Arrays.asList(
            "Matches found for patient: " + createdPatient2, "Matches found for patient: " + createdPatient1));

        Assert.assertTrue(aEmailUIPage.getEmailTitles().containsAll(patientsEmailTitleCheck));

        aEmailUIPage.navigateToHomePage().logOut();
    }

    /**
     * Creates two new patients with identical genotype and no identical phenotype.
     * Asserts that:
     * - Two new patients are processed after a match refresh since last update
     * - A match for the two patients is found.
     */
    @Test(priority = 2)
    public void matchGenotypeOnly()
    {
        List<String> loPhenotypesToAdd1 = new ArrayList<String>(Arrays.asList(
            "Unilateral deafness", "Tarsal synostosis", "Impairment of activities of daily living",
            "Obliteration of the pulp chamber", "Abnormal proportion of marginal zone B cells"));

        List<String> loPhenotypesToAdd2 = new ArrayList<String>(Arrays.asList(
            "Nausea and vomiting", "Poikilocytosis", "Swollen lip", "Narcolepsy", "Eye poking"));

        final String patientUniqueIdentifier = "GenoOnlyMatch " + randomChars;

        String createdPatient1;
        String createdPatient2;
        List<String> patientsEmailTitleCheck;

        aHomePage.navigateToLoginPage()
            .loginAsUser()
            .navigateToCreateANewPatientPage()
            .toggleFirstFourConsentBoxes()
            .updateConsent()
            .setIdentifer(patientUniqueIdentifier)
            .setDOB("08", "2008")
            .setGender("Female")
            .expandSection(SECTIONS.ClinicalSymptomsSection)
            .addPhenotypes(loPhenotypesToAdd1)
            .expandSection(SECTIONS.ClinicalSymptomsSection)
            .expandSection(SECTIONS.GenotypeInfoSection)
            .addGene("DMPK", "Candidate", "Sequencing")
            .addGene("SLC25A34", "Candidate", "Sequencing")
            .addGene("USP9YP26", "Rejected candidate", "Sequencing")
            .addGene("SH2B3", "Confirmed causal", "Sequencing")
            .addGene("RIOK1", "Confirmed causal", "Sequencing")
            .addGene("RIOK2", "Carrier", "Sequencing")
            .addGene("RIOK3", "Tested negative", "Sequencing")
            .saveAndViewSummary();

        createdPatient1 = aViewPatientPage.getPatientID();

        aViewPatientPage.logOut()
            .loginAsUserTwo()
            .navigateToCreateANewPatientPage()
            .toggleFirstFourConsentBoxes()
            .updateConsent()
            .setIdentifer(patientUniqueIdentifier + "Matchee")
            .setDOB("10", "2010")
            .setGender("Male")
            .expandSection(SECTIONS.ClinicalSymptomsSection)
            .addPhenotypes(loPhenotypesToAdd2)
            .expandSection(SECTIONS.ClinicalSymptomsSection)
            .expandSection(SECTIONS.GenotypeInfoSection)
            .addGene("DMPK", "Candidate", "Sequencing")
            .addGene("SLC25A34", "Candidate", "Sequencing")
            .addGene("USP9YP26", "Rejected candidate", "Sequencing")
            .addGene("SH2B3", "Confirmed causal", "Sequencing")
            .addGene("RIOK1", "Confirmed causal", "Sequencing")
            .addGene("RIOK2", "Carrier", "Sequencing")
            .addGene("RIOK3", "Tested negative", "Sequencing")
            .saveAndViewSummary();

        createdPatient2 = aViewPatientPage.getPatientID();

        aViewPatientPage.logOut()
            .loginAsAdmin()
            .navigateToAdminSettingsPage()
            .navigateToRefreshMatchesPage()
            .refreshMatchesSinceLastUpdate();

        Assert.assertEquals(anAdminRefreshMatchesPage.getNumberOfLocalPatientsProcessed(), "2");

        anAdminRefreshMatchesPage.navigateToAdminSettingsPage()
            .navigateToMatchingNotificationPage()
            .filterByID(createdPatient1)
            .emailSpecificPatients(createdPatient1, createdPatient2)
            .navigateToEmailInboxPage();

        patientsEmailTitleCheck = new ArrayList<>(Arrays.asList(
            "Matches found for patient: " + createdPatient2, "Matches found for patient: " + createdPatient1));

        Assert.assertTrue(aEmailUIPage.getEmailTitles().containsAll(patientsEmailTitleCheck));

        aEmailUIPage.navigateToHomePage().logOut();
    }

    @Test(enabled = false)
    public void filterAndEmailTemp()
    {
        List<String> patientsEmailTitleCheck = new ArrayList<>(Arrays.asList(
            "Matches found for patient: P0000002", "Matches found for patient: P0000001"));

        aHomePage.navigateToLoginPage()
            .loginAsAdmin()
            .navigateToAdminSettingsPage()
            .navigateToMatchingNotificationPage()
            .toggleContactedStatusCheckbox()
            .emailSpecificPatients("P0000001", "P0000002 : Auto YnoP6 Patient")
            .logOut()
            .navigateToEmailInboxPage();

        Assert.assertEquals(aEmailUIPage.getEmailTitles(), patientsEmailTitleCheck);

        aEmailUIPage.deleteAllEmails();
    }

}
