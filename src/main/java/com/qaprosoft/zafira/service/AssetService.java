package com.qaprosoft.zafira.service;

import java.io.File;

public interface AssetService {

    String create(String type, File uploadFile);

    void delete(String key);
}
