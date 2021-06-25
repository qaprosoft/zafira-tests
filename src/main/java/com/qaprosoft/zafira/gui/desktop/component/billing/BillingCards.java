package com.qaprosoft.zafira.gui.desktop.component.billing;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

//div[contains(@class,'billing-modal__cards-item ng-scope')]
public class BillingCards extends AbstractUIObject {
    @FindBy(xpath = ".//img[@class='billing-modal__cards-item-image']")
    private ExtendedWebElement cardLogo;

    @FindBy(xpath = ".//span[@class='billing-modal__cards-item-card ng-binding ng-scope']")
    private ExtendedWebElement cardInfo;

    @FindBy(xpath = ".//md-icon[@aria-label='delete_outline']")
    private ExtendedWebElement deletePaymentMethod;

    public BillingCards(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }
}
