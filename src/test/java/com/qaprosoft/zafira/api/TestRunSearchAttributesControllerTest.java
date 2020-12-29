package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.testRunSearchAttributesController.*;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.annotations.Test;


public class TestRunSearchAttributesControllerTest extends ZafiraAPIBaseTest {

    @Test
    public void testGetAllTestRunConfigBrowsers() {
        GetAllTestRunConfigBrowsersMethod getAllTestRunConfigBrowsersMethod = new GetAllTestRunConfigBrowsersMethod();
        apiExecutor.expectStatus(getAllTestRunConfigBrowsersMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getAllTestRunConfigBrowsersMethod);
        apiExecutor.validateResponse(getAllTestRunConfigBrowsersMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testGetAllTestRunEnvironments() {
        GetAllTestRunEnvironmentsMethod getAllTestRunEnvironmentsMethod = new GetAllTestRunEnvironmentsMethod();
        apiExecutor.expectStatus(getAllTestRunEnvironmentsMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getAllTestRunEnvironmentsMethod);
        apiExecutor.validateResponse(getAllTestRunEnvironmentsMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testGetAllTestRunConfigLocales() {
        GetAllTestRunConfigLocalesMethod getAllTestRunConfigLocalesMethod = new GetAllTestRunConfigLocalesMethod();
        apiExecutor.expectStatus(getAllTestRunConfigLocalesMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getAllTestRunConfigLocalesMethod);
        apiExecutor.validateResponse(getAllTestRunConfigLocalesMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testGetAllTestRunConfigPlatforms() {
        GetAllTestRunConfigPlatformsMethod getAllTestRunConfigPlatformsMethod = new GetAllTestRunConfigPlatformsMethod();
        apiExecutor.expectStatus(getAllTestRunConfigPlatformsMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getAllTestRunConfigPlatformsMethod);
        apiExecutor.validateResponse(getAllTestRunConfigPlatformsMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testGetFilterValues() {
        GetFilterValuesMethod getFilterValuesMethod = new GetFilterValuesMethod();
        apiExecutor.expectStatus(getFilterValuesMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getFilterValuesMethod);
        apiExecutor.validateResponse(getFilterValuesMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }
}
