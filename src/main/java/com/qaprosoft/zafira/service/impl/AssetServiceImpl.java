package com.qaprosoft.zafira.service.impl;

import com.qaprosoft.zafira.api.assets.DeleteAssetByKeyMethod;
import io.restassured.path.json.JsonPath;
import com.qaprosoft.zafira.api.assets.PostAssetMethod;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.AssetService;
import com.qaprosoft.zafira.service.FileUtilService;

import java.io.File;

public class AssetServiceImpl implements AssetService {
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

    @Override
    public String create(String type, File uploadFile) {
        PostAssetMethod postAssetMethod = new PostAssetMethod(type, uploadFile);
        apiExecutor.expectStatus(postAssetMethod, HTTPStatusCodeType.CREATED);
        String rs = apiExecutor.callApiMethod(postAssetMethod);
        return JsonPath.from(rs).getString(JSONConstant.KEY_KEY);
    }

    @Override
    public void delete(String key) {
        DeleteAssetByKeyMethod deleteAssetByKeyMethod = new DeleteAssetByKeyMethod(key);
        apiExecutor.expectStatus(deleteAssetByKeyMethod, HTTPStatusCodeType.NO_CONTENT);
        apiExecutor.callApiMethod(deleteAssetByKeyMethod);
    }
}
