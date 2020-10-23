package com.qaprosoft.zafira.util;

import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {

    public void createNewRequest(String body, String path) throws IOException {
        FileWriter writer = new FileWriter(path);
        writer.write(body);
        writer.close();
    }
}


