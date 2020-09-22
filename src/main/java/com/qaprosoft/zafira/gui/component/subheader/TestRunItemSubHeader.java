package com.qaprosoft.zafira.gui.component.subheader;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.zafira.gui.component.SubHeader;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class TestRunItemSubHeader extends SubHeader {

    @FindBy(className = "back_button")
    private ExtendedWebElement backButton;

    public TestRunItemSubHeader(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public ExtendedWebElement getBackButton() {
        return backButton;
    }

    public void clickBackButton() {
        backButton.click();
    }

}
