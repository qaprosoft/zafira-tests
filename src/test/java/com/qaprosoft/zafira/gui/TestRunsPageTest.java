package com.qaprosoft.zafira.gui;

import com.qaprosoft.zafira.gui.desktop.component.TestRunCard;
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
    public void checkElementOnTestRunsPage() {
        TestRunsPage testRunsPage = navigationMenu.toTestRunsPage();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(testRunsPage.isSearchFieldPresent());
        softAssert.assertTrue(testRunsPage.isBrowserFilterButtonPresent());
        softAssert.assertTrue(testRunsPage.isFilterMoreButtonPresent());
        softAssert.assertTrue(testRunsPage.isPlatformFilterButtonPresent());
        softAssert.assertTrue(testRunsPage.isShowAllSavedFiltersButtonPresent());
        softAssert.assertAll();
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

        TestRunCard apiRunCard = testRunsPage.getTestRunCard(apiRunName, 90);
        SoftAssert softAssert = new SoftAssert();
        final String expectedResult = "Passed 4, Failure 0 | 0, Skipped 0";
        softAssert.assertEquals(apiRunCard.getRunResult(), expectedResult,
                "Test run result differ expected");
        softAssert.assertTrue(apiRunCard.isApiTest(), "Can't find info about api");
        final String expectedJobName = "launcher";
        softAssert.assertEquals(apiRunCard.getJobName(), expectedJobName, "message?");
        final String defaultAgoValue = "Started";
        softAssert.assertTrue(apiRunCard.getHowLongAgoStarted().contains(defaultAgoValue));
        softAssert.assertTrue(apiRunCard.isTestSettingsButtonPresent());
        softAssert.assertTrue(apiRunCard.isCheckBoxActive());
        softAssert.assertAll();
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

        TestRunCard webRunCard = testRunsPage.getTestRunCard(webRunName, 100);
        SoftAssert softAssert = new SoftAssert();
        final String expectedResult = "Passed 0, Failure 3 | 0, Skipped 0";
        softAssert.assertEquals(webRunCard.getRunResult(), expectedResult,
                "Test run result differ expected");
        softAssert.assertTrue(webRunCard.isWebChromeTest(), "Can't find info about browser and version");
        final String expectedJobName = "launcher";
        softAssert.assertEquals(webRunCard.getJobName(), expectedJobName);
        final String defaultAgoValue = "Started";
        softAssert.assertTrue(webRunCard.getHowLongAgoStarted().contains(defaultAgoValue));
        softAssert.assertTrue(webRunCard.isTestSettingsButtonPresent());
        softAssert.assertTrue(webRunCard.isCheckBoxActive());
        softAssert.assertAll();
    }

    @Test(dependsOnMethods = "webJobRun")
    public void webRunTestVerificationOfResultPage() {
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
