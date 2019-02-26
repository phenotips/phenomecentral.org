package org.phenotips.endtoendtests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import io.qameta.allure.Step;

/**
 * This class corresponds to the Mail Sending Settings page that is found in Admin settings
 * under the left accordion menu: Email -> Mail Sending
 * (I.e. http://localhost:8083/admin/XWiki/XWikiPreferences?editor=globaladmin&section=emailSend)
 */
public class AdminEmailSendingSettingsPage extends AdminSettingsPage
{
    public AdminEmailSendingSettingsPage(WebDriver aDriver)
    {
        super(aDriver);
    }

    private final By emailServerPortBox = By.id("Mail.SendMailConfigClass_0_port");
    private final By saveBtn = By.cssSelector("input[value='Save']");

    /**
     * Sets the email port of the PC instance to the one specified. Clears whatever is in the box and sets it.
     * Requires that the Email Sending Settings page be open.
     * @param port is desired port, as number, to direct outgoing emails to.
     * @return Stay on the same page so return the same object.
     */
    @Step("Set the outgoing email port to: {0}")
    public AdminEmailSendingSettingsPage setEmailPort(int port)
    {
        clickAndClearElement(emailServerPortBox);
        clickAndTypeOnElement(emailServerPortBox, Integer.toString(port));
        clickOnElement(saveBtn);
        return this;
    }

}
