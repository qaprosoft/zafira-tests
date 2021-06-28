package com.qaprosoft.zafira.gui.desktop.page.tenant;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractPage;
import com.qaprosoft.zafira.gui.desktop.component.common.HelpMenu;
import com.qaprosoft.zafira.gui.desktop.component.common.TenantHeader;
import com.qaprosoft.zafira.gui.desktop.component.integration.CiCard;
import com.qaprosoft.zafira.gui.desktop.component.integration.EnvProviderCard;
import com.qaprosoft.zafira.gui.desktop.component.integration.NotificationCard;
import com.qaprosoft.zafira.gui.desktop.component.integration.TCMCard;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class TenantIntegrationsPage extends AbstractPage {

    @FindBy(xpath = "//div[@class='card bg-white integration-tool__item background-clear-white']")
    private List<CiCard> ciCards;

    @FindBy(xpath = "//div[@class='card bg-white integration-tool__item background-clear-white']")
    private List<EnvProviderCard> providerCards;

    @FindBy(xpath = "//div[@class='card bg-white integration-tool__item background-clear-white']")
    private List<NotificationCard> notificationCards;

    @FindBy(xpath = "//div[@class='card bg-white integration-tool__item background-clear-white']")
    private List<TCMCard> tcmCards;

    @FindBy(id = "header")
    private TenantHeader header;

    @FindBy(xpath = "//div[@data-embed='helpCenterForm']")
    private HelpMenu helpMenu;

    @FindBy(xpath = "//button[@aria-label='Help']")
    private ExtendedWebElement helpButton;

    @FindBy(id = "pageTitle")
    private ExtendedWebElement pageTitle;

    @FindBy(id = "itemsCount")
    private ExtendedWebElement itemsCount;

    @FindBy(xpath = "//span[text()='CI / Automation Server']")
    private ExtendedWebElement ciSection;

    @FindBy(xpath = "//span[text()='Notification Service']")
    private ExtendedWebElement notificationSection;

    @FindBy(xpath = "//span[text()='Test Case Management']")
    private ExtendedWebElement testCaseManagementSection;

    @FindBy(xpath = "//span[text()='Test Environment Provider']")
    private ExtendedWebElement providerSection;

    @FindBy(xpath = "//span[text()='New integration']")
    private ExtendedWebElement addJenkinsIntegration;

    @FindBy(xpath = "//div[@class='fixed-page-header']//span[contains(@class,'integration-tool__item-icon jenkins')]")
    private ExtendedWebElement addJenkinsIcon;

    public TenantIntegrationsPage(WebDriver driver) {
        super(driver);
    }
}
