package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.assets.DeleteAssetByKeyMethod;
import com.qaprosoft.zafira.api.assets.PostAssetMethod;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.AssetServiceImpl;
import io.restassured.path.json.JsonPath;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;

public class AssetsControllerTest extends ZafiraAPIBaseTest {

    @DataProvider(name = "data-provider-asset-type")
    public Object[][] dataProviderMethod() {
        return new Object[][]{{"ORG_ASSET"}, {"USER_ASSET"}};
    }

    @Test(dataProvider = "data-provider-asset-type")
    public void testUploadImageAssetSucceed(String type) {
        File uploadFile = new File(R.TESTDATA.get(ConfigConstant.IMAGE_PATH_KEY));
        PostAssetMethod postAssetMethod = new PostAssetMethod(type, uploadFile);
        apiExecutor.expectStatus(postAssetMethod, HTTPStatusCodeType.CREATED);
        String rs = apiExecutor.callApiMethod(postAssetMethod);
        apiExecutor.validateResponse(postAssetMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        String key = JsonPath.from(rs).getString(JSONConstant.KEY_KEY);
        new AssetServiceImpl().delete(key);
    }

    @Test(dataProvider = "data-provider-asset-type")
    public void testDeleteImageAssetSucceed(String type) {
        File uploadFile = new File(R.TESTDATA.get(ConfigConstant.IMAGE_PATH_KEY));
        String key = new AssetServiceImpl().create(type, uploadFile);
        DeleteAssetByKeyMethod deleteAssetByKeyMethod = new DeleteAssetByKeyMethod(key);
        apiExecutor.expectStatus(deleteAssetByKeyMethod, HTTPStatusCodeType.NO_CONTENT);
        apiExecutor.callApiMethod(deleteAssetByKeyMethod);
    }
}

