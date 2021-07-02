package com.qaprosoft.zafira.gui.impl;

import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.constant.WebConstant;
import com.qaprosoft.zafira.gui.base.LogInBase;
import com.qaprosoft.zafira.gui.desktop.component.common.NavigationMenu;
import com.qaprosoft.zafira.gui.desktop.component.project.ProcessProjectWindow;
import com.qaprosoft.zafira.gui.desktop.page.tenant.ProjectsPage;
import com.qaprosoft.zafira.gui.desktop.page.tenant.TestRunsPage;
import org.apache.commons.lang.RandomStringUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static com.qaprosoft.zafira.constant.ConfigConstant.PROJECT_NAME_KEY;

public class ProjectProcessTest extends LogInBase {
    private String projectKey = "";

    @AfterMethod(onlyForGroups = "add-edit-search")
    public void deleteCreatedProject() {
        ProjectsPage projectsPage = header.openProjectsWindow().toProjectsPage();
        projectsPage.deleteProjectByKey(projectKey);

        SoftAssert softAssert = new SoftAssert();
        pause(WebConstant.TIME_TO_LOAD_PAGE);
        softAssert.assertFalse(projectsPage.isProjectWithKeyExists(projectKey), "Project was not deleted");
        TestRunsPage page = projectsPage.toProjectByKey(R.TESTDATA.get(PROJECT_NAME_KEY));
        navigationMenu = page.getNavigationMenu();
        softAssert.assertAll();
    }

    @Test(groups = "add-edit-search")
    public void newProjectCreationFromProjectPageTest() {
        String projectName = "Automation".concat(RandomStringUtils.randomAlphabetic(5));
        projectKey = ("aut".concat(RandomStringUtils.randomAlphabetic(3))).toUpperCase();

        ProjectsPage projectsPage = header.openProjectsWindow().toProjectsPage();
        ProcessProjectWindow createProjectWindow = projectsPage.openNewProjectWindow();

        SoftAssert softAssert = new SoftAssert();
        createProjectWindow.typeProjectName(projectName);
        softAssert.assertFalse(createProjectWindow.isSubmitButtonActive(),
                "Save button should be inactive because of one empty field");
        createProjectWindow.typeProjectKey(projectKey);
        softAssert.assertTrue(createProjectWindow.isSubmitButtonActive(),
                "All fields are filled, save button should be active");
        pause(WebConstant.TIME_TO_LOAD_PAGE);

        TestRunsPage testRunsPage = createProjectWindow.clickCreateButton();
        projectsPage = testRunsPage.getNavigationMenu().toTestRunsPage().getHeader().openProjectsWindow().toProjectsPage();
        softAssert.assertTrue(projectsPage.isProjectWithNameAndKeyExists(projectName, projectKey),
                "Can't find project card with name " + projectName + ", and key " + projectKey);
        softAssert.assertAll();
    }

    @Test(groups = "add-edit-search")
    public void newProjectCreationFromHeaderTest() {
        String projectName = "Automation".concat(RandomStringUtils.randomAlphabetic(5));
        projectKey = ("aut".concat(RandomStringUtils.randomAlphabetic(3))).toUpperCase();

        ProcessProjectWindow createProjectWindow = header.openProjectsWindow().openNewProjectWindow();

        SoftAssert softAssert = new SoftAssert();
        createProjectWindow.typeProjectName(projectName);
        softAssert.assertFalse(createProjectWindow.isSubmitButtonActive(),
                "Save button should be inactive because of one empty field");
        createProjectWindow.typeProjectKey(projectKey);
        softAssert.assertTrue(createProjectWindow.isSubmitButtonActive(),
                "All fields are filled, save button should be active");
        TestRunsPage testRunsPage = createProjectWindow.clickCreateButton();
        NavigationMenu navigationMenu = testRunsPage.getNavigationMenu();
        softAssert.assertTrue(navigationMenu.waitUntilProjectKeyToBE(projectKey), "Navigation bar should have new key " + projectKey);
        ProjectsPage projectsPage = header.openProjectsWindow().toProjectsPage();
        softAssert.assertTrue(projectsPage.isProjectWithNameAndKeyExists(projectName, projectKey),
                "Can't find project card with name " + projectName + ", and key " + projectKey);
        softAssert.assertAll();
    }


}
