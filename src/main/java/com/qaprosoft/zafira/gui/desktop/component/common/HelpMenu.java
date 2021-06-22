package com.qaprosoft.zafira.gui.desktop.component.common;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

//to open object, need to add into page and click //button[@aria-label='Help'] button

//div[@data-embed='helpCenterForm']
public class HelpMenu extends AbstractUIObject {
    @FindBy(xpath = ".//h1[@data-testid='widget-title']")
    private ExtendedWebElement title;

    @FindBy(xpath = ".//button[@aria-label='Minimise widget']")
    private ExtendedWebElement minimizeWidget;

    @FindBy(xpath = "//div[@data-garden-id='forms.field']")
    private ExtendedWebElement inputField;

    @FindBy(xpath = "//button[@aria-label='Clear search']")
    private ExtendedWebElement clearInputFieldButton;

    @FindBy(xpath = "//button[@data-garden-id='buttons.button']")
    private ExtendedWebElement submitNewSuggestionButton;

    @FindBy(xpath = "//div//li//a")
    private List<ExtendedWebElement> suggestionsList;

    public HelpMenu(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }
}
