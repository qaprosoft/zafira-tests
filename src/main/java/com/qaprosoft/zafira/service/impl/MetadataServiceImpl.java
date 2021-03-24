package com.qaprosoft.zafira.service.impl;
import com.qaprosoft.zafira.api.serviceMetadataController.GetTheVersionValueOrNumberMethod;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.MetadataService;
import io.restassured.path.json.JsonPath;

public class MetadataServiceImpl implements MetadataService {
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

    @Override
    public String getBuild() {
        GetTheVersionValueOrNumberMethod getTenancyInfoMethod = new GetTheVersionValueOrNumberMethod();
        apiExecutor.expectStatus(getTenancyInfoMethod, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getTenancyInfoMethod);
        return JsonPath.from(rs).getString(JSONConstant.SERVICE);
    }

}