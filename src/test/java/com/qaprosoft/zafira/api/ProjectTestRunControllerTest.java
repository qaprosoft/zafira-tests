package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.projectTestRuns.GetProjectTestRunByCiRunIdMethod;
import com.qaprosoft.zafira.api.projectTestRuns.GetProjectTestRunByTestRunIdMethod;
import com.qaprosoft.zafira.api.projectTestRuns.GetSearchProjectTestRunMethod;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.ProjectV1ServiceImpl;
import com.qaprosoft.zafira.service.impl.TestRunServiceAPIImplV1;
import com.zebrunner.agent.core.annotation.Maintainer;
import org.apache.commons.lang3.RandomStringUtils;
import org.skyscreamer.jsonassert.JSONCompareMode;
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
        testRunServiceAPIImplV1.deleteTestRun(testRunId);
    }

    @Test
    public void getTestRunByCiRunId() {
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
    public void getTestRunByCiRunIdWithNonExistentCiRunId() {
        String ciRunId = RandomStringUtils.randomAlphabetic(5);
        GetProjectTestRunByCiRunIdMethod getProjectTestRunByCiRunIdMethod =
                new GetProjectTestRunByCiRunIdMethod(ciRunId);
        apiExecutor.expectStatus(getProjectTestRunByCiRunIdMethod, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(getProjectTestRunByCiRunIdMethod);
    }

    @Test(description = "negative")
    public void getTestRunByCiRunIdWithoutQueryParams() {
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
    public void getTestRunByTestRunId() {
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
    public void getTestRunByNonexistentTestRunId() {
        testRunId = testRunServiceAPIImplV1.start();
        testRunServiceAPIImplV1.deleteTestRun(testRunId);
        GetProjectTestRunByTestRunIdMethod projectTestRunByTestRunIdMethod =
                new GetProjectTestRunByTestRunIdMethod(testRunId);
        apiExecutor.expectStatus(projectTestRunByTestRunIdMethod, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(projectTestRunByTestRunIdMethod);
    }

    @Test()
    public void searchProjectTestRuns() {
        String projectKey = projectV1Service.getProjectKeyById(projectId);
        testRunId = testRunServiceAPIImplV1.start(projectKey);
        GetSearchProjectTestRunMethod getSearchProjectTestRunMethod =
                new GetSearchProjectTestRunMethod(projectId);
        apiExecutor.expectStatus(getSearchProjectTestRunMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getSearchProjectTestRunMethod);
        apiExecutor.validateResponse(getSearchProjectTestRunMethod, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey() + "results");
    }
}
