package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

// Represents the page reached when "Create... -> New patient" is clicked on the navbar
//    http://localhost:8083/edit/data/Pxxxxxxx (new patient ID)
public class createPatientPage extends commonInfoSelectors {

    public createPatientPage(WebDriver aDriver) { super(aDriver); }

    private final By patientIDDiv = By.id("document-title");
    private final By realPatientConsentBox = By.id("real-consent-checkbox");
    private final By geneticConsentBox = By.id("genetic-consent-checkbox");
    private final By shareHistoryConsentBox = By.id("share_history-consent-checkbox");
    private final By shareImagesConsentBox = By.id("share_images-consent-checkbox");
    private final By matchingConsentBox = By.id("matching-consent-checkbox");

    // Add in selectors for text fields here

    private final By patientConsentUpdateBtn = By.id("patient-consent-update");

    private final By identifierBox = By.id("PhenoTips.PatientClass_0_external_id");
    private final By lifeStatusDrp = By.id("PhenoTips.PatientClass_0_life_status");
    private final By dobMonthDrp = By.cssSelector("#date-of-birth-block > div:nth-child(2) > div > div > span > select");
    private final By dobYearDrp = By.cssSelector("#date-of-birth-block > div:nth-child(2) > div > div > span > select:nth-child(2)");

    private final By maleGenderBtn = By.id("PhenoTips.PatientClass_0_gender");
    private final By congenitalOnsentBtn = By.id("PhenoTips.PatientClass_0_global_age_of_onset_HP:0003577");

//    private final By familyHistoryPedigreeSection = By.id("HFamilyhistoryandpedigree");

    private final By updateBtn = By.cssSelector("#patient-consent-update > a:nth-child(1)");
    private final By saveAndViewSummaryBtn = By.cssSelector("span.buttonwrapper:nth-child(3) > input:nth-child(1)");

    public createPatientPage toggleNthConsentBox(int n) {
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
        }
        return this;
    }

    public createPatientPage updateConsent() {
        clickOnElement(updateBtn);
        unconditionalWait5s();
        return this;
    }

    public viewPatientPage saveAndViewSummary() {
        clickOnElement(saveAndViewSummaryBtn);
        return new viewPatientPage(superDriver);
    }


}
