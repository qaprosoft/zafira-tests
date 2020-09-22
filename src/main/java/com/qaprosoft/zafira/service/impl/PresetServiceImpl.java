package com.qaprosoft.zafira.service.impl;

import org.apache.log4j.Logger;

import com.jayway.restassured.path.json.JsonPath;
import com.qaprosoft.zafira.api.preset.GetLauncherWebHookMethod;
import com.qaprosoft.zafira.api.preset.PostLauncherPresetMethod;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.service.PresetService;

public class PresetServiceImpl implements PresetService {
    private static final Logger LOGGER = Logger.getLogger(PresetServiceImpl.class);
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

    public ExecutionServiceImpl getExecutor() {
        return apiExecutor;
    }

    @Override
    public int create(int launcherId) {
        String postPresetRs = apiExecutor.callApiMethod(new PostLauncherPresetMethod(launcherId));
        return JsonPath.from(postPresetRs).getInt(JSONConstant.ID_KEY);
    }

    @Override
    public String getWebhookUrl(int launcherId, int presetId) {
        GetLauncherWebHookMethod getLauncherWebhookMethod = new GetLauncherWebHookMethod(launcherId, presetId);
        String webHookUrl = apiExecutor.callApiMethod(getLauncherWebhookMethod);
        LOGGER.info(String.format("WebHookUrl = %s", webHookUrl));
        return webHookUrl;
    }
}
