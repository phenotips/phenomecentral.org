package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class homePage extends basePage {
    final By loginLink = By.id("launch-login");
    final By signUpButton = By.cssSelector("launch-register");



    public homePage(WebDriver aDriver) {
        super(aDriver); // Give the webdriver to the superclass
    }

    public loginPage navigateToLoginPage() {
        superDriver.navigate().to(HOMEPAGE_URL);
        clickOnElement(loginLink);
        return new loginPage(superDriver);
    }




}
