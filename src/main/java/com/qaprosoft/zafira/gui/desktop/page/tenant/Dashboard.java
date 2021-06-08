package com.qaprosoft.zafira.gui.desktop.page.tenant;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ElementLoadingStrategy;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.PageOpeningStrategy;
import com.qaprosoft.carina.core.gui.AbstractPage;
import com.qaprosoft.zafira.gui.desktop.component.NavigationMenu;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Dashboard extends AbstractPage {

    @FindBy(id = "pageTitle")
    private ExtendedWebElement dashboardTitle;

    @FindBy(xpath = "//button[contains(@class,'_dashboard zf-primary-button')]//span[text()='New widget']")
    private ExtendedWebElement newWidgetButton;

    @FindBy(id = "nav")
    private NavigationMenu navigationMenu;

    public Dashboard(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
        setUiLoadedMarker(newWidgetButton);
        pause(3);
    }

    public String getTitle() {
        return dashboardTitle.getText();
    }

    public NavigationMenu getNavigationMenu(){
        return navigationMenu;
    }
}
