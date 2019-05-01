package com.qaprosoft.zafira.gui;

import com.qaprosoft.zafira.domain.Route;
import org.openqa.selenium.WebDriver;

public class TestRunPage extends BasePage {

    public TestRunPage(WebDriver driver) {
        super(driver, Route.TEST_RUNS);
    }

}
