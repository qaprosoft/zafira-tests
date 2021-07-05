package com.qaprosoft.zafira.gui.impl;

import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.gui.base.LogInBase;
import com.qaprosoft.zafira.gui.desktop.component.common.Pagination;
import com.qaprosoft.zafira.gui.desktop.component.common.ProjectsMenu;
import com.qaprosoft.zafira.gui.desktop.component.common.TenantHeader;
import com.qaprosoft.zafira.gui.desktop.component.project.ProcessProjectWindow;
import com.qaprosoft.zafira.gui.desktop.page.tenant.ProjectsPage;
import com.qaprosoft.zafira.gui.desktop.page.tenant.TestRunsPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static com.qaprosoft.zafira.constant.ConfigConstant.PROJECT_NAME_KEY;

public class ProjectPageElementsTest extends LogInBase {

    @AfterMethod
    public void returnToTestRunsPage() {
        TestRunsPage page = header.openProjectsWindow().toProjectByKey(R.TESTDATA.get(PROJECT_NAME_KEY));
        navigationMenu = page.getNavigationMenu();
    }

    @Test
    public void projectsMenuInNavigationBarTest() {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(navigationMenu.getProjectKey(), R.TESTDATA.get(PROJECT_NAME_KEY),
                "Project key differs expected");
        softAssert.assertTrue(navigationMenu.isProjectPhotoPresent());
        softAssert.assertAll();
    }

    @Test
    public void projectsMenuInHeaderTest() {
        TestRunsPage testRunsPage = navigationMenu.toTestRunsPage();
        TenantHeader header = testRunsPage.getHeader();
        ProjectsMenu projectsMenu = header.openProjectsWindow();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(projectsMenu.areProjectsPresent(), "Can't find projects in project menu");
        softAssert.assertTrue(projectsMenu.isEveryProjectHasTitleKeyPhoto(),
                "Some of the projects miss the component (title, key or photo");
        softAssert.assertTrue(projectsMenu.isViewAllProjectsButtonPresent(), "View All Projects Button is not present");
        softAssert.assertTrue(projectsMenu.isCreateNewProjectButtonPresent(), "Create New Project Button is not resent");
        ProcessProjectWindow createProjectWindow = projectsMenu.openNewProjectWindow();
        softAssert.assertTrue(createProjectWindow.isUIObjectPresent(), "Project creation window should present");
        createProjectWindow.closeWindow();
        softAssert.assertAll();
    }

    @Test
    public void projectPageElementsPresenceTest() {
        ProjectsPage projectsPage = navigationMenu.toTestRunsPage().getHeader().openProjectsWindow().toProjectsPage();
        projectsPage.assertPageOpened();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(projectsPage.getTitle(),
                "Projects", "Projects page title differs expected");
        softAssert.assertTrue(projectsPage.isSearchFieldPresent(), "Can't find search field");
        softAssert.assertTrue(projectsPage.isNewProjectButtonPresent(), "Can't find new project button");
        ProcessProjectWindow createProjectWindow = projectsPage.openNewProjectWindow();
        softAssert.assertTrue(createProjectWindow.isUIObjectPresent(), "Can't find project creation window");
        createProjectWindow.closeWindow();
        softAssert.assertTrue(projectsPage.getHeader().isUIObjectPresent(), "Can't find header");
        Pagination pagination = projectsPage.getPagination();
        softAssert.assertEquals(pagination.getNumberOfItemsOnThePage(), projectsPage.getNumberOfProjectCards(),
                "Number of project cards differs to pagination info");
        softAssert.assertAll();
    }

    @Test
    public void createNewProjectWindowElementsPresence() {
        ProjectsPage projectsPage = navigationMenu.toTestRunsPage().getHeader().openProjectsWindow().toProjectsPage();
        ProcessProjectWindow createProjectWindow = projectsPage.openNewProjectWindow();

        SoftAssert softAssert = new SoftAssert();
        String expectedTitle = "Create Project";
        softAssert.assertEquals(createProjectWindow.getTitle(), expectedTitle);
        softAssert.assertTrue(createProjectWindow.isProjectLogoPresent(), "Can't find project logo");
        softAssert.assertTrue(createProjectWindow.isProjectNameFieldPresent(), "Can't find project name field");
        softAssert.assertTrue(createProjectWindow.isProjectPublic(), "By default project should be public");
        softAssert.assertTrue(createProjectWindow.isProjectVisibilitySwitcherPresent(), "Can't find project visibility switcher");
        createProjectWindow.changeProjectVisibility();
        softAssert.assertFalse(createProjectWindow.isProjectPublic(), "After clicking switcher project should become private");
        softAssert.assertTrue(createProjectWindow.isKeyFieldPresent(), "Can't fin project key field");
        softAssert.assertFalse(createProjectWindow.isSubmitButtonActive(),
                "Create button should be inactive because of empty fields");
        softAssert.assertTrue(createProjectWindow.isCloseButtonPresent(), "Can't find close button");
        softAssert.assertTrue(createProjectWindow.isCancelButtonPresent(), "Can't find cancel button");
        createProjectWindow.closeWindow();
        softAssert.assertAll();
    }
}
