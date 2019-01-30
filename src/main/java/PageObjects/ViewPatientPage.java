package PageObjects;

import java.util.ArrayList;
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
}
