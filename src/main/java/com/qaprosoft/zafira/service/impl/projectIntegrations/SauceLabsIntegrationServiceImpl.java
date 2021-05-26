package com.qaprosoft.zafira.service.impl.projectIntegrations;

import com.qaprosoft.zafira.api.projectIntegrations.SauceLabsController.DeleteSauceLabsIntegrationMethod;
import com.qaprosoft.zafira.api.projectIntegrations.SauceLabsController.GetSauceLabsIntegrationByProjectIdMethod;
import com.qaprosoft.zafira.api.projectIntegrations.SauceLabsController.PutSaveSauceLabsIntegrationMethod;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.ExecutionServiceImpl;
import com.qaprosoft.zafira.service.impl.JobServiceImpl;
import com.qaprosoft.zafira.service.projectIntegrations.SauceLabsIntegrationService;
import io.restassured.path.json.JsonPath;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;

public class SauceLabsIntegrationServiceImpl implements SauceLabsIntegrationService {
    private static final Logger LOGGER = Logger.getLogger(JobServiceImpl.class);
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

    @Override
    public void addIntegration(int projectId) {
        PutSaveSauceLabsIntegrationMethod putSaveSauceLabsIntegrationMethod = new PutSaveSauceLabsIntegrationMethod(projectId);
        apiExecutor.expectStatus(putSaveSauceLabsIntegrationMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(putSaveSauceLabsIntegrationMethod);
    }

    @Override
    public Boolean getEnabledSauceLabsIntegration(int projectId) throws UnsupportedEncodingException {
        GetSauceLabsIntegrationByProjectIdMethod checkConnection = new GetSauceLabsIntegrationByProjectIdMethod(projectId);
        String rs = apiExecutor.callApiMethod(checkConnection);
        return JsonPath.from(rs).getBoolean(JSONConstant.INTEGRATION_ENABLED_KEY);
    }

    @Override
    public void deleteSauceLabsIntegration(int projectId) {
        DeleteSauceLabsIntegrationMethod deleteSauceLabsIntegrationMethod = new DeleteSauceLabsIntegrationMethod(projectId);
        apiExecutor.callApiMethod(deleteSauceLabsIntegrationMethod);
    }
}
