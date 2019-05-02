package com.qaprosoft.zafira.gui.component;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class SubHeader extends AbstractUIObject {

    @FindBy(className = "fixed-page-header-container_title")
    private ExtendedWebElement title;

    @FindBy(id = "itemsCount")
    private ExtendedWebElement itemsCountLabel;

    public SubHeader(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public ExtendedWebElement getTitle() {
        return title;
    }

    public String getTitleText() {
        return title.getText();
    }

    public ExtendedWebElement getItemsCountLabel() {
        return itemsCountLabel;
    }

    public int getItemsCount() {
        String itemsCountLabelText = itemsCountLabel.getText();
        String itemsCountText = itemsCountLabelText.split("\\(")[1].split("\\)")[0];
        return Integer.valueOf(itemsCountText);
    }

}
