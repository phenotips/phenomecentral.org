package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Represents the page http://localhost:8083/PhenomeCentral/login
 * This is the user login page
  */
public class loginPage extends BasePage
{
    private final By userNameField = By.id("j_username");

    private final By passField = By.id("j_pasword"); // TODO: There might be a typo there

    private final By loginButton = By.cssSelector("input.button");

    public loginPage(WebDriver aDriver)
    {
        super(aDriver);
    }

    /**
     * Logs in using username and password. Assumes that login will be sucessful.
     * @param username non-empty case sensitive user ID
     * @param password non-empty and case sensitive password
     * @return a homepage object as we navigate there on successful login.
     */
    public homePage loginAs(String username, String password)
    {
        clickAndTypeOnElement(userNameField, username);
        clickAndTypeOnElement(passField, password);

        clickOnElement(loginButton);

        return new homePage(superDriver);
    }

    /**
     * Logs in with the default admin credentials
     * @return a homepage object as we navigate there upon sucessful login
     */
    public homePage loginAsAdmin()
    {
        return loginAs(ADMIN_USERNAME, ADMIN_PASS);
    }

    /**
     * Logs in with a regular user's credentials.
     * @return a homepage object as we navigate there upon sucessful login
     */
    public homePage loginAsUser()
    {
        return loginAs(USER_USERNAME, USER_PASS);
    }

    /**
     * Logs in with the second user's credentials.
     * @return a homepage object as we navigate there upon sucessful login
     */
    public homePage loginAsUserTwo()
    {
        return loginAs(USER_USERNAME2, USER_PASS2);
    }
}
