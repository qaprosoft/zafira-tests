package com.qaprosoft.zafira.gui.desktop.page.tenant;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class Dashboard extends AbstractPage {

    @FindBy(xpath = "//*[@id=\"pageTitle\"]")
    private ExtendedWebElement dashboardTitle;


    public Dashboard(WebDriver driver) {
        super(driver);
    }

    public String getTitle() {
        return dashboardTitle.getText();
    }
}
