package com.qaprosoft.zafira.gui;

import com.qaprosoft.zafira.gui.desktop.component.TestRunsLauncher;
import com.qaprosoft.zafira.gui.desktop.page.tenant.TestRunsPage;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestRunsPageTest extends SignIn {

    private static final String repository = "carina-demo";
    private static final String apiSuite = "Carina API";
    private static final String webSuite = "Carina WEB";

    @BeforeTest
    public void ClearTestRuns() {
        TestRunsPage testRunsPage = navigationMenu.toTestRunsPage();
        testRunsPage.deleteAllTestRunCards();
    }

    @Test
    public void apiJobRun() {
        String expectedResult = "Passed 4, Failure 0 | 0, Skipped 0";
        TestRunsPage testRunsPage = navigationMenu.toTestRunsPage();
        TestRunsLauncher testRunsLauncher = testRunsPage.openLauncherWindow();
        testRunsLauncher.launchSuiteTests(repository, apiSuite);
        Assert.assertTrue(testRunsPage.isTestLaunched(apiSuite),
                "Suite wasn't launched or launch card didn't appear");
        Assert.assertEquals(testRunsPage.getTestRunResult("API Sample"), expectedResult,
                "Test run result differ expected");
    }

    @Test
    public void webJobRun() {
        String expectedResult = "Passed 0, Failure 3 | 0, Skipped 0";
        TestRunsPage testRunsPage = navigationMenu.toTestRunsPage();
        TestRunsLauncher testRunsLauncher = testRunsPage.openLauncherWindow();
        testRunsLauncher.launchSuiteTests(repository, webSuite);
        Assert.assertTrue(testRunsPage.isTestLaunched(webSuite),
                "Suite wasn't launched or launch card didn't appear");
        Assert.assertEquals(testRunsPage.getTestRunResult("WEB Sample", 60000), expectedResult,
                "Test run result differ expected");
    }
}
