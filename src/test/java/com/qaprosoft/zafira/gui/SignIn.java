package com.qaprosoft.zafira.gui;

import com.qaprosoft.carina.core.foundation.AbstractTest;
import com.qaprosoft.carina.core.foundation.utils.Configuration;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.constant.WebConstant;
import com.qaprosoft.zafira.gui.desktop.component.NavigationMenu;
import com.qaprosoft.zafira.gui.desktop.page.LoginPage;
import com.qaprosoft.zafira.gui.desktop.page.tenant.TestRunsPage;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;

public class SignIn extends AbstractTest {

    private String signInUrl;
    protected NavigationMenu navigationMenu;

    @BeforeTest
    public void getSignInUrl() {
        StringBuilder urlBuilder = new StringBuilder(Configuration.get(Configuration.Parameter.URL));
        urlBuilder.insert(urlBuilder.indexOf("://") + 3, R.TESTDATA.get(WebConstant.TENANT_NAME) + ".");
        urlBuilder.append("signin");
        signInUrl = urlBuilder.toString();

        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.openURL(signInUrl);
        Assert.assertTrue(!loginPage.isSubmitButtonActive(), "Submit button should be inactive" +
                " because of empty input fields");
        TestRunsPage testRunsPage = loginPage.login(R.TESTDATA.get(WebConstant.USER_LOGIN), R.TESTDATA.get(WebConstant.USER_PASSWORD));
        testRunsPage.assertPageOpened();
        navigationMenu = testRunsPage.getNavigationMenu();
    }

}
