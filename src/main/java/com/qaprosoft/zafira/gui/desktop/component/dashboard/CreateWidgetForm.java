package com.qaprosoft.zafira.gui.desktop.component.dashboard;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

//form[@name='form-validation styled-modal__form ng-pristine ng-valid']
public class CreateWidgetForm extends AbstractUIObject {
    @FindBy(xpath = "//div[contains(@class,'selection-blocks_container_elements_item zf-radio-button ng-scope')]")
    private List<WidgetCard> widgetCards;

    @FindBy(id = "modalTitle")
    private ExtendedWebElement modalTitle;

    @FindBy(xpath = ".//md-icon[@aria-label='Close dialog']")
    private ExtendedWebElement closeForm;

    @FindBy(xpath = ".//*[contains(text(),'collections')]/ancestor::button")
    private ExtendedWebElement templatesButton;

    @FindBy(xpath = ".//md-icon[contains(@md-svg-src,'widgets_icon.1cebe89d.svg')]/ancestor::button")
    private ExtendedWebElement existingWidgetsButton;

    @FindBy(xpath = ".//button[contains(text(),'Next')]")
    private ExtendedWebElement nextButton;

    @FindBy(xpath = ".//input[contains(@type,'search')]")
    private ExtendedWebElement searchField;

    public CreateWidgetForm(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }
}
