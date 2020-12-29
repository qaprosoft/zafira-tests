//package com.qaprosoft.zafira.service.impl;
//
//import com.qaprosoft.zafira.api.assets.PostAssetMethod;
//import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
//import com.qaprosoft.zafira.service.FileUtilService;
//
//import java.io.File;
//
//public class FileUtilServiceImpl implements FileUtilService {
//    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();
//
//    @Override
//    public String getUrl(File uploadFile) {
//        PostAssetMethod postAssetMethod = new PostAssetMethod(uploadFile);
//        apiExecutor.expectStatus(postAssetMethod, HTTPStatusCodeType.CREATED);
//        return apiExecutor.callApiMethod(postAssetMethod);
//    }
//}
