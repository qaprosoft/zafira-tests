package com.qaprosoft.zafira.gui.desktop.page.tenant;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ProjectIntegrationsPage extends AbstractPage {
    @FindBy(id = "pageTitle")
    private ExtendedWebElement pageTitle;

    @FindBy(xpath = "//div[@class='integration-card__wrapper']")
    private List<ExtendedWebElement> integrations;

    //0 el = projects general page, 1 el = current project page
    @FindBy(xpath = "//a[@class='breadcrumb _link ng-binding ng-scope']")
    private List<ExtendedWebElement> projectRef;

    public ProjectIntegrationsPage(WebDriver driver) {
        super(driver);
    }
}
