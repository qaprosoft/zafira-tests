package com.qaprosoft.zafira.gui.component;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class SubHeader extends AbstractUIObject {

    @FindBy(className = "fixed-page-header-container_title")
    private ExtendedWebElement title;

    public SubHeader(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

}
