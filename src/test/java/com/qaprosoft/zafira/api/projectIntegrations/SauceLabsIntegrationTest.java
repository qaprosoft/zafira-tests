package com.qaprosoft.zafira.api.projectIntegrations;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.ZafiraAPIBaseTest;
import com.qaprosoft.zafira.api.projectIntegrations.BrowserStackController.DeleteBrowserStackIntegrationMethod;
import com.qaprosoft.zafira.api.projectIntegrations.BrowserStackController.GetBrowserStackIntegrationByProjectIdMethod;
import com.qaprosoft.zafira.api.projectIntegrations.SauceLabsController.DeleteSauceLabsIntegrationMethod;
import com.qaprosoft.zafira.api.projectIntegrations.SauceLabsController.GetSauceLabsIntegrationByProjectIdMethod;
import com.qaprosoft.zafira.api.projectIntegrations.SauceLabsController.PutSaveSauceLabsIntegrationMethod;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.projectIntegrations.SauceLabsIntegrationServiceImpl;
import com.qaprosoft.zafira.util.AbstractAPIMethodUtil;
import com.zebrunner.agent.core.annotation.Maintainer;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Test(description = "500 error", enabled = false)
    public void testGetSauceLabsStackIntegrationWithoutQuery() throws UnsupportedEncodingException {
        sauceLabsIntegrationService.addIntegration(projectId);

        GetSauceLabsIntegrationByProjectIdMethod getSauceLabsIntegrationByProjectIdMethod = new GetSauceLabsIntegrationByProjectIdMethod(projectId);
        AbstractAPIMethodUtil.deleteQuery(getSauceLabsIntegrationByProjectIdMethod);
        apiExecutor.expectStatus(getSauceLabsIntegrationByProjectIdMethod, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(getSauceLabsIntegrationByProjectIdMethod);
    }

    @Test
    public void testGetSauceLabsStackIntegrationWithNonexistentProjectId() throws UnsupportedEncodingException {
        GetSauceLabsIntegrationByProjectIdMethod getSauceLabsIntegrationByProjectIdMethod = new GetSauceLabsIntegrationByProjectIdMethod(projectId*(-1));
        apiExecutor.expectStatus(getSauceLabsIntegrationByProjectIdMethod, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(getSauceLabsIntegrationByProjectIdMethod);
    }

}
