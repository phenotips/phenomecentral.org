package TestCases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;

/**
 * An abstract test. All tests should inherit this class.
 * We should put any high level methods using @after- annotations here
 * We can also put any high level @BeforeSuite methods here too to setup/check main conditions.
 */
public abstract class BaseTest
{
    protected static WebDriver theDriver = new FirefoxDriver();

//    @BeforeTest
//    public void testSetup() {
//        theDriver = new FirefoxDriver();
//        aHomePage = new HomePage(theDriver);
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

    /**
     * Cleans up the tests. Pauses a bit first so that we can see the state of the class at the end.
     * We need to explicitly quit the web driver before it goes out of scope in order for the window to close
     * and the Firefox instance to quit
     */
    @AfterClass
    public void testCleanup()
    {
        // Pause a bit before closing.
//        try {
//            Thread.sleep(1500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        System.out.println("A single class has finished");

//        if (theDriver != null) {
//            theDriver.quit();
//        }
    }

    @AfterSuite
    public void cleanup()
    {
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
