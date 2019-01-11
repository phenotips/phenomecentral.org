package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

// This abstract class contains the toolbar elements which is visible on all pages
public abstract class basePage {
    public final By adminLink = By.id("tmAdminSpace");
    public final By aboutLink = By.id("tmAbout");

    private final By browseMenuDrp = By.cssSelector("div.rightmenu:nth-child(1) > div:nth-child(1) > ul:nth-child(1) > li:nth-child(2) > span:nth-child(1)");
    // final public By viewAllPatientsLink = By.linkText("Browse patients");
    // Must use long selector?
    private final By viewAllPatientsLink = By.cssSelector("div.rightmenu:nth-child(1) > div:nth-child(1) > ul:nth-child(1) > li:nth-child(2) > ul:nth-child(2) > li:nth-child(1) > span:nth-child(1) > a:nth-child(1)");
    private final By browseFamiliesLink = By.linkText("Browse families");
    private final By logOutLink = By.id("tmLogout");

    /*
    Constant Credentials
    */
    protected final String HOMEPAGE_URL = "http://localhost:8083";

    protected String ADMIN_USERNAME = "Admin";
    protected String ADMIN_PASS = "admin";

    protected String USER_USERNAME = "TestUser2Dos";
    protected String USER_PASS = "123456";

    /*
    Main WebDriver and Wait here
     */
    WebDriver superDriver;
    WebDriverWait pause;


    // Ctor, might want to modify timeOut for web elements
    public basePage(WebDriver aDriver) {
        superDriver = aDriver;
        pause = new WebDriverWait(superDriver, 2);
    }

    public basePage waitForElementToBePresent(By elementSelector) {
        pause.until(ExpectedConditions.presenceOfElementLocated(elementSelector));
        return this;
    }

    public basePage wait10SecondsForElement(By elementSelector) {
        WebDriverWait pauseLong = new WebDriverWait(superDriver, 10);
        pauseLong.until(ExpectedConditions.presenceOfElementLocated(elementSelector));
        return this;
    }

    public basePage unconditionalWait5s() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.err.println("Test was interrupted during an unconditional wait!!");
        }

        return this;
    }

    // Waits for element to appear and then clicks on element
    public basePage clickOnElement(By elementSelector) {
        waitForElementToBePresent(elementSelector);
        superDriver.findElement(elementSelector).click();
        return this;
    }

    public basePage clickAndTypeOnElement(By elementSelector, String input) {
        clickOnElement(elementSelector);
        superDriver.findElement(elementSelector).sendKeys(input);
        return this;
    }



    public Boolean isElementPresent(By elementSelector) {
        try {
            waitForElementToBePresent(elementSelector);
        } catch (TimeoutException e) {
            return false; // Could not find element, took too long
        }
        return true;
    }

    public loginPage logOut() {
        clickOnElement(logOutLink);
        return new loginPage(superDriver);
    }

    public allPatientsPage navigateToAllPatientsPage() {
        clickOnElement(browseMenuDrp);
        clickOnElement(viewAllPatientsLink);
        return new allPatientsPage(superDriver);
    }




}
