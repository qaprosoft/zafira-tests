package com.qaprosoft.zafira.gui.component.subheader;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.zafira.gui.component.SubHeader;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class UserProfileSubHeader extends SubHeader {

    @FindBy(xpath = ".//button[.//*[text() = 'logout']]")
    private ExtendedWebElement logoutButton;

    public UserProfileSubHeader(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public ExtendedWebElement getLogoutButton() {
        return logoutButton;
    }

    public void clickLogoutButton() {
        logoutButton.click();
    }
}
