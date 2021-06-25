package com.qaprosoft.zafira.gui.desktop.page.tenant;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractPage;
import com.qaprosoft.zafira.gui.desktop.component.common.HelpMenu;
import com.qaprosoft.zafira.gui.desktop.component.common.TenantHeader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class BillingInfoPage extends AbstractPage {
    @FindBy(id = "header")
    private TenantHeader header;

    @FindBy(xpath = "//div[@data-embed='helpCenterForm']")
    private HelpMenu helpMenu;

    @FindBy(xpath = "//button[@aria-label='Help']")
    private ExtendedWebElement helpButton;

    @FindBy(id = "pageTitle")
    private ExtendedWebElement pageTitle;

    @FindBy(xpath = "//div[@class='zebrunner-billing__card-title ng-binding']")
    private ExtendedWebElement planTitle;

    @FindBy(xpath = "//div[@class='zebrunner-billing__card-chart-text-info']")
    private ExtendedWebElement automationTestingHours;

    @FindBy(xpath = "//a[text()='upgrade']")
    private ExtendedWebElement upgradeButton;

    @FindBy(xpath = "//li[contains(@ng-if,'USERS_COUNT')]/span")
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

    @FindBy(xpath = "//div[contains(@class,'zebrunner-billing__card-title') and text()='Payment method']")
    private ExtendedWebElement paymentInfoTitle;

    @FindBy(xpath = "//button[contains(@class,'zebrunner-billing__card-modal-button ng-binding')]")
    private ExtendedWebElement addPaymentButton;



    public BillingInfoPage(WebDriver driver) {
        super(driver);
    }
}
