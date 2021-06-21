package com.qaprosoft.zafira.gui.impl;

import com.qaprosoft.zafira.gui.base.SignIn;
import com.qaprosoft.zafira.gui.desktop.component.testresult.LinkIssueWindow;
import com.qaprosoft.zafira.gui.desktop.component.testresult.ResultSessionWindow;
import com.qaprosoft.zafira.gui.desktop.component.testresult.ResultTestMethodCard;
import com.qaprosoft.zafira.gui.desktop.component.testresult.RunResultDetailsBar;
import com.qaprosoft.zafira.gui.desktop.component.testrun.TestRunCard;
import com.qaprosoft.zafira.gui.desktop.component.testrun.TestRunsLauncher;
import com.qaprosoft.zafira.gui.desktop.page.tenant.TestRunResultPage;
import com.qaprosoft.zafira.gui.desktop.page.tenant.TestRunsPage;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

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
        softAssert.assertTrue(testRunsPage.isSearchFieldPresent(),
                "Can't find search field in test runs page");
        softAssert.assertTrue(testRunsPage.isBrowserFilterButtonPresent(),
                "Can't find filter by browser in test runs page");
        softAssert.assertTrue(testRunsPage.isFilterMoreButtonPresent(),
                "Can't find more button filter in test runs page");
        softAssert.assertTrue(testRunsPage.isPlatformFilterButtonPresent(),
                "Can't find filter by platform in test runs page");
        softAssert.assertTrue(testRunsPage.isShowAllSavedFiltersButtonPresent(),
                "Can't find show all saved filters button in test runs page");
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
        softAssert.assertTrue(apiRunCard.isApiTest(),
                "Can't find info about api");
        final String expectedJobName = "launcher";
        softAssert.assertEquals(apiRunCard.getJobName(), expectedJobName,
                "Unexpected job name, expected launcher?");
        final String defaultAgoValue = "Started";
        softAssert.assertTrue(apiRunCard.getHowLongAgoStarted().contains(defaultAgoValue),
                "Test card should have info about when it test started");
        softAssert.assertTrue(apiRunCard.isTestSettingsButtonPresent(),
                "Can't find settings button on result card");
        softAssert.assertTrue(apiRunCard.isCheckBoxActive(),
                "Checkbox of api test card is no active");
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
        softAssert.assertTrue(webRunCard.isWebChromeTest(),
                "Can't find info about chrome browser");
        final String expectedJobName = "launcher";
        softAssert.assertEquals(webRunCard.getJobName(), expectedJobName,
                "Unexpected job name, expected launcher?");
        final String defaultAgoValue = "Started";
        softAssert.assertTrue(webRunCard.getHowLongAgoStarted().contains(defaultAgoValue),
                "Test card should have info about when it test started");
        softAssert.assertTrue(webRunCard.isTestSettingsButtonPresent(),
                "Can't find settings button on card");
        softAssert.assertTrue(webRunCard.isCheckBoxActive(),
                "Checkbox of web test card is not active");
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
        softAssert.assertEquals(resultPageCard.getTitle(), webRunName,
                "Test run name on the result page card differs expected");
        softAssert.assertEquals(resultPageCard.getRunResult(), expectedWebResult,
                "Test run result differ expected");
        softAssert.assertTrue(resultPageCard.isWebChromeTest(),
                "Result page card should has info about chrome browser");
        softAssert.assertEquals(resultPageCard.getJobName(), "launcher",
                "Unexpected job name, expected launcher");
        softAssert.assertTrue(resultPageCard.isCopyTestNameButtonActive(),
                "Copy button won't appeared when hovering test run name");
        final String defaultAgoValue = "Started";
        softAssert.assertTrue(resultPageCard.getHowLongAgoStarted().contains(defaultAgoValue),
                "Result test card should have info about when it test started");
        softAssert.assertTrue(resultPageCard.isTestSettingsButtonPresent(),
                "Can't find settings button on result card");
        softAssert.assertTrue(resultPage.isCollapseAllLabelVisible(),
                "Can't find collapse all label on result page");
        softAssert.assertTrue(resultPage.isExpandAllLabelVisible(),
                "Can't find expand all label on result page");
        softAssert.assertTrue(resultPage.isNumberOfTestsAsExpected(),
                "Number of tests differs with number in pagination");
        softAssert.assertTrue(resultPage.isAllFailedTestsHaveErrorTrace(),
                "Some failed tests has no error trace");
        softAssert.assertTrue(resultPage.isAllPassedTestsHaveNoErrorTrace(),
                "Some passed tests has error trace");

        RunResultDetailsBar resultBar = resultPage.getResultBar();
        softAssert.assertTrue(resultBar.icCheckboxPresent(),
                "Can't find checkbox in result page bar");
        softAssert.assertTrue(resultBar.isPassedButtonPresent(),
                "Can't find passed button in result page bar");
        softAssert.assertTrue(resultBar.isSkippedButtonPresent(),
                "Can't find skipped button in result page bar");
        softAssert.assertTrue(resultBar.isAbortedButtonPresent(),
                "Can't find aborted button in result page bar");
        softAssert.assertTrue(resultBar.isFailedButtonPresent(),
                "Can't find failed button in result page bar");
        softAssert.assertTrue(resultBar.isInProgressButtonPresent(),
                "Can't find in progress button in result page bar");
        softAssert.assertTrue(resultBar.isSearchFieldPresent(),
                "Can't find search field in result page bar");
        softAssert.assertTrue(resultBar.isGroupByPresent(),
                "Can't find Group by list in result page bar");
        softAssert.assertTrue(resultBar.isSortByPresent(),
                "Can't find Sort by list in result page bar");
        softAssert.assertFalse(resultBar.isResetButtonPresent(),
                "Can't find reset button in result page bar");

        final String expectedOwner = "anonymous";
        List<ResultTestMethodCard> methodCards = resultPage.getTestMethods();
        for (ResultTestMethodCard methodCard : methodCards) {
            System.out.println(methodCard.getLabelsText());
            softAssert.assertFalse(methodCard.getLabelsText().isEmpty(),
                    "Web methods cards should have labels" + methodCard.getTitle());
            softAssert.assertTrue(methodCard.isCheckboxPresent(),
                    "Can't find checkbox on method card " + methodCard.getTitle());
            softAssert.assertTrue(methodCard.isDurationPresent(),
                    "Can't find test duration on method card" + methodCard.getTitle());
            softAssert.assertEquals(methodCard.getTestOwner(), expectedOwner,
                    "Unexpected test owner on method card " + methodCard.getTitle());
            softAssert.assertTrue(methodCard.isMarkAsPassedButtonPresent(),
                    "Can't find mark as passed on method card " + methodCard.getTitle());
            LinkIssueWindow issueWindow = resultPage.openLinkIssueWindow(methodCard);
            softAssert.assertTrue(issueWindow.isUIObjectPresent(),
                    "Can't open link issue window for test method " + methodCard.getTitle());
            issueWindow.closeWindow();
            ResultSessionWindow sessionWindow = resultPage.openResultSessionWindow(methodCard);
            softAssert.assertTrue(sessionWindow.isUIObjectPresent(),
                    "Can't open session info window for test method" + methodCard.getTitle());
            sessionWindow.closeWindow();
        }
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
        softAssert.assertEquals(resultPageCard.getTitle(), apiRunName,
                "Test run name on the result page card differs expected");
        softAssert.assertEquals(resultPageCard.getRunResult(), expectedApiResult,
                "Test run result differ expected");
        softAssert.assertTrue(resultPageCard.isApiTest(),
                "Result page card should has info about api");
        softAssert.assertEquals(resultPageCard.getJobName(), "launcher",
                "Unexpected job name, expected launcher");
        softAssert.assertTrue(resultPageCard.isCopyTestNameButtonActive(),
                "Copy button won't appeared when hovering test run name");
        final String defaultAgoValue = "Started";
        softAssert.assertTrue(resultPageCard.getHowLongAgoStarted().contains(defaultAgoValue),
                "Result test card should have info about when it test started");
        softAssert.assertTrue(resultPageCard.isTestSettingsButtonPresent(),
                "Can't find settings button on result card");
        softAssert.assertTrue(resultPage.isCollapseAllLabelVisible(),
                "Can't find collapse all label on result page");
        softAssert.assertTrue(resultPage.isExpandAllLabelVisible(),
                "Can't find expand all label on result page");
        softAssert.assertTrue(resultPage.isNumberOfTestsAsExpected(),
                "Number of tests differs with number in pagination");
        softAssert.assertTrue(resultPage.isAllFailedTestsHaveErrorTrace(),
                "Some failed tests has no error trace");
        softAssert.assertTrue(resultPage.isAllPassedTestsHaveNoErrorTrace(),
                "Some passed tests has error trace");

        RunResultDetailsBar resultBar = resultPage.getResultBar();
        softAssert.assertTrue(resultBar.icCheckboxPresent(),
                "Can't find checkbox in result page bar");
        softAssert.assertTrue(resultBar.isPassedButtonPresent(),
                "Can't find passed button in result page bar");
        softAssert.assertTrue(resultBar.isSkippedButtonPresent(),
                "Can't find skipped button in result page bar");
        softAssert.assertTrue(resultBar.isAbortedButtonPresent(),
                "Can't find aborted button in result page bar");
        softAssert.assertTrue(resultBar.isFailedButtonPresent(),
                "Can't find failed button in result page bar");
        softAssert.assertTrue(resultBar.isInProgressButtonPresent(),
                "Can't find in progress button in result page bar");
        softAssert.assertTrue(resultBar.isSearchFieldPresent(),
                "Can't find search field in result page bar");
        softAssert.assertTrue(resultBar.isGroupByPresent(),
                "Can't find Group by list in result page bar");
        softAssert.assertTrue(resultBar.isSortByPresent(),
                "Can't find Sort by list in result page bar");
        softAssert.assertFalse(resultBar.isResetButtonPresent(),
                "Can't find reset button in result page bar");

        final String expectedOwner = "anonymous";
        List<ResultTestMethodCard> methodCards = resultPage.getTestMethods();
        for (ResultTestMethodCard methodCard : methodCards) {
            softAssert.assertTrue(methodCard.isCheckboxPresent(),
                    "Can't find checkbox on method card " + methodCard.getTitle());
            softAssert.assertTrue(methodCard.isDurationPresent(),
                    "Can't find test duration on method card" + methodCard.getTitle());
            softAssert.assertEquals(methodCard.getTestOwner(), expectedOwner,
                    "Unexpected test owner on method card " + methodCard.getTitle());
            softAssert.assertTrue(methodCard.isMarkAsFailedButtonPresent(),
                    "Can't find mark as failed on method card " + methodCard.getTitle());
        }
        softAssert.assertAll();
    }
}
