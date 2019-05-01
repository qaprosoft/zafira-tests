package com.qaprosoft.zafira.gui;

import com.qaprosoft.zafira.domain.Route;
import org.openqa.selenium.WebDriver;

public class MonitorPage extends BasePage {

    public MonitorPage(WebDriver driver) {
        super(driver, Route.MONITORS);
    }

}
