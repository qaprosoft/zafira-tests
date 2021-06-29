package com.qaprosoft.zafira.gui.desktop.page.tenant;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class SSOPage extends AbstractPage {
    @FindBy(id = "pageTitle")
    private ExtendedWebElement pageTitle;

    @FindBy(xpath = "//div[@class='icon-wrapper']/md-icon")
    private ExtendedWebElement ssoMainIcon;

    @FindBy(xpath = "//div[@class='text-wrapper']/h2")
    private ExtendedWebElement introductionTitle;

    @FindBy(xpath = "//button[@aria-label='Go back']")
    private ExtendedWebElement previousPageButton;

    @FindBy(xpath = "//div[@class='text-wrapper']/span")
    private ExtendedWebElement introductionText;

    @FindBy(xpath = "//button[@class='zf-primary-button add-button md-button md-ink-ripple']")
    private ExtendedWebElement addSampleSSOButton;

    @FindBy(xpath = "//h2[@class='right-column__header']")
    private ExtendedWebElement sampleCreationTitle;

    @FindBy(xpath = "//div[@class='right-column__header-description']")
    private ExtendedWebElement sampleCreationDescription;

    @FindBy(xpath = "//i[@role='button']/ancestor::div[@class='profile-photo_content']")
    private ExtendedWebElement addProviderLogo;

    @FindBy(id = "providerName")
    private ExtendedWebElement providerName;

    @FindBy(xpath = "//md-switch[@name='providerVisibility']/div[@class='md-container']")
    private ExtendedWebElement visibilitySwitcher;

    @FindBy(xpath = "//h3[@class='right-column__subheader']")
    private ExtendedWebElement identityProviderSubTitle;

    @FindBy(id = "sso_url")
    private ExtendedWebElement ssoUrlInputField;

    @FindBy(id = "entity-id")
    private ExtendedWebElement entityIdInputField;

    @FindBy(xpath = "//div[text()='Certificate']/following-sibling::div[@class='file-input']")
    private ExtendedWebElement certificateDropFile;

    @FindBy(xpath = "//div[text()='IDP metadata xml']/following-sibling::div[@class='file-input']")
    private ExtendedWebElement idpMetadataDropFile;

    @FindBy(id = "metadata_xml_link")
    private ExtendedWebElement metadataXmlLink;

    @FindBy(xpath = "//span[@class='switch__label']")
    private ExtendedWebElement orgUsersAccessManagementTitle;

    @FindBy(xpath = "//span[@class='switch__label']/following-sibling::md-input-container")
    private ExtendedWebElement orgUsersAccessManagementSwitcher;

    @FindBy(xpath = "//div[@class='right-column__text']")
    private ExtendedWebElement orgUsersAccessManagementText;

    @FindBy(xpath = "//button[contains(text(),'Cancel')]")
    private ExtendedWebElement cancelButton;

    @FindBy(xpath = "//button[contains(text(),'Save')]")
    private ExtendedWebElement saveButton;

    public SSOPage(WebDriver driver) {
        super(driver);
    }
}
