package com.qaprosoft.zafira.gui.desktop.page.tenant;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractPage;
import com.qaprosoft.zafira.gui.desktop.component.common.HelpMenu;
import com.qaprosoft.zafira.gui.desktop.component.common.TenantHeader;
import com.qaprosoft.zafira.gui.desktop.component.project.ProjectCard;
import com.qaprosoft.zafira.gui.desktop.component.project.ProjectProcessWindow;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ProjectsPage extends AbstractPage {
    @FindBy(id = "header")
    private TenantHeader header;

    @FindBy(xpath = "//div[@data-embed='helpCenterForm']")
    private HelpMenu helpMenu;

    @FindBy(xpath = "//div[@class='projects-table__row ng-scope']")
    private List<ProjectCard> projectCards;

    @FindBy(xpath = "//md-dialog[contains(@class,'project-modal')]")
    private ProjectProcessWindow projectProcessWindow; //delete or edit window

    @FindBy(xpath = "//button[@aria-label='Help']")
    private ExtendedWebElement helpButton;

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
