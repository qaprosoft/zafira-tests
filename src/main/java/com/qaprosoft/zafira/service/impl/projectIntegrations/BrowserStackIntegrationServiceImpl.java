package com.qaprosoft.zafira.service.impl.projectIntegrations;

import com.qaprosoft.zafira.api.projectIntegrations.BrowserStackController.GetBrowserStackIntegrationByProjectIdMethod;
import com.qaprosoft.zafira.api.projectIntegrations.BrowserStackController.PutSaveBrowserStackIntegrationMethod;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.ExecutionServiceImpl;
import com.qaprosoft.zafira.service.impl.JobServiceImpl;
import com.qaprosoft.zafira.service.projectIntegrations.BrowserStackIntegrationService;
import io.restassured.path.json.JsonPath;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;

public class BrowserStackIntegrationServiceImpl implements BrowserStackIntegrationService {
    private static final Logger LOGGER = Logger.getLogger(JobServiceImpl.class);
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

    @Override
    public void addIntegration(int projectId) {
        PutSaveBrowserStackIntegrationMethod saveBrowserStackIntegrations = new PutSaveBrowserStackIntegrationMethod(projectId);
        apiExecutor.expectStatus(saveBrowserStackIntegrations, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(saveBrowserStackIntegrations);
    }

    @Override
    public Boolean getEnabledBrowserStackIntegration(int projectId) throws UnsupportedEncodingException {
        GetBrowserStackIntegrationByProjectIdMethod checkConnection = new GetBrowserStackIntegrationByProjectIdMethod(projectId);
        String rs = apiExecutor.callApiMethod(checkConnection);
        return JsonPath.from(rs).getBoolean(JSONConstant.INTEGRATION_ENABLED_KEY);
    }
}
