package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.projectTestRuns.*;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.ProjectV1ServiceImpl;
import com.qaprosoft.zafira.service.impl.ProjectV1TestRunServiceImpl;
import com.qaprosoft.zafira.service.impl.TestRunServiceAPIImplV1;
import com.zebrunner.agent.core.annotation.Maintainer;
import org.apache.commons.lang3.RandomStringUtils;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

@Maintainer("obabich")
public class ProjectTestRunControllerTest extends ZafiraAPIBaseTest {
    private static int projectId;
    private int testRunId;
    private TestRunServiceAPIImplV1 testRunServiceAPIImplV1 = new TestRunServiceAPIImplV1();
    private ProjectV1ServiceImpl projectV1Service = new ProjectV1ServiceImpl();
    private ProjectV1TestRunServiceImpl projectV1TestRunService = new ProjectV1TestRunServiceImpl();


    @BeforeTest
    public void testCreateProject() {
        projectId = projectV1Service.createProject();
    }

    @AfterTest
    public void testDeleteProject() {
        projectV1Service.deleteProjectById(projectId);
    }

    @AfterMethod
    public void deleteTestRun() {
    projectV1TestRunService.deleteProjectTestRun(testRunId);
    }

    @Test
    public void testGetTestRunByCiRunId() {
        String projectKey = projectV1Service.getProjectKeyById(projectId);
        testRunId = testRunServiceAPIImplV1.start(projectKey);
        String ciRunId = testRunServiceAPIImplV1.getCiRunId(testRunId);
        GetProjectTestRunByCiRunIdMethod getProjectTestRunByCiRunIdMethod =
                new GetProjectTestRunByCiRunIdMethod(ciRunId);
        apiExecutor.expectStatus(getProjectTestRunByCiRunIdMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getProjectTestRunByCiRunIdMethod);
        apiExecutor.validateResponse(getProjectTestRunByCiRunIdMethod, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey() + "labels");
    }

    @Test(description = "negative")
    public void testGetTestRunByCiRunIdWithNonExistentCiRunId() {
        String ciRunId = RandomStringUtils.randomAlphabetic(5);
        GetProjectTestRunByCiRunIdMethod getProjectTestRunByCiRunIdMethod =
                new GetProjectTestRunByCiRunIdMethod(ciRunId);
        apiExecutor.expectStatus(getProjectTestRunByCiRunIdMethod, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(getProjectTestRunByCiRunIdMethod);
    }

    @Test(description = "negative")
    public void testGetTestRunByCiRunIdWithoutQueryParams() {
        String ciRunId = RandomStringUtils.randomAlphabetic(5);
        GetProjectTestRunByCiRunIdMethod getProjectTestRunByCiRunIdMethod =
                new GetProjectTestRunByCiRunIdMethod(ciRunId);
        getProjectTestRunByCiRunIdMethod
                .setMethodPath(
                        getProjectTestRunByCiRunIdMethod.getMethodPath()
                                .split("\\?")[0]);
        apiExecutor.expectStatus(getProjectTestRunByCiRunIdMethod, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(getProjectTestRunByCiRunIdMethod);
    }

    @Test
    public void testGetTestRunByTestRunId() {
        String projectKey = projectV1Service.getProjectKeyById(projectId);
        testRunId = testRunServiceAPIImplV1.start(projectKey);
        GetProjectTestRunByTestRunIdMethod projectTestRunByTestRunIdMethod =
                new GetProjectTestRunByTestRunIdMethod(testRunId);
        apiExecutor.expectStatus(projectTestRunByTestRunIdMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(projectTestRunByTestRunIdMethod);
        apiExecutor.validateResponse(projectTestRunByTestRunIdMethod, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testGetTestRunByNonexistentTestRunId() {
        testRunId = testRunServiceAPIImplV1.start();
        testRunServiceAPIImplV1.deleteTestRun(testRunId);
        GetProjectTestRunByTestRunIdMethod projectTestRunByTestRunIdMethod =
                new GetProjectTestRunByTestRunIdMethod(testRunId);
        apiExecutor.expectStatus(projectTestRunByTestRunIdMethod, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(projectTestRunByTestRunIdMethod);
    }

    @Test()
    public void testSearchProjectTestRuns() {
        String projectKey = projectV1Service.getProjectKeyById(projectId);
        testRunId = testRunServiceAPIImplV1.start(projectKey);
        GetSearchProjectTestRunMethod getSearchProjectTestRunMethod =
                new GetSearchProjectTestRunMethod(projectId);
        apiExecutor.expectStatus(getSearchProjectTestRunMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getSearchProjectTestRunMethod);
        apiExecutor.validateResponse(getSearchProjectTestRunMethod, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey() + "results");
    }

    @Test
    public void testDeleteTestRunByTestRunId() {
        String projectKey = projectV1Service.getProjectKeyById(projectId);
        testRunId = testRunServiceAPIImplV1.start(projectKey);
        DeleteProjectTestRunByIdMethod deleteProjectTestRunByIdMethod = new DeleteProjectTestRunByIdMethod(testRunId);
        apiExecutor.expectStatus(deleteProjectTestRunByIdMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(deleteProjectTestRunByIdMethod);
        Assert.assertFalse(projectV1TestRunService.getAllProjectTestRunIds(projectId).contains(testRunId));
    }

    @Test
    public void testDeleteTestRunByTestRunIdWithNonexistentId() {
        String projectKey = projectV1Service.getProjectKeyById(projectId);
        testRunId = testRunServiceAPIImplV1.start(projectKey);
        projectV1TestRunService.deleteProjectTestRun(testRunId);
        DeleteProjectTestRunByIdMethod deleteProjectTestRunByIdMethod = new DeleteProjectTestRunByIdMethod(testRunId);
        apiExecutor.expectStatus(deleteProjectTestRunByIdMethod, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(deleteProjectTestRunByIdMethod);
    }

    @Test(enabled = false)
    public void testDeleteTestRunByTestRunIdWithTestRunIdFromAnotherProject() {
        testRunId = testRunServiceAPIImplV1.start();
        DeleteProjectTestRunByIdMethod deleteProjectTestRunByIdMethod = new DeleteProjectTestRunByIdMethod(29326);
        apiExecutor.expectStatus(deleteProjectTestRunByIdMethod, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(deleteProjectTestRunByIdMethod);
    }

    @Test
    public void testPostProjectIAAnalysis() {
        String projectKey = projectV1Service.getProjectKeyById(projectId);
        testRunId = testRunServiceAPIImplV1.start(projectKey);
        testRunServiceAPIImplV1.finishTestRun(testRunId);
        PostProjectIAAnalysisMethod postProjectIAAnalysisMethod = new PostProjectIAAnalysisMethod(testRunId);
        apiExecutor.expectStatus(postProjectIAAnalysisMethod, HTTPStatusCodeType.ACCEPTED);
        apiExecutor.callApiMethod(postProjectIAAnalysisMethod);
        projectV1TestRunService.getAllProjectTestRunIds(projectId);
    }
}
