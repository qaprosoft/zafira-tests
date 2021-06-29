package com.qaprosoft.zafira.gui.desktop.component.dashboard;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

//div[contains(@class,'selection-blocks_container_elements_item zf-radio-button ng-scope')]
public class WidgetCard extends AbstractUIObject {
    @FindBy(xpath = ".//div[contains(@class,'selection-blocks_container_elements_item_container_title')]//span[@class='ng-binding']")
    private ExtendedWebElement cardTitle;

    @FindBy(xpath = ".//div[contains(@class,'selection-blocks_container_elements_item_container_description')]//span[@class='ng-binding']")
    private ExtendedWebElement cardDescription;

    @FindBy(xpath = ".//div[contains(@class,'selection-blocks_container_elements_item_preview')]/img")
    private ExtendedWebElement cardImg;

    public WidgetCard(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }
}
