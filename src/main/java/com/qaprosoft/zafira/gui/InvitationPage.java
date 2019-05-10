package com.qaprosoft.zafira.gui;

import com.qaprosoft.zafira.domain.Route;
import org.openqa.selenium.WebDriver;

public class InvitationPage extends BasePage {

    public InvitationPage(WebDriver driver) {
        super(driver, Route.INVITATIONS);
    }

}
