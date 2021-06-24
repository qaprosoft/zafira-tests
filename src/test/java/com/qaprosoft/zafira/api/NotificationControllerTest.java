package com.qaprosoft.zafira.api;

import com.qaprosoft.zafira.api.notificationController.GetNotificationFinishTestRunByCiRunIdMethod;
import com.qaprosoft.zafira.api.notificationController.GetNotificationReviewedByTestRunIdMethod;
import com.qaprosoft.zafira.api.notificationController.GetSlackNotificationFinishTestRunByCiRunIdMethod;
import com.qaprosoft.zafira.api.notificationController.GetSlackNotificationReviewedByTestRunIdMethod;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.TestRunServiceAPIImpl;
import com.qaprosoft.zafira.service.impl.TestRunServiceAPIImplV1;
import com.qaprosoft.zafira.service.impl.TestServiceV1Impl;
import com.zebrunner.agent.core.annotation.Maintainer;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

@Maintainer("obabich")
public class NotificationControllerTest extends ZafiraAPIBaseTest {
    private static Logger LOGGER = Logger.getLogger(NotificationControllerTest.class);
    private int testRunId;

    @AfterMethod
    public void testDeleteTestRun() {
        new TestRunServiceAPIImplV1().deleteTestRun(testRunId);
    }

    @Test(enabled = false)
    public void testSendSlackNotificationFinishTestRun() {
        testRunId = new TestRunServiceAPIImplV1().start();
        String ciRunId = new TestRunServiceAPIImplV1().getCiRunId(testRunId);
        int testId = new TestServiceV1Impl().startTest(testRunId);
        new TestServiceV1Impl().finishTestAsResult(testRunId, testId, "PASSED");
        new TestRunServiceAPIImplV1().finishTestRun(testRunId);
        GetSlackNotificationFinishTestRunByCiRunIdMethod getSlackNotificationFinishTestRunByCiRunIdMethod
                = new GetSlackNotificationFinishTestRunByCiRunIdMethod(ciRunId);
        apiExecutor.expectStatus(getSlackNotificationFinishTestRunByCiRunIdMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getSlackNotificationFinishTestRunByCiRunIdMethod);
    }

    @Test(enabled = false)
    public void testSendSlackNotificationReviewedTestRun() {
        TestRunServiceAPIImplV1 testRunServiceAPIImplV1 = new TestRunServiceAPIImplV1();
        testRunId = testRunServiceAPIImplV1.start();
        new TestServiceV1Impl().startTest(testRunId);
        testRunServiceAPIImplV1.finishTestRun(testRunId);
        new TestRunServiceAPIImpl().reviewTestRun(testRunId);
        GetSlackNotificationReviewedByTestRunIdMethod reviewedByTestRunIdMethod
                = new GetSlackNotificationReviewedByTestRunIdMethod(testRunId);
        apiExecutor.expectStatus(reviewedByTestRunIdMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(reviewedByTestRunIdMethod);
    }

    @Test(enabled = false)
    public void testSendNotificationFinishTestRun() {
        testRunId = new TestRunServiceAPIImplV1().start();
        String ciRunId = new TestRunServiceAPIImplV1().getCiRunId(testRunId);
        new TestServiceV1Impl().startTest(testRunId);
        new TestRunServiceAPIImplV1().finishTestRun(testRunId);
        GetNotificationFinishTestRunByCiRunIdMethod getNotificationFinishTestRun
                = new GetNotificationFinishTestRunByCiRunIdMethod(ciRunId);
        apiExecutor.expectStatus(getNotificationFinishTestRun, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getNotificationFinishTestRun);
    }

    @Test(enabled = false)
    public void testSendNotificationReviewedTestRun() {
        testRunId = new TestRunServiceAPIImplV1().start();
        new TestServiceV1Impl().startTest(testRunId);
        new TestRunServiceAPIImpl().finishTestRun(testRunId);
        new TestRunServiceAPIImpl().reviewTestRun(testRunId);
        GetNotificationReviewedByTestRunIdMethod getNotificationReviewedTestRun
                = new GetNotificationReviewedByTestRunIdMethod(testRunId);
        apiExecutor.expectStatus(getNotificationReviewedTestRun, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getNotificationReviewedTestRun);
    }

}

