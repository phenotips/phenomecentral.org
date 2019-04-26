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

import org.openqa.selenium.By;

import io.qameta.allure.Step;

/**
 * This class represents the Admin's Refresh Matches page where they can refresh matches for All Patients or Patients
 * Modified Since Last Update.
 */
public class AdminRefreshMatchesPage extends BasePage
{
    private final By patientsSinceLastModBtn = By.id("find-updated-matches-button");

    private final By allMatchesBtn = By.id("find-all-matches-button");

    private final By confirmFindAllMatchesBtn = By.cssSelector("input.button[value=\"Find matches\"]");

    // Assumes local server only, i.e. There is only one row.
    private final By selectLocalServerForMatchBox = By.cssSelector("td.select-for-update > input");

    private final By numberPatientsProcessed = By.cssSelector("td.numPatientsCheckedForMatches");

    private final By totalMatchesFound = By.cssSelector("td.totalMatchesFound");

    private final By findMatchesText = By.id("find-matches-messages");

    private final String completedMatchesMessage = "Done - refresh page to see the updated table above.";

    public AdminRefreshMatchesPage()
    {
        super();
    }

    /**
     * Hits the refresh matches "For all patients" button and then calls waitForSuccessMessage() to wait for the green
     * success message text.
     *
     * @return Stay on the same page so we return the same object.
     */
    @Step("Refresh matches for All Patients")
    public AdminRefreshMatchesPage refreshAllMatches()
    {
        clickOnElement(this.selectLocalServerForMatchBox);
        clickOnElement(this.allMatchesBtn);
        clickOnElement(this.confirmFindAllMatchesBtn);
        waitForElementToBePresent(this.findMatchesText); // Must wait for this to appear before passing to loop.
        waitForSuccessMessage();

        DRIVER.navigate().refresh();
        waitForElementToBePresent(this.selectLocalServerForMatchBox);

        return this;
    }

    /**
     * Hits the refresh matches "For patients modified since last update" button. Calls the helper
     * waitForSuccessMessage() to wait for the green success text to appear.
     *
     * @return Stay on the same page so we return the same object.
     */
    @Step("Refresh matches for patients that were modified since last update")
    public AdminRefreshMatchesPage refreshMatchesSinceLastUpdate()
    {
        clickOnElement(this.selectLocalServerForMatchBox);
        clickOnElement(this.patientsSinceLastModBtn);
        waitForElementToBePresent(this.findMatchesText); // Must wait for this to appear before passing to loop.
        waitForSuccessMessage();

        DRIVER.navigate().refresh();
        unconditionalWaitNs(1); // It might find the element before refresh is completed.
        waitForElementToBePresent(this.selectLocalServerForMatchBox);

        return this;
    }

    /**
     * Gets the number under the "Number of local patients processed" column. Assumes that there is only one row, gets
     * only the first row. String because the value could be "-" for never having a refresh before.
     *
     * @return a STRING indicating the number of patients processed in the refresh.
     */
    @Step("Get the number of local patients processed")
    public String getNumberOfLocalPatientsProcessed()
    {
        waitForElementToBePresent(this.numberPatientsProcessed);
        return DRIVER.findElement(this.numberPatientsProcessed).getText();
    }

    /**
     * Gets the number under "Total matches found" column. Assumes one row, only gets the value in the first row. String
     * because value could be "-" when there was never a refresh.
     *
     * @return a STRING indicating the number of patients found during the refresh.
     */
    @Step("Get the number of total matches found")
    public String getTotalMatchesFound()
    {
        waitForElementToBePresent(this.totalMatchesFound);
        return DRIVER.findElement(this.totalMatchesFound).getText();
    }

    /**
     * Helper function to wait for the successful green message text ("Done - refresh page to see...") Loop waits 5
     * seconds each time until the timeout of 30 seconds. Requires: Some green message text/matches search to be in
     * progress.
     */
    @Step("Wait for refresh success message 'Done - refresh page to see..'")
    private void waitForSuccessMessage()
    {
        int secondsWaited = 0;
        int secondsToWait = 30;

        // While the green matches message text is not "Done, refresh page to see...", wait 5 secs and check again.
        while (!(DRIVER.findElement(this.findMatchesText).getText().equals(this.completedMatchesMessage))) {
            if (secondsWaited > secondsToWait) {
                break;
            }

            unconditionalWaitNs(5);
            secondsWaited += 5;
        }
    }
}
