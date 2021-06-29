package com.qaprosoft.zafira.gui.desktop.component.dashboard;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

//div[@class='md-select-menu-container md-active md-clickable']
public class ParamsSelectMenu extends AbstractUIObject {
    @FindBy(xpath = ".//md-option//div[@class='md-container']")
    private List<ExtendedWebElement> checkBoxes;

    @FindBy(xpath = ".//md-option//div[@class='md-text ng-binding']")
    private List<ExtendedWebElement> optionName;

    public ParamsSelectMenu(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }
}
