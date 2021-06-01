package com.qaprosoft.zafira.service.impl.projectIntegrations;

import com.qaprosoft.zafira.api.projectIntegrations.qTest.DeleteQTestIntegrationMethod;
import com.qaprosoft.zafira.api.projectIntegrations.qTest.GetQTestIntegrationByProjectIdMethod;
import com.qaprosoft.zafira.api.projectIntegrations.qTest.PutSaveQTestIntegrationMethod;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.ExecutionServiceImpl;
import com.qaprosoft.zafira.service.impl.JobServiceImpl;
import com.qaprosoft.zafira.service.projectIntegrations.QTestIntegrationService;
import io.restassured.path.json.JsonPath;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;

public class QTestIntegrationServiceImpl implements QTestIntegrationService {
    private static final Logger LOGGER = Logger.getLogger(JobServiceImpl.class);
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

    @Override
    public void addIntegration(int projectId) {
        PutSaveQTestIntegrationMethod saveIntegrationMethod = new PutSaveQTestIntegrationMethod(projectId);
        apiExecutor.expectStatus(saveIntegrationMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(saveIntegrationMethod);
    }

    @Override
    public Boolean getEnabledIntegration(int projectId) throws UnsupportedEncodingException {
        GetQTestIntegrationByProjectIdMethod checkConnection = new GetQTestIntegrationByProjectIdMethod(projectId);
        String rs = apiExecutor.callApiMethod(checkConnection);
        return JsonPath.from(rs).getBoolean(JSONConstant.INTEGRATION_ENABLED_KEY);
    }

    @Override
    public void deleteIntegration(int projectId) {
        DeleteQTestIntegrationMethod deleteLambdaTestIntegrationMethod = new DeleteQTestIntegrationMethod(projectId);
        apiExecutor.callApiMethod(deleteLambdaTestIntegrationMethod);
    }
}
