package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.annotations.Test;

import java.io.File;

public class FileUtilTest extends ZafiraAPIBaseTest {

    @Test
    public void testUploadFile() {
        File uploadFile = new File(R.TESTDATA.get(ConfigConstant.IMAGE_PATH_KEY));
        PostUploadFileMethod postUploadFileMethod = new PostUploadFileMethod(uploadFile);
        apiExecutor.expectStatus(postUploadFileMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postUploadFileMethod);
        apiExecutor.validateResponse(postUploadFileMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }
}
