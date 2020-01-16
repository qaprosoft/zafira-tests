package com.qaprosoft.zafira.service.impl;

import com.jayway.restassured.response.Response;
import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.ExecutionService;
import org.skyscreamer.jsonassert.JSONCompareMode;

public class ExecutionServiceImpl implements ExecutionService {
    @Override
    public String callApiMethod(AbstractApiMethodV2 method, HTTPStatusCodeType status, boolean isBodyValidationNeeded,
                                JSONCompareMode mode, String... validationFlags) {
        method.getRequest().expect().statusCode(status.getStatusCode());
        Response response = method.callAPI();
        if (isBodyValidationNeeded) {
            method.validateResponse(mode, validationFlags);
        }
        return response.asString().isEmpty() ? null : response.asString();
    }
}
