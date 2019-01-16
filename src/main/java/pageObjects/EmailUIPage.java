package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * This is the webpage of the fake SMTP service's UI. In this case, we are using MockMock.
 * As it provides a web interface for the email inbox.
 * Assumes that the fake SMTP service is running as per the Readme.
 */
public class EmailUIPage extends BasePage
{
    EmailUIPage(WebDriver aDriver)
    {
        super(aDriver);
    }

    private final By deleteAllEmailsLink = By.cssSelector(".delete");

    /**
     * Deletes all emails in the inbox.
     * @return stay on the same page so return the same object.
     */
    public EmailUIPage deleteAllEmails() {
        clickOnElement(deleteAllEmailsLink);
        unconditionalWaitNs(2);
        return this;
    }


}
