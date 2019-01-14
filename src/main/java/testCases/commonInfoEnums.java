package testCases;

// This class provides common enums used by both pageObjects and can be used in test cases too
public interface commonInfoEnums {
    // Public enum for friendly names
    public enum SECTIONS {
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
