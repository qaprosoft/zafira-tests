package com.qaprosoft.zafira.gui.component;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SidebarMenu extends AbstractUIObject {

    @FindBy(xpath = ".//md-icon[text() = 'close']")
    private ExtendedWebElement closeButton;

    @FindBy(xpath = ".//a[.//*[text() = 'add']]")
    private ExtendedWebElement addButton;

    @FindBy(name = "search")
    private ExtendedWebElement searchInput;

    @FindBy(css = ".sub-search li a")
    private List<ExtendedWebElement> items;

    public SidebarMenu(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public ExtendedWebElement getCloseButton() {
        return closeButton;
    }

    public void clickCloseButton() {
        closeButton.click();
    }

    public ExtendedWebElement getAddButton() {
        return addButton;
    }

    public void clickAddButton() {
        addButton.click();
    }

    public ExtendedWebElement getSearchInput() {
        return searchInput;
    }

    public void typeSearchCriteria(String criteria) {
        searchInput.type(criteria);
    }

    public List<ExtendedWebElement> getItems() {
        return items;
    }

}
