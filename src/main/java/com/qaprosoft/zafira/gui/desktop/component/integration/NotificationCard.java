package com.qaprosoft.zafira.gui.desktop.component.integration;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class NotificationCard extends IntegrationCard {
    @FindBy(xpath = "//label[text()='WEB_HOOK_URL']/preceding-sibling::input")
    private ExtendedWebElement url;

    public NotificationCard(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }
}
