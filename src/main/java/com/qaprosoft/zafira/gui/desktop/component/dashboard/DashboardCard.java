package com.qaprosoft.zafira.gui.desktop.component.dashboard;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import com.qaprosoft.zafira.constant.WebConstant;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public class DashboardCard extends AbstractUIObject {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @FindBy(xpath = ".//div/a[@name='dashboardName']")
    private ExtendedWebElement dashboardName;

    @FindBy(xpath = ".//span[@class='dashboards-table__content ng-binding']")
    private ExtendedWebElement dashboardCreatedDate;

    @FindBy(xpath = ".//div[@class='dashboards-table__col _edit']/md-icon")
    private ExtendedWebElement dashboardEdit;

    @FindBy(xpath = ".//md-icon[@class='material-icons icon ng-scope' and text()='delete_outline']")
    private ExtendedWebElement dashboardDelete;

    @FindBy(xpath = "//*[@id=\"delete\"]")
    private ExtendedWebElement deleteButtonOnPopup;


    public DashboardCard(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public String getDashboardName() {
        String realDashboardName = dashboardName.getText();
        LOGGER.info("The name of dashboard is " + realDashboardName);
        return realDashboardName;
    }

    public String getCreatedDate() {
        String createdDate = dashboardCreatedDate.getText();
        LOGGER.info("Created date is " + createdDate);
        return createdDate;
    }

    public void clickEdit() {
        LOGGER.info("Edit dashboard");
        dashboardEdit.isVisible(3);
        dashboardEdit.click();
    }

    public Boolean isVisibleEdit() {
        Boolean isVisibleAndIsClickable = dashboardEdit.isClickable(WebConstant.TIME_TO_LOAD_PAGE);
        LOGGER.info("Edit button on dashboard with name " + dashboardName.getText() + " isVisibleAndClickable is " + isVisibleAndIsClickable);
        return isVisibleAndIsClickable;
    }

    public Boolean isVisibleDelete() {
        Boolean isVisibleAndIsClickable = dashboardDelete.isClickable(WebConstant.TIME_TO_LOAD_PAGE);
        LOGGER.info("Delete button on dashboard with name " + dashboardName.getText() + " isVisibleAndClickable is " + isVisibleAndIsClickable);
        return isVisibleAndIsClickable;
    }

    public void clickDeleteDashboardButton() {
        dashboardDelete.click();
    }
}
