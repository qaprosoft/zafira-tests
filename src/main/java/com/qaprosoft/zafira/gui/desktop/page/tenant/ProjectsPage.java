package com.qaprosoft.zafira.gui.desktop.page.tenant;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractPage;
import com.qaprosoft.zafira.constant.WebConstant;
import com.qaprosoft.zafira.gui.desktop.component.common.HelpMenu;
import com.qaprosoft.zafira.gui.desktop.component.common.Pagination;
import com.qaprosoft.zafira.gui.desktop.component.common.TenantHeader;
import com.qaprosoft.zafira.gui.desktop.component.project.ProcessProjectWindow;
import com.qaprosoft.zafira.gui.desktop.component.project.ProjectCard;
import com.qaprosoft.zafira.util.WaitUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ProjectsPage extends AbstractPage {
    @FindBy(id = "header")
    private TenantHeader header;

    @FindBy(id = "pagination")
    private Pagination pagination;

    @FindBy(xpath = "//div[@data-embed='helpCenterForm']")
    private HelpMenu helpMenu;

    @FindBy(xpath = "//div[contains(@class,'projects-table ng-scope')]//div[contains(@class,'projects-table__row')]")
    private List<ProjectCard> projectCards;

    @FindBy(xpath = "//md-dialog[contains(@class,'project-modal')]")
    private ProcessProjectWindow projectProcessWindow; //delete or edit window

    @FindBy(xpath = "//span[contains(text(),'Help')]")
    private ExtendedWebElement helpButton;

    @FindBy(id = "pageTitle")
    private ExtendedWebElement pageTitle;

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
        setUiLoadedMarker(newProjectButton);
    }

    public String getTitle() {
        return pageTitle.getText().trim();
    }

    public boolean isSearchFieldPresent() {
        return searchField.isVisible();
    }

    public boolean isNewProjectButtonPresent() {
        return newProjectButton.isVisible();
    }

    public TenantHeader getHeader() {
        return header;
    }

    public HelpMenu getHelpMenu() {
        return helpMenu;
    }

    public List<ProjectCard> getProjectCards() {
        return projectCards;
    }

    public ProcessProjectWindow openNewProjectWindow() {
        newProjectButton.click();
        pause(WebConstant.TIME_TO_LOAD_PAGE);
        return projectProcessWindow;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public String getNumberOfProjectCards() {
        pause(WebConstant.TIME_TO_LOAD_PAGE);
        WaitUtil.waitListToLoad(projectCards);
        return String.valueOf(projectCards.size());
    }

    public HelpMenu openHelpWindow() {
        helpButton.click();
        return helpMenu;
    }

    public TestRunsPage toCertainProject(String projectKey) {
        pause(WebConstant.TIME_TO_LOAD_HEAVY_ELEMENT);
        WaitUtil.waitListToLoad(projectCards);
        for (ProjectCard projectCard : projectCards) {
            if (projectCard.getKey().equalsIgnoreCase(projectKey)) {
                return projectCard.toTestRunsPage();
            }
        }

        throw new RuntimeException("Can't find project by key");
    }

    public boolean isProjectWithNameAndKeyExists(String name, String key) {
        pause(WebConstant.TIME_TO_LOAD_PAGE);
        WaitUtil.waitListToLoad(projectCards);
        for (ProjectCard projectCard : projectCards) {
            if (projectCard.getName().equalsIgnoreCase(name) && projectCard.getKey().equalsIgnoreCase(key)) {
                return true;
            }
        }
        return false;
    }

    public boolean isProjectWithKeyExists(String key) {
        WaitUtil.waitListToLoad(projectCards);
        for (ProjectCard projectCard : projectCards) {
            if (projectCard.getKey().equalsIgnoreCase(key)) {
                return true;
            }
        }
        return false;
    }

    public TestRunsPage toProjectByTitle(String projectName) {
        pause(WebConstant.TIME_TO_LOAD_HEAVY_ELEMENT);
        WaitUtil.waitListToLoad(projectCards);
        for (ProjectCard projectCard : projectCards) {
            if (projectCard.getName().equalsIgnoreCase(projectName)) {
                projectCard.toTestRunsPage();
                return new TestRunsPage(driver);
            }
        }
        throw new RuntimeException("Can't find project by title " + projectName);
    }

    public TestRunsPage toProjectByKey(String projectKey) {
        WaitUtil.waitListToLoad(projectCards);
        for (ProjectCard projectCard : projectCards) {
            if (projectCard.getKey().equalsIgnoreCase(projectKey)) {
                projectCard.toTestRunsPage();
                return new TestRunsPage(driver);
            }
        }
        throw new RuntimeException("Can't find project by key " + projectKey);
    }

    public boolean deleteProjectByKey(String projectKey) {
        pause(WebConstant.TIME_TO_LOAD_HEAVY_ELEMENT);
        WaitUtil.waitListToLoad(projectCards);
        for (ProjectCard projectCard : projectCards) {
            if (projectCard.getKey().equalsIgnoreCase(projectKey)) {
                ProcessProjectWindow editWindow = projectCard.editCard();
                return editWindow.deleteProject();
            }
        }

        return false;
    }

    public boolean deleteProjectByTitle(String projectName) {
        pause(WebConstant.TIME_TO_LOAD_HEAVY_ELEMENT);
        WaitUtil.waitListToLoad(projectCards);
        for (ProjectCard projectCard : projectCards) {
            if (projectCard.getName().equalsIgnoreCase(projectName)) {
                ProcessProjectWindow editWindow = projectCard.editCard();
                return editWindow.deleteProject();
            }
        }
        return false;
    }

    public boolean deleteProjectByNameAndKey(String name, String key) {
        pause(WebConstant.TIME_TO_LOAD_HEAVY_ELEMENT);
        WaitUtil.waitListToLoad(projectCards);
        for (ProjectCard projectCard : projectCards) {
            if (projectCard.getName().equalsIgnoreCase(name) && projectCard.getKey().equalsIgnoreCase(key)) {
                ProcessProjectWindow editWindow = projectCard.editCard();
                return editWindow.deleteProject();
            }
        }
        return false;
    }

    public ProjectCard getCertainProjectCard(String projectKey) {
        pause(WebConstant.TIME_TO_LOAD_HEAVY_ELEMENT);
        WaitUtil.waitListToLoad(projectCards);
        for (ProjectCard projectCard : projectCards) {
            if (projectCard.getKey().equalsIgnoreCase(projectKey)) {
                return projectCard;
            }
        }

        throw new RuntimeException("Can't find project by key");
    }

    public TestRunsPage createProject(String name, String key){
        openNewProjectWindow();
        projectProcessWindow.typeProjectName(name);
        projectProcessWindow.typeProjectKey(key);
        return projectProcessWindow.clickCreateButton();
    }

    public void typeInSearchField(String projectName){
        searchField.type(projectName);
    }
}
