package com.qaprosoft.zafira.util;

import com.jayway.restassured.path.json.JsonPath;
import com.qaprosoft.zafira.api.artifactsController.GetLogsV1Method;
import com.qaprosoft.zafira.api.artifactsController.GetScreenshotsV1Method;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.constant.TimeConstant;
import com.qaprosoft.zafira.service.impl.ExecutionServiceImpl;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class WaitUtil {

    public static Boolean waitForScreenshotFound(GetScreenshotsV1Method getScreenshotsV1Method) {
        ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

        Wait wait = new FluentWait<GetScreenshotsV1Method>(getScreenshotsV1Method)
                .withTimeout(TimeConstant.SCREENSHOT_WITH_TIMEOUT_DURATION, TimeUnit.SECONDS)
                .pollingEvery(TimeConstant.SCREENSHOT_POLLING_EVERY_DURATION, TimeUnit.SECONDS);

        boolean found = (boolean) wait
                .until(new Function<GetScreenshotsV1Method, Boolean>() {
                           int expectedCount = 1;

                           public Boolean apply(GetScreenshotsV1Method getScreenshotsV1Method) {
                               JsonPath.from
                                       (apiExecutor.callApiMethod(getScreenshotsV1Method)).
                                       getInt(JSONConstant.TOTAL_RESULTS_KEY);
                               return JsonPath.from
                                       (apiExecutor.callApiMethod(getScreenshotsV1Method)).
                                       getInt(JSONConstant.TOTAL_RESULTS_KEY) == expectedCount;
                           }
                       }
                );
        return found;
    }

    public static Boolean waitForLogFound(GetLogsV1Method getLogsV1Method) {
        ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

        Wait wait = new FluentWait<GetLogsV1Method>(getLogsV1Method)
                .withTimeout(TimeConstant.LOG_WITH_TIMEOUT_DURATION, TimeUnit.SECONDS)
                .pollingEvery(TimeConstant.LOG_POLLING_EVERY_DURATION, TimeUnit.SECONDS);

        boolean found = (boolean) wait
                .until(new Function<GetLogsV1Method, Boolean>() {
                           int expectedCount = 1;
                           public Boolean apply(GetLogsV1Method getLogsV1Method) {
                               JsonPath.from
                                       (apiExecutor.callApiMethod(getLogsV1Method)).
                                       getInt(JSONConstant.TOTAL_RESULTS_KEY);
                               return JsonPath.from
                                       (apiExecutor.callApiMethod(getLogsV1Method)).
                                       getInt(JSONConstant.TOTAL_RESULTS_KEY) == expectedCount;
                           }
                       }
                );
        return found;
    }

}


