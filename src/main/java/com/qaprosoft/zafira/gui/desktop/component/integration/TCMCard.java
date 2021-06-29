package com.qaprosoft.zafira.gui.desktop.component.integration;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class TCMCard extends IntegrationCard {
    @FindBy(xpath = ".//label[text()='URL']/preceding-sibling::input")
    private ExtendedWebElement url;

    @FindBy(xpath = "//label[text()='USER']/preceding-sibling::input")
    private ExtendedWebElement user;

    @FindBy(xpath = "//label[text()='CLOSED_STATUS']/preceding-sibling::input")
    private ExtendedWebElement closedStatus;

    @FindBy(xpath = "//label[text()='TOKEN']/preceding-sibling::input")
    private ExtendedWebElement token;

    public TCMCard(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }
}
