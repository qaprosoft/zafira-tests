package com.qaprosoft.zafira.gui.desktop.component.wizard;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

//md-tab-item[contains(@class,'md-tab') and text()='TestNG']
public class TestNGConfigurationWindow extends AbstractUIObject {
    @FindBy(xpath = "//div[contains(text(),'gradle')]/ancestor::md-radio-button")
    private ExtendedWebElement gradleRadioButton;

    @FindBy(xpath = "//div[contains(text(),'maven')]/ancestor::md-radio-button")
    private ExtendedWebElement mavenRadioButton;

    @FindBy(xpath = "//h2[contains(@class,'wizard-testng-configuration__section-title')]")
    private ExtendedWebElement firstStepTitle;

    @FindBy(xpath = "//h2[contains(@class,'wizard-testng-configuration__section-subtitle')]")
    private ExtendedWebElement secondStepTitle;

    public TestNGConfigurationWindow(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }
}
