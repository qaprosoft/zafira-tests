package com.qaprosoft.zafira.gui.base;

import com.qaprosoft.carina.core.foundation.AbstractTest;
import com.qaprosoft.carina.core.foundation.utils.Configuration;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.constant.WebConstant;
import com.qaprosoft.zafira.gui.desktop.component.common.NavigationMenu;
import com.qaprosoft.zafira.gui.desktop.component.common.ProjectsMenu;
import com.qaprosoft.zafira.gui.desktop.component.common.TenantHeader;
import com.qaprosoft.zafira.gui.desktop.page.accountManagement.LoginPage;
import com.qaprosoft.zafira.gui.desktop.page.tenant.TestRunsPage;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;

import static com.qaprosoft.zafira.constant.ConfigConstant.PROJECT_NAME_KEY;

public class SignIn extends AbstractTest {

    protected static NavigationMenu navigationMenu;

    @BeforeTest
    public void getSignInUrl() {
        StringBuilder urlBuilder = new StringBuilder(Configuration.get(Configuration.Parameter.URL));
        urlBuilder.insert(urlBuilder.indexOf("://") + 3, R.TESTDATA.get(WebConstant.TENANT_NAME) + ".");
        urlBuilder.append("signin");
        String signInUrl = urlBuilder.toString();

        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.openURL(signInUrl);
        Assert.assertFalse(loginPage.isSubmitButtonActive(), "Submit button should be inactive" +
                " because of empty input fields");
        TestRunsPage testRunsPage = loginPage.login(R.TESTDATA.get(WebConstant.USER_LOGIN), R.TESTDATA.get(WebConstant.USER_PASSWORD));
        testRunsPage.assertPageOpened();
        navigationMenu = testRunsPage.getNavigationMenu();
        TenantHeader header = testRunsPage.getHeader();
        ProjectsMenu projectsMenu = header.openProjectsWindow();
        projectsMenu.toProjectByKey("DEF");
        Assert.assertEquals(navigationMenu.getProjectKey(), R.TESTDATA.get(PROJECT_NAME_KEY), "Actual project key differs from defaults");
    }
}
