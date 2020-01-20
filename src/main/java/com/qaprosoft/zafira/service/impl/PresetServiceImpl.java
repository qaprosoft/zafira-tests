package com.qaprosoft.zafira.service.impl;

import com.jayway.restassured.path.json.JsonPath;
import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.GetLauncherWebHookMethod;
import com.qaprosoft.zafira.api.PostLauncherPresetMethod;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.PresetService;
import org.apache.log4j.Logger;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        String getPresetRs = apiExecutor.callApiMethod(getLauncherWebhookMethod);
        LOGGER.info(String.format("WebHookUrl = %s", getPresetRs));
        Pattern pattern = Pattern.compile("\\w{20}");
        Matcher matcher = pattern.matcher(getPresetRs);
        String ref = "";
        if (matcher.find()) {
            ref = matcher.group();
            LOGGER.info(String.format("ref = %s", ref));
        }
        return ref;
    }
}
