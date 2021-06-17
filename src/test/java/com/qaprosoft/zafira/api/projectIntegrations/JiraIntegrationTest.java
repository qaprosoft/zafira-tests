package com.qaprosoft.zafira.api.projectIntegrations;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.ZafiraAPIBaseTest;
import com.qaprosoft.zafira.api.projectIntegrations.jiraWithXray.PutSaveJiraIntegrationMethod;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.util.AbstractAPIMethodUtil;
import com.zebrunner.agent.core.annotation.Maintainer;
import io.restassured.path.json.JsonPath;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.invoke.MethodHandles;

@Maintainer("obabich")
public class JiraIntegrationTest extends ZafiraAPIBaseTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    //  private static JiraIntegrationServiceImpl jiraIntegrationService = new JiraIntegrationServiceImpl();
    private static int projectId = 1;


    @Test
    public void testSaveJiraIntegration() {
        PutSaveJiraIntegrationMethod putSaveIntegrationMethod = new PutSaveJiraIntegrationMethod(projectId);
        apiExecutor.expectStatus(putSaveIntegrationMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(putSaveIntegrationMethod);
        apiExecutor.validateResponse(putSaveIntegrationMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testSaveJiraIntegrationWithEmptyRq() {
        PutSaveJiraIntegrationMethod putSaveQTestIntegrationMethod = new PutSaveJiraIntegrationMethod(projectId);
        putSaveQTestIntegrationMethod.setRequestTemplate(R.TESTDATA.get(ConfigConstant.EMPTY_RQ_PATH));
        apiExecutor.expectStatus(putSaveQTestIntegrationMethod, HTTPStatusCodeType.BAD_REQUEST);
        String rs = apiExecutor.callApiMethod(putSaveQTestIntegrationMethod);
        Assert.assertEquals(JsonPath.from(rs).getString(JSONConstant.ERROR_CODE), "REP-1003", "Error code is not as expected!");
    }

    @Test
    public void testSaveJiraIntegrationWithoutQueryParams() {
        PutSaveJiraIntegrationMethod putSaveQTestIntegrationMethod = new PutSaveJiraIntegrationMethod(projectId);
        AbstractAPIMethodUtil.deleteQuery(putSaveQTestIntegrationMethod);
        apiExecutor.expectStatus(putSaveQTestIntegrationMethod, HTTPStatusCodeType.BAD_REQUEST);
        String rs = apiExecutor.callApiMethod(putSaveQTestIntegrationMethod);
        Assert.assertEquals(JsonPath.from(rs).getString(JSONConstant.ERROR_CODE), "REP-1010", "Error code is not as expected!");
    }

    @Test
    public void testSaveJiraIntegrationWithoutNonexistentProjectId() {
        PutSaveJiraIntegrationMethod putSaveQTestIntegrationMethod = new PutSaveJiraIntegrationMethod(projectId * (-1));
        apiExecutor.expectStatus(putSaveQTestIntegrationMethod, HTTPStatusCodeType.NOT_FOUND);
        String rs = apiExecutor.callApiMethod(putSaveQTestIntegrationMethod);
        Assert.assertEquals(JsonPath.from(rs).getString(JSONConstant.ERROR_CODE), "REP-2018", "Error code is not as expected!");
    }
//
//    @Test
//    public void testDeleteJiraIntegration() throws UnsupportedEncodingException {
//        qTestIntegrationService.addIntegration(projectId);
//
//        DeleteQTestIntegrationMethod testIntegrationMethod = new DeleteQTestIntegrationMethod(projectId);
//        apiExecutor.expectStatus(testIntegrationMethod, HTTPStatusCodeType.NO_CONTENT);
//        apiExecutor.callApiMethod(testIntegrationMethod);
//
//        GetQTestIntegrationByProjectIdMethod qTestIntegrationByProjectIdMethod = new GetQTestIntegrationByProjectIdMethod(projectId);
//        apiExecutor.expectStatus(qTestIntegrationByProjectIdMethod, HTTPStatusCodeType.NOT_FOUND);
//        apiExecutor.callApiMethod(qTestIntegrationByProjectIdMethod);
//    }
//
//
//    @Test
//    public void testDeleteJiraIntegrationsWithNonexistentProjectId() {
//        DeleteQTestIntegrationMethod deleteQTestIntegrationMethod = new DeleteQTestIntegrationMethod(projectId * (-1));
//        apiExecutor.expectStatus(deleteQTestIntegrationMethod, HTTPStatusCodeType.NOT_FOUND);
//        apiExecutor.callApiMethod(deleteQTestIntegrationMethod);
//    }
//
//    @Test
//    public void testDeleteNonexistentJiraIntegration() {
//        qTestIntegrationService.addIntegration(projectId);
//        qTestIntegrationService.deleteIntegration(projectId);
//        DeleteQTestIntegrationMethod deleteQTestIntegrationMethod = new DeleteQTestIntegrationMethod(projectId);
//        apiExecutor.expectStatus(deleteQTestIntegrationMethod, HTTPStatusCodeType.NOT_FOUND);
//        apiExecutor.callApiMethod(deleteQTestIntegrationMethod);
//    }
//
//    @Test
//    public void testDeleteJiraIntegrationWithoutQueryParams() {
//        qTestIntegrationService.addIntegration(projectId);
//        DeleteQTestIntegrationMethod deleteQTestIntegrationMethod = new DeleteQTestIntegrationMethod(projectId);
//        AbstractAPIMethodUtil.deleteQuery(deleteQTestIntegrationMethod);
//        apiExecutor.expectStatus(deleteQTestIntegrationMethod, HTTPStatusCodeType.BAD_REQUEST);
//        apiExecutor.callApiMethod(deleteQTestIntegrationMethod);
//    }
//
//    @Test
//    public void testGetJiraIntegration() throws UnsupportedEncodingException {
//        qTestIntegrationService.addIntegration(projectId);
//
//        GetQTestIntegrationByProjectIdMethod getQTestIntegrationByProjectIdMethod = new GetQTestIntegrationByProjectIdMethod(projectId);
//        apiExecutor.expectStatus(getQTestIntegrationByProjectIdMethod, HTTPStatusCodeType.OK);
//        apiExecutor.callApiMethod(getQTestIntegrationByProjectIdMethod);
//        apiExecutor.validateResponse(getQTestIntegrationByProjectIdMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
//    }
//
//    @Test
//    public void testGetJiraIntegrationWithoutQuery() throws UnsupportedEncodingException {
//        qTestIntegrationService.addIntegration(projectId);
//
//        GetQTestIntegrationByProjectIdMethod getQTestIntegrationByProjectIdMethod = new GetQTestIntegrationByProjectIdMethod(projectId);
//        AbstractAPIMethodUtil.deleteQuery(getQTestIntegrationByProjectIdMethod);
//        apiExecutor.expectStatus(getQTestIntegrationByProjectIdMethod, HTTPStatusCodeType.BAD_REQUEST);
//        apiExecutor.callApiMethod(getQTestIntegrationByProjectIdMethod);
//    }
//
//    @Test
//    public void testGetJiraIntegrationWithNonexistentProjectId() throws UnsupportedEncodingException {
//        GetQTestIntegrationByProjectIdMethod getQTestIntegrationByProjectIdMethod = new GetQTestIntegrationByProjectIdMethod(projectId * (-1));
//        apiExecutor.expectStatus(getQTestIntegrationByProjectIdMethod, HTTPStatusCodeType.NOT_FOUND);
//        apiExecutor.callApiMethod(getQTestIntegrationByProjectIdMethod);
//    }
//
//    @DataProvider(name = "enabledIntegration")
//    public Object[][] getEnabledType() {
//        return new Object[][]{{false}, {true}};
//    }
//
//    @Test(dataProvider = "enabledIntegration")
//    public void testCheckEnabledJiraIntegration(Boolean value) throws UnsupportedEncodingException {
//        qTestIntegrationService.addIntegration(projectId);
//
//        Boolean expectedEnableValue = value;
//        PatchEnabledQTestIntegrationMethod patchEnabledIntegrationMethod = new PatchEnabledQTestIntegrationMethod(projectId, expectedEnableValue);
//        apiExecutor.expectStatus(patchEnabledIntegrationMethod, HTTPStatusCodeType.NO_CONTENT);
//        apiExecutor.callApiMethod(patchEnabledIntegrationMethod);
//        Boolean actualEnabled = qTestIntegrationService.getEnabledIntegration(projectId);
//        Assert.assertEquals(actualEnabled, expectedEnableValue, "Enabled was not updated!");
//    }
//
//    @Test
//    public void testCheckEnabledJiraIntegrationWithoutQueryParam() {
//        qTestIntegrationService.addIntegration(projectId);
//
//        PatchEnabledQTestIntegrationMethod patchEnabledIntegrationMethod = new PatchEnabledQTestIntegrationMethod(projectId, true);
//        AbstractAPIMethodUtil.deleteQuery(patchEnabledIntegrationMethod);
//        apiExecutor.expectStatus(patchEnabledIntegrationMethod, HTTPStatusCodeType.BAD_REQUEST);
//        apiExecutor.callApiMethod(patchEnabledIntegrationMethod);
//    }
//
//    @Test
//    public void testCheckEnabledInDeletedJiraIntegration() {
//        qTestIntegrationService.addIntegration(projectId);
//        qTestIntegrationService.deleteIntegration(projectId);
//
//        PatchEnabledQTestIntegrationMethod patchEnabledIntegrationMethod = new PatchEnabledQTestIntegrationMethod(projectId, true);
//        apiExecutor.expectStatus(patchEnabledIntegrationMethod, HTTPStatusCodeType.NOT_FOUND);
//        apiExecutor.callApiMethod(patchEnabledIntegrationMethod);
//    }
//
//    @Test
//    public void testCheckEnabledJiraIntegrationWithNonexistentProjectId() {
//        qTestIntegrationService.addIntegration(projectId);
//
//        PatchEnabledQTestIntegrationMethod patchEnabledIntegrationMethod = new PatchEnabledQTestIntegrationMethod(projectId * (-1), true);
//        apiExecutor.expectStatus(patchEnabledIntegrationMethod, HTTPStatusCodeType.NOT_FOUND);
//        apiExecutor.callApiMethod(patchEnabledIntegrationMethod);
//    }
//
//    @Test
//    public void testCheckConnectionWithJiraIntegrationWithInvalidCreds() {
//        PostCheckConnectionQTestIntegrationMethod checkConnection = new PostCheckConnectionQTestIntegrationMethod(projectId);
//        apiExecutor.expectStatus(checkConnection, HTTPStatusCodeType.OK);
//        apiExecutor.callApiMethod(checkConnection);
//        apiExecutor.validateResponse(checkConnection, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
//    }
//
//    @Test
//    public void testCheckConnectionWithJiraIntegrationWithEmptyRq() {
//        PostCheckConnectionQTestIntegrationMethod checkConnection = new PostCheckConnectionQTestIntegrationMethod(projectId);
//        checkConnection.setRequestTemplate(R.TESTDATA.get(ConfigConstant.EMPTY_RQ_PATH));
//        apiExecutor.expectStatus(checkConnection, HTTPStatusCodeType.BAD_REQUEST);
//        apiExecutor.callApiMethod(checkConnection);
//    }
}
