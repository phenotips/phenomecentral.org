package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class loginPage extends basePage {
    public loginPage(WebDriver aDriver) { super(aDriver); }

    private final By userNameField = By.id("j_username");
    private final By passField = By.id("j_pasword"); // TODO: There might be a typo there
    private final By loginButton = By.cssSelector("input.button");


    public homePage loginAs(String username, String password) {
        clickAndTypeOnElement(userNameField, username);
        clickAndTypeOnElement(passField, password);

        clickOnElement(loginButton);

        return new homePage(superDriver);
    }

    public homePage loginAsAdmin() {
        return loginAs(ADMIN_USERNAME, ADMIN_PASS);
    }

    public homePage loginAsUser() {
        return loginAs(USER_USERNAME, USER_PASS);
    }

}
