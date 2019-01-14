package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class adminSettingsPage extends basePage {
    By matchingNotificationMenu = By.id("vertical-menu-Matching Notification");

    public adminSettingsPage(WebDriver aDriver) {
        super(aDriver);
    }

    public adminMatchNotificationPage navigateToMatchingNotificationPage() {
        clickOnElement(matchingNotificationMenu);
        return new adminMatchNotificationPage(superDriver);
    }

}
