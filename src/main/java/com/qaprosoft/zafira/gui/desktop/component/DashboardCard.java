package com.qaprosoft.zafira.gui.desktop.component;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public class DashboardCard extends AbstractUIObject {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @FindBy(xpath = "//a[@name='dashboardName']")
    private ExtendedWebElement dashboardName;

    @FindBy(xpath = "//span[@class='dashboards-table__content ng-binding']")
    private ExtendedWebElement dashboardCreatedDate;

    @FindBy(xpath = "//div[@class='dashboards-table__col _edit']")
    private ExtendedWebElement dashboardEdit;

    @FindBy(xpath = "//md-icon[@class='material-icons icon ng-scope' and text()='delete_outline']")
    private ExtendedWebElement dashboardDelete;


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

    public Boolean isPresentCreatedDate() {
        String createdDate = dashboardCreatedDate.getText();
        LOGGER.info("Created date is " + createdDate);
        return dashboardCreatedDate.isVisible(3);
    }

    public void clickEdit() {
        String createdDate = dashboardCreatedDate.getText();
        LOGGER.info("Edit dashboard");
        dashboardEdit.isVisible(3);
        dashboardEdit.click();
    }

    public Boolean isVisibleEdit() {
        return dashboardEdit.isElementPresent()&dashboardEdit.isClickable();
    }

}
