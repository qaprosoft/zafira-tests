package com.qaprosoft.zafira.util;


import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.zafira.api.artifactsController.GetLogsV1Method;
import com.qaprosoft.zafira.api.artifactsController.GetScreenshotsV1Method;
import com.qaprosoft.zafira.api.testRunController.GetTestByTestRunIdMethod;
import com.qaprosoft.zafira.api.testRunController.GetTestRunBySearchCriteriaMethod;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.constant.TimeConstant;
import com.qaprosoft.zafira.service.impl.ExecutionServiceImpl;
import io.restassured.path.json.JsonPath;
import org.apache.log4j.Logger;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Sleeper;
import org.openqa.selenium.support.ui.Wait;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class WaitUtil {
    private static final Logger LOGGER = Logger.getLogger(WaitUtil.class);

    public static Boolean waitForScreenshotFound(GetScreenshotsV1Method getScreenshotsV1Method, int expectedCount) {

        ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

        Wait wait = new FluentWait<GetScreenshotsV1Method>(getScreenshotsV1Method)
                .withTimeout(TimeConstant.SCREENSHOT_WITH_TIMEOUT_DURATION, TimeUnit.SECONDS)
                .pollingEvery(TimeConstant.SCREENSHOT_POLLING_EVERY_DURATION, TimeUnit.SECONDS);

        boolean found = (boolean) wait
                .until(new Function<GetScreenshotsV1Method, Boolean>() {
                           public Boolean apply(GetScreenshotsV1Method getScreenshotsV1Method) {
                               return JsonPath.from
                                       (apiExecutor.callApiMethod(getScreenshotsV1Method)).
                                       getInt(JSONConstant.TOTAL_RESULTS_KEY) == expectedCount;
                           }
                       }
                );
        return found;
    }

    public static Boolean waitForLogFound(GetLogsV1Method getLogsV1Method, int expectedCount) {
        ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();
        Wait wait = new FluentWait<GetLogsV1Method>(getLogsV1Method)
                .withTimeout(TimeConstant.LOG_WITH_TIMEOUT_DURATION, TimeUnit.SECONDS)
                .pollingEvery(TimeConstant.LOG_POLLING_EVERY_DURATION, TimeUnit.SECONDS);

        boolean found = (boolean) wait
                .until(new Function<GetLogsV1Method, Boolean>() {
                           public Boolean apply(GetLogsV1Method getLogsV1Method) {
                               return JsonPath.from
                                       (apiExecutor.callApiMethod(getLogsV1Method)).
                                       getInt(JSONConstant.TOTAL_RESULTS_KEY) == expectedCount;
                           }
                       }
                );
        return found;
    }

    public static void waitForTestArtifactFound(int testRunId, int testId, String name) {
        GetTestByTestRunIdMethod getTestByTestRunIdMethod = new GetTestByTestRunIdMethod(testRunId);
        ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();
        Wait<Boolean> waiter = new FluentWait<>(false).withTimeout(Duration.ofSeconds(TimeConstant.ARTIFACT_WITH_TIMEOUT_DURATION))
                .pollingEvery(Duration.ofSeconds(TimeConstant.ARTIFACT_POLLING_EVERY_DURATION));
        try {
            waiter.until(t -> {
                String rs = apiExecutor.callApiMethod(getTestByTestRunIdMethod);
                String artList = JsonPath.from(rs).getString(JSONConstant.ARTIFACTS_KEY);
                LOGGER.info("Artifacts: " + artList);
                if (artList.contains(String.valueOf(testId))
                        && (artList.contains(name))) {
                    return true;
                }
                return false;
            });
        } catch (TimeoutException e) {
            throw new RuntimeException(String.format("Artifacts were not found!"));
        }
    }

    public static void waitForTestRunArtifactFound(int testRunId, String name) {
        GetTestRunBySearchCriteriaMethod getTestRunBySearchCriteriaMethod
                = new GetTestRunBySearchCriteriaMethod(JSONConstant.ID_KEY, testRunId);
        ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();
        Wait<Boolean> waiter = new FluentWait<>(false)
                .withTimeout(Duration.ofSeconds(TimeConstant.ARTIFACT_WITH_TIMEOUT_DURATION))
                .pollingEvery(Duration.ofSeconds(TimeConstant.ARTIFACT_POLLING_EVERY_DURATION));
        try {
            waiter.until(t -> {
                String rs = apiExecutor.callApiMethod(getTestRunBySearchCriteriaMethod);
                String artList = JsonPath.from(rs).getString(JSONConstant.RESULT_ARTIFACT_KEY);
                LOGGER.info("Artifacts: " + artList);
                if (artList.contains(String.valueOf(testRunId))
                        && (artList.contains(name))) {
                    return true;
                }
                return false;
            });
        } catch (TimeoutException e) {
            throw new RuntimeException(String.format("Artifacts were not found!"));
        }
    }

    public static boolean waitListToLoad(List<?> list, long timeout, long pollingEvery) {
        Clock clock = Clock.systemDefaultZone();
        Instant end = clock.instant().plusMillis(timeout);
        Sleeper sleeper = Sleeper.SYSTEM_SLEEPER;
        Duration interval = Duration.ofMillis(pollingEvery);
        while (list.isEmpty() && end.isAfter(clock.instant())) {
            try {
                sleeper.sleep(interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return !list.isEmpty();
    }


}


