package com.qaprosoft.zafira.gui.desktop.component.billing;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

//div[@class='pricing-plans__cards-wrapper ng-scope']//div[contains(@class,'plan__wrapper ng-scope')]
public class SubscriptionPlan extends AbstractUIObject {
    @FindBy(xpath = ".//div[@class='plan__icon']//md-icon[@role='img']")
    private ExtendedWebElement planIcon;

    @FindBy(xpath = ".//div[@class='plan__name ng-binding']")
    private ExtendedWebElement planName;

    @FindBy(xpath = ".//*[@class='plan__price-value ng-scope' or @class='plan__price-text ng-binding']")
    private ExtendedWebElement planPrice;

    @FindBy(xpath = ".//button[text()='upgrade']")
    private ExtendedWebElement upgradeButton;

    @FindBy(xpath = ".//li[contains(@ng-if,'USERS_COUNT')]")
    private ExtendedWebElement quantityOfUsers;

    @FindBy(xpath = ".//li[contains(@ng-if,'PROJECTS_COUNT')]")
    private ExtendedWebElement quantityOfProjects;

    @FindBy(xpath = ".//li[contains(@ng-if,'ENGINE_EXECUTION_HOURS')]")
    private ExtendedWebElement testHours;

    @FindBy(xpath = ".//li[contains(@ng-if,'plan.features.length')]")
    private ExtendedWebElement features;

    @FindBy(xpath = ".//li[contains(@ng-if,'S3_STORAGE_GB')]")
    private ExtendedWebElement s3Storage;

    @FindBy(xpath = ".//li[contains(@ng-if,'SUPPORT_RESPONSE_HOURS')]")
    private ExtendedWebElement hoursToGetAnswerFromSupport;

    @FindBy(xpath = ".//li[contains(text(),'Training&Consulting')]")
    private ExtendedWebElement trainingAndConsulting;

    public SubscriptionPlan(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }
}
