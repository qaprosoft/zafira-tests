package com.qaprosoft.zafira.gui.desktop.page.tenant;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractPage;
import com.qaprosoft.zafira.gui.desktop.component.common.HelpMenu;
import com.qaprosoft.zafira.gui.desktop.component.common.TenantHeader;
import com.qaprosoft.zafira.gui.desktop.component.wizard.TestNGConfigurationWindow;
import com.qaprosoft.zafira.gui.desktop.component.wizard.WizardSlider;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class WizardPage extends AbstractPage {
    @FindBy(id = "header")
    private TenantHeader header;

    @FindBy(xpath = "//div[@data-embed='helpCenterForm']")
    private HelpMenu helpMenu;

    @FindBy(xpath = "//div[@class='wizard-slider__pagination swiper-pagination swiper-pagination-clickable swiper-pagination-bullets']")
    private WizardSlider wizardSlider;

    @FindBy(xpath = "//md-tab-item[contains(@class,'md-tab') and text()='TestNG']")
    private TestNGConfigurationWindow testNGConfigurationWindow;

    @FindBy(xpath = "//button[@aria-label='Help']")
    private ExtendedWebElement helpButton;

    @FindBy(xpath = "//img[@class='greetings-slide__image']")
    private ExtendedWebElement zebrunnerImg;

    @FindBy(id = "pageTitle")
    private ExtendedWebElement pageTitle;

    @FindBy(xpath = "//a[@class='wizard-controls__link']")
    private ExtendedWebElement skipWizardButton;

    @FindBy(xpath = "//div[@class='greetings-slide__content-wrapper']")
    private ExtendedWebElement firstPageText;

    @FindBy(xpath = "//button[@aria-label='Next slide']")
    private ExtendedWebElement nextSlideButton;

    @FindBy(xpath = "//button[@aria-label='Previous slide']")
    private ExtendedWebElement previousSlideButton;

    @FindBy(xpath = "//img[@class='core-concepts-slide__image _left']")
    private ExtendedWebElement testRunImg;

    @FindBy(xpath = "//div[@class='core-concepts-slide__text _left']")
    private ExtendedWebElement testRunText;

    @FindBy(xpath = "//img[@class='core-concepts-slide__image _middle']")
    private ExtendedWebElement testsImg;

    @FindBy(xpath = "//div[@class='core-concepts-slide__text _middle']")
    private ExtendedWebElement testsText;

    @FindBy(xpath = "//img[@class='core-concepts-slide__image _right']")
    private ExtendedWebElement testDetailsImg;

    @FindBy(xpath = "//div[@class='core-concepts-slide__text _right']")
    private ExtendedWebElement testDetailsText;

    @FindBy(xpath = "//div[@class='agent-config-slide__header-left']")
    private ExtendedWebElement agentIntroText;

    @FindBy(xpath = "//div[@class='agent-config-slide__header-right']")
    private ExtendedWebElement agentMotorcycleImg;

    @FindBy(xpath = "//div[@class='selenium-config-slide__header']")
    private ExtendedWebElement seleniumConfigTitle;

    @FindBy(xpath = "//input[@name='hubURL']")
    private ExtendedWebElement hubUrl;

    @FindBy(xpath = "//input[@name='hubUsername']")
    private ExtendedWebElement hubUsername;

    @FindBy(xpath = "//input[@name='hubAccessKey']")
    private ExtendedWebElement hubAccessKey;

    @FindBy(xpath = "//a[@class='wizard-selenium-configuration__footer-link']")
    private ExtendedWebElement docLink;

    public WizardPage(WebDriver driver) {
        super(driver);
    }
}