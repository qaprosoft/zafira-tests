package com.qaprosoft.zafira.gui.impl;

import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.gui.base.SignIn;
import com.qaprosoft.zafira.gui.desktop.component.common.HelpMenu;
import com.qaprosoft.zafira.gui.desktop.component.common.Pagination;
import com.qaprosoft.zafira.gui.desktop.component.common.ProjectsMenu;
import com.qaprosoft.zafira.gui.desktop.component.common.TenantHeader;
import com.qaprosoft.zafira.gui.desktop.component.project.ProjectProcessWindow;
import com.qaprosoft.zafira.gui.desktop.page.tenant.ProjectsPage;
import com.qaprosoft.zafira.gui.desktop.page.tenant.TestRunsPage;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static com.qaprosoft.zafira.constant.ConfigConstant.PROJECT_NAME_KEY;

public class ProjectsTest extends SignIn {

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
        softAssert.assertAll();
    }

    @Test
    public void projectPageElementsPresenceTest(){
        ProjectsPage projectsPage = navigationMenu.toTestRunsPage().getHeader().openProjectsWindow().toProjectsPage();
        projectsPage.assertPageOpened();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(projectsPage.getTitle(),
                "Projects", "Projects page title differs expected");
        softAssert.assertTrue(projectsPage.isSearchFieldPresent(), "Can't find search field");
        softAssert.assertTrue(projectsPage.isNewProjectButtonPresent(), "Can't find new project button");
        ProjectProcessWindow createProjectWindow = projectsPage.openNewProjectWindow();
        softAssert.assertTrue(createProjectWindow.isUIObjectPresent(), "Can't find project creation window");
        createProjectWindow.closeWindow();
        softAssert.assertTrue(projectsPage.getHeader().isUIObjectPresent(), "Can't find header");
        Pagination pagination = projectsPage.getPagination();
        softAssert.assertEquals(pagination.getNumberOfItemsOnThePage(), projectsPage.getNumberOfProjectCards(),
                "Number of project cards differs to pagination info");
//        HelpMenu helpMenu = projectsPage.openHelpWindow();
//        softAssert.assertTrue(helpMenu.isUIObjectPresent(), "Can't find help window");
//        helpMenu.closeWindow();
        softAssert.assertAll();
    }
}
