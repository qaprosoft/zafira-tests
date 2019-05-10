package com.qaprosoft.zafira.gui.component.table;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class Pagination extends AbstractUIObject {

    @FindBy(className = "label")
    private ExtendedWebElement countLabel;

    @FindBy(xpath = ".//button[.//*[contains(@md-svg-icon, 'first')]]")
    private ExtendedWebElement navigateFirstButton;

    @FindBy(xpath = ".//button[.//*[contains(@md-svg-icon, 'before')]]")
    private ExtendedWebElement navigateBeforeButton;

    @FindBy(xpath = ".//button[.//*[contains(@md-svg-icon, 'next')]]")
    private ExtendedWebElement navigateNextButton;

    @FindBy(xpath = ".//button[.//*[contains(@md-svg-icon, 'last')]]")
    private ExtendedWebElement navigateLastButton;

    public Pagination(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public ExtendedWebElement getCountLabel() {
        return countLabel;
    }

    public String getCountLabelText() {
        return countLabel.getText();
    }

    public ExtendedWebElement getNavigateFirstButton() {
        return navigateFirstButton;
    }

    public void clickNavigateFirstButton() {
        navigateFirstButton.click();
    }

    public ExtendedWebElement getNavigateBeforeButton() {
        return navigateBeforeButton;
    }

    public void clickNavigateBeforeButton() {
        navigateBeforeButton.click();
    }

    public ExtendedWebElement getNavigateNextButton() {
        return navigateNextButton;
    }

    public void clickNavigateNextButton() {
        navigateNextButton.click();
    }

    public ExtendedWebElement getNavigateLastButton() {
        return navigateLastButton;
    }

    public void clickNavigateLastButton() {
        navigateLastButton.click();
    }

}
