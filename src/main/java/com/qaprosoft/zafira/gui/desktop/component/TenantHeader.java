package com.qaprosoft.zafira.gui.desktop.component;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ElementLoadingStrategy;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class TenantHeader extends AbstractUIObject {
    @FindBy (className = "app-header__logo-icon")
    private ExtendedWebElement logoImage;

    public TenantHeader(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }
}
