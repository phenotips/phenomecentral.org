package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Represents the page on http://localhost:8083/ (HOMEPAGE_URL)
  */
public class homePage extends BasePage
{
    final By loginLink = By.id("launch-login");

    final By signUpButton = By.cssSelector("launch-register");

    public homePage(WebDriver aDriver)
    {
        super(aDriver);
    } // Give the webdriver to the superclass

    /**
     * Go to the login page for a user who is not signed in yet.
     * Requires a user to not be signed in; The start state of the page should
     * be the splash page where PC is being introduced.
     * Ex. "Enter cases, find matches, and connect with other rare disease specialists. Find out more..."
     * @return a new login page object as we navigate there
     */
    public loginPage navigateToLoginPage()
    {
        superDriver.navigate().to(HOMEPAGE_URL);
        clickOnElement(loginLink);
        return new loginPage(superDriver);
    }
}
