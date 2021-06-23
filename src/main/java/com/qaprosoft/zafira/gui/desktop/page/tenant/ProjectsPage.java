package com.qaprosoft.zafira.gui.desktop.page.tenant;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class ProjectsPage extends AbstractPage {
    @FindBy(xpath = "//div[@class='input__container']//input")
    private ExtendedWebElement searchField;

    @FindBy(xpath = "//button[@class='button md-raised md-primary gtm-project-button md-button ng-scope md-ink-ripple']")
    private ExtendedWebElement newProjectButton;

    @FindBy(xpath = "//div[@class='projects-table projects-table__header']//div[@class='projects-table__col _name']")
    private ExtendedWebElement columnNameHeader;

    @FindBy(xpath = "//div[@class='projects-table projects-table__header']//div[@class='projects-table__col _cat']")
    private ExtendedWebElement columnCategoryHeader;

    @FindBy(xpath = "//div[@class='projects-table projects-table__header']//div[@class='projects-table__col _key']")
    private ExtendedWebElement columnKeyHeader;

    @FindBy(xpath = "//div[@class='projects-table projects-table__header']//div[@class='projects-table__col _lead']")
    private ExtendedWebElement columnLeadHeader;

    @FindBy(xpath = "//div[@class='projects-table projects-table__header']//div[@class='projects-table__col _created']")
    private ExtendedWebElement columnCreatedHeader;



    public ProjectsPage(WebDriver driver) {
        super(driver);
    }
}
