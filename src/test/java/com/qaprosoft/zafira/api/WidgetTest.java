package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.widget.*;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.AuthServiceApiIamImpl;
import com.qaprosoft.zafira.service.impl.UserV1ServiceAPIImpl;
import com.qaprosoft.zafira.service.impl.WidgetServiceImpl;
import io.restassured.path.json.JsonPath;
import org.apache.commons.lang3.RandomStringUtils;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.List;

public class WidgetTest extends ZafiraAPIBaseTest {
    private UserV1ServiceAPIImpl userV1ServiceAPI = new UserV1ServiceAPIImpl();
    private WidgetServiceImpl widgetService = new WidgetServiceImpl();
    private int widgetTemplateId = widgetService.getAllWidgetTemplateIds().get(0);
    private static int userId;

    @AfterMethod
    public void testDeleteUser() {
        new UserV1ServiceAPIImpl().deleteUserById(userId);
    }


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
        PostWidgetMethod postWidgetMethod = new PostWidgetMethod(widgetdName, widgetTemplateId);
        apiExecutor.expectStatus(postWidgetMethod, HTTPStatusCodeType.OK);
        String response = apiExecutor.callApiMethod(postWidgetMethod);
        apiExecutor.validateResponse(postWidgetMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        int widgetId = JsonPath.from(response).get(JSONConstant.ID_KEY);
        List<Integer> allWidgetIds = widgetService.getAllWidgetIds();
        widgetService.deleteWidget(widgetId);
        Assert.assertTrue(allWidgetIds.contains(widgetId), "Widget was not create!");
    }

    @Test
    public void testCreateWidgetByUserFromUserGroup() {
        final String USER_NAME = "TEST_".concat(org.apache.commons.lang3.RandomStringUtils.randomAlphabetic(10));
        final String PASSWORD = "TEST_".concat(org.apache.commons.lang3.RandomStringUtils.randomAlphabetic(10));
        final String EMAIL = "TEST_".concat(org.apache.commons.lang3.RandomStringUtils.randomAlphabetic(15)).concat("@gmail.com");

        userId = userV1ServiceAPI.createAndGetId(USER_NAME, PASSWORD, EMAIL);
        String token = new AuthServiceApiIamImpl().getAuthToken(USER_NAME, PASSWORD);

        String widgetName = "TestWidget_".concat(RandomStringUtils.randomAlphabetic(15));
        PostWidgetMethod postWidgetMethod = new PostWidgetMethod(widgetName, token, widgetTemplateId);
        apiExecutor.expectStatus(postWidgetMethod, HTTPStatusCodeType.FORBIDDEN);
        apiExecutor.callApiMethod(postWidgetMethod);
    }

    @Test
    public void testDeleteWidget() {
        String widgetdName = "TestWidget_".concat(RandomStringUtils.randomAlphabetic(15));
        WidgetServiceImpl widgetService = new WidgetServiceImpl();
        int widgetId = widgetService.createWidget(widgetdName, widgetTemplateId);
        DeleteWidgetMethod deleteWidgetMethod = new DeleteWidgetMethod(widgetId);
        apiExecutor.expectStatus(deleteWidgetMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(deleteWidgetMethod);
        List<Integer> allWidgetIds = widgetService.getAllWidgetIds();
        Assert.assertFalse(allWidgetIds.contains(widgetId), "Widget was not delete!");
    }

    @Test
    public void testGetWidgetById() {
        String widgetName = "TestWidget_".concat(RandomStringUtils.randomAlphabetic(15));
        WidgetServiceImpl widgetService = new WidgetServiceImpl();
        int widgetId = widgetService.createWidget(widgetName, widgetTemplateId);
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
        int widgetId = widgetService.createWidget(widgetdName, widgetTemplateId);
        String expectedWidgetName = R.TESTDATA.get(ConfigConstant.EXPECTED_WIDGET_NAME_KEY) + widgetdName;
        PutWidgetMethod putWidgetMethod = new PutWidgetMethod(widgetId, expectedWidgetName, widgetTemplateId);
        apiExecutor.expectStatus(putWidgetMethod, HTTPStatusCodeType.OK);
        String response = apiExecutor.callApiMethod(putWidgetMethod);
        apiExecutor.validateResponse(putWidgetMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        widgetService.deleteWidget(widgetId);
        Assert.assertEquals(expectedWidgetName, JsonPath.from(response).get(JSONConstant.TITLE_KEY), "Widget was not update!");
    }

    @Test
    public void testGetWidgetTemplateByID() {
        int templateId = new WidgetServiceImpl().getAllWidgetTemplateIds().get(0);
        GetWidgetTemplateByIdMethod getWidgetTemplateByIdMethod = new GetWidgetTemplateByIdMethod(templateId);
        apiExecutor.expectStatus(getWidgetTemplateByIdMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getWidgetTemplateByIdMethod);
        apiExecutor.validateResponse(getWidgetTemplateByIdMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }
}
