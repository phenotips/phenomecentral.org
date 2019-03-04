package org.phenotips.endtoendtests.pageobjects;

import java.util.List;
import java.util.Vector;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import io.qameta.allure.Step;

/**
 * This is the webpage of the fake SMTP service's UI. In this case, we are using MockMock. As it provides a web
 * interface for the email inbox. Assumes that the fake SMTP service is running as per the Readme.
 */
public class EmailUIPage extends BasePage
{
    private final By deleteAllEmailsLink = By.cssSelector(".delete");

    private final By emailStatus = By.cssSelector("div.container:nth-of-type(2) > *:first-child");

    private final By emailRows = By.cssSelector("table > tbody > tr");

    private final By emailTitles = By.cssSelector("table > tbody > tr > td:nth-child(3) > a");

    public EmailUIPage(WebDriver aDriver)
    {
        super(aDriver);
    }

    /**
     * Deletes all emails, if any, in the inbox.
     *
     * @return stay on the same page so return the same object.
     */
    @Step("Delete all emails from inbox")
    public EmailUIPage deleteAllEmails()
    {
        if (getNumberOfEmails() > 0) {
            unconditionalWaitNs(1); // Wait is only used so that a human can see the emails.
            clickOnElement(deleteAllEmailsLink);
        } else {
            System.out.println("There are no emails to delete!");
        }
        return this;
    }

    /**
     * Get a (possibly empty) list of titles of emails from the inbox.
     *
     * @return a List of Strings which are titles of all the emails.
     */
    @Step("Retrieve a list of email titles")
    public List<String> getEmailTitles()
    {
        List<String> loTitles = new Vector<String>();

        unconditionalWaitNs(1); // Again, this wait was just for human readability
        if (getNumberOfEmails() > 0) {
            waitForElementToBePresent(emailTitles);
            superDriver.findElements(emailTitles).forEach(x -> loTitles.add(x.getText()));
        }
        return loTitles;
    }

    /**
     * Indicates the number of emails in the inbox. Checks the main text for the overall status first.
     *
     * @return integer >= 0.
     */
    @Step("Retrieve the number of emails in the inbox")
    public int getNumberOfEmails()
    {
        waitForElementToBePresent(emailStatus);
        String emailText = superDriver.findElement(emailStatus).getText();
        if (emailText.contains("You have ")) {
            return superDriver.findElements(emailRows).size();
        } else {
            return 0;
        }
    }

    /**
     * Navigates to the homepage of PC, regardless of whether or not user is logged in. This is needed as we need a way
     * to go from MockMock UI back to a PC page. Navigates directly and explicitly to the homepage url. We are
     * overriding the BasePage method since this does not try to click the PC logo that appears in the top left.
     *
     * @return a new HomePage object as we navigate there.
     */
    @Override
    @Step("Navigate to the PC instance's homepage")
    public HomePage navigateToHomePage()
    {
        superDriver.navigate().to(HOMEPAGE_URL);
        return new HomePage(superDriver);
    }
}
