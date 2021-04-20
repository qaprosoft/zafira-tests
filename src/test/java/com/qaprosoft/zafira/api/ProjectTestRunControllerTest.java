package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.projectTestRuns.GetProjectTestRunByCiRunIdMethod;
import com.qaprosoft.zafira.api.projectTestRuns.GetProjectTestRunByTestRunIdMethod;
import com.qaprosoft.zafira.api.projectTestRuns.GetSearchProjectTestRunMethod;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.TestRunServiceAPIImplV1;
import org.apache.commons.lang3.RandomStringUtils;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class ProjectTestRunControllerTest extends ZafiraAPIBaseTest {

    private int testRunId;
    private TestRunServiceAPIImplV1 testRunServiceAPIImplV1 = new TestRunServiceAPIImplV1();

    @AfterMethod
    public void deleteTestRun() {
        testRunServiceAPIImplV1.deleteTestRun(testRunId);
    }

    @Test
    public void getTestRunByCiRunId() {
        testRunId = testRunServiceAPIImplV1.start();
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
        testRunId = testRunServiceAPIImplV1.start();
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

    @Test(enabled = false)
    public void searchProjectTestRuns() {
        testRunId = testRunServiceAPIImplV1.start();
        GetSearchProjectTestRunMethod getSearchProjectTestRunMethod =
                new GetSearchProjectTestRunMethod(7);
        apiExecutor.expectStatus(getSearchProjectTestRunMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getSearchProjectTestRunMethod);
        apiExecutor.validateResponse(getSearchProjectTestRunMethod, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey() + "results");
    }
}
