package com.qaprosoft.zafira.service.impl;


import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.ExecutionService;
import io.restassured.response.Response;
import org.skyscreamer.jsonassert.JSONCompareMode;

public class ExecutionServiceImpl implements ExecutionService {
    @Override
    public void expectStatus(AbstractApiMethodV2 method, HTTPStatusCodeType status) {
        method.getRequest().expect().statusCode(status.getStatusCode());
    }

    @Override
    public String callApiMethod(AbstractApiMethodV2 method) {
        Response response = method.callAPI();

        return response.asString().isEmpty() ? null : response.asString();
    }

    @Override
    public void validateResponse(AbstractApiMethodV2 method, JSONCompareMode mode, String... validationFlags) {
        method.validateResponse(mode, validationFlags);
    }

    @Override
    public void validateResponseAgainstJSONSchema(AbstractApiMethodV2 method, String schemaPath) {
        method.validateResponseAgainstJSONSchema(schemaPath);
    }
}
