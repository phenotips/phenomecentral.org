package org.phenotips.testcases;

/**
 * This class provides common enums used by both org.phenotips.pageobjects and can be used in test cases too.
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

    // Privilage levels
    enum PRIVILAGE
    {
        CanView,
        CanViewAndModify,
        CanViewAndModifyAndManageRights
    }

    // Visibility Levels
    enum VISIBILITY
    {
        Private,
        Matchable,
        Public
    }
}
