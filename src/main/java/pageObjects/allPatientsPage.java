package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

// Represents the http://localhost:8083/AllData page, where Browse -> Browse patients is clicked on
public class allPatientsPage extends basePage {

    public allPatientsPage(WebDriver aDriver) { super(aDriver); }

    By importJSONLink = By.id("phenotips_json_import");
    By JSONBox = By.id("import");
    By importBtn = By.id("import_button");

    By sortCreationDate = By.cssSelector("th.xwiki-livetable-display-header-text:nth-child(4) > a:nth-child(1)"); // Click twice
    By firstPatientRowLink = By.cssSelector("#patients-display > tr:nth-child(1) > td:nth-child(1) > a:nth-child(1)");

    public allPatientsPage importJSONPatient(String theJSON) {
        clickOnElement(importJSONLink);
        clickAndTypeOnElement(JSONBox, theJSON);
        clickOnElement(importBtn);
        unconditionalWait5s();
        return this;
    }

    public allPatientsPage sortPatientsDateDesc() {
        clickOnElement(sortCreationDate);
        clickOnElement(sortCreationDate);
        unconditionalWait5s();
        return this;
    }

    public viewPatientPage viewFirstPatientInTable() {
        clickOnElement(firstPatientRowLink);
        return new viewPatientPage(superDriver);
    }




}
