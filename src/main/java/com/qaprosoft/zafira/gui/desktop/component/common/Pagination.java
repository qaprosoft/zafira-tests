package com.qaprosoft.zafira.gui.desktop.component.common;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

//id="pagination"
public class Pagination extends AbstractUIObject {
    @FindBy(xpath = ".//button[@aria-label='Last']")
    private ExtendedWebElement toLastPagePagination;

    @FindBy(xpath = ".//button[@aria-label='Next']")
    private ExtendedWebElement toNextPagePagination;

    @FindBy(xpath = ".//button[@aria-label='Previous']")
    private ExtendedWebElement toPreviousPagePagination;

    @FindBy(xpath = ".//button[@aria-label='First']")
    private ExtendedWebElement toFirstPagePagination;

    @FindBy(xpath = ".//div[@class='label ng-binding']")
    private ExtendedWebElement pages;

    @FindBy(xpath = ".//md-select[@aria-label='Rows']")
    private ExtendedWebElement paginationLimitMenu;

    @FindBy(xpath = "//md-option[@ng-repeat='option in $pagination.limitOptions']//div[text()='10']")
    private ExtendedWebElement paginationToTenItems;

    @FindBy(xpath = "//md-option[@ng-repeat='option in $pagination.limitOptions']//div[text()='20']")
    private ExtendedWebElement paginationToTwentyItems;

    @FindBy(xpath = "//md-option[@ng-repeat='option in $pagination.limitOptions']//div[text()='50']")
    private ExtendedWebElement paginationToFiftyItems;

    public Pagination(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public String getPages() {
        return pages.getText().trim();
    }
}
