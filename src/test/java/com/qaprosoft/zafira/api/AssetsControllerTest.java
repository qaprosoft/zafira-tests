package com.qaprosoft.zafira.api;

import com.jayway.restassured.path.json.JsonPath;
import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.assets.DeleteAssetByKeyMethod;
import com.qaprosoft.zafira.api.assets.PostAssetMethod;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.AssetServiceImpl;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.annotations.Test;

import java.io.File;

public class AssetsControllerTest extends ZafiraAPIBaseTest {

    @Test
    public void testUploadFile() {
        File uploadFile = new File(R.TESTDATA.get(ConfigConstant.IMAGE_PATH_KEY));
        PostAssetMethod postAssetMethod = new PostAssetMethod(uploadFile);
        apiExecutor.expectStatus(postAssetMethod, HTTPStatusCodeType.CREATED);
        apiExecutor.callApiMethod(postAssetMethod);
        apiExecutor.validateResponse(postAssetMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testDeleteFile() {
        File uploadFile = new File(R.TESTDATA.get(ConfigConstant.IMAGE_PATH_KEY));
        String key = new AssetServiceImpl().create(uploadFile);
        DeleteAssetByKeyMethod deleteAssetByKeyMethod = new DeleteAssetByKeyMethod(key);
        apiExecutor.expectStatus(deleteAssetByKeyMethod, HTTPStatusCodeType.NO_CONTENT);
        apiExecutor.callApiMethod(deleteAssetByKeyMethod);
    }
}

