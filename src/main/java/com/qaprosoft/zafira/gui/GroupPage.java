package com.qaprosoft.zafira.gui;

import com.qaprosoft.zafira.domain.Route;
import org.openqa.selenium.WebDriver;

public class GroupPage extends BasePage {

    public GroupPage(WebDriver driver) {
        super(driver, Route.GROUPS);
    }

}
