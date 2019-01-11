package testCases;

import org.testng.annotations.Test;
import pageObjects.basePage;
import pageObjects.homePage;
import pageObjects.viewPatientPage;

public class createPatientTest extends baseTest {
    homePage currentPage = new homePage(theDriver);

    final String JSONToImport = "[{\"allergies\":[],\"date\":\"2019-01-11T17:26:01.000Z\",\"apgar\":{},\"notes\":{\"family_history\":\"\",\"prenatal_development\":\"\",\"indication_for_referral\":\"\",\"genetic_notes\":\"\",\"medical_history\":\"\",\"diagnosis_notes\":\"\"},\"ethnicity\":{\"maternal_ethnicity\":[],\"paternal_ethnicity\":[]},\"date_of_birth\":{\"month\":3,\"year\":2011},\"global_mode_of_inheritance\":[],\"solved\":{\"status\":\"unsolved\"},\"external_id\":\"[Auto]Patient\",\"variants\":[],\"clinicalStatus\":\"affected\",\"disorders\":[],\"features\":[{\"id\":\"HP:0000385\",\"label\":\"Small earlobe\",\"type\":\"phenotype\",\"observed\":\"yes\"},{\"id\":\"HP:0000505\",\"label\":\"Visual impairment\",\"type\":\"phenotype\",\"observed\":\"yes\"},{\"id\":\"HP:0000618\",\"label\":\"Blindness\",\"type\":\"phenotype\",\"observed\":\"yes\"},{\"id\":\"HP:0001250\",\"label\":\"Seizures\",\"type\":\"phenotype\",\"observed\":\"yes\"},{\"id\":\"HP:0002121\",\"label\":\"Absence seizures\",\"type\":\"phenotype\",\"observed\":\"yes\"},{\"id\":\"HP:0006266\",\"label\":\"Small placenta\",\"type\":\"phenotype\",\"observed\":\"yes\"},{\"id\":\"HP:0007875\",\"label\":\"Congenital blindness\",\"type\":\"phenotype\",\"observed\":\"yes\"},{\"id\":\"HP:0011146\",\"label\":\"Dialeptic seizures\",\"type\":\"phenotype\",\"observed\":\"yes\"},{\"id\":\"HP:0011147\",\"label\":\"Typical absence seizures\",\"type\":\"phenotype\",\"observed\":\"yes\"},{\"id\":\"HP:0200055\",\"label\":\"Small hand\",\"type\":\"phenotype\",\"observed\":\"yes\"}],\"date_of_death\":\"\",\"last_modification_date\":\"2019-01-11T17:31:13.000Z\",\"nonstandard_features\":[],\"prenatal_perinatal_history\":{\"multipleGestation\":null,\"icsi\":null,\"ivf\":null,\"assistedReproduction_donoregg\":null,\"assistedReproduction_iui\":null,\"twinNumber\":\"\",\"assistedReproduction_fertilityMeds\":null,\"gestation\":null,\"assistedReproduction_surrogacy\":null,\"assistedReproduction_donorsperm\":null},\"family_history\":{\"miscarriages\":null,\"consanguinity\":null,\"affectedRelatives\":null},\"genes\":[{\"gene\":\"PLS1\",\"id\":\"ENSG00000120756\",\"strategy\":[\"sequencing\"],\"status\":\"candidate\"},{\"gene\":\"PLS3\",\"id\":\"ENSG00000102024\",\"strategy\":[\"sequencing\"],\"status\":\"candidate\"},{\"gene\":\"QSOX1\",\"id\":\"ENSG00000116260\",\"strategy\":[\"sequencing\"],\"status\":\"solved\"},{\"gene\":\"TXNL1\",\"id\":\"ENSG00000091164\",\"strategy\":[\"sequencing\"],\"status\":\"carrier\"}],\"life_status\":\"alive\",\"sex\":\"M\",\"clinical-diagnosis\":[],\"reporter\":\"TestUser1Uno\",\"last_modified_by\":\"TestUser1Uno\",\"global_age_of_onset\":[{\"id\":\"HP:0003577\",\"label\":\"Congenital onset\"}],\"report_id\":\"P0000009\",\"medical_reports\":[]}\n" +
            "]";

    @Test
    public void importJSONPatient() {
        String importedPID;
        importedPID = currentPage.navigateToLoginPage()
                .loginAsUser()
                .navigateToAllPatientsPage()
                .importJSONPatient(JSONToImport)
                .sortPatientsDateDesc()
                .viewFirstPatientInTable()
                .getPatientID();

        System.out.println("THE ID IS: " + importedPID);

        // TODO: Note: Remember to avoid casting, just pass the driver itself.
        viewPatientPage currentPage2 = new viewPatientPage(theDriver);

        currentPage2
                .editThisPatient()
                .toggleNthConsentBox(1)
                .toggleNthConsentBox(2)
                .toggleNthConsentBox(3)
                .toggleNthConsentBox(4)
                .updateConsent()
                .saveAndViewSummary();

        System.out.println("We just edited: " + currentPage2.getPatientID());

    }
}
