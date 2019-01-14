package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import testCases.commonInfoEnums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// Contains selectors for accordion sections on the create and view patient info pages.
public abstract class commonInfoSelectors extends basePage implements commonInfoEnums {

    private final By patientInfoSection = By.id("HPatientinformation"); // "Patient information"
    private final By familyHistorySection = By.id("HFamilyhistoryandpedigree"); // "Family history and pedigree"
    private final By prenatalHistorySection = By.id("HPrenatalandperinatalhistory"); // Prenatal and perinatal history
    private final By medicalHistorySection = By.id("HMedicalhistory"); // Medical history
    private final By measurementsSection = By.id("HMeasurements"); // Measurements
    private final By clinicalSymptomsSection = By.id("HClinicalsymptomsandphysicalfindings"); // Clinical symptoms and physical findings
    private final By suggestedGenesSection = By.id("HSuggestedGenes"); // Suggested Genes
    private final By genotypeInfoSection = By.id("HGenotypeinformation"); // Genotype information
    private final By diagnosisSection = By.id("HDiagnosis"); // Diagnosis
    private final By similarCasesSection = By.id("HSimilarcases"); // Similar cases

    Map<SECTIONS, By> sectionMap = new HashMap<SECTIONS, By>();

    public commonInfoSelectors(WebDriver aDriver) {
        super(aDriver);
        sectionMap.put(SECTIONS.ClinicalSymptomsSection, clinicalSymptomsSection);
        sectionMap.put(SECTIONS.DiagnosisSection, diagnosisSection);
        sectionMap.put(SECTIONS.FamilyHistorySection,familyHistorySection);
        sectionMap.put(SECTIONS.GenotypeInfoSection,genotypeInfoSection);
        sectionMap.put(SECTIONS.MeasurementSection,measurementsSection);
        sectionMap.put(SECTIONS.MedicalHistorySection,medicalHistorySection);
        sectionMap.put(SECTIONS.PatientInfoSection,patientInfoSection);
        sectionMap.put(SECTIONS.PrenatalHistorySection,prenatalHistorySection);
        sectionMap.put(SECTIONS.SuggestedGenesSection,suggestedGenesSection);
        sectionMap.put(SECTIONS.SimilarCasesSection, similarCasesSection);
        System.out.println("CTOR in commonInfoSelectors: Map is: " + sectionMap);
    }


    public Boolean checkForVisibleSections(SECTIONS[] loSections) {
        for (SECTIONS aSection : loSections) {
            Boolean presence = isElementPresent(sectionMap.get(aSection));
            System.out.println("commonInfoSelectors Line 44");
            if (!presence) {return false;}
//            switch (aSection) {
//                case ClinicalSymptomsSection: presence = isElementPresent(clinicalSymptomsSection); break;
//                case SECTIONS.DiagnosisSection: presence = isElementPresent(diagnosisSection); break;
//                case SECTIONS.FamilyHistorySection: presence = isElementPresent(familyHistorySection); break;
//                case SECTIONS.GenotypeInfoSection: presence = isElementPresent(genotypeInfoSection); break;
//                case SECTIONS.MeasurementSection: presence = isElementPresent(measurementsSection); break;
//                case SECTIONS.MedicalHistorySection: presence = isElementPresent(medicalHistorySection); break;
//                case SECTIONS.PatientInfoSection: presence = isElementPresent(patientInfoSection); break;
//                case SECTIONS.PrenatalHistorySection: presence = isElementPresent(prenatalHistorySection); break;
//                case SECTIONS.SuggestedGenesSection: presence = isElementPresent(suggestedGenesSection); break;
//                default: presence = false; break;
//            }
//            if (!presence) {return false;}
        }
        return true;
    }

}
