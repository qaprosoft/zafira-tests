package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.projectDashbords.GetSearchProjectDashboards;
import com.qaprosoft.zafira.api.projectDashbords.PostProjectDashboard;
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
import org.testng.annotations.Test;

public class ProjectDashboardTest extends ZafiraAPIBaseTest {
    private static Logger LOGGER = Logger.getLogger(ProjectSwitchTest.class);
    private static ProjectV1ServiceImpl projectV1Service = new ProjectV1ServiceImpl();
    private static ProjectDashboardServiceImpl projectDashboardService = new ProjectDashboardServiceImpl();
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

    @Test
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40871")
    public void testCreateProjectDashboard() {
        String dashboardName = "Dash_Name_".concat(RandomStringUtils.randomAlphabetic(6));

        PostProjectDashboard postProjectDashboard =
                new PostProjectDashboard(projectId, dashboardName);
        apiExecutor.expectStatus(postProjectDashboard, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postProjectDashboard);
        int dashboardId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        LOGGER.info("DashboardId: " + dashboardId);
        apiExecutor.validateResponse(postProjectDashboard, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());

        Assert.assertTrue(projectDashboardService.gelAllDashboardIds(projectId).contains(dashboardId));
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
}
