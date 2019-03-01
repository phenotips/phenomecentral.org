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

    protected Map<SECTIONS, By> sectionMap = new HashMap<SECTIONS, By>();

    /**
     * CTOR. Initializes the map from an enum value to a specific element for the section
     *
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
     * Iterates over each section in loSections (specified by enum values) and ensure that they are present on the page.
     * Only checks the title is present, not the actual contents.
     *
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

    /**
     * Sets the global visibility of the patient. Opens the access rights dialogue modal ("Modify Permissions") Defaults
     * to private on invalid input.
     *
     * @param theVisibility is string, one of "private", "matchable", "public". Must be exact.
     */
    @Step("Set patient's global visibility to: {0}")
    public void setGlobalVisibility(String theVisibility)
    {
        clickOnElement(modifyPermissionsBtn);
        waitForElementToBePresent(privateRadioBtn);
        switch (theVisibility) {
            case "private":
                clickOnElement(privateRadioBtn);
                break;
            case "matchable":
                clickOnElement(matchableRadioBtn);
                break;
            case "public":
                clickOnElement(publicRadioBtn);
                break;
            default:
                clickOnElement(privateRadioBtn);
                break;
        }
        clickOnElement(updateConfirmBtn);
        waitForElementToBeClickable(logOutLink);
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
        clickOnElement(modifyPermissionsBtn);
        clickAndTypeOnElement(newCollaboratorBox, collaboratorName);
        clickOnElement(firstCollaboratorResult);
        superDriver.findElement(newCollaboratorBox).sendKeys(Keys.ENTER); // Looks like we'll have to press enter

        List<WebElement> loPrivilageDropdowns = superDriver.findElements(privilageLevelDrps);
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

        forceClickOnElement(updateConfirmBtn);
        waitForElementToBeClickable(logOutLink);
        unconditionalWaitNs(2); // Modal does not seem to be fully closed even when logOut link turns to clickable?
    }

    /**
     * Deletes the Nth collaborator from the permissions modal.
     *
     * @param n is the Nth collaborator (n >= 1) Must supply valid Nth collaborator otherwise array out of bounds
     * exception will be thrown.
     */
    @Step("Remove the {0}th collaborator from the list")
    public void removeNthCollaborator(int n)
    {
        clickOnElement(modifyPermissionsBtn);
        waitForElementToBePresent(newCollaboratorBox);

        List<WebElement> loDeleteCollaboratorBtns = superDriver.findElements(deleteCollaboratorBtn);
        loDeleteCollaboratorBtns.get(n - 1).click();

        forceClickOnElement(updateConfirmBtn);
        waitForElementToBeClickable(logOutLink);
        unconditionalWaitNs(2); // Likewise, the modal does not seem to be fully closed even when logOut is clickable?
    }
}
