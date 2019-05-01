package com.qaprosoft.zafira.gui;

import com.qaprosoft.zafira.domain.Route;
import org.openqa.selenium.WebDriver;

public class UserPage extends BasePage {

    public UserPage(WebDriver driver) {
        super(driver, Route.USERS);
    }

}
