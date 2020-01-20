package com.qaprosoft.zafira.api;

import com.jayway.restassured.path.json.JsonPath;
import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.manager.APIContextManager;
import com.qaprosoft.zafira.service.impl.JobServiceImpl;
import com.qaprosoft.zafira.service.impl.LauncherServiceImpl;
import com.qaprosoft.zafira.service.impl.PresetServiceImpl;
import org.apache.log4j.Logger;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.util.List;

public class LaunchersTest extends ZariraAPIBaseTest {
    private static final Logger LOGGER = Logger.getLogger(LaunchersTest.class);

    @AfterTest
    private void deleteAllLaunchers() {
        LauncherServiceImpl launcherService = new LauncherServiceImpl();
        List<Integer> ids = launcherService.getAll();
        LOGGER.info(String.format("IDs to delete: %s", ids.toString()));
        ids.forEach(launcherService::deleteById);
    }

    @Test
    public void testGetAllLaunchers() {
        int autoServerId = APIContextManager.AUTHOMATION_SERVER_ID_VALUE;
        int accountTypeId = APIContextManager.SCM_ACCOUNT_TYPE_ID_VALUE;
        new LauncherServiceImpl().create(autoServerId, accountTypeId);
        apiExecutor.callApiMethod(new GetAllLaunchersMethod(), HTTPStatusCodeType.OK, true, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testCreateLauncher() {
        int jobId = new JobServiceImpl().create();
        int accountTypeId = APIContextManager.SCM_ACCOUNT_TYPE_ID_VALUE;
        apiExecutor.callApiMethod(new PostLauncherMethod(jobId, accountTypeId), HTTPStatusCodeType.OK,
                true, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testGetLauncherById() {
        int accountTypeId = APIContextManager.SCM_ACCOUNT_TYPE_ID_VALUE;
        int jobId = new JobServiceImpl().create();
        int launcherId = new LauncherServiceImpl().create(jobId, accountTypeId);
        apiExecutor.callApiMethod(new GetLauncherByIdMethod(launcherId), HTTPStatusCodeType.OK, true,
                JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testUpdateLauncher() {
        String expectedTypeValue = R.TESTDATA.get(ConfigConstant.TYPE_KEY);
        int accountTypeId = APIContextManager.SCM_ACCOUNT_TYPE_ID_VALUE;
        int jobId = new JobServiceImpl().create();
        int launcherId = new LauncherServiceImpl().create(jobId, accountTypeId);
        String putLauncherRs = apiExecutor.callApiMethod(new PutLauncherMethod(launcherId, expectedTypeValue),
                HTTPStatusCodeType.OK, true, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        String type = JsonPath.from(putLauncherRs).get(JSONConstant.TYPE_RS_KEY);
        Assert.assertEquals(type, expectedTypeValue, "Launcher was not updated!");
    }

    @Test
    public void testDeleteNewLauncher() {
        LauncherServiceImpl launcherService = new LauncherServiceImpl();
        int accountTypeId = APIContextManager.SCM_ACCOUNT_TYPE_ID_VALUE;
        int jobId = new JobServiceImpl().create();
        int launcherId = new LauncherServiceImpl().create(jobId, accountTypeId);
        launcherService.deleteById(launcherId);
        apiExecutor.callApiMethod(new GetLauncherByIdMethod(launcherId), HTTPStatusCodeType.NOT_FOUND, false,
                null);
    }

    @Test(enabled = false) //TODO: enable this test when jenkins mock container will be up
    public void testBuildJobByWebhook() {
        PresetServiceImpl presetServiceImpl = new PresetServiceImpl();
        int accountTypeId = APIContextManager.SCM_ACCOUNT_TYPE_ID_VALUE;
        int jobId = new JobServiceImpl().create();
        int launcherId = new LauncherServiceImpl().create(jobId, accountTypeId);
        int presetId = presetServiceImpl.create(launcherId);
        String ref = presetServiceImpl.getWebhookUrl(launcherId, presetId);
        apiExecutor.callApiMethod(new PostJobByWebHookMethod(launcherId, ref), HTTPStatusCodeType.OK, false,
                null);
    }

    @Test(enabled = false) //TODO: enable this test when jenkins mock container will be up
    public void testBuildJobByLauncher() {
        int accountTypeId = APIContextManager.SCM_ACCOUNT_TYPE_ID_VALUE;
        apiExecutor.callApiMethod(new PostJobByLauncherMethod(accountTypeId), HTTPStatusCodeType.OK, false,
                null);
    }

    @Test(enabled = false) //TODO: enable this test when jenkins mock container will be up
    public void testCreateLauncherFromJenkiins() {
        apiExecutor.callApiMethod(new PostLauncherFromJenkinsMethod(), HTTPStatusCodeType.OK, true,
                JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test(enabled = false) //TODO: enable this test when jenkins mock container will be up
    public void testScanLauncher() {
        int accountTypeId = APIContextManager.SCM_ACCOUNT_TYPE_ID_VALUE;
        apiExecutor.callApiMethod(new PostScanLaucherMethod(accountTypeId), HTTPStatusCodeType.OK, true,
                JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test(enabled = false) //TODO: enable this test when jenkins mock container will be up
    public void testGetBuildNumber() {
        int accountTypeId = APIContextManager.SCM_ACCOUNT_TYPE_ID_VALUE;
        String queueItemUrl = new LauncherServiceImpl().getQueueItemUrl(accountTypeId);
        apiExecutor.callApiMethod(new GetBuildNumberMethod(queueItemUrl), HTTPStatusCodeType.OK, false, null);
    }

    @Test(enabled = false) //TODO: enable this test when jenkins mock container will be up
    public void testCancelLauncherScanner() {
        LauncherServiceImpl launcherServiceImpl = new LauncherServiceImpl();
        int accountTypeId = APIContextManager.SCM_ACCOUNT_TYPE_ID_VALUE;
        String buildnumber = launcherServiceImpl.getBuildNumber(launcherServiceImpl.getQueueItemUrl(accountTypeId));
        apiExecutor.callApiMethod(new DeleteLauncherScannerMethod(buildnumber, accountTypeId), HTTPStatusCodeType.OK, false, null);
    }
}