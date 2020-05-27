package com.qaprosoft.zafira.api;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.preset.GetLauncherWebHookMethod;
import com.qaprosoft.zafira.api.preset.PostLauncherPresetMethod;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.manager.APIContextManager;
import com.qaprosoft.zafira.service.impl.JobServiceImpl;
import com.qaprosoft.zafira.service.impl.LauncherServiceImpl;
import com.qaprosoft.zafira.service.impl.PresetServiceImpl;

public class LauncherPresetTest extends ZafiraAPIBaseTest {
    private static final Logger LOGGER = Logger.getLogger(LauncherPresetTest.class);

    @Test
    public void testCreateLauncherPreset() {
        int accountTypeId = APIContextManager.SCM_ACCOUNT_TYPE_ID_VALUE;
        int jobId = new JobServiceImpl().create();
        int launcherId = new LauncherServiceImpl().create(jobId, accountTypeId);

        PostLauncherPresetMethod postLauncherPresetMethod = new PostLauncherPresetMethod(launcherId);
        apiExecutor.expectStatus(postLauncherPresetMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postLauncherPresetMethod);
        apiExecutor.validateResponse(postLauncherPresetMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testBuildWebHookUrl() {
        int accountTypeId = APIContextManager.SCM_ACCOUNT_TYPE_ID_VALUE;
        int jobId = new JobServiceImpl().create();
        int launcherId = new LauncherServiceImpl().create(jobId, accountTypeId);
        int presetId = new PresetServiceImpl().create(launcherId);

        GetLauncherWebHookMethod getLauncherWebhookMethod = new GetLauncherWebHookMethod(launcherId, presetId);
        apiExecutor.expectStatus(getLauncherWebhookMethod, HTTPStatusCodeType.OK);
        String response = apiExecutor.callApiMethod(getLauncherWebhookMethod);
        Pattern pattern = Pattern.compile("\\w{20}");
        Matcher matcher = pattern.matcher(response);
        Assert.assertTrue(matcher.find(), "Response was not validated!");
    }

    @AfterTest
    private void deleteAllLaunchers() {
        LauncherServiceImpl launcherService = new LauncherServiceImpl();
        List<Integer> ids = launcherService.getAll();
        LOGGER.info(String.format("IDs to delete: %s", ids.toString()));
        ids.forEach(launcherService::deleteById);
    }
}
