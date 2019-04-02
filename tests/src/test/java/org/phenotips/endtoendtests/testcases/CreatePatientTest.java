package org.phenotips.endtoendtests.testcases;

import org.phenotips.endtoendtests.common.CommonInfoEnums;
import org.phenotips.endtoendtests.common.CommonPatientMeasurement;
import org.phenotips.endtoendtests.pageobjects.AdminRefreshMatchesPage;
import org.phenotips.endtoendtests.pageobjects.CreatePatientPage;
import org.phenotips.endtoendtests.pageobjects.EmailUIPage;
import org.phenotips.endtoendtests.pageobjects.HomePage;
import org.phenotips.endtoendtests.pageobjects.ViewPatientPage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import net.bytebuddy.utility.RandomString;

/**
 * Testing the creation of two very similar patients via JSON import and manually. Asserts a match at end.
 *
 * The entire class should be run together. We need the first two methods to be run first to have two patients created.
 *
 * The remaining tests depend on them. Requires MockMock email SMTP service to be running for it to check emails.
 */
public class CreatePatientTest extends BaseTest implements CommonInfoEnums
{
    final private HomePage aHomePage = new HomePage(theDriver);

    final private ViewPatientPage aViewPatientPage = new ViewPatientPage(theDriver);

    final private CreatePatientPage aCreatePatientPage = new CreatePatientPage(theDriver);

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

    // This is a helper test to ensure matches are refreshed before any of the following tests are run.
    @Test
    public void initialMatchesRefresh()
    {
        aHomePage.navigateToLoginPage()
            .loginAsAdmin()
            .navigateToAdminSettingsPage()
            .navigateToRefreshMatchesPage()
            .refreshMatchesSinceLastUpdate()
            .logOut();
    }

    // Create a patient manually as User 1. Assert that the required section titles in checkForTheseSections are visible.
    @Test()
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

        System.out.println("We just edited: " + aViewPatientPage.getPatientID());
        Assert.assertTrue(aViewPatientPage.checkForVisibleSections(checkForTheseSections));

        aViewPatientPage.logOut();
    }

    // Creates an identical patient as User 2 via JSON import. Asserts that the section titles are visible.
    // Updates consent, and changes modifies the identifier so that it is unique and matchable.
    @Test()
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

        System.out.println("We just edited: " + aViewPatientPage.getPatientID());
        Assert.assertTrue(aViewPatientPage.checkForVisibleSections(checkForTheseSections));

        aHomePage.logOut();
    }

    // Refresh the matches and assert that two new matches are found.
//    @Test(dependsOnMethods = {"createPatientManually", "importSecondJSONPatient"})
//          dependsOnMethods causes order on XML to be ignored as they will have higher priority,
//          which is a global variable.
    @Test()
    public void refreshMatchesForTwoPatients()
    {
        AdminRefreshMatchesPage aRefreshMatchesPage = new AdminRefreshMatchesPage(theDriver);

        aHomePage.navigateToLoginPage()
            .loginAsAdmin()
            .navigateToAdminSettingsPage()
            .navigateToRefreshMatchesPage()
            .refreshMatchesSinceLastUpdate();

        int initialRefreshMatchCount = Integer.parseInt(aRefreshMatchesPage.getTotalMatchesFound());
        String expectedMatchCount = Integer.toString(initialRefreshMatchCount + 2);

        Assert.assertEquals(aRefreshMatchesPage.getNumberOfLocalPatientsProcessed(), "2");
        Assert.assertEquals(aRefreshMatchesPage.getTotalMatchesFound(), expectedMatchCount);

        aHomePage.logOut();
    }

    // Sends the email notification of an identical (100%) match to the two newly created
    // patients, checks that the inbox has emails.
//    @Test(, dependsOnMethods = {"createPatientManually", "importSecondJSONPatient"})
    @Test()
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
        emailPage.navigateToHomePage();
        aViewPatientPage.logOut();
    }

    // Adjusts Patient created by User 1 to public, ensures User 2 can now see it.
//    @Test(, dependsOnMethods = {"createPatientManually", "importSecondJSONPatient"})
    @Test()
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

    // Adds a collaborator to a patient belong to User 2. Asserts that User 1 can then access it.
//    @Test(, dependsOnMethods = {"createPatientManually", "importSecondJSONPatient"})
    @Test()
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

    // Deletes all patients, helper test in case we want to clean up all patients after this class in the future.
    @Test(enabled = false)
    public void deleteAllUsersHelper()
    {
        aHomePage
            .navigateToLoginPage()
            .loginAsAdmin()
            .navigateToAllPatientsPage()
            .deleteAllPatients();
    }

    // Adds measurements to User 1's patient, ensures that they are saved and viewable on the view patient form.
    @Test()
    public void addMeasurements()
    {
        CommonPatientMeasurement measurements = new CommonPatientMeasurement(
            1, 2, 3, 4, 5,
            6, 7, 8, 9, 10,
            11, 12, 13, 14, 15,
            16, 17, 18);

        aHomePage.navigateToLoginPage()
            .loginAsUser()
            .navigateToCreateANewPatientPage()
            .toggleFirstFourConsentBoxes()
            .updateConsent()
            .expandSection(SECTIONS.MeasurementSection)
            .addMeasurement(measurements)
            .changeMeasurementDate("11", "March", "2015")
            .saveAndViewSummary()
            .editThisPatient()
            .expandSection(SECTIONS.MeasurementSection);

        CommonPatientMeasurement foundMeasurementOnPatientForm = aCreatePatientPage.getPatientMeasurement();
        System.out.println(foundMeasurementOnPatientForm);
        Assert.assertEquals(foundMeasurementOnPatientForm, measurements);

        aCreatePatientPage
            .saveAndViewSummary()
            .logOut();
    }

    // Adds a custom phenotype and then asserts that the automatically added phenotypes show up with the
    // lightning bolt symbol and the manually added phenotype doesn't have that symbol.
    // DependsOn the addMeasurements() test to have completed.
    @Test()
    public void checkPhenotypesDueToMeasurements()
    {
        final List<String> automaticallyAddedPhenotypesToCheck = new ArrayList<>(
            Arrays.asList("Decreased body weight", "Short stature", "Microcephaly",
                "Obesity", "Long philtrum", "Long palpebral fissure",
                "Hypertelorism", "Macrotia", "Small hand", "Short foot"));

        final List<String> manuallyAddedPhenotypesToCheck = new ArrayList<>(Arrays.asList("Blue irides"));

        aHomePage.navigateToLoginPage()
            .loginAsUser()
            .navigateToAllPatientsPage()
            .sortPatientsDateDesc()
            .viewFirstPatientInTable()
            .editThisPatient()
            .setDOB("10", "1992")
            .expandSection(SECTIONS.ClinicalSymptomsSection)
            .addPhenotype("Blue irides");

        List<String> automaticallyAddedPhenotypesFound = aCreatePatientPage.getPhenotypesLightning();
        System.out.println(automaticallyAddedPhenotypesFound);

        List<String> manuallyAddedPhenotypesFound = aCreatePatientPage.getPhenotypesNonLightning();
        System.out.println(manuallyAddedPhenotypesFound);

        Assert.assertEquals(automaticallyAddedPhenotypesFound, automaticallyAddedPhenotypesToCheck);
        Assert.assertEquals(manuallyAddedPhenotypesFound, manuallyAddedPhenotypesToCheck);

        aCreatePatientPage
            .saveAndViewSummary()
            .logOut();
    }

    // Enters information to the diagnosis form. Asserts that the diagnosis section has the appropriate fields
    // as seen on the view patient form.
    @Test()
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

        aHomePage.navigateToLoginPage()
            .loginAsUser()
            .navigateToAllPatientsPage()
            .sortPatientsDateDesc()
            .viewFirstPatientInTable()
            .editThisPatient()
            .expandSection(SECTIONS.DiagnosisSection)
            .cycleThroughDiagnosisBoxes()
            .addClinicalDiagnosis("Pseudo-von Willebrand disease")
            .addFinalDiagnosis("PETTIGREW SYNDROME")
            .toggleNthFinalDiagnosisCheckbox(2)
            .toggleNthClinicalDiagnosisCheckbox(2)
            .addPubMedID("30700955")
            .saveAndViewSummary();

        System.out.println("Clinical Diagnosis Found: " + aViewPatientPage.getClinicalDiagnosisNames());
        System.out.println("Final Diagnosis Found: " + aViewPatientPage.getFinalDiagnosisNames());
        System.out.println("Additional Comments Found: " + aViewPatientPage.getAdditionalComments());
        System.out.println("PubMed IDs Found: " + aViewPatientPage.getExistingPubMedIDs());
        System.out.println("Resolution Notes Found: " + aViewPatientPage.getResolutionNotes());

        // Sort the PubMed IDs as they do not load/display in a deterministic order
        List<String> sortedPubMedIDsFound = aViewPatientPage.getExistingPubMedIDs();
        sortedPubMedIDsFound.sort(String::compareTo);
        pubMedIDsCheck.sort(String::compareTo);

        Assert.assertEquals(aViewPatientPage.getClinicalDiagnosisNames(), clinicalDiagnosisCheck);
        Assert.assertEquals(aViewPatientPage.getFinalDiagnosisNames(), finalDiagnosisCheck);
        Assert.assertEquals(aViewPatientPage.getAdditionalComments(), additionalCommentCheck);
        Assert.assertEquals(sortedPubMedIDsFound, pubMedIDsCheck);
        Assert.assertEquals(aViewPatientPage.getResolutionNotes(), resolutionNoteCheck);

        aHomePage.logOut();
    }
}
