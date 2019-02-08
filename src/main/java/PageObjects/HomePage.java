package PageObjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Represents the page on http://localhost:8083/ (HOMEPAGE_URL)
  */
public class HomePage extends BasePage
{
    final By loginLink = By.id("launch-login");

    final By signUpButton = By.id("launch-register");

    final By sectionTitles = By.cssSelector("div.gadget-title"); // Titles of the sections visible on the splash page

    final By unauthorizedActionErrorMsg = By.cssSelector("p.xwikimessage"); // Error message that users get when trying to view patients that aren't theirs.

    public HomePage(WebDriver aDriver)
    {
        super(aDriver);
    } // Give the webdriver to the superclass

    /**
     * Go to the login page for a user who is not signed in yet.
     * Requires a user to not be signed in; The start state of the page should
     * be the splash page where PC is being introduced.
     * Ex. "Enter cases, find matches, and connect with other rare disease specialists. Find out more..."
     * Requires: User should be logged out when this is called.
     * @return a new login page object as we navigate there
     */
    public LoginPage navigateToLoginPage()
    {
        superDriver.navigate().to(HOMEPAGE_URL);
        if (isElementPresent(logOutLink)) {
            logOut();
            unconditionalWaitNs(5); // Give a pause, logging out took too long last time.
            System.out.println("Trying to get to login page. Logging out (again).");
        }
        clickOnElement(loginLink);
        return new LoginPage(superDriver);
    }

    /**
     * Navigate to the User Sign up page by clicking on the "Sign Up" button from the homepage.
     * This is the public sign up page form where people can request access to the PC instance.
     * Ideally, the no user should be signed in when calling this method.
     * @return A new instance of the UserSignUp page as we navigate there.
     */
    public UserSignUpPage navigateToSignUpPage()
    {
        superDriver.navigate().to(HOMEPAGE_URL);
        if (isElementPresent(logOutLink)) {
            logOut();
        }
        clickOnElement(signUpButton);

        return new UserSignUpPage(superDriver);
    }

    /**
     * Retrieves a list of section titles that appear when a user logs in. These are the individual widgets that are
     * present right after logging in (on the home page). As of now, they are: "My Matches", "My Patients",
     * "Patients Shared With Me", "My Groups" and "Public Data"
     * This is useful for determining if the patient has privilages that are granted upon user approval. Without approval,
     * they should see none of those headings.
     * @return A list of Strings representing the titles of each section.
     */
    public List<String> getSectionTitles()
    {
        waitForElementToBePresent(logOutLink);
        List<String> loTitles = new ArrayList<>();

        superDriver.findElements(sectionTitles).forEach(x-> loTitles.add(x.getText()));

        return loTitles;
    }

    /**
     * Retrieve the unauthorized action error message that a user gets. For instance, when trying to view a patient that
     * is not theirs and is not public. "You are not allowed to view this page or perform this action."
     * Requires: That the error message page be present, otherwise will throw exception that error is not found.
     *           Exception when there is no error, hence test failure.
     * @return A String representing the message.
     */
    public String getUnauthorizedErrorMessage()
    {
        waitForElementToBePresent(unauthorizedActionErrorMsg);

        return superDriver.findElement(unauthorizedActionErrorMsg).getText();
    }
}
