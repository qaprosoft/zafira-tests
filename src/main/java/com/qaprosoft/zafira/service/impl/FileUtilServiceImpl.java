package com.qaprosoft.zafira.service.impl;

import com.qaprosoft.zafira.api.PostUploadFileMethod;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.FileUtilService;

import java.io.File;

public class FileUtilServiceImpl implements FileUtilService {
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

    @Override
    public String getUrl(File uploadFile) {
        PostUploadFileMethod postUploadFileMethod = new PostUploadFileMethod(uploadFile);
        apiExecutor.expectStatus(postUploadFileMethod, HTTPStatusCodeType.CREATED);
        return apiExecutor.callApiMethod(postUploadFileMethod);
    }
}
