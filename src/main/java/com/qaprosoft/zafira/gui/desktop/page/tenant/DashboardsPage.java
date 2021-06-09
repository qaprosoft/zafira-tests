package com.qaprosoft.zafira.gui.desktop.page.tenant;

import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.PageOpeningStrategy;
import com.qaprosoft.carina.core.gui.AbstractPage;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.gui.desktop.component.NavigationMenu;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;

public class DashboardsPage extends AbstractPage {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @FindBy(id = "nav")
    private NavigationMenu navigationMenu;

    @FindBy(xpath = "//a[@name='dashboardName']")
    private List<ExtendedWebElement> dashboards;

    @FindBy(xpath = "//div[@class='fixed-page-header-container']//div[contains(text(),'Dashboards')]")
    private ExtendedWebElement sectionHeader;

    @FindBy(xpath = "//span[@class='button__text ng-scope']")
    private ExtendedWebElement addDashboardButton;

    @FindBy(xpath = "//form[@name='dashboard_form']")
    private ExtendedWebElement newDashboardForm;

    @FindBy(xpath = "//input[@type='text']")
    private ExtendedWebElement search;

    @FindBy(xpath = "//input[@name='title']")
    private ExtendedWebElement dashboardNameInput;

    @FindBy(xpath = "//button[@type='submit']")
    private ExtendedWebElement submitButton;

    @FindBy(xpath = "//div[@class='dashboards-table__col _delete']//md-icon[@class='material-icons icon ng-scope']")
    private ExtendedWebElement deleteButton;

    @FindBy(xpath = "//*[@id=\"delete\"]")
    private ExtendedWebElement deleteButtonOnPopup;


    public DashboardsPage(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
        setUiLoadedMarker(sectionHeader);
    }

    public NavigationMenu getNavigationMenu() {
        return navigationMenu;
    }

    public Dashboard addDashboard(String dashboardName) {
        addDashboardButton.click();
        dashboardNameInput.type(dashboardName);
        submitButton.pause(R.TESTDATA.getInt(ConfigConstant.TIME_TO_LOAD_PAGE));
        submitButton.click();
        LOGGER.info("Dashboard with name " + dashboardName + " was created!");
        return new Dashboard(getDriver());
    }

    public List<ExtendedWebElement> searchDashboard(String dashboardName) {
        search.type(dashboardName);
        LOGGER.info("Dashboard with name " + dashboardName + " was found!");
        pause(R.TESTDATA.getInt(ConfigConstant.TIME_TO_LOAD_PAGE));
        return dashboards;
    }

    public Dashboard deleteDashboard(String dashboardName) {
        deleteButton.click(R.TESTDATA.getInt(ConfigConstant.TIME_TO_LOAD_PAGE));
        deleteButtonOnPopup.click();
        LOGGER.info("Dashboard with name " + dashboardName + " was deleted!");
        return new Dashboard(getDriver());
    }

    public List<ExtendedWebElement> getAllDashboards() {
        pause(R.TESTDATA.getInt(ConfigConstant.TIME_TO_LOAD_PAGE));
        this.refresh();
        pause(R.TESTDATA.getInt(ConfigConstant.TIME_TO_LOAD_PAGE) + 5);
        return dashboards;
    }

    public boolean isSubmitButtonActive() {
        return submitButton.isClickable(5);
    }
}
