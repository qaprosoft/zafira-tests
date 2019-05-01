package com.qaprosoft.zafira.gui.component.sidebarmenu;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.zafira.gui.component.SidebarMenu;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ProjectSidebarMenu extends SidebarMenu {

    @FindBy(css = "md-radio-group md-radio-button")
    private List<ExtendedWebElement> items;

    public ProjectSidebarMenu(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public List<ExtendedWebElement> getItems() {
        return items;
    }

}
