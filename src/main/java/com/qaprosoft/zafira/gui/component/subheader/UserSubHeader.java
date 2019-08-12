package com.qaprosoft.zafira.gui.component.subheader;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.zafira.gui.component.SubHeader;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class UserSubHeader extends SubHeader {

    @FindBy(css = "._search input")
    private ExtendedWebElement searchInput;

    @FindBy(xpath = ".//button[.//*[text() = 'New user']]")
    private ExtendedWebElement newUserButton;

    public UserSubHeader(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public ExtendedWebElement getSearchInput() {
        return searchInput;
    }

    public void typeSearchInput(String searchCriteria) {
        searchInput.type(searchCriteria);
    }

    public ExtendedWebElement getNewUserButton() {
        return newUserButton;
    }

    public void clickNewUserButton() {
        newUserButton.click();
    }

}
