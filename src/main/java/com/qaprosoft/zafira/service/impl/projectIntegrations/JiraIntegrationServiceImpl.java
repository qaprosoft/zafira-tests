package com.qaprosoft.zafira.service.impl.projectIntegrations;

import com.qaprosoft.zafira.api.projectIntegrations.jiraWithXray.DeleteJiraIntegrationMethod;
import com.qaprosoft.zafira.api.projectIntegrations.jiraWithXray.GetJiraIntegrationByProjectIdMethod;
import com.qaprosoft.zafira.api.projectIntegrations.jiraWithXray.PutSaveJiraIntegrationMethod;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.ExecutionServiceImpl;
import com.qaprosoft.zafira.service.impl.JobServiceImpl;
import com.qaprosoft.zafira.service.projectIntegrations.JiraIntegrationService;
import io.restassured.path.json.JsonPath;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;

public class JiraIntegrationServiceImpl implements JiraIntegrationService {
    private static final Logger LOGGER = Logger.getLogger(JobServiceImpl.class);
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

    @Override
    public void addIntegration(int projectId) {
        PutSaveJiraIntegrationMethod saveIntegrationMethod = new PutSaveJiraIntegrationMethod(projectId);
        apiExecutor.expectStatus(saveIntegrationMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(saveIntegrationMethod);
    }

    @Override
    public Boolean getEnabledIntegration(int projectId) throws UnsupportedEncodingException {
        GetJiraIntegrationByProjectIdMethod checkConnection = new GetJiraIntegrationByProjectIdMethod(projectId);
        String rs = apiExecutor.callApiMethod(checkConnection);
        return JsonPath.from(rs).getBoolean(JSONConstant.INTEGRATION_ENABLED_KEY);
    }

    @Override
    public void deleteIntegration(int projectId) {
        DeleteJiraIntegrationMethod deleteLambdaTestIntegrationMethod = new DeleteJiraIntegrationMethod(projectId);
        apiExecutor.callApiMethod(deleteLambdaTestIntegrationMethod);
    }
}
