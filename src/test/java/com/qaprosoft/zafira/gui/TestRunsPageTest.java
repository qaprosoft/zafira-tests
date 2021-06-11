package com.qaprosoft.zafira.gui;

import com.qaprosoft.zafira.gui.desktop.component.TestRunsLauncher;
import com.qaprosoft.zafira.gui.desktop.page.tenant.TestRunResultPage;
import com.qaprosoft.zafira.gui.desktop.page.tenant.TestRunsPage;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class TestRunsPageTest extends SignIn {

    private static final String repository = "carina-demo";
    private static final String apiSuite = "Carina API";
    private static final String webSuite = "Carina WEB";
    private static final String apiRunName = "Carina Demo Tests - API Sample";
    private static final String webRunName = "Carina Demo Tests - Web Sample";

    @BeforeTest
    public void ClearTestRuns() {
        TestRunsPage testRunsPage = navigationMenu.toTestRunsPage();
        testRunsPage.assertPageOpened();
        testRunsPage.deleteAllTestRunCards();
    }

    @Test
    public void apiJobRun() {
        TestRunsPage testRunsPage = navigationMenu.toTestRunsPage();
        TestRunsLauncher testRunsLauncher = testRunsPage.openLauncherWindow();
        testRunsLauncher.assertUIObjectPresent();
        testRunsLauncher.launchSuiteTests(repository, apiSuite);
        testRunsPage.assertPageOpened();
        Assert.assertTrue(testRunsPage.isTestLaunched(apiSuite),
                "Suite wasn't launched or launch card didn't appear");

        final String expectedResult = "Passed 4, Failure 0 | 0, Skipped 0";
        Assert.assertEquals(testRunsPage.getTestRunResult(apiRunName, 90), expectedResult,
                "Test run result differ expected");
    }

    @Test
    public void webJobRun() {
        TestRunsPage testRunsPage = navigationMenu.toTestRunsPage();
        TestRunsLauncher testRunsLauncher = testRunsPage.openLauncherWindow();
        testRunsLauncher.assertUIObjectPresent();
        testRunsLauncher.launchSuiteTests(repository, webSuite);
        testRunsPage.assertPageOpened();
        Assert.assertTrue(testRunsPage.isTestLaunched(webSuite),
                "Suite wasn't launched or launch card didn't appear");

        final String expectedResult = "Passed 0, Failure 3 | 0, Skipped 0";
        Assert.assertEquals(testRunsPage.getTestRunResult(webRunName, 100), expectedResult,
                "Test run result differ expected");
    }

    @Test(dependsOnMethods = "webJobRun")
    public void webTestRunPage() {
        TestRunsPage testRunsPage = navigationMenu.toTestRunsPage();
        testRunsPage.assertPageOpened();
        TestRunResultPage resultPage = testRunsPage.toTestRunResultPage(webRunName);
        resultPage.assertPageOpened();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(resultPage.getPageTitle(), "Test results",
                "Expected page title didn't match the actual");
        softAssert.assertTrue(resultPage.isBackButtonActive(), "Back button is not visible or clickable");
        softAssert.assertEquals(resultPage.getTestRunName(), webRunName);
        softAssert.assertEquals(resultPage.getTestRunJob(), "launcher");
        softAssert.assertTrue(resultPage.isCopyTestNameButtonActive(),
                "Copy button won't appeared when hovering test run name");
        softAssert.assertAll();
    }
}
