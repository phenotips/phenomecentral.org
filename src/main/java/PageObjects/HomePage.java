package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Represents the page on http://localhost:8083/ (HOMEPAGE_URL)
  */
public class HomePage extends BasePage
{
    final By loginLink = By.id("launch-login");

    final By signUpButton = By.cssSelector("launch-register");

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
}
