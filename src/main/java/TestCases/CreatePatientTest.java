package TestCases;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import PageObjects.AdminRefreshMatchesPage;
import PageObjects.EmailUIPage;
import net.bytebuddy.utility.RandomString;
import PageObjects.HomePage;
import PageObjects.ViewPatientPage;

/**
 * Testing the creation of two very similar patients via JSON import and manually. Asserts a match at end.
 *
 * The entire class should be run together. Lower methods depend on the ones above.
 *
 * Requires MockMock email SMTP service to be running for it to check emails.
 */
public class CreatePatientTest extends BaseTest implements CommonInfoEnums
{
    final private HomePage aHomePage = new HomePage(theDriver);

    final private ViewPatientPage aViewPatientPage = new ViewPatientPage(theDriver);
        // At some point we need to restart the view patients page.

    final private SECTIONS[] checkForTheseSections = {
        SECTIONS.PatientInfoSection,
        SECTIONS.ClinicalSymptomsSection,
        SECTIONS.SuggestedGenesSection,
        SECTIONS.GenotypeInfoSection,
        SECTIONS.SimilarCasesSection
    };

    final private String randomChars = RandomString.make(5);

    final private String patientUniqueIdentifier = "Auto " + randomChars + " Patient";

    final private String JSONToImport =
        "[{\"allergies\":[],\"date\":\"2019-01-11T17:26:01.000Z\",\"apgar\":{},\"notes\":{\"family_history\":\"\",\"prenatal_development\":\"\",\"indication_for_referral\":\"\",\"genetic_notes\":\"\",\"medical_history\":\"\",\"diagnosis_notes\":\"\"},\"ethnicity\":{\"maternal_ethnicity\":[],\"paternal_ethnicity\":[]},\"date_of_birth\":{\"month\":3,\"year\":2011},\"global_mode_of_inheritance\":[],\"solved\":{\"status\":\"unsolved\"},\"external_id\":\""
            + patientUniqueIdentifier +
            "\",\"variants\":[],\"clinicalStatus\":\"affected\",\"disorders\":[],\"features\":[{\"id\":\"HP:0000385\",\"label\":\"Small earlobe\",\"type\":\"phenotype\",\"observed\":\"yes\"},{\"id\":\"HP:0000505\",\"label\":\"Visual impairment\",\"type\":\"phenotype\",\"observed\":\"yes\"},{\"id\":\"HP:0000618\",\"label\":\"Blindness\",\"type\":\"phenotype\",\"observed\":\"yes\"},{\"id\":\"HP:0001250\",\"label\":\"Seizures\",\"type\":\"phenotype\",\"observed\":\"yes\"},{\"id\":\"HP:0002121\",\"label\":\"Absence seizures\",\"type\":\"phenotype\",\"observed\":\"yes\"},{\"id\":\"HP:0006266\",\"label\":\"Small placenta\",\"type\":\"phenotype\",\"observed\":\"yes\"},{\"id\":\"HP:0007875\",\"label\":\"Congenital blindness\",\"type\":\"phenotype\",\"observed\":\"yes\"},{\"id\":\"HP:0011146\",\"label\":\"Dialeptic seizures\",\"type\":\"phenotype\",\"observed\":\"yes\"},{\"id\":\"HP:0011147\",\"label\":\"Typical absence seizures\",\"type\":\"phenotype\",\"observed\":\"yes\"},{\"id\":\"HP:0200055\",\"label\":\"Small hand\",\"type\":\"phenotype\",\"observed\":\"yes\"}],\"date_of_death\":\"\",\"last_modification_date\":\"2019-01-11T17:31:13.000Z\",\"nonstandard_features\":[],\"prenatal_perinatal_history\":{\"multipleGestation\":null,\"icsi\":null,\"ivf\":null,\"assistedReproduction_donoregg\":null,\"assistedReproduction_iui\":null,\"twinNumber\":\"\",\"assistedReproduction_fertilityMeds\":null,\"gestation\":null,\"assistedReproduction_surrogacy\":null,\"assistedReproduction_donorsperm\":null},\"family_history\":{\"miscarriages\":null,\"consanguinity\":null,\"affectedRelatives\":null},\"genes\":[{\"gene\":\"PLS1\",\"id\":\"ENSG00000120756\",\"strategy\":[\"sequencing\"],\"status\":\"candidate\"},{\"gene\":\"PLS3\",\"id\":\"ENSG00000102024\",\"strategy\":[\"sequencing\"],\"status\":\"candidate\"},{\"gene\":\"QSOX1\",\"id\":\"ENSG00000116260\",\"strategy\":[\"sequencing\"],\"status\":\"solved\"},{\"gene\":\"TXNL1\",\"id\":\"ENSG00000091164\",\"strategy\":[\"sequencing\"],\"status\":\"carrier\"}],\"life_status\":\"alive\",\"sex\":\"M\",\"clinical-diagnosis\":[],\"reporter\":\"TestUser1Uno\",\"last_modified_by\":\"TestUser1Uno\",\"global_age_of_onset\":[{\"id\":\"HP:0003577\",\"label\":\"Congenital onset\"}],\"report_id\":\"P0000009\",\"medical_reports\":[]}\n" +
            "]";

    // Patient IDs of the two created patients, assigned during the tests.

    private String createdPatient1;

    private String createdPatient2;

    // Create a patient manually as User 1.
    @Test(priority = 1, groups={"CreatePatientTest.createTwoPatients"})
    public void createPatientManually()
    {
        List<String> loPhenotypesToAdd = new ArrayList<String>(Arrays.asList(
            "Blindness", "Visual impairment", "Small earlobe", "Small hand", "Absence seizures",
            "Diapleptic Seizures", "Typical absence seizures", "Seizures", "Small placenta"));

        aHomePage.navigateToLoginPage()
            .loginAsUser()
            .navigateToCreateANewPatientPage()
            .toggleFirstFourConsentBoxes()
            .updateConsent()
            .setIdentifer(patientUniqueIdentifier)
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

        createdPatient1 = aViewPatientPage.getPatientID();
        System.out.println("We just edited: " + aViewPatientPage.getPatientID());
        Assert.assertTrue(aViewPatientPage.checkForVisibleSections(checkForTheseSections));

        aViewPatientPage.logOut();

    }

    // Creates an identitcal patient as User 2 via JSON import. Asserts that the section titles are visible.
    // Updates consent, and changes modifies the identifier so that it is unique and matchable.
    @Test(priority = 2, groups={"CreatePatientTest.createTwoPatients"})
    public void importSecondJSONPatient()
    {
        aHomePage.navigateToLoginPage()
            .loginAsUserTwo()
            .navigateToAllPatientsPage()
            .importJSONPatient(JSONToImport)
            .sortPatientsDateDesc()
            .viewFirstPatientInTable()
            .editThisPatient()
            .toggleFirstFourConsentBoxes()
            .updateConsent()
            .setIdentifer(patientUniqueIdentifier + " Match")
            .saveAndViewSummary();

        createdPatient2 = aViewPatientPage.getPatientID();
        System.out.println("We just edited: " + aViewPatientPage.getPatientID());
        Assert.assertTrue(aViewPatientPage.checkForVisibleSections(checkForTheseSections));

        aHomePage.logOut();
    }

    // Refresh the matches and assert that two new matches are found.
    @Test(priority = 3, dependsOnMethods = {"createPatientManually", "importSecondJSONPatient"})
    public void refreshMatchesForTwoPatients()
    {
        AdminRefreshMatchesPage aRefreshMatchesPage= new AdminRefreshMatchesPage(theDriver);

        aHomePage.navigateToLoginPage()
            .loginAsAdmin()
            .navigateToAdminSettingsPage()
            .navigateToRefreshMatchesPage()
            .refreshAllMatches();

        Assert.assertEquals(aRefreshMatchesPage.getNumberOfLocalPatientsProcessed(), 2);
        Assert.assertEquals(aRefreshMatchesPage.getTotalMatchesFound(), 2);

        aHomePage.logOut();
    }

    // Sends the email notification of an identical (100%) match to the two newly created
    // patients, checks that the inbox has emails.
    // TODO: Maybe we should delete patient afterwards, otherwise might get other matches in first row.
    @Test(priority = 4, dependsOnMethods = {"createPatientManually", "importSecondJSONPatient"})
    public void verifyEmailNotifications()
    {
        EmailUIPage emailPage = new EmailUIPage(theDriver);

        aViewPatientPage.navigateToEmailInboxPage()
            .deleteAllEmails();

        aHomePage.navigateToLoginPage()
            .loginAsAdmin()
            .navigateToAdminSettingsPage()
            .navigateToMatchingNotificationPage()
            .filterByID(patientUniqueIdentifier + " Match")
            .emailFirstRowUsers()
            .navigateToEmailInboxPage();
        Assert.assertEquals(emailPage.getNumberOfEmails(), 2);

        emailPage.deleteAllEmails();
        theDriver.navigate().back();
        aViewPatientPage.logOut();
    }

    // Adjusts Patient created by User 1 to public, ensures User 2 can now see it.
    @Test(priority = 5, dependsOnMethods = {"createPatientManually", "importSecondJSONPatient"})
    public void publicVisiblityTest()
    {
        aHomePage.navigateToLoginPage()
            .loginAsUserTwo()
            .navigateToAllPatientsPage()
            .sortPatientsDateDesc()
            .viewFirstPatientInTable();

        String ID1 = aViewPatientPage.getPatientID();

        aViewPatientPage.setGlobalVisibility("public");

        aViewPatientPage.logOut()
            .loginAsUser()
            .navigateToAllPatientsPage()
            .sortPatientsDateDesc()
            .viewFirstPatientInTable();

        String ID2 = aViewPatientPage.getPatientID();

        Assert.assertEquals(ID1, ID2);

        aViewPatientPage.logOut().loginAsUserTwo();
        aViewPatientPage.setGlobalVisibility("matchable"); // Set patient back to private to allow for other tests.
        aHomePage.logOut();
    }

    @Test(priority = 6, dependsOnMethods = {"createPatientManually", "importSecondJSONPatient"})
    public void collaboratorVisibilityTest()
    {
        aHomePage
            .navigateToLoginPage()
            .loginAsUser()
            .navigateToAllPatientsPage()
            .viewFirstPatientInTable()
            .addCollaboratorToPatient("TestUser2Dos", PRIVILAGE.CanViewAndModifyAndManageRights);

        String patientIDThroughUser1 = aViewPatientPage.getPatientID();

        aViewPatientPage.logOut()
            .loginAsUserTwo()
            .navigateToAllPatientsPage()
            .filterByPatientID(patientIDThroughUser1)
            .viewFirstPatientInTable();

        String patientIDThroughUser2 = aViewPatientPage.getPatientID();

        Assert.assertEquals(patientIDThroughUser1, patientIDThroughUser2);

        aViewPatientPage.removeNthCollaborator(1); // Remove the collaborator to reset the state.

        aViewPatientPage.logOut();
    }

    @Test(enabled = false)
    public void deleteAllUsersHelper() {
        aHomePage
            .navigateToLoginPage()
            .loginAsAdmin()
            .navigateToAllPatientsPage()
            .deleteAllPatients();
    }


}
