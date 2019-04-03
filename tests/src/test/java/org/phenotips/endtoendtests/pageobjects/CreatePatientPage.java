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

import org.phenotips.endtoendtests.common.CommonPatientMeasurement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import io.qameta.allure.Step;

/**
 * Represents the page reached when "Create... -> New patient" is clicked on the navbar Ex.
 * http://localhost:8083/edit/data/Pxxxxxxx (new patient ID)
 */
public class CreatePatientPage extends CommonInfoSelectors
{
    ////////////////////////////////////////
    // "Patient Consent" (Consents Granted) Section - Selectors
    ////////////////////////////////////////

    private final By realPatientConsentBox = By.id("real-consent-checkbox");

    private final By geneticConsentBox = By.id("genetic-consent-checkbox");

    private final By shareHistoryConsentBox = By.id("share_history-consent-checkbox");

    private final By shareImagesConsentBox = By.id("share_images-consent-checkbox");

    private final By matchingConsentBox = By.id("matching-consent-checkbox");

    private final By patientConsentUpdateBtn = By.cssSelector("#patient-consent-update .button");

    ////////////////////////////////////////
    // "Patient Information" Section - Selectors
    ////////////////////////////////////////

    private final By identifierBox = By.id("PhenoTips.PatientClass_0_external_id");

    private final By lifeStatusDrp = By.id("PhenoTips.PatientClass_0_life_status");

    private final By dobMonthDrp = By.cssSelector("#date-of-birth-block select.month");

    private final By dobYearDrp = By.cssSelector("#date-of-birth-block select.year");

    private final By doDeathMonthDrp = By.cssSelector("#date-of-death-block select.month");

    private final By doDeathYearDrp = By.cssSelector("#date-of-death-block select.year");

    private final By maleGenderBtn = By.cssSelector(".gender input[value=M]");

    private final By femaleGenderBtn = By.cssSelector(".gender input[value=F]");

    private final By otherGenderBtn = By.cssSelector(".gender input[value=O]");

    private final By unknownGenderBtn = By.cssSelector(".gender input[value=U]");

    private final By congenitalOnsentBtn = By.id("PhenoTips.PatientClass_0_global_age_of_onset_HP:0003577");

    private final By ageOfOnsetBtns = By.cssSelector("div.global_age_of_onset > div > div  > ul > li.term-entry");

    private final By modeOfInheritanceChkboxes =
        By.cssSelector("div.global_mode_of_inheritance > div > div > ul > li.term-entry");

    private final By indicationForReferralBox = By.id("PhenoTips.PatientClass_0_indication_for_referral");

    ////////////////////////////////////////
    // "Family history and pedigree" Section - Selectors
    ////////////////////////////////////////

    private final By editPedigreeBox = By.cssSelector("#pedigree-thumbnail");

    private final By editPedigreeOKBtn = By.cssSelector(".pedigree-family-chooser input.button[name=ok]");

    private final By assignFamilyRadioBtn = By.id("pedigreeInputAssignFamily");

    private final By familySearchInputBox = By.id("family-search-input");

    private final By firstFamilySuggestion = By.cssSelector("span.suggestValue");

    private final By paternalEthnicityBox = By.id("PhenoTips.PatientClass_0_paternal_ethnicity_2");

    private final By maternalEthnicityBox = By.id("PhenoTips.PatientClass_0_maternal_ethnicity_2");

    private final By addEthnicityBtns = By.cssSelector(".fieldset.ethnicity a[title=add]");

    private final By healthConditionsFoundInFamily = By.id("PhenoTips.PatientClass_0_family_history");

    ////////////////////////////////////////
    // "Prenatal and perinatal history" Section - Selectors
    ////////////////////////////////////////
    private final By termBirthCheckbox = By.id("PhenoTips.PatientClass_0_gestation_term");

    private final By gestationWeeksBox =
        By.cssSelector("input[type=text][name='PhenoTips.PatientClass_0_gestation']");

    private final By maternalAgeBox = By.id("PhenoTips.ParentalInformationClass_0_maternal_age");

    private final By paternalAgeBox = By.id("PhenoTips.ParentalInformationClass_0_paternal_age");

    private final By apgarScore1MinDrp = By.id("PhenoTips.PatientClass_0_apgar1");

    private final By apgarScore5MinDrp = By.id("PhenoTips.PatientClass_0_apgar5");

    private final By otherPregnancyPhenotypeBox = By.xpath(
        "//*[@id='HPregnancy-history']/parent::*/div[@class='prenatal_phenotype-other custom-entries']/input[@type='text']");

    private final By otherDevelopmentPhenotypeBox = By.xpath(
        "//*[@id='HPrenatal-development']/parent::*/div[@class='prenatal_phenotype-other custom-entries']/input[@type='text']");

    private final By otherDeliveryPhenotypeBox = By.xpath(
        "//*[@id='HDelivery']/parent::*/div[@class='prenatal_phenotype-other custom-entries']/input[@type='text']");

    private final By otherGrowthPhenotypeBox = By.xpath(
        "//*[@id='HNeonatal-growth-parameters']/parent::*/div[@class='prenatal_phenotype-other custom-entries']/input[@type='text']");

    private final By otherComplicationsPhenotypeBox = By.xpath(
        "//*[@id='HPerinatal-complications']/parent::*/div[@class='prenatal_phenotype-other custom-entries']/input[@type='text']");

    private final By prenatalNotesBox = By.id("PhenoTips.PatientClass_0_prenatal_development");

    /*******************************************************
     * "Measurements" Section - Selectors
     *******************************************************/
    private final By addMeasurementBtn = By.cssSelector("div.measurement-info a.add-data-button");

    private final By measurementYearDrps = By.cssSelector("div.calendar_date_select select.year");

    private final By measurementMonthDrps = By.cssSelector("div.calendar_date_select select.month");

    private final By todayCalendarLink = By.linkText("Today");

    private final By measurementDateBoxes = By.cssSelector("input[id^='PhenoTips.MeasurementsClass_'][id$='date']");

    // Find all boxes, prepare support for multiple measurements
    private final String measurementSelectorPrefix = "input[id^='PhenoTips.MeasurementsClass_']";

    private final By weightBoxes = By.cssSelector(measurementSelectorPrefix + "[id$='weight']");

    private final By heightBoxes = By.cssSelector(measurementSelectorPrefix + "[id$='height']");

    private final By armSpanBoxes = By.cssSelector(measurementSelectorPrefix + "[id$='armspan']");

    private final By sittingHeightBoxes = By.cssSelector(measurementSelectorPrefix + "[id$='sitting']");

    private final By headCircumferenceBoxes = By.cssSelector(measurementSelectorPrefix + "[id$='hc']");

    private final By philtrumLengthBoxes = By.cssSelector(measurementSelectorPrefix + "[id$='philtrum']");

    private final By leftEarLengthBoxes = By.cssSelector(measurementSelectorPrefix + "[id$='ear']");

    private final By rightEarLengthBoxes = By.cssSelector(measurementSelectorPrefix + "[id$='ear_right']");

    private final By outherCanthalDistanceBoxes = By.cssSelector(measurementSelectorPrefix + "[id$='ocd']");

    private final By innterCanthalDistanceBoxes = By.cssSelector(measurementSelectorPrefix + "[id$='icd']");

    private final By palpebralFissureLengthBoxes = By.cssSelector(measurementSelectorPrefix + "[id$='pfl']");

    private final By interpupilaryDistanceBoxes = By.cssSelector(measurementSelectorPrefix + "[id$='ipd']");

    private final By leftHandLengthBoxes = By.cssSelector(measurementSelectorPrefix + "[id$='hand']");

    private final By leftPalmLengthBoxes = By.cssSelector(measurementSelectorPrefix + "[id$='palm']");

    private final By leftFootLengthBoxes = By.cssSelector(measurementSelectorPrefix + "[id$='foot']");

    private final By rightHandLengthBoxes = By.cssSelector(measurementSelectorPrefix + "[id$='hand_right']");

    private final By rightPalmLengthBoxes = By.cssSelector(measurementSelectorPrefix + "[id$='palm_right']");

    private final By rightFootLengthBoxes = By.cssSelector(measurementSelectorPrefix + "[id$='foot_right']");

    ////////////////////////////////////////
    // "Clinical symptoms and physical findings" (Phenotypes) Section - Selectors
    ////////////////////////////////////////

    private final By phenotypeSearchBox = By.id("quick-phenotype-search");

    // Xwiki suggestion dialogue is overlayed ontop of entire page document rather than underneath
    // the phenotype section div. Hence the vague selector.
    private final By firstPhenotypeSuggestion = By.cssSelector("li.xitem > div");

    private final By addPhenotypeDetailsBtns = By.cssSelector("button.add");

    private final By editPhenotypeDetailsBtns = By.cssSelector("button.edit");

    private final By expandCaretBtns = By.cssSelector(
        "div.phenotype-details.focused span.collapse-button, div.phenotype-details.focused span.expand-tool");

    private final By phenotypeDetailsLabels = By.cssSelector("div.phenotype-details.focused label");

    private final By phenotypesSelectedLabels = By.cssSelector("div.summary-item > label.yes");

    // Different selectors for when the thunderbolt symbol is present or not. Lightning appears when auto added
    // by measurement information.
    private final By phenotypesAutoSelectedByMeasurementLabels =
        By.xpath("//div[@class='summary-item' and span[@class='fa fa-bolt']]/label[@class='yes']");

    private final By phenotypesManuallySelectedLabels =
        By.xpath("//div[@class='summary-item' and not(span[@class='fa fa-bolt'])]/label[@class='yes']");

    ////////////////////////////////////////
    // "Genotype information" Section - Selectors
    ////////////////////////////////////////

    private final By addGeneBtn = By.cssSelector("a[title*='Add gene']");

    private final By geneNameBoxes = By.cssSelector(
        "#extradata-list-PhenoTips\\.GeneClass_PhenoTips\\.GeneVariantClass > tbody > tr > td:nth-child(2) > input[type=text]");

    private final By geneStatusDrps = By.cssSelector("td.Status > select");

    private final By geneStrategySequencingCheckboxes = By.cssSelector(
        "td.Strategy > label > input[value=sequencing]");

    private final By geneStrategyDeletionCheckboxes = By.cssSelector(
        "td.Strategy > label > input[value=deletion]");

    private final By geneStrategyFamilialMutationCheckboxes = By.cssSelector(
        "td.Strategy > label > input[value=familial_mutation]");

    private final By geneStrategyCommonMutationCheckboxes = By.cssSelector(
        "td.Strategy > label > input[value=common_mutations]");

    private final By firstGeneSuggestion = By.cssSelector("div.suggestItem > div > span.suggestValue");
    // First suggestion result for prenatal phenotypes too

    ////////////////////////////////////////
    // "Diagnosis" Section - Selectors
    ////////////////////////////////////////

    private final By clinicalDiagnosisBox = By.id("PhenoTips.PatientClass_0_clinical_diagnosis");

    private final By clinicalDiagnosisCheckboxes =
        By.cssSelector("input[id*='PhenoTips.PatientClass_0_clinical_diagnosis_ORDO:']");

    private final By finalDiagnosisBox = By.id("PhenoTips.PatientClass_0_omim_id");

    private final By finalDiagnosisCheckboxes = By.cssSelector("input[id*='PhenoTips.PatientClass_0_omim_id_']");

    private final By additionalCommentsBox = By.id("PhenoTips.PatientClass_0_diagnosis_notes");

    private final By caseSolvedCheckbox = By.id("PhenoTips.PatientClass_0_solved");

    private final By pubMedIDBoxes = By.cssSelector("[id*='PhenoTips.PatientClass_0_solved__pubmed_id_']");

    private final By addPubMedLink = By.cssSelector("div.diagnosis-info a[title=add]");

    private final By deletePubMedBtns = By.cssSelector("div.diagnosis-info a[title='delete']");

    private final By pubMedArticle = By.cssSelector("div.article-info");

    private final By pubMedIDCheckStatus =
        By.cssSelector("div.solved__pubmed_id > div.solved__pubmed_id > div > ol > li > div");

    private final By resolutionNotesBox = By.id("PhenoTips.PatientClass_0_solved__notes");

    ////////////////////////////////////////
    // Common Tree Traversal Selectors and Strings
    ////////////////////////////////////////

    // For tree traversals
    private final By ageOfOnsetAndModeInheritanceChildBtn = By.cssSelector("ul > li.term-entry > input");

    private final By ageOfOnsetAndModeInheritanceChildLabels = By.cssSelector("ul > li.term-entry > label");

    private final String yesBtnSelectorString = "div.displayed-value > span.yes-no-picker > label.yes";

    private final String labelSelectorString = "div.displayed-value > label.yes-no-picker-label";

    private final By expandToolSpan = By.cssSelector("span[class=expand-tool]");

    // Common Selectors for page

    private final By saveAndViewSummaryBtn = By.cssSelector("div.bottombuttons input[value='Save and view summary']");

    private final By quickSaveBtn = By.cssSelector("div.bottombuttons input[value='Quick save']");

    private final By cancelChangesSinceSaveBtn =
        By.cssSelector("div.bottombuttons input[value='Cancel changes since last save']");

    public CreatePatientPage(WebDriver aDriver)
    {
        super(aDriver);
    }

    ////////////////////////////////////////
    // Common Methods
    ////////////////////////////////////////

    /**
     * Hits the "Save and View Summary" button on the bottom left.
     *
     * @return navigating to the view page containing patient's full details so a new object of that type
     */
    @Step("Save and View Summary of patient form")
    public ViewPatientPage saveAndViewSummary()
    {
        clickOnElement(this.saveAndViewSummaryBtn);
        return new ViewPatientPage(this.superDriver);
    }

    /**
     * Hits the "Quick Save" button on the bottom left.
     *
     * @return stay on the same page so return the same object
     */
    @Step("Quick Save of patient form")
    public CreatePatientPage quickSave()
    {
        clickOnElement(this.quickSaveBtn);
        return this;
    }

    /**
     * Hits the "Cancel changes since last save" button on the bottom right.
     *
     * @return navigating to the view page containing patient's full details so a new object of that type
     */
    @Step("Cancel changes on patient form")
    public ViewPatientPage cancelChanges()
    {
        clickOnElement(this.cancelChangesSinceSaveBtn);
        return new ViewPatientPage(this.superDriver);
    }

    ////////////////////////////////////////
    // Consent Methods
    ////////////////////////////////////////

    /**
     * Toggles the nth consent checkbox in the "Consents granted" section
     *
     * @param n which is an integer between 1-5 representing the specified checkbox.
     * @return the same object as we are on the same page
     */
    @Step("Toggle the {0}th consent box")
    public CreatePatientPage toggleNthConsentBox(int n)
    {
        switch (n) {
            case 1:
                clickOnElement(this.realPatientConsentBox);
                break;
            case 2:
                clickOnElement(this.geneticConsentBox);
                break;
            case 3:
                clickOnElement(this.shareHistoryConsentBox);
                break;
            case 4:
                clickOnElement(this.shareImagesConsentBox);
                break;
            case 5:
                clickOnElement(this.matchingConsentBox);
                break;
            default:
                System.out.println("Invalid nth consent box specified: " + n);
                break;
        }
        return this;
    }

    /**
     * Helper method to toggle the first four consent boxes. These boxes are unchecked when first creating a patient
     * (Real patient, Genetic Sequencing data consent, Medical/Family History consent, Medical Images/Photos consent)
     *
     * @return Stay on the same page, so return the same object.
     */
    public CreatePatientPage toggleFirstFourConsentBoxes()
    {
        toggleNthConsentBox(1);
        toggleNthConsentBox(2);
        toggleNthConsentBox(3);
        toggleNthConsentBox(4);
        return this;
    }

    /**
     * Clicks on the "Update" button under the "Consents granted" section. Waits 5 seconds for consent to update.
     *
     * @return same object as we stay on the same page
     */
    @Step("Click on 'Update' button for consent")
    public CreatePatientPage updateConsent()
    {
        clickOnElement(this.patientConsentUpdateBtn);
        unconditionalWaitNs(5); // No element to wait on as the state of the consents might not have changed.
        return this;
    }

    ////////////////////////////////////////
    // "Patient Information" Section - Methods
    ////////////////////////////////////////

    /**
     * Clears and then sets the patient identifer field box.
     *
     * @param identifer the string that should be entered into the "Identifer" field under Patient Information
     * @return stay on the same page so return the same instance of object
     */
    @Step("Set the patient's identifier to: {0}")
    public CreatePatientPage setIdentifer(String identifer)
    {
        clickOnElement(this.identifierBox);
        this.superDriver.findElement(this.identifierBox).clear();
        clickAndTypeOnElement(this.identifierBox, identifer);
        unconditionalWaitNs(1); // Gives "identifier already exists" if we navigate away too fast.
        return this;
    }

    /**
     * Sets the Life Status dropdown of the patient.
     *
     * @param status is either "Alive" or "Deceased". Must be exact string otherwise Selenium throws
     *            NoSuchElementException exception.
     * @return stay on the same page so return same object.
     */
    @Step("Set patient's life status to: {0}")
    public CreatePatientPage setLifeStatus(String status)
    {
        waitForElementToBePresent(this.lifeStatusDrp);
        Select statusDrp = new Select(this.superDriver.findElement(this.lifeStatusDrp));

        statusDrp.selectByVisibleText(status);

        return this;
    }

    /**
     * Sets the Date of Birth of the patient under Patient Information. In case of invalid params or unable to find
     * selection, Selenium throws NoSuchElementException exception.
     *
     * @param month the Month as a String (01 - 12). Must exactly match the dropdown.
     * @param year the year as a String (1500s - 2019). Must exactly match the dropdown.
     * @return stay on the same page so return same object.
     */
    @Step("Set the DOB for the patient to: {0} month and {1} year")
    public CreatePatientPage setDOB(String month, String year)
    {
        Select monthDrp;
        Select yearDrp;

        waitForElementToBePresent(this.dobMonthDrp);
        monthDrp = new Select(this.superDriver.findElement(this.dobMonthDrp));
        yearDrp = new Select(this.superDriver.findElement(this.dobYearDrp));

        monthDrp.selectByVisibleText(month);
        yearDrp.selectByVisibleText(year);

        return this;
    }

    /**
     * Sets the Date of Death of the patient under Patient Information. In case of invalid params or unable to find
     * selection, Selenium throws NoSuchElementException exception. Requires: Life Status set to "Deceased" so that Date
     * of Death dropdowns are visible.
     *
     * @param month the Month as a String (01 - 12). Must exactly match the dropdown.
     * @param year the year as a String (1500s - 2019). Must exactly match the dropdown.
     * @return stay on the same page so return same object.
     */
    @Step("Set the date of death for the patient to: {0} month and {1} year")
    public CreatePatientPage setDateOfDeath(String month, String year)
    {
        Select monthDrp;
        Select yearDrp;

        waitForElementToBePresent(this.doDeathMonthDrp);
        monthDrp = new Select(this.superDriver.findElement(this.doDeathMonthDrp));
        yearDrp = new Select(this.superDriver.findElement(this.doDeathYearDrp));

        monthDrp.selectByVisibleText(month);
        yearDrp.selectByVisibleText(year);

        return this;
    }

    /**
     * Toggles the expansion of the given section. Forcibly scrolls elements into view using JS. For some reason,
     * selenium doesn't do this.
     *
     * @param theSection is the section from the enum that we want to expand
     * @return stay on the same page so the same object
     */
    public CreatePatientPage expandSection(SECTIONS theSection)
    {
        forceScrollToElement(this.sectionMap.get(theSection));

        clickOnElement(this.sectionMap.get(theSection));
        return this;
    }

    /**
     * Sets the patient's gender. Defaults to Unknown. Assumes that Patient Information section is expanded (selectors
     * in that section visible).
     *
     * @param theGender is String representing gender radio button. Must be exact text.
     * @return the same page so the same object.
     */
    @Step("Set the gender of the patient to: {0}")
    public CreatePatientPage setGender(String theGender)
    {
        waitForElementToBePresent(this.maleGenderBtn);
        switch (theGender) {
            case "Male":
                clickOnElement(this.maleGenderBtn);
                break;
            case "Female":
                clickOnElement(this.femaleGenderBtn);
                break;
            case "Other":
                clickOnElement(this.otherGenderBtn);
                break;
            case "Unknown":
                clickOnElement(this.unknownGenderBtn);
                break;
            default:
                System.out.println("Invalid gender selected! Default to Unknown");
                break;
        }
        return this;
    }

    /**
     * Sets the Age of Onset under patient information. Currently, only works for congential onset. Assumes that Patient
     * Information section is expanded (selectors in that section visible).
     *
     * @param theOnset onset specified by radio button text, must match exactly.
     * @return Stay on the same page, return same object.
     */
    @Step("Set the Age of Onset for the patient to: {0}")
    public CreatePatientPage setOnset(String theOnset)
    {
        waitForElementToBePresent(this.congenitalOnsentBtn);

        switch (theOnset) {
            default:
                clickOnElement(this.congenitalOnsentBtn);
                break;
        }
        return this;
    }

    /**
     * Traverses through all the options for the age of onset buttons, clicks on each one.
     *
     * @return a List of Strings which represent the Age of Onset radio button labels in a 'pre-order' traversal.
     */
    @Step("Traverse through UI for Age of Onset")
    public List<String> cycleThroughAgeOfOnset()
    {

        List<String> loLabels =
            preOrderTraverseAndClick(this.ageOfOnsetBtns,
                this.ageOfOnsetAndModeInheritanceChildBtn,
                this.ageOfOnsetAndModeInheritanceChildLabels);

        clickOnElement(this.congenitalOnsentBtn);

        return loLabels;
    }

    /**
     * Traverses through all the options for the mode of inheritance checkboxes.
     *
     * @return a List of Strings which represent the Mode Of Inheritance checkbox labels in a 'pre-order' traversal.
     */
    @Step("Traverse through UI for the Mode of Inheritance")
    public List<String> cycleThroughModeOfInheritance()
    {

        return preOrderTraverseAndClick(this.modeOfInheritanceChkboxes,
            this.ageOfOnsetAndModeInheritanceChildBtn,
            this.ageOfOnsetAndModeInheritanceChildLabels);
    }

    /**
     * Sets the "Indication for Referral box" in the Patient Information section. Currently, it does not clear the
     * contents of the box, just concatenates to whatever is there.
     *
     * @param neededText is the String to enter into the input box.
     * @return stay on the same page so return the same object.
     */
    @Step("Set the indication for referral to: {0}")
    public CreatePatientPage setIndicationForReferral(String neededText)
    {
        clickAndTypeOnElement(this.indicationForReferralBox, neededText);
        return this;
    }

    ////////////////////////////////////////
    // "Family history and pedigree" Section - Methods
    ////////////////////////////////////////

    /**
     * Navigates to the Pedigree Editor page by clicking "OK" on the "Assign patient to family" modal. Assumes that the
     * "Create a new family" radio option is selected by default. Requires the "Family History" section to be expanded
     *
     * @param familyName is the family name to base the pedigree off of. Pass "" (empty string) to specify the "Create a
     *            new family" radio option. Otherwise, this must be a valid existing family name.
     * @return we navigate to the Pedigree Editor page so return new instance of that.
     */
    @Step("Navigate to the pedigree editor (from patient form) with family name: {0}")
    public PedigreeEditorPage navigateToPedigreeEditor(String familyName)
    {
        clickOnElement(this.editPedigreeBox);

        // Case where we want to specify a family, also need to ensure that the dialogue is actually there.
        if (!familyName.equals("") && isElementPresent(this.editPedigreeOKBtn)) {
            clickOnElement(this.assignFamilyRadioBtn);
            clickAndTypeOnElement(this.familySearchInputBox, familyName);
            clickOnElement(this.firstFamilySuggestion);
        }

        // If we are editing a pedigree, there is no family selection dialogue that appears, hence just
        // check for an OK button before trying to click.
        if (isElementPresent(this.editPedigreeOKBtn)) {
            clickOnElement(this.editPedigreeOKBtn);
        }

        return new PedigreeEditorPage(this.superDriver);
    }

    /**
     * Traverses through the options for the health conditions found in family yes/no boxes.
     *
     * @return a List of Strings which represent the health conditions found under "Family history and pedigree"
     */
    @Step("Traverse through the familial health conditions UI")
    public List<String> cycleThroughFamilialHealthConditions()
    {

        return preOrderTraverseAndClick(By.cssSelector("div.family-info > div.fieldset"),
            By.cssSelector(this.yesBtnSelectorString),
            By.cssSelector(this.labelSelectorString));
    }

    /**
     * Sets the ethnicity of the patient in the "Family History and Pedigree" section. Defaults to Maternal ethnicity in
     * case of invalid maternity passed. Will select the first option in the suggestions.
     *
     * @param maternity pass either "Paternal" or "Maternal"
     * @param ethnicity is the ethncity to set. Requires this to be as close as possible to an exact match to
     *            suggestions dropdown.
     * @return Stay on the same page so return the same object.
     */
    @Step("Set the {0} ethnicity to {1}")
    public CreatePatientPage setEthnicity(String maternity, String ethnicity)
    {
        if (maternity.equals("Paternal")) {
            clickAndTypeOnElement(this.paternalEthnicityBox, ethnicity);
            clickOnElement(this.firstFamilySuggestion);
        } else {
            clickAndTypeOnElement(this.maternalEthnicityBox, ethnicity);
            clickOnElement(this.firstFamilySuggestion);
        }
        return this;
    }

    /**
     * Inputs a note into the Health Conditions within the "Family History and pedigree" section.
     *
     * @param note to type into the box. Any string. Will concatenate to what is there already.
     * @return Stay on the same page so return the same object.
     */
    @Step("Set the Health Conditions in Family note box to: {0}")
    public CreatePatientPage setHealthConditionsFoundInFamily(String note)
    {
        clickAndTypeOnElement(this.healthConditionsFoundInFamily, note);
        return this;
    }

    ////////////////////////////////////////
    // "Prenatal and perinatal history" Section - Methods
    ////////////////////////////////////////

    /**
     * Traverses through the options for the health conditions found in Prenatal and perinatal history yes/no boxes.
     * Requires: The "Prenatal and perinatal history" section to be expanded and that none of the yes/no options are
     * already selected/expanded (i.e. should be at the state of a new patient) Otherwise, traversal result might be off
     * due to presence of additional (appearing) selectors.
     *
     * @return a List of Strings which represent the health conditions found under the yes/no boxes of "Prenatal and
     *         perinatal history"
     */
    @Step("Set the DOB for the patient to: {0} month and {1} year")
    public List<String> cycleThroughPrenatalHistory()
    {

        List<String> loLabels = new ArrayList<>();

        // It is difficult to specify more unique (readable) selectors for the latter two arguments as they will be searched
        // for as children of the first argument's selector. We have similar selectors in cycleThroughAllPhenotypes()
        // but they are a bit different due to how the tree structure is arranged.
        // We add the strings together instead of ByChained becaues ByChained is itself a recursive tree traversal that
        // will go deep and ignore the ">" to link the chain which means the "immediate child" (depth 1).
        List<String> loUncategorizedLabels = preOrderTraverseAndClick(By.cssSelector("div.prenatal-info"),
            By.cssSelector("div.fieldset > " + this.yesBtnSelectorString),
            By.cssSelector("div.fieldset > " + this.labelSelectorString));

        // Expand the dropdowns for the yes/no options that are categorized into sections, lets them load first
        preOrderTraverseAndClick(
            By.cssSelector("div.prenatal-info > div > div > div"), this.expandToolSpan, this.expandToolSpan);

        List<String> loCategorizedLabels = preOrderTraverseAndClick(
            By.cssSelector(
                "div.prenatal-info div[class=dropdown] div[class=entry-data], div.prenatal-info div[class*=term-entry]"),
            By.cssSelector("span.yes-no-picker > label.yes"),
            By.cssSelector("label.yes-no-picker-label, span.yes-no-picker-label > span.value"));

        loLabels.addAll(loUncategorizedLabels);
        loLabels.addAll(loCategorizedLabels);

        return loLabels;
    }

    /**
     * Traverses through the rest of the boxes in prenatal options by entering in some text to each box. Traverses
     * through the dropdowns too. Requires that the "Prenatal and perinatal history" section be expanded
     *
     * @return Stay on the same page so return the same object.
     */
    @Step("Traverse through the prenatal options UI")
    public CreatePatientPage cycleThroughPrenatalOptions()
    {
        waitForElementToBePresent(this.apgarScore1MinDrp);

        clickOnElement(this.termBirthCheckbox);
        clickOnElement(this.termBirthCheckbox);
        clickAndTypeOnElement(this.gestationWeeksBox, "12");
        clickAndTypeOnElement(this.maternalAgeBox, "26");
        clickAndTypeOnElement(this.paternalAgeBox, "30");

        forceScrollToElement(this.apgarScore1MinDrp);
        Select apgarScore1Min = new Select(this.superDriver.findElement(this.apgarScore1MinDrp));
        Select apgarScore5Min = new Select(this.superDriver.findElement(this.apgarScore5MinDrp));
        List<String> loAPGARScores = new ArrayList<>(Arrays.asList(
            "Unknown", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"));

        for (String score : loAPGARScores) {
            apgarScore1Min.selectByVisibleText(score);
            apgarScore5Min.selectByVisibleText(score);
        }

        apgarScore1Min.selectByVisibleText("Unknown");
        apgarScore5Min.selectByVisibleText("Unknown");

        clickAndTypeOnElement(this.otherPregnancyPhenotypeBox, "Tall chin");
        clickOnElement(this.firstGeneSuggestion);

        clickAndTypeOnElement(this.otherDevelopmentPhenotypeBox, "Tall chin");
        clickOnElement(this.firstGeneSuggestion);

        clickAndTypeOnElement(this.otherDeliveryPhenotypeBox, "Tall chin");
        clickOnElement(this.firstGeneSuggestion);

        clickAndTypeOnElement(this.otherGrowthPhenotypeBox, "Tall chin");
        clickOnElement(this.firstGeneSuggestion);

        clickAndTypeOnElement(this.otherComplicationsPhenotypeBox, "Tall chin");
        clickOnElement(this.firstGeneSuggestion);

        clickAndTypeOnElement(this.prenatalNotesBox, "Notes for prenatal. Moving on...");

        return this;
    }

    ////////////////////////////////////////
    // "Measurements"Section-Methods
    ////////////////////////////////////////

    // TODO: Methods for this section only support one measurement entry right now. I don't have test cases for
    // multiple measurement entries

    /**
     * Adds a new entry of measurement data to the patient under the "Measurements" section. Requires: The
     * "Measurements" section to already be expanded.
     *
     * @param aMeasurement is a measurement object (instantiated struct) containing all the measurement fields to enter.
     * @return Stay on the same page so return the same object.
     */
    @Step("Add a measurement to patient as: {0}")
    public CreatePatientPage addMeasurement(CommonPatientMeasurement aMeasurement)
    {
        clickOnElement(this.addMeasurementBtn);
        clickAndTypeOnElement(this.weightBoxes, String.valueOf(aMeasurement.weight));
        clickAndTypeOnElement(this.heightBoxes, String.valueOf(aMeasurement.height));
        clickAndTypeOnElement(this.armSpanBoxes, String.valueOf(aMeasurement.armSpan));
        clickAndTypeOnElement(this.sittingHeightBoxes, String.valueOf(aMeasurement.sittingHeight));
        clickAndTypeOnElement(this.headCircumferenceBoxes, String.valueOf(aMeasurement.headCircumference));
        clickAndTypeOnElement(this.philtrumLengthBoxes, String.valueOf(aMeasurement.philtrumLength));
        clickAndTypeOnElement(this.leftEarLengthBoxes, String.valueOf(aMeasurement.leftEarLength));
        clickAndTypeOnElement(this.rightEarLengthBoxes, String.valueOf(aMeasurement.rightEarLength));
        clickAndTypeOnElement(this.outherCanthalDistanceBoxes, String.valueOf(aMeasurement.outerCanthalDistance));
        clickAndTypeOnElement(this.innterCanthalDistanceBoxes, String.valueOf(aMeasurement.inntercanthalDistance));
        clickAndTypeOnElement(this.palpebralFissureLengthBoxes, String.valueOf(aMeasurement.palpebralFissureLength));
        clickAndTypeOnElement(this.interpupilaryDistanceBoxes, String.valueOf(aMeasurement.interpupilaryDistance));
        clickAndTypeOnElement(this.leftHandLengthBoxes, String.valueOf(aMeasurement.leftHandLength));
        clickAndTypeOnElement(this.leftPalmLengthBoxes, String.valueOf(aMeasurement.leftPalmLength));
        clickAndTypeOnElement(this.leftFootLengthBoxes, String.valueOf(aMeasurement.leftFootLength));
        clickAndTypeOnElement(this.rightHandLengthBoxes, String.valueOf(aMeasurement.rightHandLength));
        clickAndTypeOnElement(this.rightPalmLengthBoxes, String.valueOf(aMeasurement.rightPalmLength));
        clickAndTypeOnElement(this.rightFootLengthBoxes, String.valueOf(aMeasurement.rightFootLength));

        return this;
    }

    /**
     * Changes the date of the first measurement to the specified month and year and date. Must be valid date otherwise
     * Selenium will throw an ElementNotFound exception. Requires: The measurement section to be open and at least one
     * measurement entry to be present.
     *
     * @param month is the month as a String "January" to "December". Must be exact.
     * @param year is the year as a String "1920" to current year (ex. "2019"). Must be exact.
     * @return Stay on the same page so return the same object.
     */
    @Step("Change the measurement date to: {0} day {1} month and {2} year")
    public CreatePatientPage changeMeasurementDate(String day, String month, String year)
    {
        By calendarDayBtn = By.xpath("//div[contains(text(), '" + day + "')]");

        clickOnElement(this.measurementDateBoxes);

        waitForElementToBePresent(this.measurementMonthDrps);
        Select monthDrp = new Select(this.superDriver.findElement(this.measurementMonthDrps));
        Select yearDrp = new Select(this.superDriver.findElement(this.measurementYearDrps));

        monthDrp.selectByVisibleText(month);
        yearDrp.selectByVisibleText(year);

        clickOnElement(calendarDayBtn);
        waitForElementToBeGone(this.measurementMonthDrps);

        return this;
    }

    /**
     * Retrieves the first measurement entry for the patient that has measurement data entered. Requires: The
     * "Measurement" section to be open and there be at least one measurmenet entry already there.
     *
     * @return A Measurement object constructed with the measurement data gathered from the patient.
     */
    @Step("Retrieve the patients measurement")
    public CommonPatientMeasurement getPatientMeasurement()
    {
        waitForElementToBePresent(this.weightBoxes);

        float weight = getSpecificMeasurement(this.weightBoxes);
        float armSpan = getSpecificMeasurement(this.armSpanBoxes);
        float headCircumference = getSpecificMeasurement(this.headCircumferenceBoxes);
        float outerCanthalDistance = getSpecificMeasurement(this.outherCanthalDistanceBoxes);
        float leftHandLength = getSpecificMeasurement(this.leftHandLengthBoxes);
        float rightHandLength = getSpecificMeasurement(this.rightHandLengthBoxes);

        float height = getSpecificMeasurement(this.heightBoxes);
        float sittingHeight = getSpecificMeasurement(this.sittingHeightBoxes);
        float philtrumLength = getSpecificMeasurement(this.philtrumLengthBoxes);
        float inntercanthalDistance = getSpecificMeasurement(this.innterCanthalDistanceBoxes);
        float leftPalmLength = getSpecificMeasurement(this.leftPalmLengthBoxes);
        float rightPalmLength = getSpecificMeasurement(this.rightPalmLengthBoxes);

        float leftEarLength = getSpecificMeasurement(this.leftEarLengthBoxes);
        float palpebralFissureLength = getSpecificMeasurement(this.palpebralFissureLengthBoxes);
        float leftFootLength = getSpecificMeasurement(this.leftFootLengthBoxes);
        float rightFootLength = getSpecificMeasurement(this.rightFootLengthBoxes);

        float rightEarLength = getSpecificMeasurement(this.rightEarLengthBoxes);
        float interpupilaryDistance = getSpecificMeasurement(this.interpupilaryDistanceBoxes);

        return new CommonPatientMeasurement(
            weight, armSpan, headCircumference, outerCanthalDistance, leftHandLength, rightHandLength,
            height, sittingHeight, philtrumLength, inntercanthalDistance, leftPalmLength, rightPalmLength,
            leftEarLength, palpebralFissureLength, leftFootLength, rightFootLength,
            rightEarLength, interpupilaryDistance);
    }

    /**
     * Retrieves the measurement value within the specified measurement box as a float. This is a helper function for
     * getPatientMeasurement()
     *
     * @param measurementBoxSelector Selector of the specific box
     * @return The float value of what was in the measurement box. If it were empty, returns 0.
     */
    private float getSpecificMeasurement(By measurementBoxSelector)
    {
        return Float.parseFloat(this.superDriver.findElement(measurementBoxSelector).getAttribute("value"));
    }

    ////////////////////////////////////////
    // "Clinical symptoms and physical findings" (Phenotypes) Section - Methods
    ////////////////////////////////////////

    /**
     * Adds a phenotype by searching. Selects the first suggested phenotype search result. Assumes that "Clinical
     * symptoms and physical findings" is expanded and there is at least one suggested search result.
     *
     * @param thePhenotype phenotype needed. Be exact and as specific as possible.
     * @return Stay on the same page, so return same object.
     */
    public CreatePatientPage addPhenotype(String thePhenotype)
    {
        clickAndTypeOnElement(this.phenotypeSearchBox, thePhenotype);
        clickOnElement(this.firstPhenotypeSuggestion);
        return this;
    }

    /**
     * Helper method to add a list of Phenotypes to the patient. Requires the "Clinical symptoms and physical findings"
     * section is expanded and there is at least one suggested search result.
     *
     * @param phenotypes is a List of Strings specifying the phenotypes. You should be as exact as possible.
     * @return Stay on the same page so return the same object.
     */
    @Step("Add the following list of phenotypes: {0}")
    public CreatePatientPage addPhenotypes(List<String> phenotypes)
    {
        for (String aPhenotype : phenotypes) {
            addPhenotype(aPhenotype);
        }
        return this;
    }

    /**
     * Gets all the labels for the labels within the Edit Phenotype Details box. This does not do a tree traversal due
     * to the dropdowns having issues hiding/showing for now. Requires: A phenotype to already be present and "Add
     * Details" to already be pressed so that the details box appears.
     *
     * @return A list of strings representing the labels found in the Edit Phenotype Details Box. This should not be
     *         empty.
     */
    @Step("Traverse through the phenotype details UI section")
    public List<String> cycleThroughPhenotypeDetailsLabels()
    {
        waitForElementToBePresent(this.phenotypeDetailsLabels);

        List<WebElement> loExpandCarets = this.superDriver.findElements(this.expandCaretBtns);
        List<String> loLabels = new ArrayList<>();

        // Expand all first
        for (WebElement aCaret : loExpandCarets) {
            // Should find this text, rather than rely on expansion selector due to PT-3095
            if (aCaret.getText().equals("►")) {
                clickOnElement(aCaret);
            }
        }

        this.superDriver.findElements(this.phenotypeDetailsLabels).forEach(x -> loLabels.add(x.getText()));

        return loLabels;
    }

    /**
     * Adds phenotype details to the nth detail-less phenotype (done it this way due to the simplicity of
     * implementation) in the list of phenotypes already present. Makes the grey phenotype details box appear.
     *
     * @param n is the nth phenotype WITHOUT any details added yet.
     * @return Stay on the same page so return same object.
     */
    @Step("Add details to the {0}th phenotype")
    public CreatePatientPage addDetailsToNthPhenotype(int n)
    {
        waitForElementToBePresent(this.addPhenotypeDetailsBtns);
        List<WebElement> loPhenotypeAddBtnsPresent = this.superDriver.findElements(this.addPhenotypeDetailsBtns);

        loPhenotypeAddBtnsPresent.get(n - 1).click();

        waitForElementToBePresent(this.phenotypeDetailsLabels);
        return this;
    }

    /**
     * Traverses through the options for phenotypes in the Clinical Symptoms and Physical Findings Section. This
     * specific traversal only goes a depth level of 1 as the HPO tree is rather large. Maybe split to several smaller
     * functions for each HPO section and recurse full tree if we want a more detailed sanity test. Requires: The
     * "Clinical Symptoms and Physical Findings" section to be expanded and that none of the yes/no options are already
     * selected/expanded (i.e. should be at the state of a new patient) Otherwise, traversal result might be off due to
     * presence of additional (appearing) selectors.
     *
     * @return a List of Strings which represent the health conditions found under the yes/no boxes of "Clinical
     *         Symptoms and Physical Findings"
     */
    @Step("Traverse (pre-order) through all phenotypes. Depth level limited to 1.")
    public List<String> cycleThroughAllPhenotypes()
    {

        clickOnElement(By.cssSelector("span.expand-all"));

        List<String> loLabels = new ArrayList<>();

        forceScrollToElement(By.cssSelector("div.phenotype > div > div > div"));

        // Expand all dropdowns, lets them load first
        preOrderTraverseAndClick(
            By.cssSelector("div.phenotype > div > div > div"), this.expandToolSpan, this.expandToolSpan);

        List<String> loCategorizedLabels = preOrderTraverseAndClick(
            By.cssSelector(
                "div.phenotype div[class=dropdown] div[class=entry-data], div.prenatal-info div[class*=term-entry]"),
            By.cssSelector("span.yes-no-picker > label.na"),
            By.cssSelector("label.yes-no-picker-label, span.yes-no-picker-label > span.value"));

        loLabels.addAll(loCategorizedLabels);

        return loLabels;
    }

    /**
     * Retrieves the phenotypes specified by the passed selector. This is a helper method that is used to differentiate
     * between different phenotypes such as ones automatically added via measurements data vs. manual input Requires:
     * The "Clinical symptoms and physical findings" section to be expanded
     *
     * @param phenotypeLabelsSelector the By selector of the phenotype label, three standard selectors defined so far in
     *            this class: {@link #phenotypesSelectedLabels} (all),
     *            {@link #phenotypesAutoSelectedByMeasurementLabels} (lightning bolt/auto ones), and
     *            {@link #phenotypesManuallySelectedLabels} (non-lightning bolt/manual ones)
     * @return A List of Strings representing the names of the phenotypes found. Can potentially be empty.
     */
    private List<String> getPresentPhenotypes(By phenotypeLabelsSelector)
    {
        List<String> loPhenotypesFound = new ArrayList<>();
        waitForElementToBePresent(this.phenotypeSearchBox);
        this.superDriver.findElements(phenotypeLabelsSelector).forEach(x -> loPhenotypesFound.add(x.getText()));

        return loPhenotypesFound;
    }

    /**
     * Retrieves all of the phenotypes already present (entered) in the Patient Information Form. Requires: The
     * "Clinical symptoms and physical findings" section to be expanded.
     *
     * @return A (potentially empty) list of Strings representing the names of the phenotypes found.
     */
    @Step("Retrieve all phenotypes")
    public List<String> getAllPhenotypes()
    {
        return getPresentPhenotypes(this.phenotypesSelectedLabels);
    }

    /**
     * Retrieves a list of phenotypes entered automatically due to measurement data. These are the phenotypes with the
     * lightning symbol beside them. Requires: The "Clinical symptoms and physical findings" section to be expanded
     *
     * @return A, possibly empty, list of Strings representing phenotypes that have a lightning symbol beside them due
     *         to them being automatically added from data contained on a measurements entry.
     */
    @Step("Retrieve phenotypes added automatically due to measurements (lightning icon beside them)")
    public List<String> getPhenotypesLightning()
    {
        return getPresentPhenotypes(this.phenotypesAutoSelectedByMeasurementLabels);
    }

    /**
     * Retrieves a list of phenotypes that were manually entered. These are the ones that are not prefixed with a
     * lightning symbol. Requires: The "Clinical symptoms and physical findings" section to be expanded
     *
     * @return A List of Strings, possibly empty, of the phenotypes that were manually entered (do not have a lightning
     *         symbol in front of them).
     */
    @Step("Retrieve phenotypes that were added manually (no lightning icon beside them)")
    public List<String> getPhenotypesNonLightning()
    {
        return getPresentPhenotypes(this.phenotypesManuallySelectedLabels);
    }

    ////////////////////////////////////////
    // "Genotype Information" Section - Methods
    ////////////////////////////////////////

    /**
     * Adds a gene to the "Genotype information" section, using the gene name, status, and strategy. Clicks the "add
     * gene" button before adding to the bottom-most row. Searches for gene and selects first suggestion. Assumes that
     * "Genotype information" is already expanded (selectors visible) and at least one gene is returned in search
     * results.
     *
     * @param theGene name of the gene, be as exact and specific as possible.
     * @param geneStatus status of the gene. One of: "Candidate", "Rejected candidate", "Confirmed causal", "Carrier",
     *            and "Tested negative". Must be exact, will throw exception otherwise.
     * @param strategy specifies which Gene strategy checkbox to toggle. One of: "Sequencing", "Deletion/duplication",
     *            "Familial mutation", and "Common mutations". Must be exact. Defaults to no strategy to specify.
     * @return Stay on the same page so we return the same object.
     */
    @Step("Add gene: {0} with status: {1} and strategy: {2}")
    public CreatePatientPage addGene(String theGene, String geneStatus, String strategy)
    {
        forceScrollToElement(this.addGeneBtn);
        clickOnElement(this.addGeneBtn);

        waitForElementToBePresent(this.geneNameBoxes); // Must wait before search for elements
        unconditionalWaitNs(1);

        List<WebElement> foundGeneBoxes = this.superDriver.findElements(this.geneNameBoxes);
        List<WebElement> foundStatusDrps = this.superDriver.findElements(this.geneStatusDrps);

        // Get the last element of each list for the most bottom one
        WebElement bottommostGeneNameBox = foundGeneBoxes.get(foundGeneBoxes.size() - 1);
        WebElement bottommostStatusDrp = foundStatusDrps.get(foundStatusDrps.size() - 1);

        Select statusDrp = new Select(bottommostStatusDrp);

        bottommostGeneNameBox.click();
        bottommostGeneNameBox.sendKeys(theGene);
        clickOnElement(this.firstGeneSuggestion);

        bottommostStatusDrp.click();
        statusDrp.selectByVisibleText(geneStatus);

        List<WebElement> foundDesiredStrategyCheckboxes;

        switch (strategy) {
            case "Sequencing":
                foundDesiredStrategyCheckboxes = this.superDriver.findElements(this.geneStrategySequencingCheckboxes);
                foundDesiredStrategyCheckboxes.get(foundDesiredStrategyCheckboxes.size() - 1).click();
                break;

            case "Deletion/duplication":
                foundDesiredStrategyCheckboxes = this.superDriver.findElements(this.geneStrategyDeletionCheckboxes);
                foundDesiredStrategyCheckboxes.get(foundDesiredStrategyCheckboxes.size() - 1).click();
                break;

            case "Familial mutation":
                foundDesiredStrategyCheckboxes =
                    this.superDriver.findElements(this.geneStrategyFamilialMutationCheckboxes);
                foundDesiredStrategyCheckboxes.get(foundDesiredStrategyCheckboxes.size() - 1).click();
                break;

            case "Common mutations":
                foundDesiredStrategyCheckboxes =
                    this.superDriver.findElements(this.geneStrategyCommonMutationCheckboxes);
                foundDesiredStrategyCheckboxes.get(foundDesiredStrategyCheckboxes.size() - 1).click();
                break;

            default:
                System.out.println("Invalid gene strategy passed to addGene(). No strategy checked.");
                break;
        }

        return this;
    }

    ////////////////////////////////////////
    // "Diagnosis" Section - Methods
    ////////////////////////////////////////

    /**
     * Adds a clinical diagnosis in the "Diagnosis" section to the patient. Selects the first result from the suggestion
     * dropdown. Requires: Diagnosis section to be expanded and the ORDO name to be as exact as possible to the
     * suggestions list.
     *
     * @param ORDO the name of the diagnosis, either the ID number or the name of the diagnosis.
     * @return Stay on the same page so return the same object.
     */
    @Step("Add a clinical diagnosis with ORDO: {0}")
    public CreatePatientPage addClinicalDiagnosis(String ORDO)
    {
        clickAndTypeOnElement(this.clinicalDiagnosisBox, ORDO);
        clickOnElement(this.firstGeneSuggestion);
        return this;
    }

    /**
     * Adds a final diagnosis in the "Diagnosis" section to the patient. Selects the first result in the suggestions
     * dropdown. Requires the "Diagnosis" section to be expanded.
     *
     * @param OMIM is the name of the diagnosis or the OMIM number, as a String. Should be as exact as possible.
     * @return Stay on the same page so return the same object.
     */
    @Step("Add final diagnosis with OMIM: {0}")
    public CreatePatientPage addFinalDiagnosis(String OMIM)
    {
        clickAndTypeOnElement(this.finalDiagnosisBox, OMIM);
        clickOnElement(this.firstGeneSuggestion);
        return this;
    }

    /**
     * Toggles the "Case Solved" checkbox in the "Diagnosis" section. Requires the "Diagnosis" section to be expanded.
     *
     * @return Stay on the same page so return the same object.
     */
    @Step("Toggle the 'Case Solved' checkbox")
    public CreatePatientPage toggleCaseSolved()
    {
        clickOnElement(this.caseSolvedCheckbox);
        return this;
    }

    /**
     * Determines if the "Case Solved" checkbox under "Diagnosis" section is enabled or not. Requires the "Diagnosis"
     * section to be expanded.
     *
     * @return A boolean, true for checked and false for unchecked.
     */
    @Step("Determine if the case is solved")
    public boolean isCaseSolved()
    {
        waitForElementToBePresent(this.caseSolvedCheckbox);
        return this.superDriver.findElement(this.caseSolvedCheckbox).isSelected();
    }

    /**
     * Adds a PubMed ID to the bottom-most PubMed ID box there is. If there is a pubmed article on the page, will add a
     * new row by clicking on the add pubMed ID link. Requires the "Diagnosis" section to be expanded and the "Case
     * Solved" checkbox to be enabled.
     *
     * @param ID is the pubMed ID (8 digit number) to add to the patient
     * @return Stay on the same page so return the same object.
     */
    @Step("Add a PubMed ID of {0}")
    public CreatePatientPage addPubMedID(String ID)
    {
        // Needed to defocus PubMed ID boxes.
        clickOnElement(this.additionalCommentsBox);

        if (isElementPresent(this.pubMedArticle)) {
            clickOnElement(this.addPubMedLink);
        }

        List<WebElement> loPubMedIDBoxes = this.superDriver.findElements(this.pubMedIDBoxes);
        clickOnElement(loPubMedIDBoxes.get(loPubMedIDBoxes.size() - 1));
        loPubMedIDBoxes.get(loPubMedIDBoxes.size() - 1).sendKeys(ID);

        clickOnElement(this.additionalCommentsBox);

        return this;
    }

    /**
     * Removes the Nth PubMed entry from the Diagnosis section. Requires that there be at least n PubMed entries and the
     * "Diagnosis" section to be expanded. This should not be called if there are no existing PubMed entries.
     *
     * @param n is int >= 1.
     * @return Stay on the same page so return the same object.
     */
    @Step("Remove the {0}th PubMed ID")
    public CreatePatientPage removeNthPubMedID(int n)
    {
        waitForElementToBePresent(this.deletePubMedBtns);
        List<WebElement> loDeletePubMedBtns = this.superDriver.findElements(this.deletePubMedBtns);

        clickOnElement(loDeletePubMedBtns.get(n - 1));

        return this;
    }

    /**
     * Adds a note to the Resolution Notes box under "Diagnosis" section. Concatenates to what is already present in
     * that box. Requires: Diagnosis section to already be expanded.
     *
     * @param note is a String representing the note you need to enter.
     * @return Stay on the same page so return the same object.
     */
    @Step("Add resolution notes of {0}")
    public CreatePatientPage addResolutionNotes(String note)
    {
        clickAndTypeOnElement(this.resolutionNotesBox, note);
        return this;
    }

    /**
     * Adds a note to the Additional Comments box under "Diagnosis" section. Concatenates to what is already present in
     * that box.
     *
     * @param comment is the comment to add, as an arbitrary String
     * @return Stay on the same page so return the same object.
     */
    @Step("Add additional comments in diagnosis section called {0}")
    public CreatePatientPage addAdditionalComments(String comment)
    {
        clickAndTypeOnElement(this.additionalCommentsBox, comment);
        return this;
    }

    /**
     * Determines if the nth PubMed ID box has valid input. Locates the red error message that is given in the case of
     * invalid input. Requires the "Diagnosis" section to be expanded and that there be at least n PubMed IDs already
     * entered.
     *
     * @param n is the Nth PubMed ID box.
     * @return A boolean indicating validity, true for valid (summary of article shown) and false for invalid (the error
     *         message is displayed).
     */
    @Step("Determine if {0}th PubMedID box has valid input")
    public boolean isNthPubMDBoxValid(int n)
    {
        final String invalidPubMedIDError = "Invalid Pubmed ID";

        // Needed so that pubMed boxes goes out of focus and does validation
        clickOnElement(this.additionalCommentsBox);
        waitForElementToBePresent(this.pubMedIDCheckStatus);

        String statusText = this.superDriver.findElements(this.pubMedIDCheckStatus).get(n - 1).getText();

        return !statusText.equals(invalidPubMedIDError);
    }

    /**
     * Tries to modify each input box within the "Diagnosis" section. Adds something to each box.
     *
     * @return Stay on the same page so return the same object.
     */
    @Step("Traverse through the diagnosis section UI")
    public CreatePatientPage cycleThroughDiagnosisBoxes()
    {
        addClinicalDiagnosis("Allergic bronchopulmonary aspergillosis");
        addClinicalDiagnosis("Essential iris atrophy");
        toggleNthClinicalDiagnosisCheckbox(1);
        toggleNthClinicalDiagnosisCheckbox(1);
        toggleNthClinicalDiagnosisCheckbox(2);
        toggleNthClinicalDiagnosisCheckbox(2);
        addFinalDiagnosis("ALLERGIC RHINITIS");
        addFinalDiagnosis("KOOLEN-DE VRIES SYNDROME");
        toggleNthFinalDiagnosisCheckbox(1);
        toggleNthFinalDiagnosisCheckbox(1);
        toggleNthFinalDiagnosisCheckbox(2);
        toggleNthFinalDiagnosisCheckbox(2);
        addAdditionalComments("Comment in Additional Comments");
        toggleCaseSolved();
        addPubMedID("30699054");
        addPubMedID("30699052");
        addResolutionNotes("Resolved");

        return this;
    }

    /**
     * Toggles the Nth Clinical Diagnosis checkbox within the list of added clinical diagnosis. Requires the "Diagnosis"
     * section to be expanded and there at least be n ORDOs already listed.
     *
     * @param n is int >= 1, indicating the Nth clinical diagnosis checkbox to toggle.
     * @return Stay on the same page so return the same object.
     */
    @Step("Toggle the {0} clinical diagnosis checkbox.")
    public CreatePatientPage toggleNthClinicalDiagnosisCheckbox(int n)
    {
        waitForElementToBePresent(this.clinicalDiagnosisCheckboxes);
        clickOnElement(this.superDriver.findElements(this.clinicalDiagnosisCheckboxes).get(n - 1));

        return this;
    }

    /**
     * Toggles the Nth Final Diagnosis checkbox within the list of Final Diagnosis already added. Requires the
     * "Diagnosis" section to be expanded and there at least be n OMIMs listed already.
     *
     * @param n is integer >= 1, indicating the Nth Final Diagnosis checkbox to toggle.
     * @return Stay on the same page so return the same object.
     */
    @Step("Toggle the {0}th final diagnosis checkbox")
    public CreatePatientPage toggleNthFinalDiagnosisCheckbox(int n)
    {
        waitForElementToBePresent(this.finalDiagnosisCheckboxes);
        clickOnElement(this.superDriver.findElements(this.finalDiagnosisCheckboxes).get(n - 1));

        return this;
    }

    /**
     * Checks for the visibility (i.e. clickability) of the pubMed ID boxes and Resolution Notes box. Visibility is not
     * enough as the elements are always loaded but with odd hidden property that is still considered visible. Requires
     * the "Diagnosis" section to be expanded.
     *
     * @return bool indicating the presence of those two boxes. True for present, and false if not present.
     */
    @Step("Determine if the PubMed and Resolution boxes are clickable")
    public boolean isPubMedAndResolutionBoxesClickable()
    {
        return (isElementClickable(this.pubMedIDBoxes) && (isElementClickable(this.resolutionNotesBox)));
    }
}
