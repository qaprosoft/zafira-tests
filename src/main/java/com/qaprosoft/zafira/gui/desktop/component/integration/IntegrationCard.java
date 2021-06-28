package com.qaprosoft.zafira.gui.desktop.component.integration;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

//div[@class='card bg-white integration-tool__item background-clear-white']
public abstract class IntegrationCard extends AbstractUIObject {
    @FindBy(xpath = ".//span[@name='toolName']")
    protected ExtendedWebElement toolName;

    @FindBy(xpath = ".//span[@class='integration-tool__item-icon jenkins flex-none']")
    protected ExtendedWebElement toolIcon;

    @FindBy(xpath = ".//md-switch[@name='toolEnabled']")
    protected ExtendedWebElement toolEnableSwitch;

    public IntegrationCard(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }
}
