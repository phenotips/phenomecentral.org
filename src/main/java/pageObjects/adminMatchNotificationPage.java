package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class adminMatchNotificationPage extends basePage {
    public adminMatchNotificationPage(WebDriver aDriver) { super(aDriver); }

    By patientIDContainsBox = By.id("external-id-filter");
    By reloadMatchesBtn = By.id("show-matches-button");

    By firstRowFirstEmailBox = By.cssSelector("#matchesTable > tbody > tr > td[name=\"referenceEmails\"] > input");
    By firstRowSecondEmailBox = By.cssSelector("#matchesTable > tbody > tr > td[name=\"matchedEmails\"] > input");
    By sendNotificationsBtn = By.id("send-notifications-button");

    public adminMatchNotificationPage filterByID(String identifier) {
        clickAndTypeOnElement(patientIDContainsBox, identifier);
        clickOnElement(reloadMatchesBtn);
        unconditionalWaitNs(2);
        return this;
    }

    // TODO: Requires MockMock to be working and configured!!
    public adminMatchNotificationPage emailAndCheckFirstRowUsers() {
        clickOnElement(firstRowFirstEmailBox);
        clickOnElement(firstRowSecondEmailBox);
        clickOnElement(sendNotificationsBtn);
        unconditionalWaitNs(10);
        superDriver.navigate().to("http://localhost:8085/");
        waitForElementToBePresent(By.cssSelector(".delete"));
        unconditionalWaitNs(3);
        superDriver.navigate().back();
        return this;
    }



}
