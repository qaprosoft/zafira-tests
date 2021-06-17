package com.qaprosoft.zafira.gui.desktop.component;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class ResultSessionWindow extends AbstractUIObject {
    public ResultSessionWindow(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }
}
