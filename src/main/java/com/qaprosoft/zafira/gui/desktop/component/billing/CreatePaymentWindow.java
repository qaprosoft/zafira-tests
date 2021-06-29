package com.qaprosoft.zafira.gui.desktop.component.billing;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

//md-dialog[@class='billing-add-card-modal styled-modal _md md-dialog-fullscreen background-clear-white md-transition-in']
public class CreatePaymentWindow extends AbstractUIObject {
    @FindBy(xpath = ".//div[contains(@class,'billing-modal__cards-item ng-scope')]")
    private List<BillingCards> billingCards;

    @FindBy(xpath = ".//h2[@class='modal-header__title ng-binding']")
    private ExtendedWebElement title;

    @FindBy(xpath = ".//button[@class='md-icon-button _default-md-style md-button md-ink-ripple']")
    private ExtendedWebElement closeButton;

    @FindBy(xpath = ".//span[@class='billing-modal__cards-button-text']")
    private ExtendedWebElement addNewPaymentMethod;

    @FindBy(xpath = ".//span[@class='billing-modal__card-description']")
    private ExtendedWebElement currentPaymentTitle;

    @FindBy(xpath = ".//div[@class='billing-modal__card-number ng-binding ng-scope']")
    private ExtendedWebElement currentPaymentInfo;

    @FindBy(xpath = ".//div[@class='braintree-option braintree-option__card']")
    private ExtendedWebElement byCard;

    @FindBy(xpath = ".//div[@class='braintree-option braintree-option__paypal']")
    private ExtendedWebElement byPayPal;

    @FindBy(xpath = ".//span[text()='cancel']/ancestor::button")
    private ExtendedWebElement cancelButton;

    @FindBy(xpath = ".//span[text()='Back']/ancestor::button")
    private ExtendedWebElement backButton;

    @FindBy(xpath = ".//button[@class='md-raised md-primary md-button ng-scope md-ink-ripple']")
    private ExtendedWebElement createButton;

    @FindBy(id = "cardholder-name")
    private ExtendedWebElement cardHolderName;

    @FindBy(id = "credit-card-number")
    private ExtendedWebElement creditCardNumber;

    @FindBy(id = "expiration")
    private ExtendedWebElement expirationDate;

    @FindBy(id = "cvv")
    private ExtendedWebElement cvv;

    @FindBy(xpath = ".//div[@aria-label='PayPal Checkout']")
    private ExtendedWebElement payPalCheckout;

    @FindBy(xpath = ".//div[@class='md-container md-ink-ripple']")
    private ExtendedWebElement licenceAgreementCheckbox;

    @FindBy(xpath = ".//div[@data-braintree-id='toggle']")
    private ExtendedWebElement chooseAnotherPayWay;

    public CreatePaymentWindow(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }
}
