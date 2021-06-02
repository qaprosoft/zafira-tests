package com.qaprosoft.zafira.gui;

import com.qaprosoft.zafira.gui.desktop.component.LandingRegistrationComp;
import com.qaprosoft.zafira.gui.desktop.page.LandingPage;
import org.testng.annotations.Test;

public class WorkSpaceRegister extends BaseWorkSpaceRegister {

    @Test
    public void createNewFreeWorkspace(){
        LandingPage landingPage = new LandingPage(getDriver());
        landingPage.open();
        landingPage.closeCookiesNotification();
        LandingRegistrationComp registrationComp = landingPage.getRegistrationComponent();
        registrationComp.createFreeWorkspace(orgName, ownerName,tenantEmail );
        landingPage.clickOnCaptcha();
        int i = 22+2;
    }
}
