package com.qaprosoft.zafira.api;

import com.jayway.restassured.path.json.JsonPath;
import com.qaprosoft.apitools.validation.JsonCompareKeywords;
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

    @Test
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
}
