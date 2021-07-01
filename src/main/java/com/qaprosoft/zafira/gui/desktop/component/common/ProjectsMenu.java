package com.qaprosoft.zafira.gui.desktop.component.common;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import com.qaprosoft.zafira.constant.WebConstant;
import com.qaprosoft.zafira.gui.desktop.component.project.ProcessProjectWindow;
import com.qaprosoft.zafira.gui.desktop.page.tenant.ProjectsPage;
import com.qaprosoft.zafira.gui.desktop.page.tenant.TestRunsPage;
import com.qaprosoft.zafira.util.WaitUtil;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

//div[contains(@class,'md-select-menu-container project-settings__select md-active md-clickable')]
public class ProjectsMenu extends AbstractUIObject {
    @FindBy(xpath = "//md-dialog[contains(@class,'project-modal')]")
    private ProcessProjectWindow createProjectWindow;

    @FindBy(xpath = ".//div[contains(@class,'md-text')]//span[@class='project-settings__item-name ng-binding']")
    private List<ExtendedWebElement> projectTitles;

    @FindBy(xpath = ".//div[contains(@class,'md-text')]//span[@class='project-settings__item-key ng-binding']")
    private List<ExtendedWebElement> projectKeys;

    @FindBy(xpath = ".//div[contains(@class,'md-text')]//span[contains(@class,'profile-photo')]")
    private List<ExtendedWebElement> projectPhotos;

    @FindBy(xpath = ".//div[@class='project-settings__button']")
    private ExtendedWebElement viewAllProjectsButton;

    @FindBy(xpath = ".//span[text()='Create a Project']")
    private ExtendedWebElement createNewProjectButton;

    public ProjectsMenu(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public TestRunsPage toProjectByTitle(String projectName) {
        WaitUtil.waitListToLoad(projectTitles, 1000, 100);
        for (ExtendedWebElement element : projectTitles) {
            if (element.getText().contains(projectName)) {
                element.click();
                return new TestRunsPage(driver);
            }
        }
        throw new RuntimeException("Can't find project title " + projectName);
    }

    public TestRunsPage toProjectByKey(String projectKey) {
        WaitUtil.waitListToLoad(projectKeys, 1000, 100);
        for (ExtendedWebElement element : projectKeys) {
            if (element.getText().contains(projectKey)) {
                element.click();
                return new TestRunsPage(driver);
            }
        }
        throw new RuntimeException("Can't find project title " + projectKey);
    }

    public boolean isCreateNewProjectButtonPresent() {
        return createNewProjectButton.isVisible();
    }

    public boolean isViewAllProjectsButtonPresent() {
        return viewAllProjectsButton.isVisible();
    }

    public boolean areProjectsPresent() {
        WaitUtil.waitListToLoad(projectTitles, 1000, 100);
        if (projectTitles.size() < 1) {
            return false;
        }

        if (projectTitles.size() != projectKeys.size() || projectTitles.size() != projectPhotos.size()) {
            return false;
        }

        return true;
    }

    public boolean isEveryProjectHasTitleKeyPhoto() {
        WaitUtil.waitListToLoad(projectTitles, 1000, 100);
        boolean isHas = true;
        for (int i = 0; i < projectTitles.size() && isHas; i++) {
            isHas = projectKeys.get(i).isVisible() && projectTitles.get(i).isVisible() && projectPhotos.get(i).isVisible();
        }
        return isHas;
    }

    public ProjectsPage toProjectsPage() {
        viewAllProjectsButton.click();
        return new ProjectsPage(driver);
    }

    public ProcessProjectWindow openNewProjectWindow() {
        createNewProjectButton.click();
        pause(WebConstant.TIME_TO_LOAD_PAGE);
        return createProjectWindow;
    }

}
