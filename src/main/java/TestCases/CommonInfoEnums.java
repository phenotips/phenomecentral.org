package TestCases;

/**
 * This class provides common enums used by both PageObjects and can be used in test cases too.
 * This level of indirection is actually optional for our usage. It allows for the headings
 * name to change and then we can update the enum without touching the selector.
  */

public interface CommonInfoEnums
{
    // Public enum for friendly names
    enum SECTIONS
    {
        PatientInfoSection,
        FamilyHistorySection,
        PrenatalHistorySection,
        MedicalHistorySection,
        MeasurementSection,
        ClinicalSymptomsSection,
        SuggestedGenesSection,
        GenotypeInfoSection,
        DiagnosisSection,
        SimilarCasesSection
    }
}
