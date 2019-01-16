package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

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
        By.cssSelector("#date-of-birth-block > div:nth-child(2) > div > div > span > select");

    private final By dobYearDrp =
        By.cssSelector("#date-of-birth-block > div:nth-child(2) > div > div > span > select:nth-child(2)");

    private final By maleGenderBtn = By.id("PhenoTips.PatientClass_0_gender");

    private final By congenitalOnsentBtn = By.id("PhenoTips.PatientClass_0_global_age_of_onset_HP:0003577");

    private final By updateBtn = By.cssSelector("#patient-consent-update > a:nth-child(1)");

//    private final By familyHistoryPedigreeSection = By.id("HFamilyhistoryandpedigree");

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

    public CreatePatientPage setIdentifer(String identifer)
    {
        clickOnElement(identifierBox);
        superDriver.findElement(identifierBox).clear();
        clickAndTypeOnElement(identifierBox, identifer);
        unconditionalWaitNs(1);
        return this;
    }


}
