package org.phenotips.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Represents viewing a specifc patient's full information page.
 * Ex. http://localhost:8083/P0000005
 */
public class ViewPatientPage extends CommonInfoSelectors
{
    private final By patientID = By.cssSelector("#document-title > h1:nth-child(1)");

    private final By editBtn = By.id("prActionEdit");

    private final By similarityTable = By.cssSelector(".similarity-results");

    private final By geneNames = By.cssSelector("#extradata-list-PhenoTips\\2e GeneClass_PhenoTips\\2e GeneVariantClass td.Gene > p");

    private final By geneStatuses = By.cssSelector("#extradata-list-PhenoTips\\2e GeneClass_PhenoTips\\2e GeneVariantClass td.Status");

    private final By geneStrategies = By.cssSelector("#extradata-list-PhenoTips\\2e GeneClass_PhenoTips\\2e GeneVariantClass td.Strategy");

    private final By geneComments = By.cssSelector("#extradata-list-PhenoTips\\2e GeneClass_PhenoTips\\2e GeneVariantClass td.Comments");

    private final By clinicalDiagnosisNames = By.cssSelector("div.diagnosis-info div.clinical_diagnosis div.vocabulary-term-list > p");
    private final By finalDiagnosisNames = By.cssSelector("div.diagnosis-info div.omim_id div.vocabulary-term-list > p");
    private final By additionalCommentsText = By.cssSelector("div.diagnosis_notes > div.displayed-value > p");
    private final By pubMedIDsPresent = By.cssSelector("div.article-ids > span.p-id:nth-child(1)");
    private final By resolutionNotesText = By.cssSelector("div.solved__notes > div > p");

    public ViewPatientPage(WebDriver aDriver)
    {
        super(aDriver);
    }

    /**
     * Returns a string representing the current patient's ID number. It is the string
     * at the top left corner of the page.
     * @return a String in the form of Pxxxxxxx
     */
    public String getPatientID()
    {
        waitForElementToBePresent(patientID);
        return superDriver.findElement(patientID).getText();
    }

    /**
     * Clicks on the "Edit" link to edit the patient
     * @return new patient editor page object as we navigate to the patient editing page
     */
    public CreatePatientPage editThisPatient()
    {
        clickOnElement(editBtn);
        return new CreatePatientPage(superDriver);
    }

    /**
     * Retrieves all the gene names found in the "Genotype information" section. The order of the table is preserved.
     * @return A, possibly empty, list of strings containing the gene names found. This should not have empty strings (i.e. "").
     */
    public List<String> getGeneNames()
    {
        return getLabelsFromList(geneNames);
    }

    /**
     * Retrieves all the gene statuses found in the "Genotype information" section.
     * The order of the column is preserved.
     * @return A, possibly empty, list of strings containing the gene statuses found (the entire column).
     *          This might have empty strings (i.e. "") for genes with unspecified status.
     */
    public List<String> getGeneStatus()
    {
        return getLabelsFromList(geneStatuses);
    }

    /**
     * Retrieves all the gene strategies found in the "Genotype information" section.
     * The order of the column is preserved.
     * @return A, possibly empty, list of strings containing the gene strategies found.
     *          This might have empty strings (i.e. "") for genes with unspecified strategy.
     */
    public List<String> getGeneStrategies()
    {
        return getLabelsFromList(geneStrategies);
    }

    /**
     * Retrieves all the gene comments found in the "Genotype information" section.
     * The order of the column is preserved.
     * @return A, possibly empty, list of strings containing the gene strategies found.
     *          This might have empty strings (i.e. "") for genes with no comments.
     */
    public List<String> getGeneComments()
    {
        return getLabelsFromList(geneComments);
    }

    /**
     * Retrieves all the ORDO names of Clinical Diagnosis, in order, that have been entered within the
     * "Diagnosis" section. Each String is ORDO number followed by name. Ex. "427 Familial hypoaldosteronism".
     * @return A possibly empty, list of Strings representing the ORDO names that were found under
     *          Clinical Diagnosis.
     */
    public List<String> getClinicalDiagnosisNames() { return getLabelsFromList(clinicalDiagnosisNames); }

    /**
     * Retrieves all the OMIM names of Final Diagnosis, in order, that have been entered within the
     * "Diagnosis" section. Each String has OMIM number and name. Ex. "#151623 LI-FRAUMENI SYNDROME"
     * @return A possibly empty, list of Strings representing the OMIM names that were found under
     *          Final Diagnosis.
     */
    public List<String> getFinalDiagnosisNames() { return getLabelsFromList(finalDiagnosisNames); }

    /**
     * Retrieves the additional comment under the Diagnosis section.
     * @return A String containing the comment under Additional Comments. If there is no Additional Comments
     *          section, will return an empty String ("").
     */
    public String getAdditionalComments()
    {
        if (isElementPresent(additionalCommentsText)) {
            return superDriver.findElement(additionalCommentsText).getText();
        }
        else {
            return "";
        }
    }

    /**
     * Retrieves a List of PubMed IDs that have been entered in the "This case is published in:" area under the
     * "Diagnosis" section. These strings will be in the form of "PMID: 30700910".
     * @return A, possibly empty, list of Strings containing the PMIDs of entered cases.
     */
    public List<String> getExistingPubMedIDs()
    {
        return getLabelsFromList(pubMedIDsPresent);
    }

    /**
     * Retrieves the comment under the "Resolution Notes:" area under the "Diagnosis" section.
     * If there is no note and the element is not visible, will return empty String ("").
     * @return A String representing the contents of what was entered and saved in the "Resolution Notes" box.
     */
    public String getResolutionNotes()
    {
        if (isElementPresent(resolutionNotesText)) {
            return superDriver.findElement(resolutionNotesText).getText();
        }
        else {
            return "";
        }
    }

}
