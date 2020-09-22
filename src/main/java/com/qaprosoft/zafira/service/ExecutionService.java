package com.qaprosoft.zafira.service;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import org.skyscreamer.jsonassert.JSONCompareMode;

public interface ExecutionService {
    void expectStatus(AbstractApiMethodV2 method, HTTPStatusCodeType status);

    String callApiMethod(AbstractApiMethodV2 method);

    void validateResponse(AbstractApiMethodV2 method, JSONCompareMode mode, String... validationFlags);
}
