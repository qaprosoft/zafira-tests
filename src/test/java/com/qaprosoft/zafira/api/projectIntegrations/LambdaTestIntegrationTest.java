package com.qaprosoft.zafira.api.projectIntegrations;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.ZafiraAPIBaseTest;
import com.qaprosoft.zafira.api.projectIntegrations.LambdaTestController.*;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.projectIntegrations.LambdaTestIntegrationServiceImpl;
import com.qaprosoft.zafira.util.AbstractAPIMethodUtil;
import com.zebrunner.agent.core.annotation.Maintainer;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.UnsupportedEncodingException;
import java.lang.invoke.MethodHandles;

@Maintainer("obabich")
public class LambdaTestIntegrationTest extends ZafiraAPIBaseTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static LambdaTestIntegrationServiceImpl lambdaTestIntegrationService = new LambdaTestIntegrationServiceImpl();
    private static int projectId = 1;


    @Test
    public void testSaveLambdaTestIntegration() {
        PutSaveLambdaTestIntegrationMethod putSaveLambdaTestIntegrationMethod = new PutSaveLambdaTestIntegrationMethod(projectId);
        apiExecutor.expectStatus(putSaveLambdaTestIntegrationMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(putSaveLambdaTestIntegrationMethod);
        apiExecutor.validateResponse(putSaveLambdaTestIntegrationMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testSaveLambdaTestIntegrationWithEmptyRq() {
        PutSaveLambdaTestIntegrationMethod putSaveLambdaTestIntegrationMethod = new PutSaveLambdaTestIntegrationMethod(projectId);
        putSaveLambdaTestIntegrationMethod.setRequestTemplate(R.TESTDATA.get(ConfigConstant.EMPTY_RQ_PATH));
        apiExecutor.expectStatus(putSaveLambdaTestIntegrationMethod, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(putSaveLambdaTestIntegrationMethod);
    }

    @Test
    public void testSaveLambdaTestIntegrationWithoutQueryParams() {
        PutSaveLambdaTestIntegrationMethod putSaveLambdaTestIntegrationMethod = new PutSaveLambdaTestIntegrationMethod(projectId);
        AbstractAPIMethodUtil.deleteQuery(putSaveLambdaTestIntegrationMethod);
        apiExecutor.expectStatus(putSaveLambdaTestIntegrationMethod, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(putSaveLambdaTestIntegrationMethod);
    }

    @Test
    public void testSaveLambdaTestIntegrationWithoutNonexistentProjectId() {
        PutSaveLambdaTestIntegrationMethod putSaveLambdaTestIntegrationMethod = new PutSaveLambdaTestIntegrationMethod(projectId * (-1));
        apiExecutor.expectStatus(putSaveLambdaTestIntegrationMethod, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(putSaveLambdaTestIntegrationMethod);
    }

    @Test
    public void testDeleteLambdaTestStackIntegration() throws UnsupportedEncodingException {
        lambdaTestIntegrationService.addIntegration(projectId);

        DeleteLambdaTestIntegrationMethod deleteLambdaTestIntegrationMethod = new DeleteLambdaTestIntegrationMethod(projectId);
        apiExecutor.expectStatus(deleteLambdaTestIntegrationMethod, HTTPStatusCodeType.NO_CONTENT);
        apiExecutor.callApiMethod(deleteLambdaTestIntegrationMethod);

        GetLambdaTestIntegrationByProjectIdMethod getLambdaTestIntegrationByProjectIdMethod = new GetLambdaTestIntegrationByProjectIdMethod(projectId);
        apiExecutor.expectStatus(getLambdaTestIntegrationByProjectIdMethod, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(getLambdaTestIntegrationByProjectIdMethod);
    }


    @Test
    public void testDeleteLambdaTestStackIntegrationsWithNonexistentProjectId() {
        DeleteLambdaTestIntegrationMethod deleteLambdaTestIntegrationMethod = new DeleteLambdaTestIntegrationMethod(projectId * (-1));
        apiExecutor.expectStatus(deleteLambdaTestIntegrationMethod, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(deleteLambdaTestIntegrationMethod);
    }

    @Test
    public void testDeleteNonexistentLambdaTestIntegration() {
        lambdaTestIntegrationService.addIntegration(projectId);
        lambdaTestIntegrationService.deleteIntegration(projectId);
        DeleteLambdaTestIntegrationMethod deleteLambdaTestIntegrationMethod = new DeleteLambdaTestIntegrationMethod(projectId);
        apiExecutor.expectStatus(deleteLambdaTestIntegrationMethod, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(deleteLambdaTestIntegrationMethod);
    }

    @Test
    public void testDeleteLambdaTestIntegrationWithoutQueryParams() {
        lambdaTestIntegrationService.addIntegration(projectId);
        DeleteLambdaTestIntegrationMethod deleteLambdaTestIntegrationMethod = new DeleteLambdaTestIntegrationMethod(projectId);
        AbstractAPIMethodUtil.deleteQuery(deleteLambdaTestIntegrationMethod);
        apiExecutor.expectStatus(deleteLambdaTestIntegrationMethod, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(deleteLambdaTestIntegrationMethod);
    }

    @Test
    public void testGetLambdaTestStackIntegration() throws UnsupportedEncodingException {
        lambdaTestIntegrationService.addIntegration(projectId);

        GetLambdaTestIntegrationByProjectIdMethod getLambdaTestIntegrationByProjectIdMethod = new GetLambdaTestIntegrationByProjectIdMethod(projectId);
        apiExecutor.expectStatus(getLambdaTestIntegrationByProjectIdMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getLambdaTestIntegrationByProjectIdMethod);
        apiExecutor.validateResponse(getLambdaTestIntegrationByProjectIdMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testGetLambdaTestStackIntegrationWithoutQuery() throws UnsupportedEncodingException {
        lambdaTestIntegrationService.addIntegration(projectId);

        GetLambdaTestIntegrationByProjectIdMethod getLambdaTestIntegrationByProjectIdMethod = new GetLambdaTestIntegrationByProjectIdMethod(projectId);
        AbstractAPIMethodUtil.deleteQuery(getLambdaTestIntegrationByProjectIdMethod);
        apiExecutor.expectStatus(getLambdaTestIntegrationByProjectIdMethod, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(getLambdaTestIntegrationByProjectIdMethod);
    }

    @Test
    public void testGetLambdaTestIntegrationWithNonexistentProjectId() throws UnsupportedEncodingException {
        GetLambdaTestIntegrationByProjectIdMethod getLambdaTestIntegrationByProjectIdMethod = new GetLambdaTestIntegrationByProjectIdMethod(projectId * (-1));
        apiExecutor.expectStatus(getLambdaTestIntegrationByProjectIdMethod, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(getLambdaTestIntegrationByProjectIdMethod);
    }

    @DataProvider(name = "enabledIntegration")
    public Object[][] getEnabledType() {
        return new Object[][]{{false}, {true}};
    }

    @Test(dataProvider = "enabledIntegration")
    public void testCheckEnabledLambdaTestIntegration(Boolean value) throws UnsupportedEncodingException {
        lambdaTestIntegrationService.addIntegration(projectId);

        Boolean expectedEnableValue = value;
        PatchEnabledLambdaTestIntegrationMethod patchEnabledIntegrationMethod = new PatchEnabledLambdaTestIntegrationMethod(projectId, expectedEnableValue);
        apiExecutor.expectStatus(patchEnabledIntegrationMethod, HTTPStatusCodeType.NO_CONTENT);
        apiExecutor.callApiMethod(patchEnabledIntegrationMethod);
        Boolean actualEnabled = lambdaTestIntegrationService.getEnabledIntegration(projectId);
        Assert.assertEquals(actualEnabled, expectedEnableValue, "Enabled was not updated!");
    }

    @Test
    public void testCheckEnabledLambdaTestIntegrationWithoutQueryParam() {
        lambdaTestIntegrationService.addIntegration(projectId);

        PatchEnabledLambdaTestIntegrationMethod patchEnabledIntegrationMethod = new PatchEnabledLambdaTestIntegrationMethod(projectId, true);
        AbstractAPIMethodUtil.deleteQuery(patchEnabledIntegrationMethod);
        apiExecutor.expectStatus(patchEnabledIntegrationMethod, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(patchEnabledIntegrationMethod);
    }

    @Test
    public void testCheckEnabledInDeletedLambdaTestIntegration() {
        lambdaTestIntegrationService.addIntegration(projectId);
        lambdaTestIntegrationService.deleteIntegration(projectId);

        PatchEnabledLambdaTestIntegrationMethod patchEnabledIntegrationMethod = new PatchEnabledLambdaTestIntegrationMethod(projectId, true);
        apiExecutor.expectStatus(patchEnabledIntegrationMethod, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(patchEnabledIntegrationMethod);
    }

    @Test
    public void testCheckEnabledLambdaTestIntegrationWithNonexistentProjectId() {
        lambdaTestIntegrationService.addIntegration(projectId);

        PatchEnabledLambdaTestIntegrationMethod patchEnabledIntegrationMethod = new PatchEnabledLambdaTestIntegrationMethod(projectId * (-1), true);
        apiExecutor.expectStatus(patchEnabledIntegrationMethod, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(patchEnabledIntegrationMethod);
    }

    @Test
    public void testCheckConnectionWithLambdaTestIntegration() {
        PostCheckConnectionLambdaTestIntegrationMethod checkConnection = new PostCheckConnectionLambdaTestIntegrationMethod(projectId);
        apiExecutor.expectStatus(checkConnection, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(checkConnection);
        apiExecutor.validateResponse(checkConnection, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testCheckConnectionWithLambdaTestIntegrationWithInvalidCreds() {
        PostCheckConnectionLambdaTestIntegrationMethod checkConnection = new PostCheckConnectionLambdaTestIntegrationMethod(projectId);
        checkConnection.addProperty("username", "invalid_cred");
        checkConnection.addProperty("accessKey", "invalid_cred");
        checkConnection.addProperty("reachable", String.valueOf(false));
        apiExecutor.expectStatus(checkConnection, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(checkConnection);
        apiExecutor.validateResponse(checkConnection, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testCheckConnectionWithLambdaTestIntegrationWithEmptyRq() {
        PostCheckConnectionLambdaTestIntegrationMethod checkConnection = new PostCheckConnectionLambdaTestIntegrationMethod(projectId);
        checkConnection.setRequestTemplate(R.TESTDATA.get(ConfigConstant.EMPTY_RQ_PATH));
        apiExecutor.expectStatus(checkConnection, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(checkConnection);
    }
}
