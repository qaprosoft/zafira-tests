package com.qaprosoft.zafira.gui;

import com.qaprosoft.zafira.gui.desktop.component.Launcher;
import com.qaprosoft.zafira.gui.desktop.page.tenant.TestRunsPage;
import org.testng.annotations.Test;

public class LauncherTest extends SignInTest {

    @Test
    public void apiJobRun() {
        TestRunsPage testRunsPage = navigationMenu.toTestRunsPage();
        Launcher launcher = testRunsPage.openLauncherWindow();
        launcher.launchSuiteTests("carina-demo","Carina API");
    }
}
