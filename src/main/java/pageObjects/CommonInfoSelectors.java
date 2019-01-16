package pageObjects;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import testCases.commonInfoEnums;

/** Contains common selectors for the accordion sections on the create and view patient info pages.
 *  Ex. http://localhost:8083/data/P0000015 and http://localhost:8083/edit/data/P0000015
 */
public abstract class CommonInfoSelectors extends BasePage implements commonInfoEnums
{
    private final By patientInfoSection = By.id("HPatientinformation"); // "Patient information"

    private final By familyHistorySection = By.id("HFamilyhistoryandpedigree"); // "Family history and pedigree"

    private final By prenatalHistorySection = By.id("HPrenatalandperinatalhistory"); // Prenatal and perinatal history

    private final By medicalHistorySection = By.id("HMedicalhistory"); // Medical history

    private final By measurementsSection = By.id("HMeasurements"); // Measurements

    private final By clinicalSymptomsSection = By.id("HClinicalsymptomsandphysicalfindings");
        // Clinical symptoms and physical findings

    private final By suggestedGenesSection = By.id("HSuggestedGenes"); // Suggested Genes

    private final By genotypeInfoSection = By.id("HGenotypeinformation"); // Genotype information

    private final By diagnosisSection = By.id("HDiagnosis"); // Diagnosis

    private final By similarCasesSection = By.id("HSimilarcases"); // Similar cases

    private Map<SECTIONS, By> sectionMap = new HashMap<SECTIONS, By>();

    /**
     * CTOR. Initializes the map from an enum value to a specific element for the section
     * @param aDriver is not {@code null}
     */
    public CommonInfoSelectors(WebDriver aDriver)
    {
        super(aDriver);
        sectionMap.put(SECTIONS.ClinicalSymptomsSection, clinicalSymptomsSection);
        sectionMap.put(SECTIONS.DiagnosisSection, diagnosisSection);
        sectionMap.put(SECTIONS.FamilyHistorySection, familyHistorySection);
        sectionMap.put(SECTIONS.GenotypeInfoSection, genotypeInfoSection);
        sectionMap.put(SECTIONS.MeasurementSection, measurementsSection);
        sectionMap.put(SECTIONS.MedicalHistorySection, medicalHistorySection);
        sectionMap.put(SECTIONS.PatientInfoSection, patientInfoSection);
        sectionMap.put(SECTIONS.PrenatalHistorySection, prenatalHistorySection);
        sectionMap.put(SECTIONS.SuggestedGenesSection, suggestedGenesSection);
        sectionMap.put(SECTIONS.SimilarCasesSection, similarCasesSection);
    }

    /**
     * Iterates over each section in loSections (specified by enum values) and ensure that they
     * are present on the page. Only checks the title is present, not the actual contents.
     * @param loSections a possibly empty array of sections specified by the SECTIONS enum
     * @return True if all the sections in loSections are visible and false if one of them isn't.
     */
    public Boolean checkForVisibleSections(SECTIONS[] loSections)
    {
        for (SECTIONS aSection : loSections) {
            Boolean presence = isElementPresent(sectionMap.get(aSection));
            System.out.println("CommonInfoSelectors Line 44");
            if (!presence) {
                return false;
            }
        }
        return true;
    }
}
