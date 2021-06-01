package com.qaprosoft.zafira.api.projectIntegrations;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.ZafiraAPIBaseTest;
import com.qaprosoft.zafira.api.projectIntegrations.qTest.*;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.projectIntegrations.QTestIntegrationServiceImpl;
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
public class QTestIntegrationTest extends ZafiraAPIBaseTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static QTestIntegrationServiceImpl qTestIntegrationService = new QTestIntegrationServiceImpl();
    private static int projectId = 1;


    @Test
    public void testSaveQTestIntegration() {
        PutSaveQTestIntegrationMethod putSaveIntegrationMethod = new PutSaveQTestIntegrationMethod(projectId);
        apiExecutor.expectStatus(putSaveIntegrationMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(putSaveIntegrationMethod);
        apiExecutor.validateResponse(putSaveIntegrationMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testSaveQTestIntegrationWithEmptyRq() {
        PutSaveQTestIntegrationMethod putSaveQTestIntegrationMethod = new PutSaveQTestIntegrationMethod(projectId);
        putSaveQTestIntegrationMethod.setRequestTemplate(R.TESTDATA.get(ConfigConstant.EMPTY_RQ_PATH));
        apiExecutor.expectStatus(putSaveQTestIntegrationMethod, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(putSaveQTestIntegrationMethod);
    }

    @Test
    public void testSaveQTestIntegrationWithoutQueryParams() {
        PutSaveQTestIntegrationMethod putSaveQTestIntegrationMethod = new PutSaveQTestIntegrationMethod(projectId);
        AbstractAPIMethodUtil.deleteQuery(putSaveQTestIntegrationMethod);
        apiExecutor.expectStatus(putSaveQTestIntegrationMethod, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(putSaveQTestIntegrationMethod);
    }

    @Test
    public void testSaveQTestIntegrationWithoutNonexistentProjectId() {
        PutSaveQTestIntegrationMethod putSaveQTestIntegrationMethod = new PutSaveQTestIntegrationMethod(projectId * (-1));
        apiExecutor.expectStatus(putSaveQTestIntegrationMethod, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(putSaveQTestIntegrationMethod);
    }

    @Test
    public void testDeleteQTestStackIntegration() throws UnsupportedEncodingException {
        qTestIntegrationService.addIntegration(projectId);

        DeleteQTestIntegrationMethod testIntegrationMethod = new DeleteQTestIntegrationMethod(projectId);
        apiExecutor.expectStatus(testIntegrationMethod, HTTPStatusCodeType.NO_CONTENT);
        apiExecutor.callApiMethod(testIntegrationMethod);

        GetQTestIntegrationByProjectIdMethod qTestIntegrationByProjectIdMethod = new GetQTestIntegrationByProjectIdMethod(projectId);
        apiExecutor.expectStatus(qTestIntegrationByProjectIdMethod, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(qTestIntegrationByProjectIdMethod);
    }


    @Test
    public void testDeleteQTestStackIntegrationsWithNonexistentProjectId() {
        DeleteQTestIntegrationMethod deleteQTestIntegrationMethod = new DeleteQTestIntegrationMethod(projectId * (-1));
        apiExecutor.expectStatus(deleteQTestIntegrationMethod, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(deleteQTestIntegrationMethod);
    }

    @Test
    public void testDeleteNonexistentQTestIntegration() {
        qTestIntegrationService.addIntegration(projectId);
        qTestIntegrationService.deleteIntegration(projectId);
        DeleteQTestIntegrationMethod deleteQTestIntegrationMethod = new DeleteQTestIntegrationMethod(projectId);
        apiExecutor.expectStatus(deleteQTestIntegrationMethod, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(deleteQTestIntegrationMethod);
    }

    @Test
    public void testDeleteQTestIntegrationWithoutQueryParams() {
        qTestIntegrationService.addIntegration(projectId);
        DeleteQTestIntegrationMethod deleteQTestIntegrationMethod = new DeleteQTestIntegrationMethod(projectId);
        AbstractAPIMethodUtil.deleteQuery(deleteQTestIntegrationMethod);
        apiExecutor.expectStatus(deleteQTestIntegrationMethod, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(deleteQTestIntegrationMethod);
    }

    @Test
    public void testGetQTestStackIntegration() throws UnsupportedEncodingException {
        qTestIntegrationService.addIntegration(projectId);

        GetQTestIntegrationByProjectIdMethod getQTestIntegrationByProjectIdMethod = new GetQTestIntegrationByProjectIdMethod(projectId);
        apiExecutor.expectStatus(getQTestIntegrationByProjectIdMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getQTestIntegrationByProjectIdMethod);
        apiExecutor.validateResponse(getQTestIntegrationByProjectIdMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testGetQTestStackIntegrationWithoutQuery() throws UnsupportedEncodingException {
        qTestIntegrationService.addIntegration(projectId);

        GetQTestIntegrationByProjectIdMethod getQTestIntegrationByProjectIdMethod = new GetQTestIntegrationByProjectIdMethod(projectId);
        AbstractAPIMethodUtil.deleteQuery(getQTestIntegrationByProjectIdMethod);
        apiExecutor.expectStatus(getQTestIntegrationByProjectIdMethod, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(getQTestIntegrationByProjectIdMethod);
    }

    @Test
    public void testGetQTestIntegrationWithNonexistentProjectId() throws UnsupportedEncodingException {
        GetQTestIntegrationByProjectIdMethod getQTestIntegrationByProjectIdMethod = new GetQTestIntegrationByProjectIdMethod(projectId * (-1));
        apiExecutor.expectStatus(getQTestIntegrationByProjectIdMethod, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(getQTestIntegrationByProjectIdMethod);
    }

    @DataProvider(name = "enabledIntegration")
    public Object[][] getEnabledType() {
        return new Object[][]{{false}, {true}};
    }

    @Test(dataProvider = "enabledIntegration")
    public void testCheckEnabledQTestIntegration(Boolean value) throws UnsupportedEncodingException {
        qTestIntegrationService.addIntegration(projectId);

        Boolean expectedEnableValue = value;
        PatchEnabledQTestIntegrationMethod patchEnabledIntegrationMethod = new PatchEnabledQTestIntegrationMethod(projectId, expectedEnableValue);
        apiExecutor.expectStatus(patchEnabledIntegrationMethod, HTTPStatusCodeType.NO_CONTENT);
        apiExecutor.callApiMethod(patchEnabledIntegrationMethod);
        Boolean actualEnabled = qTestIntegrationService.getEnabledIntegration(projectId);
        Assert.assertEquals(actualEnabled, expectedEnableValue, "Enabled was not updated!");
    }

    @Test
    public void testCheckEnabledQTestIntegrationWithoutQueryParam() {
        qTestIntegrationService.addIntegration(projectId);

        PatchEnabledQTestIntegrationMethod patchEnabledIntegrationMethod = new PatchEnabledQTestIntegrationMethod(projectId, true);
        AbstractAPIMethodUtil.deleteQuery(patchEnabledIntegrationMethod);
        apiExecutor.expectStatus(patchEnabledIntegrationMethod, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(patchEnabledIntegrationMethod);
    }

    @Test
    public void testCheckEnabledInDeletedQTestIntegration() {
        qTestIntegrationService.addIntegration(projectId);
        qTestIntegrationService.deleteIntegration(projectId);

        PatchEnabledQTestIntegrationMethod patchEnabledIntegrationMethod = new PatchEnabledQTestIntegrationMethod(projectId, true);
        apiExecutor.expectStatus(patchEnabledIntegrationMethod, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(patchEnabledIntegrationMethod);
    }

    @Test
    public void testCheckEnabledQTestIntegrationWithNonexistentProjectId() {
        qTestIntegrationService.addIntegration(projectId);

        PatchEnabledQTestIntegrationMethod patchEnabledIntegrationMethod = new PatchEnabledQTestIntegrationMethod(projectId * (-1), true);
        apiExecutor.expectStatus(patchEnabledIntegrationMethod, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(patchEnabledIntegrationMethod);
    }

    @Test
    public void testCheckConnectionWithQTestIntegrationWithInvalidCreds() {
        PostCheckConnectionQTestIntegrationMethod checkConnection = new PostCheckConnectionQTestIntegrationMethod(projectId);
        apiExecutor.expectStatus(checkConnection, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(checkConnection);
        apiExecutor.validateResponse(checkConnection, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testCheckConnectionWithQTestIntegrationWithEmptyRq() {
        PostCheckConnectionQTestIntegrationMethod checkConnection = new PostCheckConnectionQTestIntegrationMethod(projectId);
        checkConnection.setRequestTemplate(R.TESTDATA.get(ConfigConstant.EMPTY_RQ_PATH));
        apiExecutor.expectStatus(checkConnection, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(checkConnection);
    }
}
