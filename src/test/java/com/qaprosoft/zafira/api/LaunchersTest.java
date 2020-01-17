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
        APIContextManager manager = new APIContextManager();
        String token = manager.getAccessToken();
        List<Integer> ids = launcherService.getAll(token);
        LOGGER.info(String.format("IDs to delete: %s", ids.toString()));
        ids.forEach(id -> launcherService.deleteById(token, id));
    }

    @Test
    public void testGetAllLaunchers() {
        APIContextManager manager = new APIContextManager();
        String token = manager.getAccessToken();
        int autoServerId = APIContextManager.AUTHOMATION_SERVER_ID_VALUE;
        int accountTypeId = APIContextManager.SCM_ACCOUNT_TYPE_ID_VALUE;
        new LauncherServiceImpl().create(token, autoServerId, accountTypeId);
        apiExecutor.callApiMethod(new GetAllLaunchersMethod(token), HTTPStatusCodeType.OK, true, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testCreateLauncher() {
        APIContextManager manager = new APIContextManager();
        String token = manager.getAccessToken();
        int jobId = new JobServiceImpl().create(token);
        int accountTypeId = APIContextManager.SCM_ACCOUNT_TYPE_ID_VALUE;
        apiExecutor.callApiMethod(new PostLauncherMethod(token, jobId, accountTypeId), HTTPStatusCodeType.OK,
                true, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testGetLauncherById() {
        APIContextManager manager = new APIContextManager();
        String token = manager.getAccessToken();
        int autoServerId = APIContextManager.AUTHOMATION_SERVER_ID_VALUE;
        int accountTypeId = APIContextManager.SCM_ACCOUNT_TYPE_ID_VALUE;
        int launcherId = new LauncherServiceImpl().create(token, autoServerId, accountTypeId);
        apiExecutor.callApiMethod(new GetLauncherByIdMethod(token, launcherId), HTTPStatusCodeType.OK, true,
                JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testUpdateLauncher() {
        String expectedTypeValue = R.TESTDATA.get(ConfigConstant.TYPE_KEY);
        APIContextManager manager = new APIContextManager();
        String token = manager.getAccessToken();
        int autoServerId = APIContextManager.AUTHOMATION_SERVER_ID_VALUE;
        int accountTypeId = APIContextManager.SCM_ACCOUNT_TYPE_ID_VALUE;
        int launcherId = new LauncherServiceImpl().create(token, autoServerId, accountTypeId);
        String putLauncherRs = apiExecutor.callApiMethod(new PutLauncherMethod(token, launcherId, expectedTypeValue),
                HTTPStatusCodeType.OK, true, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        String type = JsonPath.from(putLauncherRs).get(JSONConstant.TYPE_RS_KEY);
        Assert.assertEquals(type, expectedTypeValue, "Launcher was not updated!");
    }

    @Test
    public void testDeleteNewLauncher() {
        APIContextManager manager = new APIContextManager();
        LauncherServiceImpl launcherService = new LauncherServiceImpl();
        String token = manager.getAccessToken();
        int autoServerId = APIContextManager.AUTHOMATION_SERVER_ID_VALUE;
        int accountTypeId = APIContextManager.SCM_ACCOUNT_TYPE_ID_VALUE;
        int launcherId = launcherService.create(token, autoServerId, accountTypeId);
        launcherService.deleteById(token, launcherId);
        apiExecutor.callApiMethod(new GetLauncherByIdMethod(token, launcherId), HTTPStatusCodeType.NOT_FOUND, false,
                null);
    }

    @Test
    public void testBuildJobByWebhook() {
        APIContextManager manager = new APIContextManager();
        PresetServiceImpl presetServiceImpl = new PresetServiceImpl();
        String token = manager.getAccessToken();
        int autoServerId = APIContextManager.AUTHOMATION_SERVER_ID_VALUE;
        int accountTypeId = APIContextManager.SCM_ACCOUNT_TYPE_ID_VALUE;
        int launcherId = new LauncherServiceImpl().create(token, autoServerId, accountTypeId);
        int presetId = presetServiceImpl.create(token, launcherId);
        String ref = presetServiceImpl.getWebhookUrl(token, launcherId, presetId);
        apiExecutor.callApiMethod(new PostJobByWebHookMethod(token, launcherId, ref), HTTPStatusCodeType.OK, false,
                null);
    }

    @Test(enabled = false) //TODO: enable this test when jenkins mock container will be up
    public void testBuildJobByLauncher() {
        APIContextManager manager = new APIContextManager();
        String token = manager.getAccessToken();
        int accountTypeId = APIContextManager.SCM_ACCOUNT_TYPE_ID_VALUE;
        apiExecutor.callApiMethod(new PostJobByLauncherMethod(token, accountTypeId), HTTPStatusCodeType.OK, false,
                null);
    }

    @Test(enabled = false) //TODO: enable this test when jenkins mock container will be up
    public void testCreateLauncherFromJenkiins() {
        APIContextManager manager = new APIContextManager();
        String token = manager.getAccessToken();
        apiExecutor.callApiMethod(new PostLauncherFromJenkinsMethod(token), HTTPStatusCodeType.OK, true,
                JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testScanLauncher() {
        APIContextManager manager = new APIContextManager();
        String token = manager.getAccessToken();
        int accountTypeId = APIContextManager.SCM_ACCOUNT_TYPE_ID_VALUE;
        apiExecutor.callApiMethod(new PostScanLaucherMethod(token, accountTypeId), HTTPStatusCodeType.OK, true,
                JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testGetBuildNumber() {
        APIContextManager manager = new APIContextManager();
        String token = manager.getAccessToken();
        int accountTypeId = APIContextManager.SCM_ACCOUNT_TYPE_ID_VALUE;
        String queueItemUrl = new LauncherServiceImpl().getQueueItemUrl(token, accountTypeId);
        apiExecutor.callApiMethod(new GetBuildNumberMethod(token, queueItemUrl), HTTPStatusCodeType.OK, false, null);
    }

    @Test
    public void testCancelLauncherScanner() {
        APIContextManager manager = new APIContextManager();
        LauncherServiceImpl launcherServiceImpl = new LauncherServiceImpl();
        String token = manager.getAccessToken();
        int accountTypeId = APIContextManager.SCM_ACCOUNT_TYPE_ID_VALUE;
        String buildnumber = launcherServiceImpl.getBuildNumber(token,
                launcherServiceImpl.getQueueItemUrl(token, accountTypeId));
        apiExecutor.callApiMethod(new DeleteLauncherScannerMethod(token, buildnumber, accountTypeId),
                HTTPStatusCodeType.OK, false, null);
    }
}