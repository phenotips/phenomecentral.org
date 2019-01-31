package PageObjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.pagefactory.ByChained;
import org.openqa.selenium.support.ui.Select;

import TestCases.CommonPatientMeasurement;

/**
 * Represents the page reached when "Create... -> New patient" is clicked on the navbar
 * Ex. http://localhost:8083/edit/data/Pxxxxxxx (new patient ID)
*/
public class CreatePatientPage extends CommonInfoSelectors
{
    private final By patientIDDiv = By.id("document-title");

    private final By realPatientConsentBox = By.id("real-consent-checkbox");

    private final By geneticConsentBox = By.id("genetic-consent-checkbox");

    private final By shareHistoryConsentBox = By.id("share_history-consent-checkbox");

    private final By shareImagesConsentBox = By.id("share_images-consent-checkbox");

    private final By matchingConsentBox = By.id("matching-consent-checkbox");

    private final By patientConsentUpdateBtn = By.id("patient-consent-update");

    // Add in selectors for text fields here

    private final By identifierBox = By.id("PhenoTips.PatientClass_0_external_id");

    private final By lifeStatusDrp = By.id("PhenoTips.PatientClass_0_life_status");

    private final By dobMonthDrp =
        By.cssSelector("#date-of-birth-block > div > div:nth-child(2) > div > div > span > select.month");

    private final By dobYearDrp =
        By.cssSelector("#date-of-birth-block > div > div:nth-child(2) > div > div > span > select.year");

    private final By doDeathMonthDrp =
        By.cssSelector("#date-of-death-block > div > div:nth-child(2) > div > div > span > select.month");

    private final By doDeathYearDrp =
        By.cssSelector("#date-of-death-block > div > div:nth-child(2) > div > div > span > select.year");

    private final By maleGenderBtn = By.id("xwiki-form-gender-0-0");
    private final By femaleGenderBtn = By.id("xwiki-form-gender-0-1");
    private final By otherGenderBtn = By.id("xwiki-form-gender-0-2");
    private final By unknownGenderBtn = By.id("xwiki-form-gender-0-3");

    private final By congenitalOnsentBtn = By.id("PhenoTips.PatientClass_0_global_age_of_onset_HP:0003577");

    private final By ageOfOnsetBtns = By.cssSelector("div.global_age_of_onset > div > div  > ul > li.term-entry");
    private final By modeOfInheritanceChkboxes = By.cssSelector("div.global_mode_of_inheritance > div > div > ul > li.term-entry");

    private final By indicationForReferralBox = By.id("PhenoTips.PatientClass_0_indication_for_referral");

    private final By updateBtn = By.cssSelector("#patient-consent-update > a:nth-child(1)");

    private final By editPedigreeBox = By.cssSelector("div.pedigree-wrapper > div");
    private final By editPedigreeOKBtn = By.cssSelector("input.button[name=ok]");
    private final By assignFamilyRadioBtn = By.id("pedigreeInputAssignFamily");
    private final By familySearchInputBox = By.id("family-search-input");
    private final By firstFamilySuggestion = By.cssSelector("span.suggestValue");
    private final By paternalEthnicityBox = By.id("PhenoTips.PatientClass_0_paternal_ethnicity_2");
    private final By maternalEthnicityBox = By.id("PhenoTips.PatientClass_0_maternal_ethnicity_2");
    private final By addEthnicityBtns = By.cssSelector("div.family-info a[title=add]");
    private final By healthConditionsFoundInFamily = By.id("PhenoTips.PatientClass_0_family_history");

    private final By addMeasurementBtn = By.cssSelector("div.measurement-info a.add-data-button");
    private final By measurementYearDrp = By.cssSelector("div.calendar_date_select select.year");
    private final By measurementMonthDrp = By.cssSelector("div.calendar_date_select select.month");
    private final By todayCalendarLink = By.linkText("Today");
    private final By MondayOfTheThirdWeek = By.cssSelector(
        "#body > div.calendar_date_select > div.cds_part.cds_body > table > tbody > tr.row_2 > td:nth-child(2) > div");
    private final By measurementDateBoxes = By.id("PhenoTips.MeasurementsClass_0_date");
    private final By weightBox = By.id("PhenoTips.MeasurementsClass_0_weight");
    private final By heightBox = By.id("PhenoTips.MeasurementsClass_0_height");
    private final By armSpanBox = By.id("PhenoTips.MeasurementsClass_0_armspan");
    private final By sittingHeightBox = By.id("PhenoTips.MeasurementsClass_0_sitting");
    private final By headCircumferenceBox = By.id("PhenoTips.MeasurementsClass_0_hc");
    private final By philtrumLengthBox = By.id("PhenoTips.MeasurementsClass_0_philtrum");
    private final By leftEarLengthBox = By.id("PhenoTips.MeasurementsClass_0_ear");
    private final By rightEarLengthBox = By.id("PhenoTips.MeasurementsClass_0_ear_right");
    private final By outherCanthalDistanceBox = By.id("PhenoTips.MeasurementsClass_0_ocd");
    private final By innterCanthalDistanceBox = By.id("PhenoTips.MeasurementsClass_0_icd");
    private final By palpebralFissureLengthBox = By.id("PhenoTips.MeasurementsClass_0_pfl");
    private final By interpupilaryDistanceBox = By.id("PhenoTips.MeasurementsClass_0_ipd");
    private final By leftHandLengthBox = By.id("PhenoTips.MeasurementsClass_0_hand");
    private final By leftPalmLengthBox = By.id("PhenoTips.MeasurementsClass_0_palm");
    private final By leftFootLengthBox = By.id("PhenoTips.MeasurementsClass_0_foot");
    private final By rightHandLengthBox = By.id("PhenoTips.MeasurementsClass_0_hand_right");
    private final By rightPalmLengthBox = By.id("PhenoTips.MeasurementsClass_0_palm_right");
    private final By rightFootLengthBox = By.id("PhenoTips.MeasurementsClass_0_foot_right");

    private final By phenotypeSearchBox = By.id("quick-phenotype-search");
    private final By firstPhenotypeSuggestion = By.cssSelector("li.xitem > div");
    private final By addPhenotypeDetailsBtns = By.cssSelector("button.add");
    private final By editPhenotypeDetailsBtns = By.cssSelector("button.edit");
    private final By expandCaretBtns = By.cssSelector(
        "div.phenotype-details.focused span.collapse-button, div.phenotype-details.focused span.expand-tool");
    private final By phenotypeDetailsLabels = By.cssSelector("div.phenotype-details.focused label");
    private final By phenotypesSelectedLabels = By.cssSelector("div.summary-item > label.yes");
    // Different selectors for when the thunderbolt symbol is present or not. Lightning appears when auto added
    //   by measurement information.
    private final By phenotypesAutoSelectedByMeasurementLabels = By.xpath("//div[@class='summary-item' and span[@class='fa fa-bolt']]/label[@class='yes']");
    private final By phenotypesManuallySelectedLabels = By.xpath("//div[@class='summary-item' and not(span[@class='fa fa-bolt'])]/label[@class='yes']");
//    private final By phenotypesSelectedDivs = By.cssSelector("div.summary-item");

    private final By addGeneBtn = By.cssSelector("a[title*='Add gene']");

    private final By geneNameBoxes = By.cssSelector(
        "#extradata-list-PhenoTips\\.GeneClass_PhenoTips\\.GeneVariantClass > tbody > tr > td:nth-child(2) > input[type=text]");
    private final By geneStatusDrps = By.cssSelector("td.Status > select");
    private final By geneStrategySequencingCheckboxes = By.cssSelector(
        "td.Strategy > label > input[value=sequencing]");
    private final By firstGeneSuggestion = By.cssSelector("div.suggestItem > div > span.suggestValue");


    private final By saveAndViewSummaryBtn = By.cssSelector("span.buttonwrapper:nth-child(3) > input:nth-child(1)");

    public CreatePatientPage(WebDriver aDriver)
    {
        super(aDriver);
    }

    /**
     * Toggles the nth consent checkbox in the "Consents granted" section
     * @param n which is an integer between 1-5 representing the specified checkbox.
     * @return the same object as we are on the same page
     */
    public CreatePatientPage toggleNthConsentBox(int n)
    {
        switch (n) {
            case 1:
                clickOnElement(realPatientConsentBox);
                break;
            case 2:
                clickOnElement(geneticConsentBox);
                break;
            case 3:
                clickOnElement(shareHistoryConsentBox);
                break;
            case 4:
                clickOnElement(shareImagesConsentBox);
                break;
            case 5:
                clickOnElement(matchingConsentBox);
                break;
            default:
                System.out.println("Invalid nth consent box specified: " + n);
                break;
        }
        return this;
    }

    /**
     * Helper method to toggle the first four consent boxes. These boxes are unchecked when first creating
     * a patient (Real patient, Genetic Sequencing data consent, Medical/Family History consent, Medical Images/Photos consent)
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
     * Clicks on the "Update" button under the "Consents granted" section and then waits
     * 5 seconds for it to update the consent.
     * @return same object as we stay on the same page
     */
    public CreatePatientPage updateConsent()
    {
        clickOnElement(updateBtn);
        unconditionalWaitNs(5);
        return this;
    }

    /**
     * Hits the "Save and View Summary" button on the bottom left.
     * @return navigating to the view page containing patient's full details so a new object of that type
     */
    public ViewPatientPage saveAndViewSummary()
    {
        clickOnElement(saveAndViewSummaryBtn);
        return new ViewPatientPage(superDriver);
    }

    /**
     * Clears and then sets the patient identifer field box.
     * @param identifer the string that should be entered into the "Identifer" field under Patient Information
     * @return stay on the same page so return the same instance of object
     */
    public CreatePatientPage setIdentifer(String identifer)
    {
        clickOnElement(identifierBox);
        superDriver.findElement(identifierBox).clear();
        clickAndTypeOnElement(identifierBox, identifer);
        unconditionalWaitNs(1); // Gives "identifier already exists" if we navigate away too fast.
        return this;
    }

    /**
     * Sets the Life Status dropdown of the patient.
     * @param status is either "Alive" or "Deceased". Must be exact string otherwise defaults to "Alive".
     * @return stay on the same page so return same object.
     */
    public CreatePatientPage setLifeStatus(String status)
    {
        waitForElementToBePresent(lifeStatusDrp);
        Select statusDrp = new Select(superDriver.findElement(lifeStatusDrp));

        try {
            statusDrp.selectByVisibleText(status);
        } catch (NoSuchElementException e) {
            System.out.println("No such life status. Defaulting to Alive.");
            statusDrp.selectByVisibleText("Alive");
        }

        return this;
    }

    /**
     * Sets the Date of Birth of the patient under Patient Information.
     * Will safely handle invalid strings by defaulting to 01/2019.
     * @param month the Month as a String (01 - 12). Must exactly match the dropdown.
     * @param year the year as a String (1500s - 2019). Must exactly match the dropdown.
     * @return stay on the same page so return same object.
     */
    public CreatePatientPage setDOB(String month, String year) {
        Select monthDrp;
        Select yearDrp;

        waitForElementToBePresent(dobMonthDrp);
        monthDrp = new Select(superDriver.findElement(dobMonthDrp));
        yearDrp = new Select(superDriver.findElement(dobYearDrp));

        try {
            monthDrp.selectByVisibleText(month);
            yearDrp.selectByVisibleText(year);
        } catch (NoSuchElementException e) {
            System.out.println("Invalid DOB passed. Default set to 01/2019");
            monthDrp.selectByVisibleText("01");
            yearDrp.selectByVisibleText("2019");
        }

        return this;
    }

    /**
     * Sets the Date of Death of the patient under Patient Information.
     * Will safely handle invalid strings by defaulting to 01/2018.
     * Requires: Life Status set to "Deceased" so that Date of Death dropdowns are visible.
     * @param month the Month as a String (01 - 12). Must exactly match the dropdown.
     * @param year the year as a String (1500s - 2019). Must exactly match the dropdown.
     * @return stay on the same page so return same object.
     */
    public CreatePatientPage setDateOfDeath(String month, String year) {
        Select monthDrp;
        Select yearDrp;

        waitForElementToBePresent(doDeathMonthDrp);
        monthDrp = new Select(superDriver.findElement(doDeathMonthDrp));
        yearDrp = new Select(superDriver.findElement(doDeathYearDrp));

        try {
            monthDrp.selectByVisibleText(month);
            yearDrp.selectByVisibleText(year);
        } catch (NoSuchElementException e) {
            System.out.println("Invalid date of death passed. Default set to 01/2018");
            monthDrp.selectByVisibleText("01");
            yearDrp.selectByVisibleText("2018");
        }

        return this;
    }

    /**
     * Toggles the expansion of the given section.
     * Forcibly scrolls elements into view using JS. For some reason, selenium doesn't do this.
     * @param theSection is the section from the enum that we want to expand
     * @return stay on the same page so the same object
     */
    public CreatePatientPage expandSection(SECTIONS theSection) {
        forceScrollToElement(sectionMap.get(theSection));
        /*
        WebElement element = superDriver.findElement(sectionMap.get(theSection));
        Actions actions = new Actions(superDriver);
        actions.moveToElement(element);
        actions.perform();
        */
        clickOnElement(sectionMap.get(theSection));
        return this;
    }

    /**
     * Sets the patient's gender. Defaults to Unknown.
     * Assumes that Patient Information section is expanded (selectors in that section visible).
     * @param theGender is String representing gender radio button. Must be exact text.
     * @return the same page so the same object.
     */
    public CreatePatientPage setGender(String theGender) {
        waitForElementToBePresent(maleGenderBtn);
        switch (theGender) {
            case "Male": clickOnElement(maleGenderBtn); break;
            case "Female": clickOnElement(femaleGenderBtn); break;
            case "Other": clickOnElement(otherGenderBtn); break;
            case "Unknown": clickOnElement(unknownGenderBtn); break;
            default: System.out.println("Invalid gender selected! Default to Unknown"); break;
        }
        return this;
    }

    /**
     * Sets the Age of Onset under patient information.
     * Currently, only works for congential.
     * Assumes that Patient Information section is expanded (selectors in that section visible).
     * @param theOnset onset specified by radio button text, must match exactly.
     * @return Stay on the same page, return same object.
     */
    public CreatePatientPage setOnset(String theOnset) {
        waitForElementToBePresent(congenitalOnsentBtn);
        switch (theOnset) {
            default: clickOnElement(congenitalOnsentBtn); break;
        }
        return this;
    }

    /**
     * Adds a phenotype by searching. Selects the first suggested phenotype search result.
     * Assumes that "Clinical symptoms and physical findings" is expanded and there is at
     * least one suggested search result.
     * @param thePhenotype phenotype needed. Be exact and as specific as possible.
     * @return Stay on the same page, so return same object.
     */
    public CreatePatientPage addPhenotype(String thePhenotype) {
        clickAndTypeOnElement(phenotypeSearchBox, thePhenotype);
        clickOnElement(firstPhenotypeSuggestion);
        return this;
    }

    /**
     * Helper method to add a list of Phenotypes to the patient.
     * Requires the "Clinical symptoms and physical findings" section is expanded and there is at
     * least one suggested search result.
     * @param phenotypes is a List of Strings specifying the phenotypes. You should be as exact as possible.
     * @return Stay on the same page so return the same object.
     */
    public CreatePatientPage addPhenotypes(List<String> phenotypes)
    {
        for (String aPhenotype : phenotypes) {
            addPhenotype(aPhenotype);
        }
        return this;
    }

    /**
     * Adds a gene to the "Genotype information" section, using the gene name, status, and strategy.
     * Clicks the "add gene" button before adding to the bottom-most row. Searches for gene and selects first
     * suggestion.
     * Assumes that "Genotype information" is already expanded (selectors visible) and at least one gene
     * is returned in search results.
     * @param theGene name of the gene, be as exact and specific as possible.
     * @param geneStatus status of the gene, "Candidate", "Rejected candidate", etc. Must be exact.
     * @param strategy not used/implemented yet, supposed to specify which strategy checkbox to tick.
     * @return Stay on the same page so we return the same object.
     */
    public CreatePatientPage addGene(String theGene, String geneStatus, String strategy) {
        forceScrollToElement(addGeneBtn);
        clickOnElement(addGeneBtn);

        waitForElementToBePresent(geneNameBoxes); // Must wait before search for elements
        unconditionalWaitNs(1);

        List<WebElement> foundGeneBoxes = superDriver.findElements(geneNameBoxes);
        List<WebElement> foundStatusDrps = superDriver.findElements(geneStatusDrps);
        List<WebElement> foundSequencingCheckboxes = superDriver.findElements(geneStrategySequencingCheckboxes);

        // Get the last element of each list for the most bottom one
        WebElement bottommostGeneNameBox = foundGeneBoxes.get(foundGeneBoxes.size() - 1);
        WebElement bottommostStatusDrp = foundStatusDrps.get(foundStatusDrps.size() - 1);
        WebElement bottommostSequenceCheckbox = foundSequencingCheckboxes.get(foundSequencingCheckboxes.size() - 1);

        Select statusDrp = new Select(bottommostStatusDrp);

        bottommostGeneNameBox.click();
        bottommostGeneNameBox.sendKeys(theGene);
        clickOnElement(firstGeneSuggestion);

        bottommostStatusDrp.click();
        statusDrp.selectByVisibleText(geneStatus);

        bottommostSequenceCheckbox.click();

        return this;
    }

    /**
     * Traverses through all the options for the age of onset buttons, clicks on each one.
     * @return a List of Strings which represent the Age of Onset radio button labels in a
     * 'pre-order' traversal.
     */
    public List<String> cycleThroughAgeOfOnset() {
        List <String> loLabels =
            preOrderTraverseAndClick(ageOfOnsetBtns, By.cssSelector("ul > li.term-entry > input"),
                By.cssSelector("ul > li.term-entry > label"));

        clickOnElement(congenitalOnsentBtn);

        return loLabels;
    }


    /**
     * Traverses through all the options for the mode of inheritance checkboxes.
     * @return a List of Strings which represent the Mode Of Inheritance checkbox labels in a
     * 'pre-order' traversal.
     */
    public List<String> cycleThroughModeOfInheritance() {

        return preOrderTraverseAndClick(modeOfInheritanceChkboxes,
            By.cssSelector("ul > li.term-entry > input"),
            By.cssSelector("ul > li.term-entry > label"));
    }

    /**
     * Sets the "Indication for Referral box" in the Patient Information section.
     * Currently, it does not clear the contents of the box, just concatenates to whatever is there.
     * @param neededText is the String to enter into the input box.
     * @return stay on the same page so return the same object.
     */
    public CreatePatientPage setIndicationForReferral(String neededText)
    {
        clickAndTypeOnElement(indicationForReferralBox, neededText);
        return this;
    }

    /**
     * Navigates to the Pedigree Editor page by clicking "OK" on the "Assign patient to family" modal.
     * Assumes that the "Create a new family" radio option is selected by default.
     * Requires the "Family History" section to be expanded
     * @param familyName is the family name to base the pedigree off of. Pass "" (empty string) to specify
     *          the "Create a new family" radio option. Otherwise, this must be a valid existing family name.
     * @return we navigate to the Pedigree Editor page so return new instance of that.
     */
    public PedigreeEditorPage navigateToPedigreeEditor(String familyName)
    {
        clickOnElement(editPedigreeBox);

        // Case where we want to specify a family, also need to ensure that the dialogue is actually there.
        if (!familyName.equals("") && isElementPresent(editPedigreeOKBtn)) {
            clickOnElement(assignFamilyRadioBtn);
            clickAndTypeOnElement(familySearchInputBox, familyName);
            clickOnElement(firstFamilySuggestion);
        }

        // If we are editing a pedigree, there is no family selection dialogue that appears, hence just
        //   check for an OK button before trying to click.
        if (isElementPresent(editPedigreeOKBtn)) {
            clickOnElement(editPedigreeOKBtn);
        }

        return new PedigreeEditorPage(superDriver);
    }

    /**
     * Traverses through the options for the health conditions found in family yes/no boxes.
     * @return a List of Strings which represent the health conditions found under "Family history and pedigree"
     */
    public List<String> cycleThroughFamilialHealthConditions() {

        return preOrderTraverseAndClick(By.cssSelector("div.family-info > div.fieldset"),
            By.cssSelector("div.displayed-value > span.yes-no-picker > label.yes"),
            By.cssSelector("div.displayed-value > label.yes-no-picker-label"));
    }

    /**
     * Sets the ethnicity of the patient in the "Family History and Pedigree" section. Defaults to Maternal ethnicity
     * in case of invalid maternity passed. Will select the first option in the suggestions.
     * @param maternity pass either "Paternal" or "Maternal"
     * @param ethnicity is the ethncity to set. Requires this to be as close as possible to an exact match to suggestions dropdown.
     * @return Stay on the same page so return the same object.
     */
    public CreatePatientPage setEthnicity(String maternity, String ethnicity) {
        if (maternity.equals("Paternal")) {
            clickAndTypeOnElement(paternalEthnicityBox, ethnicity);
            clickOnElement(firstFamilySuggestion);
        }
        else {
            clickAndTypeOnElement(maternalEthnicityBox, ethnicity);
            clickOnElement(firstFamilySuggestion);
        }
        return this;
    }

    /**
     * Inputs a note into the Health Conditions within the "Family History and pedigree" section.
     * @param note to type into the box. Any string. Will concatenate to what is there already.
     * @return Stay on the same page so return the same object.
     */
    public CreatePatientPage setHealthConditionsFoundInFamily(String note) {
        clickAndTypeOnElement(healthConditionsFoundInFamily, note);
        return this;
    }

    /**
     * Traverses through the options for the health conditions found in
     * Prenatal and perinatal history yes/no boxes.
     * Requires: The "Prenatal and perinatal history" section to be expanded and that
     *              none of the yes/no options are already selected/expanded (i.e. should be at the state of a new patient)
     *               Otherwise, traversal result might be off due to presence of additional (appearing) selectors.
     * @return a List of Strings which represent the health conditions found under the yes/no boxes of
     *          "Prenatal and perinatal history"
     */
    public List<String> cycleThroughPrenatalHistory() {

        // TODO: Those selectors are used once, okay to leave them without variable?
        By expandToolSpan = By.cssSelector("span[class=expand-tool]");

        List<String> loLabels = new ArrayList<>();

        List<String> loUncategorizedLabels = preOrderTraverseAndClick(By.cssSelector("div.prenatal-info"),
            By.cssSelector("div.fieldset > div.displayed-value > span.yes-no-picker > label.yes"),
            By.cssSelector("div.fieldset > div.displayed-value > label.yes-no-picker-label"));

//      Expand all dropdowns, lets them load first
        preOrderTraverseAndClick(
            By.cssSelector("div.prenatal-info > div > div > div"), expandToolSpan, expandToolSpan);

        List<String> loCategorizedLabels = preOrderTraverseAndClick(
            By.cssSelector("div.prenatal-info div[class=dropdown] div[class=entry-data], div.prenatal-info div[class*=term-entry]"),
            By.cssSelector("span.yes-no-picker > label.yes"),
            By.cssSelector("label.yes-no-picker-label, span.yes-no-picker-label > span.value"));

        loLabels.addAll(loUncategorizedLabels);
        loLabels.addAll(loCategorizedLabels);

        return loLabels;
    }

    /**
     * Gets all the labels for the labels within the Edit Phenotype Details box. This does not do a tree
     * traversal due to the dropdowns having issues hiding/showing for now.
     * Requires: A phenotype to already be present and "Add Details" to already be pressed so that the details box appears.
     * @return A list of strings representing the labels found in the Edit Phenotype Details Box. This should not be
     *          empty.
     */
    public List<String> cycleThroughPhenotypeDetailsLabels()
    {
        waitForElementToBePresent(phenotypeDetailsLabels);

        List<WebElement> loExpandCarets = superDriver.findElements(expandCaretBtns);
        List<String> loLabels = new ArrayList<>();

        //Expand all first
        for (WebElement aCaret : loExpandCarets) {
            if (aCaret.getText().equals("►")) {
                clickOnElement(aCaret);
            }
        }

        superDriver.findElements(phenotypeDetailsLabels).forEach(x -> loLabels.add(x.getText()));

        return loLabels;
    }

    /**
     * Adds phenotype details to the nth detail-less phenotype (done it this way due to the simplicity of implementation)
     * in the list of phenotypes already present. Makes the grey phenotype details box appear.
     * @param n is the nth phenotype WITHOUT any details added yet.
     * @return Stay on the same page so return same object.
     */
    public CreatePatientPage addDetailsToNthPhenotype(int n) {
        waitForElementToBePresent(addPhenotypeDetailsBtns);
        List<WebElement> loPhenotypeAddBtnsPresent = superDriver.findElements(addPhenotypeDetailsBtns);

        loPhenotypeAddBtnsPresent.get(n - 1).click();

        waitForElementToBePresent(phenotypeDetailsLabels);
        return this;
    }

    /**
     * Traverses through the options for phenotypes in the Clinical Symptoms and Physical Findings Section
     * Requires: The "Clinical Symptoms and Physical Findings" section to be expanded and that
     *              none of the yes/no options are already selected/expanded (i.e. should be at the state of a new patient)
     *               Otherwise, traversal result might be off due to presence of additional (appearing) selectors.
     * @return a List of Strings which represent the health conditions found under the yes/no boxes of
     *          "Clinical Symptoms and Physical Findings"
     */
    public List<String> cycleThroughAllPhenotypes() {

        By expandAllBtn = By.cssSelector("span.expand-all");

        clickOnElement(expandAllBtn);

        // TODO: Those selectors are used once, okay to leave them without variable?
        By expandToolSpan = By.cssSelector("span[class=expand-tool]");

        List<String> loLabels = new ArrayList<>();

        forceScrollToElement(By.cssSelector("div.phenotype > div > div > div"));

//      Expand all dropdowns, lets them load first
        preOrderTraverseAndClick(
            By.cssSelector("div.phenotype > div > div > div"), expandToolSpan, expandToolSpan);

        List<String> loCategorizedLabels = preOrderTraverseAndClick(
            By.cssSelector("div.phenotype div[class=dropdown] div[class=entry-data], div.prenatal-info div[class*=term-entry]"),
            By.cssSelector("span.yes-no-picker > label.na"),
            By.cssSelector("label.yes-no-picker-label, span.yes-no-picker-label > span.value"));

        loLabels.addAll(loCategorizedLabels);

    //    unconditionalWaitNs(25);

        return loLabels;
    }

    /**
     * Retrieves the phenotypes specified by the passed selector. This is a helper method that is used to
     * differentiate between different phenotypes such as ones automatically added via measurements data vs. manual input
     * Requires: The "Clinical symptoms and physical findings" section to be expanded
     * @param phenotypeLabelsSelector The By selector of the phenotype label.
     *      There are three so far: - phenotypesSelectedLabels (all),
     *                              - phenotypesAutoSelectedByMeasurementLabels (lightning bolt/auto ones),
     *                              - phenotypesManuallySelectedLabels (non-lightning bolt/manual ones)
     * @return A List of Strings representing the names of the phenotypes found. Can potentially be empty.
     */
    private List<String> getPresentPhenotypes(By phenotypeLabelsSelector)
    {
        List<String> loPhenotypesFound = new ArrayList<>();
        waitForElementToBePresent(phenotypeSearchBox);
        superDriver.findElements(phenotypeLabelsSelector).forEach(x -> loPhenotypesFound.add(x.getText()));

        return loPhenotypesFound;
    }

    /**
     * Retrieves all of the phenotypes already present (entered) in the Patient Information Form
     * Requires: The "Clinical symptoms and physical findings" section to be expanded
     * @return A (potentially empty) list of Strings representing the names of the phenotypes found.
     */
    public List<String> getAllPhenotypes()
    {
        return getPresentPhenotypes(phenotypesSelectedLabels);
    }

    /**
     * Retrieves a list of phenotypes entered automatically due to measurement data. These are the phenotypes
     * with the lightning symbol beside them.
     * Requires: The "Clinical symptoms and physical findings" section to be expanded
     * @return A, possibly empty, list of Strings representing phenotypes that have a lightning symbol beside them
     *          due to them being automatically added from data contained on a measurements entry.
     */
    public List<String> getPhenotypesLightning()
    {
        return getPresentPhenotypes(phenotypesAutoSelectedByMeasurementLabels);
    }

    /**
     * Retrieves a list of phenotypes that were manually entered. These are the ones that are not prefixed with a
     * lightning symbol.
     * Requires: The "Clinical symptoms and physical findings" section to be expanded
     * @return A List of Strings, possibly empty, of the phenotypes that were manually entered (do not have a lightning
     *          symbol in front of them).
     */
    public List<String> getPhenotypesNonLightning()
    {
        return getPresentPhenotypes(phenotypesManuallySelectedLabels);
    }

    /**
     * Adds a new entry of measurement data to the patient under the "Measurements" section.
     * TODO: Check that it truncates floats within the measurements object to something that can be input to the box.
     * Requires: The "Measurements" section to already be expanded.
     * @param aMeasurement is a measurement object (instantiated struct) containing all the measurement fields to enter.
     * @return Stay on the same page so return the same object.
     */
    public CreatePatientPage addMeasurement(CommonPatientMeasurement aMeasurement)
    {
        clickOnElement(addMeasurementBtn);
        clickAndTypeOnElement(weightBox, String.valueOf(aMeasurement.weight));
        clickAndTypeOnElement(heightBox, String.valueOf(aMeasurement.height));
        clickAndTypeOnElement(armSpanBox, String.valueOf(aMeasurement.armSpan));
        clickAndTypeOnElement(sittingHeightBox, String.valueOf(aMeasurement.sittingHeight));
        clickAndTypeOnElement(headCircumferenceBox, String.valueOf(aMeasurement.headCircumference));
        clickAndTypeOnElement(philtrumLengthBox, String.valueOf(aMeasurement.philtrumLength));
        clickAndTypeOnElement(leftEarLengthBox, String.valueOf(aMeasurement.leftEarLength));
        clickAndTypeOnElement(rightEarLengthBox, String.valueOf(aMeasurement.rightEarLength));
        clickAndTypeOnElement(outherCanthalDistanceBox, String.valueOf(aMeasurement.outerCanthalDistance));
        clickAndTypeOnElement(innterCanthalDistanceBox, String.valueOf(aMeasurement.inntercanthalDistance));
        clickAndTypeOnElement(palpebralFissureLengthBox, String.valueOf(aMeasurement.palpebralFissureLength));
        clickAndTypeOnElement(interpupilaryDistanceBox, String.valueOf(aMeasurement.interpupilaryDistance));
        clickAndTypeOnElement(leftHandLengthBox, String.valueOf(aMeasurement.leftHandLength));
        clickAndTypeOnElement(leftPalmLengthBox, String.valueOf(aMeasurement.leftPalmLength));
        clickAndTypeOnElement(leftFootLengthBox, String.valueOf(aMeasurement.leftFootLength));
        clickAndTypeOnElement(rightHandLengthBox, String.valueOf(aMeasurement.rightHandLength));
        clickAndTypeOnElement(rightPalmLengthBox, String.valueOf(aMeasurement.rightPalmLength));
        clickAndTypeOnElement(rightFootLengthBox, String.valueOf(aMeasurement.rightFootLength));

        return this;
    }

    /**
     * Changes the date of the first measurement to the specified month and year. Defaults to January 2018
     * upon invalid input.
     * Requires: The measurement section to be open and at least one measurement entry to be present.
     * @param month is the month as a String "January" to "December". Must be exact.
     * @param year is the year as a String "1920" to current year (ex. "2019"). Must be exact.
     * @return Stay on the same page so return the same object.
     */
    public CreatePatientPage changeMeasurementDate(String day, String month, String year)
    {
        By calendarDayBtn = By.xpath("//div[contains(text(), '" + day + "')]");

        clickOnElement(measurementDateBoxes);

        waitForElementToBePresent(measurementMonthDrp);
        Select monthDrp = new Select(superDriver.findElement(measurementMonthDrp));
        Select yearDrp = new Select(superDriver.findElement(measurementYearDrp));

        try {
            monthDrp.selectByVisibleText(month);
            yearDrp.selectByVisibleText(year);
        } catch (NoSuchElementException e) {
            System.out.println("Invalid dropdown month or year passed. Defaulting to January 2018");
            monthDrp.selectByVisibleText("January");
            yearDrp.selectByVisibleText("2018");
        }

        clickOnElement(calendarDayBtn);
        waitForElementToBeGone(measurementMonthDrp);

        return this;
    }

    /**
     * Retrieves the first measurement entry for the patient that has measurement data entered.
     * Requires: The "Measurement" section to be open and there be at least one measurmenet entry already there.
     * @return A Measurement object constructed with the measurement data gathered from the patient.
     */
    public CommonPatientMeasurement getPatientMeasurement()
    {
        waitForElementToBePresent(weightBox);

        float weight = Float.parseFloat(superDriver.findElement(weightBox).getAttribute("value"));
        float armSpan = Float.parseFloat(superDriver.findElement(armSpanBox).getAttribute("value"));
        float headCircumference = Float.parseFloat(superDriver.findElement(headCircumferenceBox).getAttribute("value"));
        float outerCanthalDistance = Float.parseFloat(superDriver.findElement(outherCanthalDistanceBox).getAttribute("value"));
        float leftHandLength = Float.parseFloat(superDriver.findElement(leftHandLengthBox).getAttribute("value"));
        float rightHandLength = Float.parseFloat(superDriver.findElement(rightHandLengthBox).getAttribute("value"));

        float height = Float.parseFloat(superDriver.findElement(heightBox).getAttribute("value"));
        float sittingHeight = Float.parseFloat(superDriver.findElement(sittingHeightBox).getAttribute("value"));
        float philtrumLength = Float.parseFloat(superDriver.findElement(philtrumLengthBox).getAttribute("value"));
        float inntercanthalDistance = Float.parseFloat(superDriver.findElement(innterCanthalDistanceBox).getAttribute("value"));
        float leftPalmLength = Float.parseFloat(superDriver.findElement(leftPalmLengthBox).getAttribute("value"));
        float rightPalmLength = Float.parseFloat(superDriver.findElement(rightPalmLengthBox).getAttribute("value"));

        float leftEarLength = Float.parseFloat(superDriver.findElement(leftEarLengthBox).getAttribute("value"));
        float palpebralFissureLength = Float.parseFloat(superDriver.findElement(palpebralFissureLengthBox).getAttribute("value"));
        float leftFootLength = Float.parseFloat(superDriver.findElement(leftFootLengthBox).getAttribute("value"));
        float rightFootLength = Float.parseFloat(superDriver.findElement(rightFootLengthBox).getAttribute("value"));

        float rightEarLength = Float.parseFloat(superDriver.findElement(rightEarLengthBox).getAttribute("value"));
        float interpupilaryDistance = Float.parseFloat(superDriver.findElement(interpupilaryDistanceBox).getAttribute("value"));

        return new CommonPatientMeasurement(
            weight, armSpan, headCircumference, outerCanthalDistance, leftHandLength, rightHandLength,
            height, sittingHeight, philtrumLength, inntercanthalDistance, leftPalmLength, rightPalmLength,
            leftEarLength, palpebralFissureLength, leftFootLength, rightFootLength,
            rightEarLength, interpupilaryDistance);
    }



}
