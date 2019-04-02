/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/
 */
package org.phenotips.endtoendtests.common;

/**
 * This class provides common enums used by both org.phenotips.endtoendtests.pageobjects and in org.phenotips.endtoendtests.test cases
 * too. This level of indirection is actually optional for our usage. It allows for the headings name to change and then
 * we can update the enum without touching the selector.
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
