package com.qaprosoft.zafira.gui.desktop.page.tenant;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ParticularIntegrationPage extends AbstractPage {
    //0 el = projects general page, 1 el = current project page, 2 el = general integrations page
    @FindBy(xpath = "//a[@class='breadcrumb _link ng-binding ng-scope']")
    private List<ExtendedWebElement> projectRef;

    @FindBy(id = "pageTitle")
    private ExtendedWebElement pageTitle;

    @FindBy(xpath = "//p[@class='integration-config-intro__description-paragraph ng-binding ng-scope']")
    private ExtendedWebElement description;

    @FindBy(xpath = "//img[contains(@ng-src,'integrations_')]")
    private List<ExtendedWebElement> images;

    @FindBy(xpath = "//md-icon[contains(@aria-label,'Next slide')]")
    private ExtendedWebElement nextImage;

    @FindBy(xpath = "//md-icon[contains(@aria-label,'Previous slide')]")
    private ExtendedWebElement previousSlide;

    @FindBy(xpath = "//button[contains(@aria-label,'Enable/disable integration')]")
    private ExtendedWebElement enableIntegrationButton;

    @FindBy(id = "integrationHubUrl")
    private ExtendedWebElement integrationHubUrl;

    @FindBy(id = "integrationUsername")
    private ExtendedWebElement integrationUsername;

    @FindBy(id = "integrationAccessKey")
    private ExtendedWebElement integrationAccessKey;

    @FindBy(xpath = "//span[contains(@class,'md-button__text ng-scope') and text()='Test']/ancestor::button")
    private ExtendedWebElement testButton;

    @FindBy(xpath = "//span[contains(@class,'md-button__text ng-scope') and text()='Regenerate Key']/ancestor::button")
    private ExtendedWebElement regenerateKeyButton;

    public ParticularIntegrationPage(WebDriver driver) {
        super(driver);
    }
}
