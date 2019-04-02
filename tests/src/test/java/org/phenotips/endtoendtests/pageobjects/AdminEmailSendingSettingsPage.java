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
import org.openqa.selenium.WebDriver;

import io.qameta.allure.Step;

/**
 * This class corresponds to the Mail Sending Settings page that is found in Admin settings under the left accordion
 * menu: Email -> Mail Sending (I.e. http://localhost:8083/admin/XWiki/XWikiPreferences?editor=globaladmin&section=emailSend)
 */
public class AdminEmailSendingSettingsPage extends AdminSettingsPage
{
    private final By emailServerPortBox = By.id("Mail.SendMailConfigClass_0_port");

    private final By saveBtn = By.cssSelector("input[value='Save']");

    public AdminEmailSendingSettingsPage(WebDriver aDriver)
    {
        super(aDriver);
    }

    /**
     * Sets the email port of the PC instance to the one specified. Clears whatever is in the box and sets it. Requires
     * that the Email Sending Settings page be open.
     *
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
