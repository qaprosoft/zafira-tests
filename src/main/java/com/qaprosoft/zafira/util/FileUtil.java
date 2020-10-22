package com.qaprosoft.zafira.util;

import com.qaprosoft.carina.core.foundation.utils.common.CommonUtils;

import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {

    public static void createNewRequest(String body, String path) {

        try (FileWriter writer = new FileWriter(path)) {
            {
                writer.write(body);
                writer.flush();
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }
}

