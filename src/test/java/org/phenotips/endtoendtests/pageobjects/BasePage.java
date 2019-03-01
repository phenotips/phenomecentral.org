package org.phenotips.endtoendtests.pageobjects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ByChained;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.qameta.allure.Step;

/**
 * This abstract class contains the toolbar (navbar) elements which is visible on all pages. All page classes should
 * inherit this base class
 */
public abstract class BasePage
{
    /***************************************************************************************
     * Environment Information. You can change these as needed to run tests easily through
     * IDE instead of passing in command line options to JVM.
     ***************************************************************************************/
    /**
     * Public selectors, the LoginPageTest touches these. Ideally, tests should not touch selectors.
     */
    public final By adminLink = By.id("tmAdminSpace");

    public final By aboutLink = By.id("tmAbout");

    /**
     * Common login credentials.
     */
    protected final String ADMIN_USERNAME = "Admin";

    protected final String ADMIN_PASS = "admin";

    protected final String USER_USERNAME = "TestUser1Uno";

    protected final String USER_PASS = "123456";

    protected final String USER_USERNAME2 = "TestUser2Dos";

    protected final String USER_PASS2 = "123456";

    /*******************************
     * Selectors
     *******************************/
    protected final By logOutLink = By.id("tmLogout"); // Used in child classes to check when modals close.

    // Approval Pending Message that appears on all pages for an unapproved user
    protected final By approvalPendingMessage = By.cssSelector("#mainContentArea > div.infomessage");

    // PC logo at top left to navigate to homepage
    protected final By phenomeCentralLogoBtn = By.cssSelector("#companylogo > a > img");

    /**
     * Default "maximum" waiting time in seconds. Notably it is used when waiting for an element to appear. This can be
     * thought of the timeout time if no additional wait was added to a method. Increase if your system is slow.
     */
    private final int PAUSE_LENGTH = 5;

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

    private final By loadingStatusBar = By.id("patients-ajax-loader");

    private final By inProgressMsg = By.cssSelector("div[class='xnotification xnotification-inprogress']");

    /**
     * Common URLs, specify address of the PC instance Possible mutation in BasePage ctor, get URLs from SystemProperty
     */
    protected String HOMEPAGE_URL = "http://localhost:8083";

    protected String EMAIL_UI_URL = "http://localhost:8085";

    /**
     * Declaration of the webdriver and the explicit waiting objects. Will be initialized when a test runs and any page
     * class instantiated. See BasePage ctor.
     */
    protected WebDriver superDriver; // Initialized only when a test suite runs and needs a page, so see BaseTest class.

    private WebDriverWait pause;

    private WebDriverWait longPause; // Use to wait for element to disappear.

    /**
     * CTOR. The timeout period is defined here. We can also set the polling interval if need be.
     *
     * @param aDriver is the instance of webdriver created for the test. Must not be {@code null}
     */
    public BasePage(WebDriver aDriver)
    {
        // Sets the HOMEPAGE_URL and EMAIL_UI_URL if they were passed in as command line
        // property to JVM. Otherwise, will use what HOMEPAGE_URL and EMAIL_UI_URL as defined
        // in its declaration at the top of this page.
        final String homePageParameter = System.getProperty("homePageURL");
        final String emailUIPageParameter = System.getProperty("emailUIPageURL");

        if (homePageParameter != null) {
            HOMEPAGE_URL = homePageParameter;
            // Debug message in BaseTest when static webDriver gets instantiated
        }

        if (emailUIPageParameter != null) {
            EMAIL_UI_URL = emailUIPageParameter;
            // Debug message in BaseTest when static webDriver gets instantiated
        }

        // Set the reference to the webdriver passed from a test case and initialize waiting times
        // longPause is used to wait for something to disappear. Ex. loading bar, or a sending message.
        //  Might take longer than PAUSE_LENGTH seconds, so give it up to a minute.
        superDriver = aDriver;
        pause = new WebDriverWait(superDriver, PAUSE_LENGTH);
        longPause = new WebDriverWait(superDriver, PAUSE_LENGTH + 55);
    }

    /*************************************
     * Generic page interaction methods
     *************************************/
    /**
     * Explicitly wait for the passed element to appear, upto the timeout specified in {@code pause} Checks immediately
     * and then keeps polling at the default interval.
     *
     * @param elementSelector specifies the element selector on the page. Must not be {@code null}
     * @Throws TimeOutException (implicit) from selenium if it fails to locate element within timeout
     */
    public void waitForElementToBePresent(By elementSelector)
    {
        pause.until(ExpectedConditions.presenceOfElementLocated(elementSelector));
    }

    /**
     * Explicitly wait for the passed element to disappear, upto the timeout specified in {@code pause} Checks
     * immediately and then keeps polling at the default interval. Will return immediately if it cannot find element on
     * that immediate first try.
     *
     * @param elementSelector specifies the element selector on the page. Must not be {@code null}
     * @Throws TimeOutException (implicit) from selenium if it fails to locate element within timeout
     */
    public void waitForElementToBeGone(By elementSelector)
    {
        longPause.until(ExpectedConditions.invisibilityOfElementLocated(elementSelector));
    }

    /**
     * Explicitly wait for the specified element to be clickable. Useful for when a modal blocks the access of the rest
     * of the page (i.e. waiting for the modal to close).
     *
     * @param elementSelector specifies the element to wait for clickable. Must not be {@code null}
     */
    public void waitForElementToBeClickable(By elementSelector)
    {
        pause.until(ExpectedConditions.elementToBeClickable(elementSelector));
    }

    /**
     * Determines if the passed element is clickable or not.
     *
     * @param elementSelector is the element to check for clickability
     * @return a boolean stating whether it was clickable or not. true for clickable, and false for unclickable.
     */
    public boolean isElementClickable(By elementSelector)
    {
        try {
            waitForElementToBeClickable(elementSelector);
        } catch (TimeoutException e) {
            return false; // Could not find element, took too long
        }
        return true;
    }

    /**
     * Explicitly sleep for a full n seconds. Does not wait on anything specific. Useful when it is difficult to specify
     * an element to wait and check upon. Ex. Filtering update
     *
     * @param n is time to pause in seconds.
     * @Throws InterruptedException (implicit) if thread is interrupted, ex. SIGIGNT. Handles it by just continuing
     * after printing a message.
     */
    @Step("Unconditional wait (wait the full length) of {0} seconds.")
    public void unconditionalWaitNs(long n)
    {
        try {
            Thread.sleep(n * 1000);
        } catch (InterruptedException e) {
            System.err.println("Test was interrupted during an unconditional wait of " + n + " seconds!");
        }
    }

    /**
     * Waits for an element and then tries to click. The findElement() might have a (short) implicit wait but it is
     * safer to explicitly wait for the element first.
     *
     * @param elementSelector is the element to click on. Should not be {@code null}
     */
    public void clickOnElement(By elementSelector)
    {
        waitForElementToBePresent(elementSelector);
        clickOnElement(superDriver.findElement(elementSelector));
    }

    /**
     * Overloaded this function to allow for a located WebElement (instead of a selector) to be passed to it. This
     * function catches the ElementNotInteractableException that can happen during a click which is due to the element
     * being outside of Selenium's viewport. Strangely, sometimes we have to issue an explicit scroll using JS as
     * Selenium fails to do so automatically in its built in .click() method.
     *
     * @param aWebElement the element to click on. It will scroll to this element if outside of viewport.
     */
    public void clickOnElement(WebElement aWebElement)
    {
        try {
            aWebElement.click();
        } catch (WebDriverException e) {
            ((JavascriptExecutor) superDriver).executeScript("arguments[0].scrollIntoView();", aWebElement);
            try {
                aWebElement.click();
            } catch (WebDriverException f) {
                ((JavascriptExecutor) superDriver).executeScript("arguments[0].click();", aWebElement);
                System.out.println("Warning: Force click on element: " + aWebElement);
            }
        }
    }

    /**
     * Similar to {@link BasePage#clickOnElement(By)}. Just sends a specified input string to the element. This usually
     * should be a text box.
     *
     * @param elementSelector is the selector. It must accept a string input from keyboard.
     * @param input an arbitrary string to input.
     * @Throws Some kind of Element-not-interactable exception (implicit) when element cannot accept keyboard string
     */
    public void clickAndTypeOnElement(By elementSelector, String input)
    {
        clickOnElement(elementSelector);
        superDriver.findElement(elementSelector).clear(); // Clear first
        superDriver.findElement(elementSelector).sendKeys(input);
    }

    /**
     * Click on the element, usually a text box, and clear the contents.
     *
     * @param elementSelector The selector for the element to clear the contents of, usually a text box.
     */
    public void clickAndClearElement(By elementSelector)
    {
        clickOnElement(elementSelector);
        superDriver.findElement(elementSelector).clear(); // Clear first
    }

    /**
     * Toggles the specified checkbox to the enabled state. If it is already selected/enabled, it does not click on it
     * again.
     *
     * @param checkboxSelector is the By selector for the checkbox to changed to enabled state.
     */
    public void toggleCheckboxToChecked(By checkboxSelector)
    {
        waitForElementToBePresent(checkboxSelector);
        if (!superDriver.findElement(checkboxSelector).isSelected()) {
            clickOnElement(checkboxSelector);
        }
    }

    /**
     * Returns a Bool after waiting and then checking if the element is present
     *
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
     * Forces a scroll via JS so that the indicated element is in Selenium's viewport. Without this, sometimes a
     * "ElementClickInterceptedException" exception is thrown as the element to click is out of window or otherwise
     * blocked by some other element.
     *
     * Not sure why Selenium doesn't do this automatically for Firefox
     *
     * @param elementSelector a By selector to indicate which element you want to scroll into view.
     */
    public void forceScrollToElement(By elementSelector)
    {
        waitForElementToBePresent(elementSelector);

        WebElement webElement = superDriver.findElement(elementSelector);
        ((JavascriptExecutor) superDriver).executeScript("arguments[0].scrollIntoView();", webElement);
    }

    /**
     * Waits for an elements presence then forcefully clicks using JS on it. Sometimes, selectors have properties of
     * hidden even thought they might not necessarialy be so.
     *
     * @param elementSelector a By selector indiciating the element to click on.
     */
    public void forceClickOnElement(By elementSelector)
    {
        waitForElementToBePresent(elementSelector);

        WebElement webElement = superDriver.findElement(elementSelector);
        ((JavascriptExecutor) superDriver).executeScript("arguments[0].click();", webElement);
    }

    /**
     * Provides a pre-order traversal of a tree structure, clicking on each node and building a List of Strings
     * representing the text that Selenium can find at each node.
     *
     * @param rootPath is the By selector of where the root nodes of the tree structure is
     * @param childrenPath within each root structure found, children under {$code rootPath} are searched for using this
     * path.
     * @param childrenLabelLocation is the path to where the label text is located for the children.
     * @return a (might be empty) list of Strings representing the visits of the traversal. In case of unequal lengths
     * of the lists (i.e. it found more buttons than labels), it prints error message to stdout then returns NULL
     */
    public List<String> preOrderTraverseAndClick(By rootPath, By childrenPath, By childrenLabelLocation)
    {
        waitForElementToBePresent(rootPath); // Must wait before any driver does a find

        List<String> loLabels = new ArrayList<>();

        // Finds all children, pre-order, no recursion needed?
        List<WebElement> loButtons = superDriver
            .findElements(new ByChained(rootPath, childrenPath));

        List<WebElement> loButtonsLabels = superDriver
            .findElements(new ByChained(rootPath, childrenLabelLocation));

        Iterator<WebElement> buttonIter = loButtons.iterator();
        Iterator<WebElement> labelsIter = loButtonsLabels.iterator();

        // Check that list of buttons size (clickable area) == size of list of labels (text strings)
        if (loButtons.size() != loButtonsLabels.size()) {
            System.out.println("Unequal array sizes for buttons and labels in preOrderTraverseAndClick: " +
                "Found Buttons: " + loButtons.size() + " but found Labels: " + loLabels.size());
            return null;
        }

        forceScrollToElement(rootPath);

        while (buttonIter.hasNext() && labelsIter.hasNext()) {
            WebElement theButton = buttonIter.next();
            WebElement theLabel = labelsIter.next();

            // Might need to force a scroll to a checklist item again
            clickOnElement(theButton);
            System.out.println("DEBUG: Clicking on: " + theLabel.getText());

            loLabels.add(theLabel.getText());
        }

        return loLabels;
    }

    /**
     * Extracts the text strings for the elements that can be found via the labelsSelector. That selector can represent
     * zero or more instances of that element on the page containing a text value.
     *
     * @param labelsSelector is the selector for zero or more elements existing on the page containing text to extract.
     * @return a, possibly empty, list of Strings representing the text from each instance of the selector. A String
     * will be the empty String ("") if an element had no text on it.
     */
    public List<String> getLabelsFromList(By labelsSelector)
    {
        waitForElementToBePresent(labelsSelector);

        List<String> loTextStrings = new ArrayList<>();
        List<WebElement> loFoundLabels = superDriver.findElements(labelsSelector);

        for (WebElement e : loFoundLabels) {
            loTextStrings.add(e.getText());
        }

        return loTextStrings;
    }

    /**********************************************************************
     * Common toolbar methods. These methods can be called from any page.
     **********************************************************************/
    /**
     * Logs out by clicking on the Log Out link on the navigation bar.
     *
     * @return a new LoginPage object.
     * @requires: A user to already by logged in.
     */
    @Step("Log out")
    public LoginPage logOut()
    {
        clickOnElement(logOutLink);
        return new LoginPage(superDriver);
    }

    /**
     * Navigates to the "Browse... -> Browse patients" page
     *
     * @return new instance of the browse patients page (AllPatientsPage)
     * @requires: A user to already by logged in.
     */
    @Step("Navigate to All Patients page")
    public AllPatientsPage navigateToAllPatientsPage()
    {
        clickOnElement(browseMenuDrp);

        clickOnElement(viewAllPatientsLink);

        waitForLoadingBarToDisappear();

        return new AllPatientsPage(superDriver);
    }

    /**
     * Navigates to the Admin Settings page by clicking the "Administrator" (gears icon) link at the top left of the
     * navbar
     *
     * @return new object of the AdminSettingsPage
     * @requires: An administrator to already be logged in.
     */
    @Step("Navigate to Admin Settings Page")
    public AdminSettingsPage navigateToAdminSettingsPage()
    {
        clickOnElement(adminLink);
        return new AdminSettingsPage(superDriver);
    }

    /**
     * Navigates to MockMock's (fake SMTP service) email landing page.
     *
     * @return new object of the EmailUIPage where the email inbox can be seen
     */
    @Step("Navigate to Email Inbox Page (Fake SMTP UI)")
    public EmailUIPage navigateToEmailInboxPage()
    {
        superDriver.navigate().to(EMAIL_UI_URL);
        return new EmailUIPage(superDriver);
    }

    /**
     * Navigates to the create patient page by "Create... -> New patient"
     *
     * @return a new object of the CreatePatientPage where the creation of a new patient is performed.
     */
    @Step("Navigate to Create Patient Page (Create a new patient)")
    public CreatePatientPage navigateToCreateANewPatientPage()
    {
        clickOnElement(createMenuDrp);

        clickOnElement(newPatientLink);

        return new CreatePatientPage(superDriver);
    }

    /**
     * Retrieves the approval pending message that a user receives when they are unapproved and waiting to be granted
     * access. It should be "Please wait for your account to be approved. Thank you."
     *
     * @return A String representing the approval pending message.
     */
    public String getApprovalPendingMessage()
    {
        waitForElementToBePresent(approvalPendingMessage);
        return superDriver.findElement(approvalPendingMessage).getText();
    }

    /**
     * Navigates to the homepage by clicking on the PhenomeCentral Logo at the top left corner of the top toolbar. Note
     * that this gets overridden in the EmailUIPage class because the PC logo doesn't appear there and we have to
     * explicitly navigate back to the homepage by explicitly navigating to the HOMEPAGE_URL
     *
     * @return A new instance of a HomePage as we navigate there.
     */
    @Step("Navigate to PC home page for this instance")
    public HomePage navigateToHomePage()
    {
        clickOnElement(phenomeCentralLogoBtn);
        return new HomePage(superDriver);
    }

    /**
     * Explicitly wait for the loading bar to disappear. This is the xwiki loading bar which appears wherever there is a
     * live table filtration. Examples include the patient list (AllPatientsPage) and the match table
     * (AdminMatchNotificationPage).
     */
    public void waitForLoadingBarToDisappear()
    {
        waitForElementToBeGone(loadingStatusBar);
    }

    /**
     * Explicitly wait until the spinning message (xwiki generated) that appears on the bottom of the page disappears.
     * This message appears when sending a request to the servers. It has several different texts as they appear in
     * multiple areas but they usually have a black background before changing to green. Example, deleting a patient
     * makes the black "Sending Request..." before changing to green "Done!". Also, importing JSON patient also makes
     * "Importing JSON, please be patient..." followed by "Done!".
     */
    public void waitForInProgressMsgToDisappear()
    {
        waitForElementToBePresent(inProgressMsg);
        waitForElementToBeGone(inProgressMsg);
    }

    /**
     * Dismiss the "You have unsaved changes on this page. Continue?" warning message that appears when trying to
     * navigate away from a page with unsaved edits. It selects "Leave" button. This is a native browser (operating
     * system, not web-based) warning dialogue. Requires: That there actually be a warning box open and active on the
     * browser. Selenium will throw a "NoAlertPresentException" if there is actually no dialogue to interact with.
     */
    @Step("Dismiss the unsaved changes warning dialogue")
    public void dismissUnsavedChangesWarning()
    {
        superDriver.switchTo().alert().accept();
        superDriver.switchTo().defaultContent();
    }
}
