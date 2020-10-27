package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.settingsController.GetCompanyLogoURLMethod;
import com.qaprosoft.zafira.api.settingsController.GetSettingsByToolMethod;
import com.qaprosoft.zafira.api.settingsController.PutUpdateSettingsMethod;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import org.apache.commons.lang3.RandomStringUtils;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.annotations.Test;

public class SettingsControllerTest extends ZafiraAPIBaseTest {
    private static final String TOOL = "ZEBRUNNER";

    @Test
    public void testGetCompanyLogoURL() {
        GetCompanyLogoURLMethod getCompanyLogoURLMethod = new GetCompanyLogoURLMethod();
        apiExecutor.expectStatus(getCompanyLogoURLMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getCompanyLogoURLMethod);
        apiExecutor.validateResponse(getCompanyLogoURLMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testGetSettingsByTool() {
        GetSettingsByToolMethod getSettingsByToolMethod = new GetSettingsByToolMethod(TOOL);
        apiExecutor.expectStatus(getSettingsByToolMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getSettingsByToolMethod);
        apiExecutor.validateResponse(getSettingsByToolMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testUpdateSettings() {
        String newName = "TEST_NAME".concat(RandomStringUtils.randomAlphabetic(10));
        PutUpdateSettingsMethod putUpdateSettingsMethod = new PutUpdateSettingsMethod(newName);
        apiExecutor.expectStatus(putUpdateSettingsMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(putUpdateSettingsMethod);
        apiExecutor.validateResponse(putUpdateSettingsMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }
}
