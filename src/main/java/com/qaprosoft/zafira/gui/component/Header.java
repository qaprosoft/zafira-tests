package com.qaprosoft.zafira.gui.component;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class Header extends AbstractUIObject {

    @FindBy(className = "logo-icon")
    private ExtendedWebElement brandImage;

    public Header(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public ExtendedWebElement getBrandImage() {
        return brandImage;
    }

    public void clickBrandImage() {
        brandImage.click();
    }

}
