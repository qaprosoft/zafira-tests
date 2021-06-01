package com.qaprosoft.zafira.gui;

import com.qaprosoft.carina.core.foundation.AbstractTest;
import com.qaprosoft.zafira.gui.desktop.component.LandingRegistrationComp;
import com.qaprosoft.zafira.gui.desktop.page.LandingPage;
import org.testng.annotations.Test;

public class WorkSpaceRegister extends AbstractTest {

    @Test
    public void createNewFreeWorkspace(){
        LandingPage landingPage = new LandingPage(getDriver());
        landingPage.open();
        LandingRegistrationComp registrationComp = landingPage.getRegistrationComponent();
        registrationComp.createFreeWorkspace("erat", "testUser","test.zebrunner@gmail.com" );
    }
}
