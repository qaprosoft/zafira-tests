package com.qaprosoft.zafira.util;

import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.constant.ConfigConstant;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {

    public void createNewRequest(String body, String path) throws IOException {
        FileWriter writer = new FileWriter(path);
        writer.write(body);
        writer.close();
    }

    public void createEmailFile(String text) throws IOException {
        FileOutputStream fos = new FileOutputStream(R.TESTDATA.get(ConfigConstant.IMAGE_PATH_KEY_EMAIL));
        byte[] buffer = text.getBytes();
        fos.write(buffer, 0, buffer.length);
    }
}


