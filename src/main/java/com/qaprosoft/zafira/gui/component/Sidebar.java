package com.qaprosoft.zafira.gui.component;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import com.qaprosoft.zafira.gui.component.sidebarmenu.DashboardSidebarMenu;
import com.qaprosoft.zafira.gui.component.sidebarmenu.ProjectSidebarMenu;
import com.qaprosoft.zafira.gui.component.sidebarmenu.TestRunViewSidebarMenu;
import com.qaprosoft.zafira.gui.component.sidebarmenu.UserSidebarMenu;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class Sidebar extends AbstractUIObject {

    @FindBy(xpath = ".//li[.//a[./*[text() = 'Boards']]]")
    private DashboardSidebarMenu dashboardMenuButton;

    @FindBy(xpath = ".//a[./*[text() = 'Runs']]")
    private ExtendedWebElement testRunsButton;

    @FindBy(xpath = ".//li[.//a[./*[text() = 'Views']]]")
    private TestRunViewSidebarMenu testRunsViewMenuButton;

    @FindBy(xpath = ".//li[.//*[text() = 'supervisor_account']]")
    private UserSidebarMenu usersButton;

    @FindBy(xpath = ".//a[./*[text() = 'Monitors']]")
    private ExtendedWebElement monitorsButton;

    @FindBy(xpath = ".//a[./*[text() = 'Integrations']]")
    private ExtendedWebElement integrationsButton;

    @FindBy(xpath = ".//i[text() = 'person']")
    private ExtendedWebElement userProfileButton;

    @FindBy(css = "li.projects")
    private ProjectSidebarMenu companyLogoButton;

    public Sidebar(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public DashboardSidebarMenu getDashboardMenuButton() {
        return dashboardMenuButton;
    }

    public void clickDashboardMenuButton() {
        dashboardMenuButton.getRootElement().click();
    }

    public ExtendedWebElement getTestRunsButton() {
        return testRunsButton;
    }

    public void clickTestRunsButton() {
        testRunsButton.click();
    }

    public TestRunViewSidebarMenu getTestRunsViewMenuButton() {
        return testRunsViewMenuButton;
    }

    public void clickTestRunsViewMenuButton() {
        testRunsViewMenuButton.getRootElement().click();
    }

    public UserSidebarMenu getUsersButton() {
        return usersButton;
    }

    public void clickUsersButton() {
        usersButton.getRootElement().click();
    }

    public ExtendedWebElement getMonitorsButton() {
        return monitorsButton;
    }

    public void clickMonitorsButton() {
        monitorsButton.click();
    }

    public ExtendedWebElement getIntegrationsButton() {
        return integrationsButton;
    }

    public void clickIntegrationsButton() {
        integrationsButton.click();
    }

    public ExtendedWebElement getUserProfileButton() {
        return userProfileButton;
    }

    public void clickUserProfileButton() {
        userProfileButton.click();
    }

    public ProjectSidebarMenu getCompanyLogoButton() {
        return companyLogoButton;
    }

    public void clickCompanyLogoButton() {
        companyLogoButton.getRootElement().click();
    }

}
