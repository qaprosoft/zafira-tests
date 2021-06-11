package com.qaprosoft.zafira.gui.desktop.component;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public abstract class TestRunCardBase extends AbstractUIObject {
    @FindBy(xpath = "//span[@class='test-run-card__title-text ng-binding']")
    private ExtendedWebElement title;

    @FindBy(xpath = "//div[contains(@class,'test-run-card__cell _selection')]")
    private ExtendedWebElement checkBox;

    public TestRunCardBase(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public String getTitle(){
        waitUntil(ExpectedConditions.presenceOfElementLocated(title.getBy()), 5000);
        return title.getText();
    }

    public boolean clickCheckBox(){
        if (checkBox.isClickable()) {
            checkBox.click();
            return true;
        }
        return false;
    }

    public boolean isCheckBoxActive(){
        return checkBox.isClickable() && checkBox.isVisible();
    }
}
