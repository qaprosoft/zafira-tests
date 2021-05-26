package com.qaprosoft.zafira.api.projectIntegrations;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.ZafiraAPIBaseTest;
import com.qaprosoft.zafira.api.projectIntegrations.SauceLabsController.PutSaveSauceLabsIntegrationMethod;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.util.AbstractAPIMethodUtil;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.lang.invoke.MethodHandles;

public class SauceLabsIntegrationTest extends ZafiraAPIBaseTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static int projectId = 1;


    @Test
    public void testSaveSauceLabsIntegration() {
        PutSaveSauceLabsIntegrationMethod putSaveSauceLabsIntegrationMethod = new PutSaveSauceLabsIntegrationMethod(projectId);
        apiExecutor.expectStatus(putSaveSauceLabsIntegrationMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(putSaveSauceLabsIntegrationMethod);
        apiExecutor.validateResponse(putSaveSauceLabsIntegrationMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testSaveSauceLabsIntegrationWithEmptyRq() {
        PutSaveSauceLabsIntegrationMethod putSaveSauceLabsIntegrationMethod = new PutSaveSauceLabsIntegrationMethod(projectId);
        putSaveSauceLabsIntegrationMethod.setRequestTemplate(R.TESTDATA.get(ConfigConstant.EMPTY_RQ_PATH));
        apiExecutor.expectStatus(putSaveSauceLabsIntegrationMethod, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(putSaveSauceLabsIntegrationMethod);
    }

    @Test
    public void testSaveSauceLabsIntegrationWithoutQueryParams() {
        PutSaveSauceLabsIntegrationMethod putSaveSauceLabsIntegrationMethod = new PutSaveSauceLabsIntegrationMethod(projectId);
        AbstractAPIMethodUtil.deleteQuery(putSaveSauceLabsIntegrationMethod);
        apiExecutor.expectStatus(putSaveSauceLabsIntegrationMethod, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(putSaveSauceLabsIntegrationMethod);
    }
}
