package com.qaprosoft.zafira.api.artifactsController;

import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.manager.APIContextManager;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import static com.jayway.restassured.RestAssured.given;

public class PostScreenshotsV1Method extends ZafiraBaseApiMethodWithAuth {

    public PostScreenshotsV1Method(int testRunId , int testId, String filePath)  {
        super(null, null, new Properties());
        replaceUrlPlaceholder("base_api_url", APIContextManager.BASE_URL);
        replaceUrlPlaceholder("testRunId", String.valueOf(testRunId));
        replaceUrlPlaceholder("testId", String.valueOf(testId));
        setHeaders("Content-Type=image/png");
        try {
            getRequest().body(FileUtils.readFileToByteArray(new File(filePath).getAbsoluteFile()));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Something went wrong while getting/reading file!");
        }
    }

}
