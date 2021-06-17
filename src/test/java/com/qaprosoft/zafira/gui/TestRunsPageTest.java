package com.qaprosoft.zafira.gui;

import com.qaprosoft.zafira.gui.desktop.component.RunResultDetailsBar;
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
    private static final String expectedWebResult = "Passed 0, Failure 3 | 0, Skipped 0";
    private static final String expectedApiResult = "Passed 4, Failure 0 | 0, Skipped 0";

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
        softAssert.assertEquals(apiRunCard.getRunResult(), expectedApiResult,
                "Test run result differ expected");
        softAssert.assertTrue(apiRunCard.isApiTest(), "Can't find info about api");
        final String expectedJobName = "launcher";
        softAssert.assertEquals(apiRunCard.getJobName(), expectedJobName, "message?");
        final String defaultAgoValue = "Started";
        softAssert.assertTrue(apiRunCard.getHowLongAgoStarted().contains(defaultAgoValue));
        softAssert.assertTrue(apiRunCard.isTestSettingsButtonPresent());
        softAssert.assertTrue(apiRunCard.isCheckBoxActive());
        softAssert.assertTrue(apiRunCard.isCopyTestNameButtonActive(),
                "Copy button won't appeared when hovering test run name");
        softAssert.assertEquals(testRunsPage.getNumberOfTestsOnThePage(), testRunsPage.getNumberOfTestRunCards(),
                "Number of test cards differ to pagination info");
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
        softAssert.assertEquals(webRunCard.getRunResult(), expectedWebResult,
                "Test run result differ expected");
        softAssert.assertTrue(webRunCard.isWebChromeTest(), "Can't find info about browser and version");
        final String expectedJobName = "launcher";
        softAssert.assertEquals(webRunCard.getJobName(), expectedJobName);
        final String defaultAgoValue = "Started";
        softAssert.assertTrue(webRunCard.getHowLongAgoStarted().contains(defaultAgoValue));
        softAssert.assertTrue(webRunCard.isTestSettingsButtonPresent());
        softAssert.assertTrue(webRunCard.isCheckBoxActive());
        softAssert.assertTrue(webRunCard.isCopyTestNameButtonActive(),
                "Copy button won't appeared when hovering test run name");
        softAssert.assertEquals(testRunsPage.getNumberOfTestsOnThePage(), testRunsPage.getNumberOfTestRunCards(),
                "Number of test cards differ to pagination info");
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

        TestRunCard resultPageCard = resultPage.getPageCard();
        softAssert.assertEquals(resultPageCard.getTitle(), webRunName);
        softAssert.assertEquals(resultPageCard.getRunResult(), expectedWebResult,
                "Test run result differ expected");
        softAssert.assertTrue(resultPageCard.isWebChromeTest());
        softAssert.assertEquals(resultPageCard.getJobName(), "launcher");
        softAssert.assertTrue(resultPageCard.isCopyTestNameButtonActive(),
                "Copy button won't appeared when hovering test run name");
        final String defaultAgoValue = "Started";
        softAssert.assertTrue(resultPageCard.getHowLongAgoStarted().contains(defaultAgoValue));
        softAssert.assertTrue(resultPageCard.isTestSettingsButtonPresent());
        softAssert.assertTrue(resultPage.isCollapseAllLabelVisible());
        softAssert.assertTrue(resultPage.isExpandAllLabelVisible());

        RunResultDetailsBar resultBar = resultPage.getResultBar();
        softAssert.assertTrue(resultBar.icCheckboxPresent());
        softAssert.assertTrue(resultBar.isPassedButtonPresent());
        softAssert.assertTrue(resultBar.isSkippedButtonPresent());
        softAssert.assertTrue(resultBar.isAbortedButtonPresent());
        softAssert.assertTrue(resultBar.isFailedButtonPresent());
        softAssert.assertTrue(resultBar.isInProgressButtonPresent());
        softAssert.assertTrue(resultBar.isSearchFieldPresent());
        softAssert.assertTrue(resultBar.isGroupByPresent());
        softAssert.assertTrue(resultBar.isSortByPresent());
        softAssert.assertFalse(resultBar.isResetButtonPresent());
        softAssert.assertAll();
    }

    @Test(dependsOnMethods = "apiJobRun")
    public void apiRunTestVerificationOfResultPage() {
        TestRunsPage testRunsPage = navigationMenu.toTestRunsPage();
        testRunsPage.assertPageOpened();
        TestRunResultPage resultPage = testRunsPage.toTestRunResultPage(apiRunName);
        resultPage.assertPageOpened();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(resultPage.getPageTitle(), "Test results",
                "Expected page title didn't match the actual");
        softAssert.assertTrue(resultPage.isBackButtonActive(), "Back button is not visible or clickable");

        TestRunCard resultPageCard = resultPage.getPageCard();
        softAssert.assertEquals(resultPageCard.getTitle(), apiRunName);
        softAssert.assertEquals(resultPageCard.getRunResult(), expectedApiResult,
                "Test run result differ expected");
        softAssert.assertTrue(resultPageCard.isApiTest());
        softAssert.assertEquals(resultPageCard.getJobName(), "launcher");
        softAssert.assertTrue(resultPageCard.isCopyTestNameButtonActive(),
                "Copy button won't appeared when hovering test run name");
        final String defaultAgoValue = "Started";
        softAssert.assertTrue(resultPageCard.getHowLongAgoStarted().contains(defaultAgoValue));
        softAssert.assertTrue(resultPageCard.isTestSettingsButtonPresent());
        softAssert.assertTrue(resultPage.isCollapseAllLabelVisible());
        softAssert.assertTrue(resultPage.isExpandAllLabelVisible());

        RunResultDetailsBar resultBar = resultPage.getResultBar();
        softAssert.assertTrue(resultBar.icCheckboxPresent());
        softAssert.assertTrue(resultBar.isPassedButtonPresent());
        softAssert.assertTrue(resultBar.isSkippedButtonPresent());
        softAssert.assertTrue(resultBar.isAbortedButtonPresent());
        softAssert.assertTrue(resultBar.isFailedButtonPresent());
        softAssert.assertTrue(resultBar.isInProgressButtonPresent());
        softAssert.assertTrue(resultBar.isSearchFieldPresent());
        softAssert.assertTrue(resultBar.isGroupByPresent());
        softAssert.assertTrue(resultBar.isSortByPresent());
        softAssert.assertFalse(resultBar.isResetButtonPresent());
        softAssert.assertAll();
    }
}
