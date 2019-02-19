package org.phenotips.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Corresponds to the "Request An Account" page that the public can see to request to sign up.
 * i.e. http://localhost:8083/register/PhenomeCentral/WebHome
 */
public class UserSignUpPage extends BasePage implements CommonSignUpSelectors
{
    private final By registerBtn = By.cssSelector("input[type=submit][value=Register]");
    private final By cancelAndReturnBtn = By.cssSelector("#register a.button.secondary");
    private final By infoMessageArea = By.cssSelector("div.infomessage");

    public UserSignUpPage(WebDriver aDriver)
    {
        super(aDriver);
    }

    /**
     * Requests an account through the sign up page. Only fills out the fields and submits the request,
     * does not approve it.
     * @param firstName First Name as a String.
     * @param lastName Last name as a String.
     * @param password is password as a String.
     * @param email is email for the user as a String. Should be either a dummy address or something that we can access.
     * @param affiliation is the value for the Affiliation box as a String.
     * @param referral value for the "How did you hear about/  Who referred you" box as a String.
     * @param justification value for the "Why are you requesting access" box as a String.
     * @return Stay on the same page so return the same object.
     */
    public UserSignUpPage requestAccount(String firstName, String lastName, String password, String confirmPassword,
        String email, String affiliation, String referral, String justification)
    {
        clickAndClearElement(userNameBox);
        unconditionalWaitNs(1); // Needed as entering a firstname immediately does not regen username.

        clickAndTypeOnElement(firstNameBox, firstName);
        clickAndTypeOnElement(lastNameBox, lastName);
        clickOnElement(userNameBox);
        clickAndTypeOnElement(passwordBox, password);
        clickAndTypeOnElement(confirmPasswordBox, confirmPassword);
        clickAndTypeOnElement(emailBox, email);
        clickAndTypeOnElement(affiliationBox, affiliation);
        clickAndTypeOnElement(referralBox, referral);
        clickAndTypeOnElement(reasoningBox, justification);

        toggleCheckboxToChecked(professionalCheckbox);
        toggleCheckboxToChecked(liabilityCheckbox);
        toggleCheckboxToChecked(nonIdentificationCheckbox);
        toggleCheckboxToChecked(cooperationCheckbox);
        toggleCheckboxToChecked(acknoledgementCheckbox);

        clickOnElement(registerBtn);
        return this;
    }

    /**
     * Retrieves the confirmation message that is presented to the user when submitting sign up information.
     * As of writing this code, it says "Thank you for your interest in PhenomeCentral. We took note of your
     * request and we will process it shortly."
     * @return A String representing the message.
     */
    public String getConfirmationMessage()
    {
        waitForElementToBePresent(infoMessageArea);
        return superDriver.findElement(infoMessageArea).getText();
    }

    /**
     * Cancel the Request an Account page. Navigates back to the home page by clicking on the cancel button
     * @return Navigate back to the home page so return a new instance of HomePage object.
     */
    public HomePage cancelRequestingAccount()
    {
        clickOnElement(cancelAndReturnBtn);
        return new HomePage(superDriver);
    }
}
