package com.qaprosoft.zafira.api.projectIntegrations;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.ZafiraAPIBaseTest;
import com.qaprosoft.zafira.api.projectIntegrations.BrowserStackController.*;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.projectIntegrations.BrowserStackIntegrationServiceImpl;
import com.qaprosoft.zafira.util.AbstractAPIMethodUtil;
import com.zebrunner.agent.core.annotation.Maintainer;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.UnsupportedEncodingException;
import java.lang.invoke.MethodHandles;

@Maintainer("obabich")
public class BrowserStackIntegrationTest extends ZafiraAPIBaseTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static BrowserStackIntegrationServiceImpl browserStackIntegrationService = new BrowserStackIntegrationServiceImpl();
    private static int projectId = 1;


    @Test
    public void testSaveBrowserStackIntegrations() {
        PutSaveBrowserStackIntegrationMethod saveBrowserStackIntegrations = new PutSaveBrowserStackIntegrationMethod(projectId);
        apiExecutor.expectStatus(saveBrowserStackIntegrations, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(saveBrowserStackIntegrations);
        apiExecutor.validateResponse(saveBrowserStackIntegrations, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testCheckConnectionWithBrowserStackIntegrations() {
        PostCheckConnectionWithBrowserStackMethod checkConnection = new PostCheckConnectionWithBrowserStackMethod(projectId);
        apiExecutor.expectStatus(checkConnection, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(checkConnection);
        apiExecutor.validateResponse(checkConnection, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testGetBrowserStackIntegrations() throws UnsupportedEncodingException {
        browserStackIntegrationService.addIntegration(projectId);

        GetBrowserStackIntegrationByProjectIdMethod checkConnection = new GetBrowserStackIntegrationByProjectIdMethod(projectId);
        apiExecutor.expectStatus(checkConnection, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(checkConnection);
        apiExecutor.validateResponse(checkConnection, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test(description = "500 error", enabled = false)
    public void testGetBrowserStackIntegrationsWithoutQueryParams() throws UnsupportedEncodingException {
        browserStackIntegrationService.addIntegration(projectId);

        GetBrowserStackIntegrationByProjectIdMethod checkConnection = new GetBrowserStackIntegrationByProjectIdMethod(projectId);
        AbstractAPIMethodUtil.deleteQuery(checkConnection);
        apiExecutor.expectStatus(checkConnection, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(checkConnection);
    }

    @Test
    public void testCheckEnabledBrowserStackIntegration() throws UnsupportedEncodingException {
        browserStackIntegrationService.addIntegration(projectId);

        Boolean expectedEnableValue = false;
        PatchEnabledBrowserStackIntegrationMethod checkConnection = new PatchEnabledBrowserStackIntegrationMethod(projectId, expectedEnableValue);
        apiExecutor.expectStatus(checkConnection, HTTPStatusCodeType.NO_CONTENT);
        apiExecutor.callApiMethod(checkConnection);
        Boolean actualEnabled = browserStackIntegrationService.getEnabledBrowserStackIntegration(projectId);
        Assert.assertEquals(actualEnabled, expectedEnableValue, "Enabled was not updated!");
    }

    @Test
    public void testDeleteBrowserStackIntegrations() throws UnsupportedEncodingException {
        browserStackIntegrationService.addIntegration(projectId);

        DeleteBrowserStackIntegrationMethod deleteBrowserStackIntegrationMethod = new DeleteBrowserStackIntegrationMethod(projectId);
        apiExecutor.expectStatus(deleteBrowserStackIntegrationMethod, HTTPStatusCodeType.NO_CONTENT);
        apiExecutor.callApiMethod(deleteBrowserStackIntegrationMethod);

        GetBrowserStackIntegrationByProjectIdMethod getBrowserStackIntegrationByProjectIdMethod = new GetBrowserStackIntegrationByProjectIdMethod(projectId);
        apiExecutor.expectStatus(getBrowserStackIntegrationByProjectIdMethod, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(getBrowserStackIntegrationByProjectIdMethod);
    }
}
