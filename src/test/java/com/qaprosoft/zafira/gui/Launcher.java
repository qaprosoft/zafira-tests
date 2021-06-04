package com.qaprosoft.zafira.gui;

import com.qaprosoft.zafira.gui.desktop.page.tenant.TestRunsPage;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Launcher extends SignIn {

    private static final String repository = "carina-demo";
    private static final String apiSuite = "Carina API";
    private static final String webSuite = "Carina WEB";

    @BeforeTest
    public void ClearTestRuns(){
        TestRunsPage testRunsPage = navigationMenu.toTestRunsPage();
        testRunsPage.deleteAllTestRunCards();
    }

    @Test
    public void ApiJobRun() {
        TestRunsPage testRunsPage = navigationMenu.toTestRunsPage();
        com.qaprosoft.zafira.gui.desktop.component.Launcher launcher = testRunsPage.openLauncherWindow();
        launcher.launchSuiteTests(repository, apiSuite);
        Assert.assertTrue(testRunsPage.isTestLaunched(apiSuite),
                "Suite wasn't launched or launch card didn't appear");
    }

    @Test
    public void WebJobRun() {
        TestRunsPage testRunsPage = navigationMenu.toTestRunsPage();
        com.qaprosoft.zafira.gui.desktop.component.Launcher launcher = testRunsPage.openLauncherWindow();
        launcher.launchSuiteTests(repository, webSuite);
        Assert.assertTrue(testRunsPage.isTestLaunched(webSuite),
                "Suite wasn't launched or launch card didn't appear");
    }
}
