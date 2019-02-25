package org.phenotips.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import io.qameta.allure.Step;

/**
 * This class represents the Admin's Refresh Matches page where they can refresh matches for All Patients or
 * Patients Modified Since Last Update.
 */
public class AdminRefreshMatchesPage extends BasePage
{
    public AdminRefreshMatchesPage(WebDriver aDriver)
    {
        super(aDriver);
    }

    private final By patientsSinceLastModBtn = By.id("find-updated-matches-button");
    private final By allMatchesBtn = By.id("find-all-matches-button");
    private final By confirmFindAllMatchesBtn = By.cssSelector("input.button[value=\"Find matches\"]");

    // Assumes local server only, i.e. There is only one row.
    private final By selectLocalServerForMatchBox = By.cssSelector("td.select-for-update > input");
    private final By numberPatientsProcessed = By.cssSelector("td.numPatientsCheckedForMatches");
    private final By totalMatchesFound = By.cssSelector("td.totalMatchesFound");

    private final By findMatchesText = By.id("find-matches-messages");
    private final String completedMatchesMessage = "Done - refresh page to see the updated table above.";

    /**
     * Hits the refresh matches "For all patients" button and then calls waitForSucessMessage() to wait for the
     * green sucess message text.
     * @return Stay on the same page so we return the same object.
     */
    @Step("Refresh matches for All Patients")
    public AdminRefreshMatchesPage refreshAllMatches()
    {
        clickOnElement(selectLocalServerForMatchBox);
        clickOnElement(allMatchesBtn);
        clickOnElement(confirmFindAllMatchesBtn);
        waitForElementToBePresent(findMatchesText); // Must wait for this to appear before passing to loop.
        waitForSucessMessage();

        superDriver.navigate().refresh();
        waitForElementToBePresent(selectLocalServerForMatchBox);

        return this;
    }

    /**
     * Hits the refresh matches "For patients modified since last update" button.
     * Calls the helper waitForSucessMessage() to wait for the green success text to appear.
     * @return Stay on the same page so we return the same object.
     */
    @Step("Refresh matches for patients that were modified since last update")
    public AdminRefreshMatchesPage refreshMatchesSinceLastUpdate()
    {
        clickOnElement(selectLocalServerForMatchBox);
        clickOnElement(patientsSinceLastModBtn);
        waitForElementToBePresent(findMatchesText); // Must wait for this to appear before passing to loop.
        waitForSucessMessage();

        superDriver.navigate().refresh();
        waitForElementToBePresent(selectLocalServerForMatchBox);

        return this;
    }

    /**
     * Gets the number under the "Number of local patients processed" column.
     * Assumes that there is only one row, gets only the first row.
     * String because the value could be "-" for never having a refresh before.
     * @return a STRING indicating the number of patients processed in the refresh.
     */
    @Step("Get the number of local patients processed")
    public String getNumberOfLocalPatientsProcessed()
    {
        waitForElementToBePresent(numberPatientsProcessed);
        return superDriver.findElement(numberPatientsProcessed).getText();
    }

    /**
     * Gets the number under "Total matches found" column.
     * Assumes one row, only gets the value in the first row.
     * String because value could be "-" when there was never a refresh.
     * @return a STRING indicating the number of patients found during the refresh.
     */
    @Step("Get the number of total matches found")
    public String getTotalMatchesFound()
    {
        waitForElementToBePresent(totalMatchesFound);
        return superDriver.findElement(totalMatchesFound).getText();
    }

    /**
     * Helper function to wait for the sucessful green message text ("Done - refresh page to see...")
     * Loop waits 5 seconds each time until the timeout of 30 seconds.
     * Requires: Some green message text/matches search to be in progress.
     */
    @Step("Wait for refresh sucess message 'Done - refresh page to see..'")
    private void waitForSucessMessage() {
        int secondsWaited = 0;
        int secondsToWait = 30;

        // While the green matches message text is not "Done, refresh page to see...", wait 5 secs and check again.
        while (!(superDriver.findElement(findMatchesText).getText().equals(completedMatchesMessage))) {
            if (secondsWaited > secondsToWait) {break;}

            unconditionalWaitNs(5);
            secondsWaited += 5;
        }
    }


}
