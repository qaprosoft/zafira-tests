package com.qaprosoft.zafira.gui.desktop.page.tenant;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractPage;
import com.qaprosoft.zafira.gui.desktop.component.billing.SubscriptionPlan;
import com.qaprosoft.zafira.gui.desktop.component.common.HelpMenu;
import com.qaprosoft.zafira.gui.desktop.component.common.TenantHeader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SubscriptionPlansPage extends AbstractPage {
    @FindBy(id = "header")
    private TenantHeader header;

    @FindBy(xpath = "//div[@class='pricing-plans__cards-wrapper ng-scope']//div[contains(@class,'plan__wrapper ng-scope')]")
    private List<SubscriptionPlan> plans;

    @FindBy(xpath = "//div[@data-embed='helpCenterForm']")
    private HelpMenu helpMenu;

    @FindBy(xpath = "//button[@aria-label='Help']")
    private ExtendedWebElement helpButton;

    @FindBy(id = "pageTitle")
    private ExtendedWebElement pageTitle;

    @FindBy(xpath = "//span[contains(text(),'Annually')]/span")
    private ExtendedWebElement annualDiscount;

    @FindBy(xpath = "//md-switch/div[@class='md-container']")
    private ExtendedWebElement yearToMonthPriceSwitcher;

    @FindBy(xpath = "//span[@class='zebrunner-subscription__link gtm-chat-button']")
    private ExtendedWebElement chatLink;

    @FindBy(xpath = "//a[@ui-sref='billing' and @class='zebrunner-subscription__link']")
    private ExtendedWebElement billingRef;

    @FindBy(xpath = "//a[text()='email' and @class='zebrunner-subscription__link']")
    private ExtendedWebElement emailRef;

    public SubscriptionPlansPage(WebDriver driver) {
        super(driver);
    }
}
