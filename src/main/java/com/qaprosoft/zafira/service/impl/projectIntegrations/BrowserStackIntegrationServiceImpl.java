package com.qaprosoft.zafira.service.impl.projectIntegrations;

import com.qaprosoft.zafira.api.projectIntegrations.BrowserStackController.PutSaveBrowserStackIntegrationMethod;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.ExecutionServiceImpl;
import com.qaprosoft.zafira.service.impl.JobServiceImpl;
import com.qaprosoft.zafira.service.projectIntegrations.BrowserStackIntegrationService;
import org.apache.log4j.Logger;

public class BrowserStackIntegrationServiceImpl implements BrowserStackIntegrationService {
    private static final Logger LOGGER = Logger.getLogger(JobServiceImpl.class);
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

    @Override
    public void addIntegration(int projectId) {
        PutSaveBrowserStackIntegrationMethod saveBrowserStackIntegrations = new PutSaveBrowserStackIntegrationMethod(projectId);
        apiExecutor.expectStatus(saveBrowserStackIntegrations, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(saveBrowserStackIntegrations);
    }
}
