package com.qaprosoft.zafira.api;

import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.annotations.Test;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.mock.GetAllJobs;
import com.qaprosoft.zafira.api.mock.GetCreateJob;
import com.qaprosoft.zafira.api.mock.GetCreateJobBuild;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;

public class JenkinsMockTest extends ZafiraAPIBaseTest {

    @Test
    public void testGetAllJobs(){
        GetAllJobs getAllJobs = new GetAllJobs();
        apiExecutor.expectStatus(getAllJobs, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getAllJobs);
        apiExecutor.validateResponse(getAllJobs, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testCreateJob(){
        GetCreateJob getCreateJob = new GetCreateJob(R.TESTDATA.get(ConfigConstant.JENKINS_MOCK_JOBNAME_KEY));
        apiExecutor.expectStatus(getCreateJob, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getCreateJob);
        apiExecutor.validateResponse(getCreateJob, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testCreateJobBuild(){
        GetCreateJobBuild getCreateJobBuild = new GetCreateJobBuild(R.TESTDATA.get(ConfigConstant.JENKINS_MOCK_JOBNAME_KEY));
        apiExecutor.expectStatus(getCreateJobBuild, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getCreateJobBuild);
        apiExecutor.validateResponse(getCreateJobBuild, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }
}
