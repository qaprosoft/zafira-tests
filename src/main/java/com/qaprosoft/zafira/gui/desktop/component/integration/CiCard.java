package com.qaprosoft.zafira.gui.desktop.component.integration;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class CiCard extends IntegrationCard{
    @FindBy(xpath = ".//label[text()='NAME']/preceding-sibling::input")
    private ExtendedWebElement name;

    @FindBy(xpath = ".//label[text()='URL']/preceding-sibling::input")
    private ExtendedWebElement url;

    @FindBy(xpath = ".//label[text()='USER']/preceding-sibling::input")
    private ExtendedWebElement user;

    @FindBy(xpath = ".//label[text()='API_TOKEN_OR_PASSWORD']/preceding-sibling::input")
    private ExtendedWebElement apiToken;

    @FindBy(xpath = ".//label[text()='FOLDER']/preceding-sibling::input")
    private ExtendedWebElement folder;

    @FindBy(xpath = ".//label[text()='JOB_URL_VISIBILITY']/preceding-sibling::input")
    private ExtendedWebElement jobUrlVisibility;

    @FindBy(xpath = ".//button[@name='save']")
    private ExtendedWebElement save;

    @FindBy(xpath = ".//button[contains(text(),'Cancel')]")
    private ExtendedWebElement cancel;

    public CiCard(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }
}
