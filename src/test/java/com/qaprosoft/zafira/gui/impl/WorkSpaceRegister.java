package com.qaprosoft.zafira.gui.impl;

import com.qaprosoft.zafira.gui.base.BaseWorkSpaceRegister;
import com.qaprosoft.zafira.gui.desktop.component.landing.LandingRegistrationComp;
import com.qaprosoft.zafira.gui.desktop.page.accountManagement.LandingPage;
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
