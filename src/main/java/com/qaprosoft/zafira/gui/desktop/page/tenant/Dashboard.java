package com.qaprosoft.zafira.gui.desktop.page.tenant;

import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.PageOpeningStrategy;
import com.qaprosoft.carina.core.gui.AbstractPage;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.constant.WebConstant;
import com.qaprosoft.zafira.gui.desktop.component.NavigationMenu;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public class Dashboard extends AbstractPage {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @FindBy(id = "pageTitle")
    private ExtendedWebElement dashboardTitle;

    @FindBy(xpath = "//button[contains(@class,'_dashboard zf-primary-button')]//span[text()='New widget']")
    private ExtendedWebElement newWidgetButton;

    @FindBy(xpath = "//button[@class='md-icon-button md-button ng-scope md-ink-ripple']")
    private ExtendedWebElement editButton;

    @FindBy(xpath = "//input[@name='title']")
    private ExtendedWebElement editInputField;

    @FindBy(id = "save")
    private ExtendedWebElement saveEditButton;

    @FindBy(id = "cancel")
    private ExtendedWebElement cancelEditButton;

    @FindBy(id = "nav")
    private NavigationMenu navigationMenu;

    public Dashboard(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
        setUiLoadedMarker(newWidgetButton);
        pause(R.TESTDATA.getInt(ConfigConstant.TIME_TO_LOAD_PAGE));
    }

    public void editDashboard(String newDashboardName) {
        editButton.click();
        editInputField.type(newDashboardName);
        saveEditButton.click();
        LOGGER.info("The dashboard name has been changed to " + newDashboardName + "!");
    }

    public String getTitle() {
        pause(WebConstant.TIME_TO_LOAD_PAGE + 3);
        return dashboardTitle.getText();
    }

    public NavigationMenu getNavigationMenu() {
        return navigationMenu;
    }
}
