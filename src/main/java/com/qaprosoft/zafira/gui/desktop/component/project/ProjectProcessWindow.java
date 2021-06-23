package com.qaprosoft.zafira.gui.desktop.component.project;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

//md-dialog[contains(@class,'project-modal')]
public class ProjectProcessWindow extends AbstractUIObject {
    @FindBy(xpath = ".//span[@class='projects-photo__icon-wrapper _squared ng-scope']")
    private ExtendedWebElement img;

    public ProjectProcessWindow(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }
}
