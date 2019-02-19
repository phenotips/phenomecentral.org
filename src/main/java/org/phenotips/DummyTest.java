package org.phenotips;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

// This is a self-contained test that does not depend on anything in org.phenotips.pageobjects
// Implement using JUnit or TestNG for annotations
// Seperate into org.phenotips.pageobjects and Testcases

public class DummyTest
{
    final String url = "http://localhost:8083/";

    final String username = "TestUser2Dos";

    final String pass = "123456";

    final By loginLink = By.id("launch-login");

    final By userNameField = By.id("j_username");

    final By passField = By.id("j_pasword"); // TODO: There might be a typo there

    //final By loginButton = By.xpath("/html/body/div/div/div[2]/div/div[1]/div/div/div/div[2]/div[2]/form/div[2]/input");
    final By loginButton = By.cssSelector("input.button");

    final By logOutLink = By.id("tmLogout");

    /*
     * Logs in and asserts that "Log Out" is somewhere on the page"
     * */
    @Test
    public void loginTest()
    {
        WebDriver theDriver = new FirefoxDriver();
        WebDriverWait wait = new WebDriverWait(theDriver, 2);

        theDriver.navigate().to(url);

        wait.until(ExpectedConditions.presenceOfElementLocated(loginLink));
        theDriver.findElement(loginLink).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(userNameField));
        theDriver.findElement(userNameField).click();
        theDriver.findElement(userNameField).sendKeys(username);

        theDriver.findElement(passField).click();
        theDriver.findElement(passField).sendKeys(pass);

        theDriver.findElement(loginButton).click();

        // Assert true on JUnit here.
        wait.until(ExpectedConditions.presenceOfElementLocated(logOutLink));
        theDriver.findElement(logOutLink);

        // Pause for demo video
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        theDriver.quit();
    }
}
