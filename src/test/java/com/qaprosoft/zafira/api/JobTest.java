package com.qaprosoft.zafira.api;

import io.restassured.path.json.JsonPath;
import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.jobController.GetAllJobsMethod;
import com.qaprosoft.zafira.api.jobController.PostJobByJenkinsJobURLMethod;
import com.qaprosoft.zafira.api.jobController.PostJobMethod;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.JobServiceImpl;
import org.apache.log4j.Logger;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class JobTest extends ZafiraAPIBaseTest {
    private static final Logger LOGGER = Logger.getLogger(JobTest.class);

    @Test
    public void testCreateJob() {
        PostJobMethod postJobMethod = new PostJobMethod();
        apiExecutor.expectStatus(postJobMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postJobMethod);
        apiExecutor.validateResponse(postJobMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test(enabled = false)
    public void testGetAllJobs() {
        int jobId = new JobServiceImpl().create();
        GetAllJobsMethod getAllJobsMethod = new GetAllJobsMethod();
        apiExecutor.expectStatus(getAllJobsMethod, HTTPStatusCodeType.OK);
        String response = apiExecutor.callApiMethod(getAllJobsMethod);
        apiExecutor.validateResponse(getAllJobsMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        List<Integer> allJobsId = JsonPath.from(response).getList(JSONConstant.ID_KEY);
        LOGGER.info(allJobsId);
        Assert.assertTrue(allJobsId.contains(jobId), "JobId is not found");
    }

    @Test(enabled = false)
    public void testCreateJobByJenkinsJobURL() {
        PostJobByJenkinsJobURLMethod postJobByJenkinsJobURLMethod = new PostJobByJenkinsJobURLMethod();
        apiExecutor.expectStatus(postJobByJenkinsJobURLMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postJobByJenkinsJobURLMethod);
        apiExecutor.validateResponse(postJobByJenkinsJobURLMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }
}
