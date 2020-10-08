package com.qaprosoft.zafira.util;

import com.jayway.restassured.path.json.JsonPath;
import com.qaprosoft.zafira.api.artifactsController.GetScreenshotsV1Method;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.service.impl.ExecutionServiceImpl;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class WaitUtil {

    public static Boolean waitForScreenshotFound(GetScreenshotsV1Method getScreenshotsV1Method) {
        ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

        Wait wait = new FluentWait<GetScreenshotsV1Method>(getScreenshotsV1Method)
                .withTimeout(45, TimeUnit.SECONDS)
                .pollingEvery(5, TimeUnit.SECONDS);

        boolean found = (boolean) wait
                .until(new Function<GetScreenshotsV1Method, Boolean>() {
                           public Boolean apply(GetScreenshotsV1Method getScreenshotsV1Method) {
                               JsonPath.from
                                       (apiExecutor.callApiMethod(getScreenshotsV1Method)).
                                       getInt(JSONConstant.TOTAL_RESULTS_KEY);
                               return JsonPath.from
                                       (apiExecutor.callApiMethod(getScreenshotsV1Method)).
                                       getInt(JSONConstant.TOTAL_RESULTS_KEY) == 1;
                           }
                       }
                );
        return found;
    }
}


