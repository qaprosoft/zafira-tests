package com.qaprosoft.zafira.service;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import org.skyscreamer.jsonassert.JSONCompareMode;

public interface ExecutionService {
    String callApiMethod(AbstractApiMethodV2 method, HTTPStatusCodeType status, boolean isBodyValidationNeeded, JSONCompareMode mode, String... validationFlags);
}
