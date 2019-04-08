/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/
 */
package org.phenotips.endtoendtests.testcases;

import org.phenotips.endtoendtests.common.BaseSuite;
import org.phenotips.endtoendtests.pageobjects.HomePage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;

/**
 * An abstract test. All tests should inherit this class. We should put any high level methods using @after- annotations
 * here We can also put any high level @BeforeSuite methods here too to setup/check main conditions.
 */
public abstract class BaseTest extends BaseSuite
{
    /**
     * Now uses default constructor. Superclass instantiates the WebDriver.
     */
    public BaseTest() { }

    /**
     * Runs after every test method. In the case that TestNG's listener reports a failure, call methods to take a
     * screenshot and to cleanup the state of the browser.
     *
     * @param testResult resulting status of a test method that has just run, as reported by TestNGs listener. Check
     *            this passed info for failure.
     */
    @AfterMethod
    public void onTestEnd(ITestResult testResult)
    {
        String testMethod = testResult.getMethod().getMethodName();

        if (ITestResult.FAILURE == testResult.getStatus()) {
            System.out.println("Test:" + testMethod + " has failed. Taking a screenshot and cleaning up...");
            captureScreenshot(testMethod, DRIVER.getCurrentUrl());
            cleanupBrowserState();
        } else if (ITestResult.SKIP == testResult.getStatus()) {
            System.out.println("Test:" + testMethod +
                " was skipped, possibly due to unfinished dependent tests, system error, or unable to reach Selenium. No screenshot. Moving on.");
        } else {
            System.out.println("Test:" + testMethod + " has succeeded. No screenshot. Moving on.");
        }
    }

    /**
     * Runs after each class finishes. Now no longer really being used, except for a debug message.
     */
    @AfterClass
    public void testCleanup()
    {
        System.out.println("A single class has finished");
    }

    /**
     * Captures a screenshot of the browser viewport as a PNG to the target/screenshot folder. This is called by the
     * AfterMethod in the case of a test failure.
     *
     * @param testMethod The method name of the test that failed, as a String. Will be used to name the file.
     * @param URL the URL that the browser was at during a test failure. Used by Allure's listener in the Step
     *            annotation
     */
    @Step("Taking screenshot of {0} for URL {1}")
    private void captureScreenshot(String testMethod, String URL)
    {
        // Screenshot mechanism
        // Cast webDriver over to TakeScreenshot. Call getScreenshotAs method to create image file
        File srcFile = ((TakesScreenshot) DRIVER).getScreenshotAs(OutputType.FILE);

        LocalDateTime dateTime = ZonedDateTime.now().toLocalDateTime();

        String scrnFileName = "target/screenshots/" + testMethod + "_" + dateTime + ".png";

        // Save screenshot in target/screenshots folder with the methodName of failed test and timestamp.
        File destFile = new File(scrnFileName);

        System.out.println("Taking screenshot of failed test...");

        // Copy over to target/screenshots folder
        try {
            FileUtils.copyFile(srcFile, destFile);
        } catch (IOException e) {
            System.out.println("Something went wrong copying over screenshot to target/screenshots: " + e);
        }

        // Add screenshot to Allure Report
        Path content = Paths.get(scrnFileName);
        try (InputStream is = Files.newInputStream(content)) {
            Allure.addAttachment("Page Screenshot: " + testMethod, is);
        } catch (IOException e) {
            System.out.println("Something went wrong giving screenshot to Allure: " + e);
        }
    }

    /**
     * Cleans up the state of the browser after a test failure. This takes care of any warning modal that pops up for
     * unsaved edits before navigating back to the login page so that the next tests can continue. Called by the
     * AfterMethod in case of test failure.
     */
    @Step("Test failed. Cleaning up browser state.")
    private void cleanupBrowserState()
    {
        HomePage tempHomePage = new HomePage();

        try {
            tempHomePage.navigateToLoginPage();
            System.out.println("Test failure, navigate to login page. There is no unsaved changes warning.");
        } catch (UnhandledAlertException e) {
            DRIVER.switchTo().alert().accept();
            DRIVER.switchTo().defaultContent();
            tempHomePage.navigateToLoginPage();
            System.out.println("Test failure, navigate to login page. Closed an unsaved changes warning dialogue");
        }
    }
}
