package com.qaprosoft.zafira.api.projectIntegrations;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.ZafiraAPIBaseTest;
import com.qaprosoft.zafira.api.projectIntegrations.SauceLabsController.*;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.projectIntegrations.SauceLabsIntegrationServiceImpl;
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
public class SauceLabsIntegrationTest extends ZafiraAPIBaseTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static SauceLabsIntegrationServiceImpl sauceLabsIntegrationService = new SauceLabsIntegrationServiceImpl();
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

    @Test
    public void testSaveSauceLabsIntegrationWithoutNonexistentProjectId() {
        PutSaveSauceLabsIntegrationMethod putSaveSauceLabsIntegrationMethod = new PutSaveSauceLabsIntegrationMethod(projectId * (-1));
        apiExecutor.expectStatus(putSaveSauceLabsIntegrationMethod, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(putSaveSauceLabsIntegrationMethod);
    }

    @Test
    public void testDeleteSauceLabsStackIntegration() throws UnsupportedEncodingException {
        sauceLabsIntegrationService.addIntegration(projectId);

        DeleteSauceLabsIntegrationMethod deleteSauceLabsIntegrationMethod = new DeleteSauceLabsIntegrationMethod(projectId);
        apiExecutor.expectStatus(deleteSauceLabsIntegrationMethod, HTTPStatusCodeType.NO_CONTENT);
        apiExecutor.callApiMethod(deleteSauceLabsIntegrationMethod);

        GetSauceLabsIntegrationByProjectIdMethod getSauceLabsIntegrationByProjectIdMethod = new GetSauceLabsIntegrationByProjectIdMethod(projectId);
        apiExecutor.expectStatus(getSauceLabsIntegrationByProjectIdMethod, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(getSauceLabsIntegrationByProjectIdMethod);
    }


    @Test
    public void testDeleteSauceLabsStackIntegrationsWithNonexistentProjectId() {
        DeleteSauceLabsIntegrationMethod deleteSauceLabsIntegrationMethod = new DeleteSauceLabsIntegrationMethod(projectId * (-1));
        apiExecutor.expectStatus(deleteSauceLabsIntegrationMethod, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(deleteSauceLabsIntegrationMethod);
    }

    @Test
    public void testDeleteNonexistentSauceLabsStackIntegration() {
        sauceLabsIntegrationService.addIntegration(projectId);
        sauceLabsIntegrationService.deleteSauceLabsIntegration(projectId);
        DeleteSauceLabsIntegrationMethod deleteSauceLabsIntegrationMethod = new DeleteSauceLabsIntegrationMethod(projectId);
        apiExecutor.expectStatus(deleteSauceLabsIntegrationMethod, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(deleteSauceLabsIntegrationMethod);
    }

    @Test
    public void testDeleteSauceLabsStackIntegrationWithoutQueryParams() {
        sauceLabsIntegrationService.addIntegration(projectId);
        DeleteSauceLabsIntegrationMethod deleteSauceLabsIntegrationMethod = new DeleteSauceLabsIntegrationMethod(projectId);
        AbstractAPIMethodUtil.deleteQuery(deleteSauceLabsIntegrationMethod);
        apiExecutor.expectStatus(deleteSauceLabsIntegrationMethod, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(deleteSauceLabsIntegrationMethod);
    }

    @Test
    public void testGetSauceLabsStackIntegration() throws UnsupportedEncodingException {
        sauceLabsIntegrationService.addIntegration(projectId);

        GetSauceLabsIntegrationByProjectIdMethod getSauceLabsIntegrationByProjectIdMethod = new GetSauceLabsIntegrationByProjectIdMethod(projectId);
        apiExecutor.expectStatus(getSauceLabsIntegrationByProjectIdMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getSauceLabsIntegrationByProjectIdMethod);
        apiExecutor.validateResponse(getSauceLabsIntegrationByProjectIdMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testGetSauceLabsStackIntegrationWithoutQuery() throws UnsupportedEncodingException {
        sauceLabsIntegrationService.addIntegration(projectId);

        GetSauceLabsIntegrationByProjectIdMethod getSauceLabsIntegrationByProjectIdMethod = new GetSauceLabsIntegrationByProjectIdMethod(projectId);
        AbstractAPIMethodUtil.deleteQuery(getSauceLabsIntegrationByProjectIdMethod);
        apiExecutor.expectStatus(getSauceLabsIntegrationByProjectIdMethod, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(getSauceLabsIntegrationByProjectIdMethod);
    }

    @Test
    public void testGetSauceLabsStackIntegrationWithNonexistentProjectId() throws UnsupportedEncodingException {
        GetSauceLabsIntegrationByProjectIdMethod getSauceLabsIntegrationByProjectIdMethod = new GetSauceLabsIntegrationByProjectIdMethod(projectId * (-1));
        apiExecutor.expectStatus(getSauceLabsIntegrationByProjectIdMethod, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(getSauceLabsIntegrationByProjectIdMethod);
    }

    @DataProvider(name = "enabledIntegration")
    public Object[][] getEnabledType() {
        return new Object[][]{{false}, {true}};
    }

    @Test(dataProvider = "enabledIntegration")
    public void testCheckEnabledSauceLabsIntegration(Boolean value) throws UnsupportedEncodingException {
        sauceLabsIntegrationService.addIntegration(projectId);

        Boolean expectedEnableValue = value;
        PatchEnabledSauceLabsIntegrationMethod enabledSauceLabsIntegrationMethod = new PatchEnabledSauceLabsIntegrationMethod(projectId, expectedEnableValue);
        apiExecutor.expectStatus(enabledSauceLabsIntegrationMethod, HTTPStatusCodeType.NO_CONTENT);
        apiExecutor.callApiMethod(enabledSauceLabsIntegrationMethod);
        Boolean actualEnabled = sauceLabsIntegrationService.getEnabledSauceLabsIntegration(projectId);
        Assert.assertEquals(actualEnabled, expectedEnableValue, "Enabled was not updated!");
    }

    @Test
    public void testCheckEnabledSauceLabsIntegrationWithoutQueryParam() {
        sauceLabsIntegrationService.addIntegration(projectId);

        PatchEnabledSauceLabsIntegrationMethod enabledSauceLabsIntegrationMethod = new PatchEnabledSauceLabsIntegrationMethod(projectId, true);
        AbstractAPIMethodUtil.deleteQuery(enabledSauceLabsIntegrationMethod);
        apiExecutor.expectStatus(enabledSauceLabsIntegrationMethod, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(enabledSauceLabsIntegrationMethod);
    }

    @Test
    public void testCheckEnabledInDeletedSauceLabsIntegration() {
        sauceLabsIntegrationService.addIntegration(projectId);
        sauceLabsIntegrationService.deleteSauceLabsIntegration(projectId);

        PatchEnabledSauceLabsIntegrationMethod enabledSauceLabsIntegrationMethod = new PatchEnabledSauceLabsIntegrationMethod(projectId, true);
        apiExecutor.expectStatus(enabledSauceLabsIntegrationMethod, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(enabledSauceLabsIntegrationMethod);
    }

    @Test
    public void testCheckEnabledSauceLabsIntegrationWithNonexistentProjectId() {
        sauceLabsIntegrationService.addIntegration(projectId);

        PatchEnabledSauceLabsIntegrationMethod enabledSauceLabsIntegrationMethod = new PatchEnabledSauceLabsIntegrationMethod(projectId * (-1), true);
        apiExecutor.expectStatus(enabledSauceLabsIntegrationMethod, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(enabledSauceLabsIntegrationMethod);
    }

    @Test
    public void testCheckConnectionWithSauceLabsIntegration() {
        PostCheckConnectionSauceLabsIntegrationMethod checkConnection = new PostCheckConnectionSauceLabsIntegrationMethod(projectId);
        apiExecutor.expectStatus(checkConnection, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(checkConnection);
        apiExecutor.validateResponse(checkConnection, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testCheckConnectionWithSauceLabsIntegrationWithInvalidCreds() {
        PostCheckConnectionSauceLabsIntegrationMethod checkConnection = new PostCheckConnectionSauceLabsIntegrationMethod(projectId);
        checkConnection.addProperty("username", "invalid_cred");
        checkConnection.addProperty("accessKey", "invalid_cred");
        checkConnection.addProperty("reachable", String.valueOf(false));
        apiExecutor.expectStatus(checkConnection, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(checkConnection);
        apiExecutor.validateResponse(checkConnection, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testCheckConnectionWithSauceLabsIntegrationWithEmptyRq() {
        PostCheckConnectionSauceLabsIntegrationMethod checkConnection = new PostCheckConnectionSauceLabsIntegrationMethod(projectId);
        checkConnection.setRequestTemplate(R.TESTDATA.get(ConfigConstant.EMPTY_RQ_PATH));
        apiExecutor.expectStatus(checkConnection, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(checkConnection);
    }
}
