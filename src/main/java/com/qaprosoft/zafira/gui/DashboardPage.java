package com.qaprosoft.zafira.gui;

import com.qaprosoft.zafira.domain.Route;
import org.openqa.selenium.WebDriver;

public class DashboardPage extends BasePage {

    public DashboardPage(WebDriver driver, long id) {
        super(driver, Route.DASHBOARDS, id);
    }

}
