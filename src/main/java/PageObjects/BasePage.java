package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * This abstract class contains the toolbar (navbar) elements which is visible on all pages
 * All page classes should inherit this base class
 */
public abstract class BasePage
{
    /**
     * Public selectors, the LoginPageTest touches these.
     * Ideally, tests should not touch selectors.
     */
    public final By adminLink = By.id("tmAdminSpace");

    public final By aboutLink = By.id("tmAbout");

    /**
     * Common URLs, specify address of the PC instance
     */
    protected final String HOMEPAGE_URL = "http://localhost:8083";

    protected final String ALL_PAITIENTS_URL = "http://localhost:8083/AllData";

    // protected final String NEW_PAITIENTS_URL = "http://localhost:8083/AllData";

    protected final String EMAIL_UI_URL = "http://localhost:8085";

    /**
     * Common login credentials.
     */
    protected final String ADMIN_USERNAME = "Admin";

    protected final String ADMIN_PASS = "admin";

    protected final String USER_USERNAME = "TestUser1Uno";

    protected final String USER_PASS = "123456";

    protected final String USER_USERNAME2 = "TestUser2Dos";

    protected final String USER_PASS2 = "123456";

    /**
     * Private selectors from the navigation toolbar
     */

    private final By createMenuDrp = By.cssSelector(
        "#phenotips-globalTools > div > div > ul > li:nth-child(1) > span"
    );

    private final By newPatientLink = By.id("create-patient-record");

    private final By browseMenuDrp = By.cssSelector(
        "#phenotips-globalTools > div > div > ul > li:nth-child(2) > span");

    private final By viewAllPatientsLink = By.cssSelector(
        "#phenotips-globalTools > div > div > ul > li:nth-child(2) > ul > li:nth-child(1) > span > a");

    protected final By logOutLink = By.id("tmLogout"); // Used to check when modals close

    /**
     * Declaration of the webdriver and the waiting objects. Will be initialized
     * when a test runs.
     */
    WebDriver superDriver;

    WebDriverWait pause;

    /**
     * CTOR. The timeout period is defined here. We can also set the polling
     * interval if need be.
     * @param aDriver is the instance of webdriver created for the test. Must not be {@code null}
     */
    public BasePage(WebDriver aDriver)
    {
        superDriver = aDriver;
        pause = new WebDriverWait(superDriver, 5);
    }



    /**
     * Explicitly wait for the passed element to appear, upto the timeout specified in {@code pause}
     * Checks immediately and then keeps polling at the default interval.
     * @Throws TimeOutException (implicit) from selenium if it fails to locate element within timeout
     * @param elementSelector specifies the element selector on the page. Must not be {@code null}
     */
    public void waitForElementToBePresent(By elementSelector)
    {
        pause.until(ExpectedConditions.presenceOfElementLocated(elementSelector));
    }

    /**
     * Explicitly wait for the specified element to be clickable. Useful for when a modal blocks the
     * access of the rest of the page (i.e. waiting for the modal to close).
     * @param elementSelector specifies the element to wait for clickable. Must not be {@code null}
     */
    public void waitForElementToBeClickable(By elementSelector)
    {
        pause.until(ExpectedConditions.elementToBeClickable(elementSelector));
    }

    /**
     * Explicitly sleep for a full n seconds. Does not wait on anything specific. Useful when it is difficult
     * to specify an element to wait and check upon. Ex. Filtering update
     * @Throws InterruptedException (implicit) if thread is interrupted, ex. SIGIGNT.
     *         Handles it by just continuing after printing a message.
     * @param n is time to pause in seconds.
     */
    public void unconditionalWaitNs(int n)
    {
        try {
            Thread.sleep(n * 1000);
        } catch (InterruptedException e) {
            System.err.println("Test was interrupted during an unconditional wait of " + n + " seconds!");
        }
    }

    /**
     * Waits for an element and then tries to click. The findElement() might have a (short) implicit wait but it
     * is safer to explicitly wait for the element first.
     * @param elementSelector is the element to click on. Should not be {@code null}
     */
    public void clickOnElement(By elementSelector)
    {
        waitForElementToBePresent(elementSelector);
        superDriver.findElement(elementSelector).click();
    }

    /**
     * Similar to {@link BasePage#clickOnElement(By)}. Just sends a specified input string to the element.
     * This usually should be a text box.
     * @Throws Some kind of Element-not-interactable exception (implicit) when element cannot accept keyboard string
     * @param elementSelector is the selector. It must accept a string input from keyboard.
     * @param input an arbitrary string to input.
     */
    public void clickAndTypeOnElement(By elementSelector, String input)
    {
        clickOnElement(elementSelector);
        superDriver.findElement(elementSelector).sendKeys(input);
    }

    /**
     * Returns a Bool after waiting and then checking if the element is present
     * @param elementSelector is the element in question. Requires not {@code null}
     * @return True for present and False for not present (or is taking too long to find/appear).
     */
    public Boolean isElementPresent(By elementSelector)
    {
        try {
            waitForElementToBePresent(elementSelector);
        } catch (TimeoutException e) {
            return false; // Could not find element, took too long
        }
        return true;
    }

    /**
     * Logs out by clicking on the Log Out link on the navigation bar.
     * @requires: A user to already by logged in.
     * @return a new LoginPage object.
     */
    public LoginPage logOut()
    {
        clickOnElement(logOutLink);
        return new LoginPage(superDriver);
    }

    /**
     * Navigates to the "Browse... -> Browse patients" page
     * Tries to click link first, if that fails, it will navigate directly by providing
     * the explicit AllPatients URL. Not sure why it fails sometimes.
     * @requires: A user to already by logged in.
     * @return new instance of the browse patients page
     */
    public AllPatientsPage navigateToAllPatientsPage()
    {
        // TODO: Investigate why an error is being thrown (why link is not clickable at times)
        clickOnElement(browseMenuDrp);
        try {
            clickOnElement(viewAllPatientsLink);
        } catch (ElementNotInteractableException e) {
            System.err.println("Might throw an error, All Patients Link not clickable!");
            superDriver.navigate().to(ALL_PAITIENTS_URL);
        }

        return new AllPatientsPage(superDriver);
    }

    /**
     * Navigates to the Admin Settings page by clicking the "Administrator" (gears icon) link at the
     * top left of the navbar
     * @requires: An administrator to already be logged in.
     * @return new object of the AdminSettingsPage
     */
    public AdminSettingsPage navigateToAdminSettingsPage()
    {
        clickOnElement(adminLink);
        return new AdminSettingsPage(superDriver);
    }

    /**
     * Navigates to MockMock's (fake SMTP service) email landing page.
     * @return new object of the EmailUIPage where the email inbox can be seen
     */
    public EmailUIPage navigateToEmailInboxPage() {
        superDriver.navigate().to(EMAIL_UI_URL);
        return new EmailUIPage(superDriver);
    }

    /**
     * Navigates to the create patient page by "Create... -> New patient"
     * @return a new object of the CreatePatientPage where the creation of a new patient is performed.
     */
    public CreatePatientPage navigateToCreateANewPatientPage() {
        clickOnElement(createMenuDrp);
        unconditionalWaitNs(1);

        try {
            clickOnElement(newPatientLink);
        } catch (ElementNotInteractableException e) {
            System.err.println("Might throw an error, New Patients Link not clickable!");
            forceClickOnElement(newPatientLink);
        }
        return new CreatePatientPage(superDriver);
    }

    /**
     * Forces a scroll via JS so that the indicated element is in Selenium's viewport.
     * Without this, sometimes a "ElementClickInterceptedException" exception is thrown as
     * the element to click is out of window or otherwise blocked by some other element.
     *
     * Not sure why Selenium doesn't do this automatically for Firefox
     * @param elementSelector a By selector to indicate which element you want to scroll into view.
     */
    public void forceScrollToElement(By elementSelector) {
        waitForElementToBePresent(elementSelector);

        WebElement webElement = superDriver.findElement(elementSelector);
        ((JavascriptExecutor)superDriver).executeScript("arguments[0].scrollIntoView();", webElement);
    }

    /**
     * Waits for an elements presence then forcefully clicks using JS on it.
     * Sometimes, selectors have properties of hidden even thought they might not necessarialy be so.
     * @param elementSelector a By selector indiciating the element to click on.
     */
    public void forceClickOnElement(By elementSelector)
    {
        waitForElementToBePresent(elementSelector);

        WebElement webElement = superDriver.findElement(elementSelector);
        ((JavascriptExecutor)superDriver).executeScript("arguments[0].click();", webElement);
    }
}
