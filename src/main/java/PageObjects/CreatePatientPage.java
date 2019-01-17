package PageObjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

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

    private final By maleGenderBtn = By.id("xwiki-form-gender-0-0");
    private final By femaleGenderBtn = By.id("xwiki-form-gender-0-1");
    private final By otherGenderBtn = By.id("xwiki-form-gender-0-2");
    private final By unknownGenderBtn = By.id("xwiki-form-gender-0-3");

    private final By congenitalOnsentBtn = By.id("PhenoTips.PatientClass_0_global_age_of_onset_HP:0003577");

    private final By updateBtn = By.cssSelector("#patient-consent-update > a:nth-child(1)");

//    private final By familyHistoryPedigreeSection = By.id("HFamilyhistoryandpedigree");

    private final By phenotypeSearchBox = By.id("quick-phenotype-search");
    private final By firstPhenotypeSuggestion = By.cssSelector("li.xitem > div");
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
     * Sets the date of birth of the patient under Patient Information.
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
            case "Unkown": clickOnElement(unknownGenderBtn); break;
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


}
