package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.widget.GetAllWidgetMethod;
import com.qaprosoft.zafira.api.widget.GetAllWidgetTemplatesMethod;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.annotations.Test;

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
}
