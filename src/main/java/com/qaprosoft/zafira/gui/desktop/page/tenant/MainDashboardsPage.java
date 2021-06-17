package com.qaprosoft.zafira.gui.desktop.page.tenant;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.PageOpeningStrategy;
import com.qaprosoft.carina.core.gui.AbstractPage;
import com.qaprosoft.zafira.constant.WebConstant;
import com.qaprosoft.zafira.gui.desktop.component.DashboardCard;
import com.qaprosoft.zafira.util.WaitUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;

public class MainDashboardsPage extends AbstractPage {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @FindBy(id = "pageTitle")
    private ExtendedWebElement mainDashboardsTitle;

    @FindBy(xpath = "//div[@class='dashboards-table__row ng-scope']")
    private List<DashboardCard> dashboardCards;

    @FindBy(xpath = "//span[@class='button__text ng-scope']")
    private ExtendedWebElement addDashboardButton;

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

    @FindBy(xpath = "//div[@class='dashboards-table__col _name']//span")
    private ExtendedWebElement dashboardsTableColName;

    @FindBy(xpath = "//div[@class='dashboards-table__col _created']/div/span")
    private ExtendedWebElement dashboardsTableColCreationDate;

    public MainDashboardsPage(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
        setUiLoadedMarker(generalDashboard);
    }

    public DashboardPage addDashboard(String dashboardName) {
        addDashboardButton.click();
        dashboardNameInput.type(dashboardName);
        submitButton.pause(WebConstant.TIME_TO_LOAD_PAGE);
        submitButton.click();
        LOGGER.info("Dashboard with name " + dashboardName + " was created!");
        return new DashboardPage(getDriver());
    }

    public List<DashboardCard> searchDashboard(String dashboardName) {
        search.type(dashboardName);
        LOGGER.info("Dashboard with name " + dashboardName + " was found!");
        pause(WebConstant.TIME_TO_LOAD_PAGE);
        return dashboardCards;
    }

    public DashboardPage deleteDashboard(String dashboardName) {
        deleteButton.click(WebConstant.TIME_TO_LOAD_PAGE);
        deleteButtonOnPopup.click();
        LOGGER.info("Dashboard with name " + dashboardName + " was deleted!");
        return new DashboardPage(getDriver());
    }

    public DashboardCard getDashboardByName(String dashboardName) {
        Boolean loaded = WaitUtil.waitListToLoad(dashboardCards, 5000, 500);
        for (DashboardCard dashboardCard : dashboardCards) {
            if (dashboardCard.getDashboardName().toLowerCase().contains(dashboardName.toLowerCase()))

                return dashboardCard;
        }
        throw new RuntimeException("Can't find dashboard with name " + dashboardName + " !");
    }

    public Boolean isDashboardPresentOnMainPage(String dashboardName) {
        pause(WebConstant.TIME_TO_LOAD_PAGE);
        for (DashboardCard dashboardCard : dashboardCards) {
            if (dashboardCard.getDashboardName().toLowerCase().equals(dashboardName.toLowerCase())) {

                return true;
            }
        }
        return false;
    }

    public String getTitle() {
        pause(WebConstant.TIME_TO_LOAD_PAGE);
        return mainDashboardsTitle.getText();
    }

    public boolean isSearchPresentAndClickable() {
        return search.isVisible(WebConstant.TIME_TO_LOAD_PAGE) && search.isClickable(WebConstant.TIME_TO_LOAD_PAGE);
    }

    public boolean isAddDashboardButtonPresentAndClickable() {
        return addDashboardButton.isVisible(WebConstant.TIME_TO_LOAD_PAGE) && addDashboardButton.isClickable(WebConstant.TIME_TO_LOAD_PAGE);
    }

    public String getColonNameDASBOARD_NAME() {
        pause(WebConstant.TIME_TO_LOAD_PAGE);
        String dashboardNameColon = dashboardsTableColName.getText();
        LOGGER.info("Dashboard name column name is  " + dashboardNameColon);
        return dashboardNameColon;
    }

    public String getColonNameCREATION_DATE() {
        pause(WebConstant.TIME_TO_LOAD_PAGE);
        String creationDate = dashboardsTableColCreationDate.getText();
        LOGGER.info("Creation date column name is  " + creationDate);
        return creationDate;
    }
}
