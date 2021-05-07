package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.projectDashbords.*;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.constant.TestRailConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.ProjectDashboardServiceImpl;
import com.qaprosoft.zafira.service.impl.ProjectV1ServiceImpl;
import com.zebrunner.agent.core.annotation.TestLabel;
import io.restassured.path.json.JsonPath;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class ProjectDashboardTest extends ZafiraAPIBaseTest {
    private static Logger LOGGER = Logger.getLogger(ProjectSwitchTest.class);
    private static ProjectV1ServiceImpl projectV1Service = new ProjectV1ServiceImpl();
    private static ProjectDashboardServiceImpl projectDashboardService = new ProjectDashboardServiceImpl();
    private static int dashboardId;
    private static int projectId = 1;

    //    @BeforeTest
//    public void testCreateProject() {
//        projectId = new ProjectV1ServiceImpl().createProject();
//    }
//
//    @AfterTest
//    public void testDeleteProject() {
//        new ProjectV1ServiceImpl().deleteProjectById(projectId);
//    }

    @AfterMethod
    public void testDashboard() {
        projectDashboardService.deleteDashboardById(dashboardId);
    }

    @Test
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40871")
    public void testCreateProjectDashboard() {
        String dashboardName = "Dash_Name_".concat(RandomStringUtils.randomAlphabetic(6));

        PostProjectDashboard postProjectDashboard =
                new PostProjectDashboard(projectId, dashboardName);
        apiExecutor.expectStatus(postProjectDashboard, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postProjectDashboard);
        dashboardId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        LOGGER.info("DashboardId: " + dashboardId);
        apiExecutor.validateResponse(postProjectDashboard, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());

        Assert.assertTrue(projectDashboardService.gelAllDashboardIds(projectId).contains(dashboardId));
    }

    @Test(enabled = false)
    public void testCreateProjectDashboardWithEmptyName() {
        PostProjectDashboard postProjectDashboard =
                new PostProjectDashboard(projectId, "");
        apiExecutor.expectStatus(postProjectDashboard, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postProjectDashboard);
    }

    @Test(enabled = false)
    public void testCreateProjectDashboardWithoutTitleInRq() {
        PostProjectDashboard postProjectDashboard =
                new PostProjectDashboard(projectId, "WithoutName");
        postProjectDashboard.removeProperty("title");
        apiExecutor.expectStatus(postProjectDashboard, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postProjectDashboard);
    }

    @Test(enabled = false)
    public void testCreateProjectDashboardWithoutProjectIdInRq() {
        PostProjectDashboard postProjectDashboard =
                new PostProjectDashboard(projectId, "WithoutProjectId");
        postProjectDashboard.removeProperty("projectId");
        apiExecutor.expectStatus(postProjectDashboard, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postProjectDashboard);
    }

    @Test(enabled = false)
    public void testCreateProjectDashboardWitEmptyRq() {
        PostProjectDashboard postProjectDashboard =
                new PostProjectDashboard(projectId, "WithoutName");
        postProjectDashboard.setRequestTemplate(R.TESTDATA.get(ConfigConstant.EMPTY_RQ_PATH));
        apiExecutor.expectStatus(postProjectDashboard, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postProjectDashboard);
    }

    @Test
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40870")
    public void testSearchProjectDashboard() {
        GetSearchProjectDashboards searchProjectDashboards =
                new GetSearchProjectDashboards(projectId);
        apiExecutor.expectStatus(searchProjectDashboards, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(searchProjectDashboards);
        apiExecutor.validateResponse(searchProjectDashboards, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey() + "results");
    }

    @Test
    public void testSearchProjectDashboardWithNonexistentId() {
        GetSearchProjectDashboards searchProjectDashboards =
                new GetSearchProjectDashboards(projectId * (-1));
        apiExecutor.expectStatus(searchProjectDashboards, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(searchProjectDashboards);
        apiExecutor.validateResponse(searchProjectDashboards, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey() + "results");
    }

    @Test
    //  @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40870")
    public void testGetProjectDashboardById() {
        String dashboardName = "Dash_Name_".concat(RandomStringUtils.randomAlphabetic(6));
        dashboardId = projectDashboardService.createDashboard(projectId, dashboardName);

        GetProjectDashboardById getProjectDashboardById =
                new GetProjectDashboardById(dashboardId);
        apiExecutor.expectStatus(getProjectDashboardById, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getProjectDashboardById);
        apiExecutor.validateResponse(getProjectDashboardById, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testGetProjectDashboardByIdWithNonexistentId() {
        String dashboardName = "Dash_Name_".concat(RandomStringUtils.randomAlphabetic(6));
        dashboardId = projectDashboardService.createDashboard(projectId, dashboardName);
        projectDashboardService.deleteDashboardById(dashboardId);

        GetProjectDashboardById getProjectDashboardById =
                new GetProjectDashboardById(dashboardId);
        apiExecutor.expectStatus(getProjectDashboardById, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(getProjectDashboardById);
    }

    @Test
    //  @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40870")
    public void testDeleteProjectDashboardById() {
        String dashboardName = "Dash_Name_".concat(RandomStringUtils.randomAlphabetic(6));
        dashboardId = projectDashboardService.createDashboard(projectId, dashboardName);

        DeleteProjectDashboardById getProjectDashboardById =
                new DeleteProjectDashboardById(dashboardId);
        apiExecutor.expectStatus(getProjectDashboardById, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getProjectDashboardById);
        Assert.assertFalse(projectDashboardService.gelAllDashboardIds(projectId).contains(dashboardId));
    }

    @Test
    public void testDeleteProjectDashboardByIdWithNonexistentId() {
        String dashboardName = "Dash_Name_".concat(RandomStringUtils.randomAlphabetic(6));
        dashboardId = projectDashboardService.createDashboard(projectId, dashboardName);
        projectDashboardService.deleteDashboardById(dashboardId);

        DeleteProjectDashboardById getProjectDashboardById =
                new DeleteProjectDashboardById(dashboardId);
        apiExecutor.expectStatus(getProjectDashboardById, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(getProjectDashboardById);
    }

    public @Test
    void testUpdateProjectDashboardById() {
        String dashboardName = "Dash_Name_".concat(RandomStringUtils.randomAlphabetic(6));
        String newTitle = "New_".concat(dashboardName);
        dashboardId = projectDashboardService.createDashboard(projectId, dashboardName);

        PutProjectDashboard putProjectDashboard =
                new PutProjectDashboard(projectId, dashboardId, newTitle);
        apiExecutor.expectStatus(putProjectDashboard, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(putProjectDashboard);
        apiExecutor.validateResponse(putProjectDashboard, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }
}
