package PageObjects;

import java.util.List;
import java.util.Vector;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

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
    public EmailUIPage deleteAllEmails()
    {
        if (getNumberOfEmails() > 0) {
            unconditionalWaitNs(5);
            clickOnElement(deleteAllEmailsLink);
        }
        else {
            System.out.println("There are no emails to delete!");
        }
        return this;
    }

    /**
     * Get a (possibly empty) list of titles of emails from the inbox.
     * @return a List of Strings which are titles of all the emails.
     */
    public List<String> getEmailTitles() {
        List<String> loTitles = new Vector<String>();

        unconditionalWaitNs(1);
        if (getNumberOfEmails() > 0) {
            waitForElementToBePresent(emailTitles);
            superDriver.findElements(emailTitles).forEach(x -> loTitles.add(x.getText()));
        }
        return loTitles;
    }

    /**
     * Indicates the number of emails in the inbox. Checks the main text for the overall status first.
     * @return integer >= 0.
     */
    public int getNumberOfEmails() {
        waitForElementToBePresent(emailStatus);
        String emailText = superDriver.findElement(emailStatus).getText();
        if (emailText.contains("You have ")) {
            return superDriver.findElements(emailRows).size();
        }
        else {
            return 0;
        }
    }

}
