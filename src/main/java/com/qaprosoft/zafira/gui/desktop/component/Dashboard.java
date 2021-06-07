package com.qaprosoft.zafira.gui.desktop.component;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class Dashboard extends AbstractUIObject {

    @FindBy(xpath = "//input[@name='title']")
    private ExtendedWebElement dashboardNameInput;

    @FindBy(xpath = "//button[@type='submit']")
    private ExtendedWebElement submitButton;

    public Dashboard(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public void createDashboard(String dashboardName) {
        dashboardNameInput.type(dashboardName);
        submitButton.click();
    }

    public boolean isSubmitButtonActive(){
        return submitButton.isClickable(5);
    }
}
