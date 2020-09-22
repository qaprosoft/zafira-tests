package com.qaprosoft.zafira.gui;

import com.qaprosoft.zafira.domain.Route;
import org.openqa.selenium.WebDriver;

public class IntegrationPage extends BasePage {

    public IntegrationPage(WebDriver driver) {
        super(driver, Route.INTEGRATIONS);
    }

}
