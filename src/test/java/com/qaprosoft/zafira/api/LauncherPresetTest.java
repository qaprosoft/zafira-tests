package com.qaprosoft.zafira.api;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.qaprosoft.zafira.api.preset.*;
import org.apache.log4j.Logger;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.manager.APIContextManager;
import com.qaprosoft.zafira.service.impl.JobServiceImpl;
import com.qaprosoft.zafira.service.impl.LauncherServiceImpl;
import com.qaprosoft.zafira.service.impl.PresetServiceImpl;

public class LauncherPresetTest extends ZafiraAPIBaseTest {
    private static final Logger LOGGER = Logger.getLogger(LauncherPresetTest.class);
    private static final int ACCOUNT_TYPE_ID = APIContextManager.SCM_ACCOUNT_TYPE_ID_VALUE;

    @Test
    public void testCreateLauncherPreset() {
      //  int accountTypeId = APIContextManager.SCM_ACCOUNT_TYPE_ID_VALUE;
        int jobId = new JobServiceImpl().create();
        int launcherId = new LauncherServiceImpl().create(jobId, ACCOUNT_TYPE_ID);
        PostLauncherPresetMethod postLauncherPresetMethod = new PostLauncherPresetMethod(launcherId);
        apiExecutor.expectStatus(postLauncherPresetMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postLauncherPresetMethod);
        apiExecutor.validateResponse(postLauncherPresetMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testUpdateLauncherPreset() {
       // int accountTypeId = APIContextManager.SCM_ACCOUNT_TYPE_ID_VALUE;
        int jobId = new JobServiceImpl().create();
        int launcherId = new LauncherServiceImpl().create(jobId, ACCOUNT_TYPE_ID);
        int presetId = new PresetServiceImpl().create(launcherId);
        PutLauncherPresetMethod putLauncherPresetMethod = new PutLauncherPresetMethod(launcherId, presetId);
        apiExecutor.expectStatus(putLauncherPresetMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(putLauncherPresetMethod);
        apiExecutor.validateResponse(putLauncherPresetMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testDeleteLauncherPreset() {
       // int accountTypeId = APIContextManager.SCM_ACCOUNT_TYPE_ID_VALUE;
        int jobId = new JobServiceImpl().create();
        int launcherId = new LauncherServiceImpl().create(jobId, ACCOUNT_TYPE_ID);
        int presetId = new PresetServiceImpl().create(launcherId);
        DeleteLauncherPresetMethod deleteLauncherPresetMethod = new DeleteLauncherPresetMethod(launcherId, presetId);
        apiExecutor.expectStatus(deleteLauncherPresetMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(deleteLauncherPresetMethod);
    }

    @Test
    public void testBuildWebHookUrl() {
     //   int accountTypeId = APIContextManager.SCM_ACCOUNT_TYPE_ID_VALUE;
        int jobId = new JobServiceImpl().create();
        int launcherId = new LauncherServiceImpl().create(jobId, ACCOUNT_TYPE_ID);
        int presetId = new PresetServiceImpl().create(launcherId);

        GetLauncherWebHookMethod getLauncherWebhookMethod = new GetLauncherWebHookMethod(launcherId, presetId);
        apiExecutor.expectStatus(getLauncherWebhookMethod, HTTPStatusCodeType.OK);
        String response = apiExecutor.callApiMethod(getLauncherWebhookMethod);
        Pattern pattern = Pattern.compile("\\w{20}");
        Matcher matcher = pattern.matcher(response);
        Assert.assertTrue(matcher.find(), "Response was not validated!");
    }

    @Test
    public void testDeleteWebhookUrlInLauncherPreset() {

        int jobId = new JobServiceImpl().create();
        int launcherId = new LauncherServiceImpl().create(jobId, ACCOUNT_TYPE_ID);
        int presetId = new PresetServiceImpl().create(launcherId);
        String hook = new PresetServiceImpl().getWebhookUrl(launcherId, presetId)
                .replace("https://automation.qaprosoft.farm/reporting-service/api/launchers/hooks/", "");
        DeleteWebhookUrlLauncherPresetMethod deleteWebhookUrlLauncherPresetMethod = new DeleteWebhookUrlLauncherPresetMethod(launcherId, presetId, hook);
        apiExecutor.expectStatus(deleteWebhookUrlLauncherPresetMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(deleteWebhookUrlLauncherPresetMethod);
    }

    @AfterTest
    private void deleteAllLaunchers() {
        LauncherServiceImpl launcherService = new LauncherServiceImpl();
        List<Integer> ids = launcherService.getAll();
        LOGGER.info(String.format("IDs to delete: %s", ids.toString()));
        ids.forEach(launcherService::deleteById);
    }
}
