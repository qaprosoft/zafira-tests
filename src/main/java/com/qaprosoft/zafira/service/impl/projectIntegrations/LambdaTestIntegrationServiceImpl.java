package com.qaprosoft.zafira.service.impl.projectIntegrations;

import com.qaprosoft.zafira.api.projectIntegrations.LambdaTestController.DeleteLambdaTestIntegrationMethod;
import com.qaprosoft.zafira.api.projectIntegrations.LambdaTestController.GetLambdaTestIntegrationByProjectIdMethod;
import com.qaprosoft.zafira.api.projectIntegrations.LambdaTestController.PutSaveLambdaTestIntegrationMethod;
import com.qaprosoft.zafira.api.projectIntegrations.SauceLabsController.DeleteSauceLabsIntegrationMethod;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.ExecutionServiceImpl;
import com.qaprosoft.zafira.service.impl.JobServiceImpl;
import com.qaprosoft.zafira.service.projectIntegrations.LambdaTestIntegrationService;
import io.restassured.path.json.JsonPath;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;

public class LambdaTestIntegrationServiceImpl implements LambdaTestIntegrationService {
    private static final Logger LOGGER = Logger.getLogger(JobServiceImpl.class);
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

    @Override
    public void addIntegration(int projectId) {
        PutSaveLambdaTestIntegrationMethod saveLambdaTestIntegrationMethod = new PutSaveLambdaTestIntegrationMethod(projectId);
        apiExecutor.expectStatus(saveLambdaTestIntegrationMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(saveLambdaTestIntegrationMethod);
    }

    @Override
    public Boolean getEnabledIntegration(int projectId) throws UnsupportedEncodingException {
        GetLambdaTestIntegrationByProjectIdMethod checkConnection = new GetLambdaTestIntegrationByProjectIdMethod(projectId);
        String rs = apiExecutor.callApiMethod(checkConnection);
        return JsonPath.from(rs).getBoolean(JSONConstant.INTEGRATION_ENABLED_KEY);
    }

    @Override
    public void deleteIntegration(int projectId) {
        DeleteLambdaTestIntegrationMethod deleteLambdaTestIntegrationMethod = new DeleteLambdaTestIntegrationMethod(projectId);
        apiExecutor.callApiMethod(deleteLambdaTestIntegrationMethod);
    }
}
