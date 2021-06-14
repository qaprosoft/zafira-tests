package com.qaprosoft.zafira.gui.desktop.component;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import com.qaprosoft.zafira.gui.desktop.page.tenant.TestRunsPage;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class NavigationMenu extends AbstractUIObject {
    @FindBy(xpath = ".//li[contains(@class,'nav-item dashboards')]")
    private ExtendedWebElement dashboardButton;

    @FindBy(xpath = ".//li[contains(@class,'nav-item tests')]")
    private ExtendedWebElement testRunsButton;

    @FindBy(xpath = ".//li[contains(@class,'nav-item integrations')]")
    private ExtendedWebElement integrationsButton;

    @FindBy(xpath = ".//li[contains(@class,'nav-item users')]")
    private ExtendedWebElement membersButton;

    @FindBy(xpath = ".//div[@class='project__selected ng-binding']")
    private ExtendedWebElement projectKey;

    public NavigationMenu(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public void toDashboardPage() {
        dashboardButton.click();
    }

    public TestRunsPage toTestRunsPage(){
        testRunsButton.click();
        return new TestRunsPage(getDriver());
    }

    public void toIntegrationPage(){
        integrationsButton.click();
    }

    public void toMembersPage(){
        membersButton.click();
    }

    public String getProjectKey(){
        return projectKey.getText();
    }
}
