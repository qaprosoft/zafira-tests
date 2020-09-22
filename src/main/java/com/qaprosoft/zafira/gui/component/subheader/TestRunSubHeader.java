package com.qaprosoft.zafira.gui.component.subheader;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.zafira.gui.component.SubHeader;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class TestRunSubHeader extends SubHeader {

    @FindBy(className = "_launcher")
    private ExtendedWebElement launcherButton;

    public TestRunSubHeader(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public ExtendedWebElement getLauncherButton() {
        return launcherButton;
    }

    public void clickLauncherButton() {
        launcherButton.click();
    }

}
