package com.qaprosoft.zafira.gui.captcha;

import com.qaprosoft.zafira.gui.desktop.component.LandingRegistrationComp;
import com.qaprosoft.zafira.gui.desktop.page.LandingPage;
import org.junit.Assert;
import org.testng.annotations.Test;

public class WorkSpaceRegister extends BaseWorkSpaceRegister {

    @Test
    public void createNewFreeWorkspace() {
        LandingPage landingPage = new LandingPage(getDriver());
        landingPage.open();
        landingPage.closeCookiesNotification();
        LandingRegistrationComp registrationComp = landingPage.getRegistrationComponent();
        Assert.assertTrue("Registration component isn't present", registrationComp.isUIObjectPresent());
        registrationComp.createFreeWorkspace(orgName, ownerName, tenantEmail);
        // landingPage.clickOnCaptcha();
    }
}
