package com.qaprosoft.zafira.api;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.jayway.restassured.path.json.JsonPath;
import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.widget.*;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.WidgetServiceImpl;

public class WidgetTest extends ZafiraAPIBaseTest {

    @Test
    public void testGetAllWidgetTemplates() {
        GetAllWidgetTemplatesMethod getAllWidgetTemplatesMethod = new GetAllWidgetTemplatesMethod();
        apiExecutor.expectStatus(getAllWidgetTemplatesMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getAllWidgetTemplatesMethod);
        apiExecutor.validateResponse(getAllWidgetTemplatesMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testGetAllWidget() {
        GetAllWidgetMethod getAllWidgetMethod = new GetAllWidgetMethod();
        apiExecutor.expectStatus(getAllWidgetMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getAllWidgetMethod);
        apiExecutor.validateResponse(getAllWidgetMethod, JSONCompareMode.STRICT_ORDER, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testCreateWidget() {
        String widgetdName = "TestWidget_".concat(RandomStringUtils.randomAlphabetic(15));
        PostWidgetMethod postWidgetMethod = new PostWidgetMethod(widgetdName);
        apiExecutor.expectStatus(postWidgetMethod, HTTPStatusCodeType.OK);
        String response = apiExecutor.callApiMethod(postWidgetMethod);
        apiExecutor.validateResponse(postWidgetMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        int widgetId = JsonPath.from(response).get(JSONConstant.ID_KEY);
        WidgetServiceImpl widgetService = new WidgetServiceImpl();
        List<Integer> allWidgetIds = widgetService.getAllWidgetIds();
        widgetService.deleteWidget(widgetId);
        Assert.assertTrue(allWidgetIds.contains(widgetId), "Widget was not create!");
    }

    @Test
    public void testDeleteWidget() {
        String widgetdName = "TestWidget_".concat(RandomStringUtils.randomAlphabetic(15));
        WidgetServiceImpl widgetService = new WidgetServiceImpl();
        int widgetId = widgetService.createWidget(widgetdName);
        DeleteWidgetMethod deleteWidgetMethod = new DeleteWidgetMethod(widgetId);
        apiExecutor.expectStatus(deleteWidgetMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(deleteWidgetMethod);
        List<Integer> allWidgetIds = widgetService.getAllWidgetIds();
        Assert.assertFalse(allWidgetIds.contains(widgetId), "Widget was not delete!");
    }

    @Test
    public void testGetWidgetById() {
        String widgetdName = "TestWidget_".concat(RandomStringUtils.randomAlphabetic(15));
        WidgetServiceImpl widgetService = new WidgetServiceImpl();
        int widgetId = widgetService.createWidget(widgetdName);
        GetWidgetByIdMethod getWidgetByIdMethod = new GetWidgetByIdMethod(widgetId);
        apiExecutor.expectStatus(getWidgetByIdMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getWidgetByIdMethod);
        apiExecutor.validateResponse(getWidgetByIdMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        widgetService.deleteWidget(widgetId);
    }

    @Test
    public void testPutWidget() {
        String widgetdName = "TestWidget_".concat(RandomStringUtils.randomAlphabetic(15));
        WidgetServiceImpl widgetService = new WidgetServiceImpl();
        int widgetId = widgetService.createWidget(widgetdName);
        String expectedWidgetName = R.TESTDATA.get(ConfigConstant.EXPECTED_WIDGET_NAME_KEY);

        PutWidgetMethod putWidgetMethod = new PutWidgetMethod(widgetId, expectedWidgetName);
        apiExecutor.expectStatus(putWidgetMethod, HTTPStatusCodeType.OK);
        String response = apiExecutor.callApiMethod(putWidgetMethod);
        apiExecutor.validateResponse(putWidgetMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        widgetService.deleteWidget(widgetId);
        Assert.assertEquals(expectedWidgetName, JsonPath.from(response).get(JSONConstant.TITLE_KEY), "Widget was not update!");
    }
}
