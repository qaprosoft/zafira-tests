package com.qaprosoft.zafira.gui.desktop.page.tenant;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.PageOpeningStrategy;
import com.qaprosoft.carina.core.gui.AbstractPage;
import com.qaprosoft.zafira.constant.WebConstant;
import com.qaprosoft.zafira.gui.desktop.component.DashboardCard;
import com.qaprosoft.zafira.gui.desktop.component.NavigationMenu;
import com.qaprosoft.zafira.util.WaitUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;

public class MainDashboardsPage extends AbstractPage {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());


    @FindBy(id = "nav")
    private NavigationMenu navigationMenu;

    @FindBy(xpath = "//a[@name='dashboardName']")
    private List<ExtendedWebElement> dashboards;

    @FindBy(xpath = "//div[@class='dashboards-table__row ng-scope']")
    private List<DashboardCard> dashboardCards;

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

    @FindBy(xpath = "//a[@name='dashboardName' and contains(text(), 'General')]")
    private ExtendedWebElement generalDashboard;

    @FindBy(xpath = "//a[@name='dashboardName' and contains(text(), 'Personal')]")
    private ExtendedWebElement personalDashboard;

    public MainDashboardsPage(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
        setUiLoadedMarker(sectionHeader);
    }

    public NavigationMenu getNavigationMenu() {
        return navigationMenu;
    }

    public DashboardPage addDashboard(String dashboardName) {
        addDashboardButton.click();
        dashboardNameInput.type(dashboardName);
        submitButton.pause(WebConstant.TIME_TO_LOAD_PAGE);
        submitButton.click();
        LOGGER.info("Dashboard with name " + dashboardName + " was created!");
        return new DashboardPage(getDriver());
    }

    public List<ExtendedWebElement> searchDashboard(String dashboardName) {
        search.type(dashboardName);
        LOGGER.info("Dashboard with name " + dashboardName + " was found!");
        pause(WebConstant.TIME_TO_LOAD_PAGE);
        return dashboards;
    }

    public DashboardPage deleteDashboard(String dashboardName) {
        deleteButton.click(WebConstant.TIME_TO_LOAD_PAGE);
        deleteButtonOnPopup.click();
        LOGGER.info("Dashboard with name " + dashboardName + " was deleted!");
        return new DashboardPage(getDriver());
    }

    public List<ExtendedWebElement> getAllDashboards() {
        pause(WebConstant.TIME_TO_LOAD_PAGE + 3);
        this.refresh();
        pause(WebConstant.TIME_TO_LOAD_PAGE + 5);
        return dashboards;
    }

    public DashboardCard getDashboardByName(String dashboardName) {
        Boolean loaded = WaitUtil.waitListToLoad(dashboards, 5000, 500);

        for (DashboardCard dashboardCard : dashboardCards) {
            if (dashboardCard.getDashboardName().toLowerCase().contains(dashboardName.toLowerCase()))

                return dashboardCard;
        }
        throw new RuntimeException("Can't find dashboard with name " + dashboardName + " !");
    }

    public DashboardPage getGeneralDashboard() {
        generalDashboard.click();
        return new DashboardPage(getDriver());
    }

    public ExtendedWebElement getPersonalDashboard() {
        return personalDashboard;
    }
}
