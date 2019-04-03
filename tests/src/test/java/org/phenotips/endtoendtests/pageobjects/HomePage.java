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

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

import io.qameta.allure.Step;

/**
 * Represents the homepage on {@code http://localhost:8083/}.
 */
public class HomePage extends BasePage
{
    final By loginLink = By.id("launch-login");

    final By signUpButton = By.id("launch-register");

    final By sectionTitles = By.cssSelector("div.gadget-title"); // Titles of the sections visible on the splash page

    final By unauthorizedActionErrorMsg = By.cssSelector("p.xwikimessage");
    // Error message that users get when trying to view patients that aren't theirs.

    public HomePage(WebDriver aDriver)
    {
        super(aDriver);
    } // Give the webdriver to the superclass

    /**
     * Go to the login page where a user enters their credentials. It tries to logout when it cannot find the login link
     * on the homepage. This should result in the login page being displayed. Ideally, this gets called from the splash
     * page that the public sees when on the home page. Ex. "Enter cases, find matches, and connect with other rare
     * disease specialists. Find out more..."
     *
     * @return a new login page object as we navigate there
     */
    @Step("Navigate to login page")
    public LoginPage navigateToLoginPage()
    {
        this.superDriver.navigate().to(this.HOMEPAGE_URL);
        // Try to click on the link immediately, rather than checking for a logOut link
        // being present. That causes five seconds of whenever trying to navigate to login page.
        try {
            clickOnElement(this.loginLink);
        } catch (TimeoutException e) {
            logOut();
        }

        return new LoginPage(this.superDriver);
    }

    /**
     * Navigate to the User Sign up page by clicking on the "Sign Up" button from the homepage. This is the public sign
     * up page form where people can request access to the PC instance. Ideally, the no user should be signed in when
     * calling this method.
     *
     * @return A new instance of the UserSignUp page as we navigate there.
     */
    @Step("Navigate to sign up page")
    public UserSignUpPage navigateToSignUpPage()
    {
        this.superDriver.navigate().to(this.HOMEPAGE_URL);
        if (isElementPresent(this.logOutLink)) {
            logOut();
        }
        clickOnElement(this.signUpButton);

        return new UserSignUpPage(this.superDriver);
    }

    /**
     * Retrieves a list of section titles that appear when a user logs in. These are the individual widgets that are
     * present right after logging in (on the home page). As of now, they are: "My Matches", "My Patients", "Patients
     * Shared With Me", "My Groups" and "Public Data" This is useful for determining if the patient has privileges that
     * are granted upon user approval. Without approval, they should see none of those headings.
     *
     * @return A list of Strings representing the titles of each section.
     */
    @Step("Retrieve titles of each section on the splash page")
    public List<String> getSectionTitles()
    {
        waitForElementToBePresent(this.logOutLink);
        List<String> loTitles = new ArrayList<>();

        this.superDriver.findElements(this.sectionTitles).forEach(x -> loTitles.add(x.getText()));

        return loTitles;
    }

    /**
     * Retrieve the unauthorized action error message that a user gets. For instance, when trying to view a patient that
     * is not theirs and is not public. "You are not allowed to view this page or perform this action." Requires: That
     * the error message page be present, otherwise will throw exception that error is not found. Exception when there
     * is no error, hence test failure.
     *
     * @return A String representing the message.
     */
    @Step("Retrieve the unauthorized access error message")
    public String getUnauthorizedErrorMessage()
    {
        waitForElementToBePresent(this.unauthorizedActionErrorMsg);

        return this.superDriver.findElement(this.unauthorizedActionErrorMsg).getText();
    }
}
