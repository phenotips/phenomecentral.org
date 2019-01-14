package testCases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import pageObjects.basePage;

public abstract class baseTest {
    protected WebDriver theDriver = new FirefoxDriver();

//    @BeforeTest
//    public void testSetup() {
//        theDriver = new FirefoxDriver();
//        currentPage = new homePage(theDriver);
//    }
//
//    @AfterTest
//    public void testCleanup() {
//        // Pause a bit before closing.
//        try {
//            Thread.sleep(1500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("A single test has finished");
//
//        if (theDriver != null) {
//            theDriver.quit();
//        }
//    }

    @AfterClass
    public void testCleanup() {
        // Pause a bit before closing.
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("A single class has finished");

        if (theDriver != null) {
            theDriver.quit();
        }
    }

    @AfterSuite
    public void cleanup() {
        // Pause a bit before closing.
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Test suite finished running");

        if (theDriver != null) {
            theDriver.quit();
        }
    }

}
