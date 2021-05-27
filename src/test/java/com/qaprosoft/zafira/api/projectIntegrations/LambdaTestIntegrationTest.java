package com.qaprosoft.zafira.api.projectIntegrations;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.ZafiraAPIBaseTest;
import com.qaprosoft.zafira.api.projectIntegrations.LambdaTestController.DeleteLambdaTestIntegrationMethod;
import com.qaprosoft.zafira.api.projectIntegrations.LambdaTestController.GetLambdaTestIntegrationByProjectIdMethod;
import com.qaprosoft.zafira.api.projectIntegrations.LambdaTestController.PutSaveLambdaTestIntegrationMethod;
import com.qaprosoft.zafira.api.projectIntegrations.SauceLabsController.*;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.projectIntegrations.LambdaTestIntegrationServiceImpl;
import com.qaprosoft.zafira.util.AbstractAPIMethodUtil;
import com.zebrunner.agent.core.annotation.Maintainer;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.io.UnsupportedEncodingException;
import java.lang.invoke.MethodHandles;

@Maintainer("obabich")
public class LambdaTestIntegrationTest extends ZafiraAPIBaseTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static LambdaTestIntegrationServiceImpl lambdaTestIntegrationService = new LambdaTestIntegrationServiceImpl();
    private static int projectId = 1;


    @Test
    public void testSaveSauceLabsIntegration() {
        PutSaveLambdaTestIntegrationMethod putSaveLambdaTestIntegrationMethod = new PutSaveLambdaTestIntegrationMethod(projectId);
        apiExecutor.expectStatus(putSaveLambdaTestIntegrationMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(putSaveLambdaTestIntegrationMethod);
        apiExecutor.validateResponse(putSaveLambdaTestIntegrationMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testSaveSauceLabsIntegrationWithEmptyRq() {
        PutSaveLambdaTestIntegrationMethod putSaveLambdaTestIntegrationMethod = new PutSaveLambdaTestIntegrationMethod(projectId);
        putSaveLambdaTestIntegrationMethod.setRequestTemplate(R.TESTDATA.get(ConfigConstant.EMPTY_RQ_PATH));
        apiExecutor.expectStatus(putSaveLambdaTestIntegrationMethod, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(putSaveLambdaTestIntegrationMethod);
    }

    @Test
    public void testSaveSauceLabsIntegrationWithoutQueryParams() {
        PutSaveLambdaTestIntegrationMethod putSaveLambdaTestIntegrationMethod = new PutSaveLambdaTestIntegrationMethod(projectId);
        AbstractAPIMethodUtil.deleteQuery(putSaveLambdaTestIntegrationMethod);
        apiExecutor.expectStatus(putSaveLambdaTestIntegrationMethod, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(putSaveLambdaTestIntegrationMethod);
    }

    @Test
    public void testSaveSauceLabsIntegrationWithoutNonexistentProjectId() {
        PutSaveLambdaTestIntegrationMethod putSaveLambdaTestIntegrationMethod = new PutSaveLambdaTestIntegrationMethod(projectId * (-1));
        apiExecutor.expectStatus(putSaveLambdaTestIntegrationMethod, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(putSaveLambdaTestIntegrationMethod);
    }

    @Test
    public void testDeleteSauceLabsStackIntegration() throws UnsupportedEncodingException {
        lambdaTestIntegrationService.addIntegration(projectId);

        DeleteLambdaTestIntegrationMethod deleteLambdaTestIntegrationMethod = new DeleteLambdaTestIntegrationMethod(projectId);
        apiExecutor.expectStatus(deleteLambdaTestIntegrationMethod, HTTPStatusCodeType.NO_CONTENT);
        apiExecutor.callApiMethod(deleteLambdaTestIntegrationMethod);

        GetSauceLabsIntegrationByProjectIdMethod getSauceLabsIntegrationByProjectIdMethod = new GetSauceLabsIntegrationByProjectIdMethod(projectId);
        apiExecutor.expectStatus(getSauceLabsIntegrationByProjectIdMethod, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(getSauceLabsIntegrationByProjectIdMethod);
    }


    @Test
    public void testDeleteSauceLabsStackIntegrationsWithNonexistentProjectId() {
        DeleteLambdaTestIntegrationMethod deleteLambdaTestIntegrationMethod = new DeleteLambdaTestIntegrationMethod(projectId * (-1));
        apiExecutor.expectStatus(deleteLambdaTestIntegrationMethod, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(deleteLambdaTestIntegrationMethod);
    }

    @Test
    public void testDeleteNonexistentSauceLabsStackIntegration() {
        lambdaTestIntegrationService.addIntegration(projectId);
        lambdaTestIntegrationService.deleteIntegration(projectId);
        DeleteLambdaTestIntegrationMethod deleteLambdaTestIntegrationMethod = new DeleteLambdaTestIntegrationMethod(projectId);
        apiExecutor.expectStatus(deleteLambdaTestIntegrationMethod, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(deleteLambdaTestIntegrationMethod);
    }

    @Test
    public void testDeleteSauceLabsStackIntegrationWithoutQueryParams() {
        lambdaTestIntegrationService.addIntegration(projectId);
        DeleteLambdaTestIntegrationMethod deleteLambdaTestIntegrationMethod = new DeleteLambdaTestIntegrationMethod(projectId);
        AbstractAPIMethodUtil.deleteQuery(deleteLambdaTestIntegrationMethod);
        apiExecutor.expectStatus(deleteLambdaTestIntegrationMethod, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(deleteLambdaTestIntegrationMethod);
    }

    @Test
    public void testGetSauceLabsStackIntegration() throws UnsupportedEncodingException {
        lambdaTestIntegrationService.addIntegration(projectId);

        GetLambdaTestIntegrationByProjectIdMethod getLambdaTestIntegrationByProjectIdMethod = new GetLambdaTestIntegrationByProjectIdMethod(projectId);
        apiExecutor.expectStatus(getLambdaTestIntegrationByProjectIdMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getLambdaTestIntegrationByProjectIdMethod);
        apiExecutor.validateResponse(getLambdaTestIntegrationByProjectIdMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testGetSauceLabsStackIntegrationWithoutQuery() throws UnsupportedEncodingException {
        lambdaTestIntegrationService.addIntegration(projectId);

        GetLambdaTestIntegrationByProjectIdMethod getLambdaTestIntegrationByProjectIdMethod = new GetLambdaTestIntegrationByProjectIdMethod(projectId);
        AbstractAPIMethodUtil.deleteQuery(getLambdaTestIntegrationByProjectIdMethod);
        apiExecutor.expectStatus(getLambdaTestIntegrationByProjectIdMethod, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(getLambdaTestIntegrationByProjectIdMethod);
    }

    @Test
    public void testGetSauceLabsStackIntegrationWithNonexistentProjectId() throws UnsupportedEncodingException {
        GetLambdaTestIntegrationByProjectIdMethod getLambdaTestIntegrationByProjectIdMethod = new GetLambdaTestIntegrationByProjectIdMethod(projectId * (-1));
        apiExecutor.expectStatus(getLambdaTestIntegrationByProjectIdMethod, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(getLambdaTestIntegrationByProjectIdMethod);
    }

//    @DataProvider(name = "enabledIntegration")
//    public Object[][] getEnabledType() {
//        return new Object[][]{{false}, {true}};
//    }
//
//    @Test(dataProvider = "enabledIntegration")
//    public void testCheckEnabledBrowserStackIntegration(Boolean value) throws UnsupportedEncodingException {
//        sauceLabsIntegrationService.addIntegration(projectId);
//
//        Boolean expectedEnableValue = value;
//        PatchEnabledSauceLabsIntegrationMethod enabledSauceLabsIntegrationMethod = new PatchEnabledSauceLabsIntegrationMethod(projectId, expectedEnableValue);
//        apiExecutor.expectStatus(enabledSauceLabsIntegrationMethod, HTTPStatusCodeType.NO_CONTENT);
//        apiExecutor.callApiMethod(enabledSauceLabsIntegrationMethod);
//        Boolean actualEnabled = sauceLabsIntegrationService.getEnabledSauceLabsIntegration(projectId);
//        Assert.assertEquals(actualEnabled, expectedEnableValue, "Enabled was not updated!");
//    }
//
//    @Test
//    public void testCheckEnabledBrowserStackIntegrationWithoutQueryParam() {
//        sauceLabsIntegrationService.addIntegration(projectId);
//
//        PatchEnabledSauceLabsIntegrationMethod enabledSauceLabsIntegrationMethod = new PatchEnabledSauceLabsIntegrationMethod(projectId, true);
//        AbstractAPIMethodUtil.deleteQuery(enabledSauceLabsIntegrationMethod);
//        apiExecutor.expectStatus(enabledSauceLabsIntegrationMethod, HTTPStatusCodeType.BAD_REQUEST);
//        apiExecutor.callApiMethod(enabledSauceLabsIntegrationMethod);
//    }
//
//    @Test
//    public void testCheckEnabledInDeletedBrowserStackIntegration() {
//        sauceLabsIntegrationService.addIntegration(projectId);
//        sauceLabsIntegrationService.deleteSauceLabsIntegration(projectId);
//
//        PatchEnabledSauceLabsIntegrationMethod enabledSauceLabsIntegrationMethod = new PatchEnabledSauceLabsIntegrationMethod(projectId, true);
//        apiExecutor.expectStatus(enabledSauceLabsIntegrationMethod, HTTPStatusCodeType.NOT_FOUND);
//        apiExecutor.callApiMethod(enabledSauceLabsIntegrationMethod);
//    }
//
//    @Test
//    public void testCheckEnabledBrowserStackIntegrationWithNonexistentProjectId() {
//        sauceLabsIntegrationService.addIntegration(projectId);
//
//        PatchEnabledSauceLabsIntegrationMethod enabledSauceLabsIntegrationMethod = new PatchEnabledSauceLabsIntegrationMethod(projectId * (-1), true);
//        apiExecutor.expectStatus(enabledSauceLabsIntegrationMethod, HTTPStatusCodeType.NOT_FOUND);
//        apiExecutor.callApiMethod(enabledSauceLabsIntegrationMethod);
//    }
//
//    @Test
//    public void testCheckConnectionWithBrowserStackIntegration() {
//        PostCheckConnectionSauceLabsIntegrationMethod checkConnection = new PostCheckConnectionSauceLabsIntegrationMethod(projectId);
//        apiExecutor.expectStatus(checkConnection, HTTPStatusCodeType.OK);
//        apiExecutor.callApiMethod(checkConnection);
//        apiExecutor.validateResponse(checkConnection, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
//    }
//
//    @Test
//    public void testCheckConnectionWithBrowserStackIntegrationWithInvalidCreds() {
//        PostCheckConnectionSauceLabsIntegrationMethod checkConnection = new PostCheckConnectionSauceLabsIntegrationMethod(projectId);
//        checkConnection.addProperty("username", "invalid_cred");
//        checkConnection.addProperty("accessKey", "invalid_cred");
//        checkConnection.addProperty("reachable", false);
//        apiExecutor.expectStatus(checkConnection, HTTPStatusCodeType.OK);
//        apiExecutor.callApiMethod(checkConnection);
//        apiExecutor.validateResponse(checkConnection, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
//    }
//
//    @Test
//    public void testCheckConnectionWithBrowserStackIntegrationWithEmptyRq() {
//        PostCheckConnectionSauceLabsIntegrationMethod checkConnection = new PostCheckConnectionSauceLabsIntegrationMethod(projectId);
//        checkConnection.setRequestTemplate(R.TESTDATA.get(ConfigConstant.EMPTY_RQ_PATH));
//        apiExecutor.expectStatus(checkConnection, HTTPStatusCodeType.BAD_REQUEST);
//        apiExecutor.callApiMethod(checkConnection);
//    }
}
