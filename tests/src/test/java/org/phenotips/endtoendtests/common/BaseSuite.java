package org.phenotips.endtoendtests.common;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;

/**
 * This class is to handle BeforeSuite methods including instantiating the WebDriver
 * and assigning values to undefined environment variables
 */

public abstract class BaseSuite {

    /**
     * Environment Information. Change the default as needed.
     */
    protected static final String HOMEPAGE_URL = System.getProperty("homePageURL", "http://localhost:8083");

    protected static final String EMAIL_UI_URL = System.getProperty("emailUIPageURL", "http://localhost:8085");

    private static final String BROWSER_TO_USE = System.getProperty("browser", "chrome");

    /**
     * Common login credentials.
     */
    protected static final String ADMIN_USERNAME = "Admin";

    protected static final String ADMIN_PASS = "admin";

    protected static final String USER_1_USERNAME = "TestUser1Uno";

    protected static final String USER_1_PASS = "123456";

    protected static final String USER_2_USERNAME = "TestUser2Dos";

    protected static final String USER_2_PASS = "123456";

    /**
     * WebDriver variables. WebDriver is static to force one instance only.
     */
    protected static WebDriver DRIVER;

    /**
     * Default "maximum" waiting time in seconds. Notably it is used when waiting for an element to appear. This can be
     * thought of the timeout time if no additional wait was added to a method. Increase if your system is slow.
     */
    private static final int PAUSE_LENGTH = 5;

    private static final int PAUSE_LENGTH_LONG = 60;

    // Initialize the following two vars in constructor as they require an instantiated WebDriver
    protected WebDriverWait PAUSE;

    // Use to wait for element to disappear.
    protected WebDriverWait LONG_PAUSE;

    /**
     * Instantiate the WebDriver instance in the CTOR. The WebDriverManager takes care of setting up the environment
     * including the intermediary protocol used to communicate with the browser. This allows a user to just run the
     * tests and the manager should take care of finding the path to the desired executable and setting up environment.
     */
    public BaseSuite()
    {
        if (DRIVER == null) {
            printCommandLinePropertyMessages();
            setUpBrowser();
        }

        this.PAUSE = new WebDriverWait(BaseSuite.DRIVER, BaseSuite.PAUSE_LENGTH);
        this.LONG_PAUSE = new WebDriverWait(BaseSuite.DRIVER, BaseSuite.PAUSE_LENGTH_LONG);
    }

    /**
     * Helper function called in ctor to instantiate the desired browser.
     */
    private void setUpBrowser()
    {
        if (BROWSER_TO_USE.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            DRIVER = new ChromeDriver();
        } else if (BROWSER_TO_USE.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            DRIVER = new FirefoxDriver();
        } else if (BROWSER_TO_USE.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            DRIVER = new EdgeDriver();
        } else if (BROWSER_TO_USE.equalsIgnoreCase("ie")) {
            WebDriverManager.iedriver().setup();
            DRIVER = new InternetExplorerDriver();
        } else if (BROWSER_TO_USE.equalsIgnoreCase("safari")) {
            // No need to setup for Safari, native Selenium api should work... according to Apple docs
            DRIVER = new SafariDriver();
        } else {
            System.out.println("Unknown browser, defaulting to Chrome. browser can be one of (case insensitive): ");
            System.out.println("chrome, firefox, edge, ie, safari");
            WebDriverManager.chromedriver().setup();
            DRIVER = new ChromeDriver();
        }

        System.out.println("Initiated the webDriver for: " + BROWSER_TO_USE);
    }

    /**
     * Prints out debug messages for the three environment variables (the ones the tests are interested in) that were
     * passed through the command line. This includes the homePageURL, emailUIPageURL and browser if they were passed.
     */
    private void printCommandLinePropertyMessages()
    {
        // Detect if any command line parameters were passed to specify browser, homepageURL or
        // EmailUI page URL
        if (System.getProperty("homePageURL") != null) {
            System.out.println("homePageURL property passed. PC instance is at: " + System.getProperty("homePageURL"));
        }

        if (System.getProperty("emailUIPageURL") != null) {
            System.out
                    .println("emailUIPageURL property passed. EmailUI is at: " + System.getProperty("emailUIPageURL"));
        }

        if (System.getProperty("browser") != null) {
            System.out.println("browser property passed. Browser to use: " + System.getProperty("browser"));
        }
    }

    /**
     * Runs after the entire suite (all test cases specified in the test run) are finished. We explicitly quit the
     * webDriver here as it does not close on its own when the reference is trashed. Quitting the webDriver means
     * closing the browser window and quitting the firefox process (important).
     */
    @AfterSuite
    public void cleanup()
    {
        System.out.println("Test suite finished running");

        if (DRIVER != null) {
            DRIVER.quit();
        }
    }


}