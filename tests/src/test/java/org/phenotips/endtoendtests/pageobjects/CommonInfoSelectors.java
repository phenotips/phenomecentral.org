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
package org.phenotips.endtoendtests.pageobjects;

import org.phenotips.endtoendtests.common.CommonInfoEnums;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import io.qameta.allure.Step;

/**
 * Contains common selectors for the accordion sections on the create and view patient info pages. Ex.
 * http://localhost:8083/data/P0000015 and http://localhost:8083/edit/data/P0000015
 */
public abstract class CommonInfoSelectors extends BasePage implements CommonInfoEnums
{
    private final By modifyPermissionsBtn = By.cssSelector("span[title=\"Modify visibility and collaborations\"]");

    private final By privateRadioBtn = By.cssSelector("input[type=radio][name=visibility][value=private]");

    private final By matchableRadioBtn = By.cssSelector("input[type=radio][name=visibility][value=matchable]");

    private final By publicRadioBtn = By.cssSelector("input[type=radio][name=visibility][value=public]");

    private final By newCollaboratorBox = By.id("new-collaborator-input");

    private final By firstCollaboratorResult = By.cssSelector("div.suggestItem > div.user > div.user-name");

    private final By updateConfirmBtn = By.cssSelector("input.button[value=Update]");

    private final By privilageLevelDrps = By.cssSelector("select[name=accessLevel]");

    private final By deleteCollaboratorBtn = By.cssSelector("span[title=\"Remove this collaborator\"]");

    // Selectors for the sections below
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

    protected Map<SECTIONS, By> sectionMap = new HashMap<>();

    /**
     * CTOR. Initializes the map from an enum value to a specific element for the section
     *
     * @param aDriver is not {@code null}
     */
    public CommonInfoSelectors(WebDriver aDriver)
    {
        super(aDriver);
        this.sectionMap.put(SECTIONS.ClinicalSymptomsSection, this.clinicalSymptomsSection);
        this.sectionMap.put(SECTIONS.DiagnosisSection, this.diagnosisSection);
        this.sectionMap.put(SECTIONS.FamilyHistorySection, this.familyHistorySection);
        this.sectionMap.put(SECTIONS.GenotypeInfoSection, this.genotypeInfoSection);
        this.sectionMap.put(SECTIONS.MeasurementSection, this.measurementsSection);
        this.sectionMap.put(SECTIONS.MedicalHistorySection, this.medicalHistorySection);
        this.sectionMap.put(SECTIONS.PatientInfoSection, this.patientInfoSection);
        this.sectionMap.put(SECTIONS.PrenatalHistorySection, this.prenatalHistorySection);
        this.sectionMap.put(SECTIONS.SuggestedGenesSection, this.suggestedGenesSection);
        this.sectionMap.put(SECTIONS.SimilarCasesSection, this.similarCasesSection);
    }

    /**
     * Iterates over each section in loSections (specified by enum values) and ensure that they are present on the page.
     * Only checks the title is present, not the actual contents.
     *
     * @param loSections a possibly empty array of sections specified by the SECTIONS enum
     * @return True if all the sections in loSections are visible and false if one of them isn't.
     */
    public Boolean checkForVisibleSections(SECTIONS[] loSections)
    {
        for (SECTIONS aSection : loSections) {
            Boolean presence = isElementPresent(this.sectionMap.get(aSection));
            System.out.println("CommonInfoSelectors Line 44");
            if (!presence) {
                return false;
            }
        }
        return true;
    }

    /**
     * Sets the global visibility of the patient. Opens the access rights dialogue modal ("Modify Permissions") Defaults
     * to private on invalid input.
     *
     * @param theVisibility is string, one of "private", "matchable", "public". Must be exact.
     */
    @Step("Set patient's global visibility to: {0}")
    public void setGlobalVisibility(String theVisibility)
    {
        clickOnElement(this.modifyPermissionsBtn);
        waitForElementToBePresent(this.privateRadioBtn);
        switch (theVisibility) {
            case "private":
                clickOnElement(this.privateRadioBtn);
                break;
            case "matchable":
                clickOnElement(this.matchableRadioBtn);
                break;
            case "public":
                clickOnElement(this.publicRadioBtn);
                break;
            default:
                clickOnElement(this.privateRadioBtn);
                break;
        }
        clickOnElement(this.updateConfirmBtn);
        waitForElementToBeClickable(this.logOutLink);
        unconditionalWaitNs(
            2); // This might be needed still. For some reason, modal does not close immediately and nothing to wait for.
    }

    /**
     * Adds a collaborator to the patient via the "Modify Permissions" modal. Searches for name and clicks on first
     * result, therefore, assumes that there is at least one result. Sets the privilage to the one specified.
     *
     * @param collaboratorName is the exact name of the collaborator to add.
     * @param privilageLevel is one of three levels of privilages specified by the PRIVIALGE enum.
     */
    @Step("Add collaborator to patient with: Collaborator name: {0} and Privilage level of: {1}")
    public void addCollaboratorToPatient(String collaboratorName, PRIVILAGE privilageLevel)
    {
        clickOnElement(this.modifyPermissionsBtn);
        clickAndTypeOnElement(this.newCollaboratorBox, collaboratorName);
        clickOnElement(this.firstCollaboratorResult);
        // Looks like we'll have to press enter
        this.superDriver.findElement(this.newCollaboratorBox).sendKeys(Keys.ENTER);

        List<WebElement> loPrivilageDropdowns = this.superDriver.findElements(this.privilageLevelDrps);
        Select bottomMostPDrop = new Select(loPrivilageDropdowns.get(loPrivilageDropdowns.size() - 1));

        switch (privilageLevel) {
            case CanView:
                bottomMostPDrop.selectByVisibleText("Can view the record");
                break;
            case CanViewAndModify:
                bottomMostPDrop.selectByVisibleText("Can view and modify the record");
                break;
            case CanViewAndModifyAndManageRights:
                bottomMostPDrop.selectByVisibleText("Can view and modify the record and manage access rights");
                break;
        }

        forceClickOnElement(this.updateConfirmBtn);
        waitForElementToBeClickable(this.logOutLink);
        unconditionalWaitNs(2); // Modal does not seem to be fully closed even when logOut link turns to clickable?
    }

    /**
     * Deletes the Nth collaborator from the permissions modal.
     *
     * @param n is the Nth collaborator (n >= 1) Must supply valid Nth collaborator otherwise array out of bounds
     *            exception will be thrown.
     */
    @Step("Remove the {0}th collaborator from the list")
    public void removeNthCollaborator(int n)
    {
        clickOnElement(this.modifyPermissionsBtn);
        waitForElementToBePresent(this.newCollaboratorBox);

        List<WebElement> loDeleteCollaboratorBtns = this.superDriver.findElements(this.deleteCollaboratorBtn);
        loDeleteCollaboratorBtns.get(n - 1).click();

        forceClickOnElement(this.updateConfirmBtn);
        waitForElementToBeClickable(this.logOutLink);
        unconditionalWaitNs(2); // Likewise, the modal does not seem to be fully closed even when logOut is clickable?
    }
}
