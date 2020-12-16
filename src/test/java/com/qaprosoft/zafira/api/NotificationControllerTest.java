package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.notificationController.GetNotificationFinishTestRunByCiRunIdMethod;
import com.qaprosoft.zafira.api.notificationController.GetNotificationReviewedByTestRunIdMethod;
import com.qaprosoft.zafira.api.notificationController.GetSlackNotificationFinishTestRunByCiRunIdMethod;
import com.qaprosoft.zafira.api.notificationController.GetSlackNotificationReviewedByTestRunIdMethod;
import com.qaprosoft.zafira.api.project.*;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.ProjectServiceImpl;
import com.qaprosoft.zafira.service.impl.TestRunServiceAPIImpl;
import com.qaprosoft.zafira.service.impl.TestRunServiceAPIImplV1;
import com.qaprosoft.zafira.service.impl.TestServiceAPIV1Impl;
import io.restassured.path.json.JsonPath;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.List;


public class NotificationControllerTest extends ZafiraAPIBaseTest {
    private static Logger LOGGER = Logger.getLogger(NotificationControllerTest.class);
    private int testRunId;

    @AfterMethod
    public void testDeleteTestRun() {
        new TestRunServiceAPIImplV1().deleteTestRun(testRunId);
    }

    @Test
    public void testSendSlackNotificationFinishTestRun() {
        testRunId = new TestRunServiceAPIImplV1().create();
        String ciRunId = new TestRunServiceAPIImplV1().getCiRunId(testRunId);
        new TestServiceAPIV1Impl().createTest(testRunId);
        new TestRunServiceAPIImplV1().finishTestRun(testRunId);
        GetSlackNotificationFinishTestRunByCiRunIdMethod getSlackNotificationFinishTestRunByCiRunIdMethod
                = new GetSlackNotificationFinishTestRunByCiRunIdMethod(ciRunId);
        apiExecutor.expectStatus(getSlackNotificationFinishTestRunByCiRunIdMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getSlackNotificationFinishTestRunByCiRunIdMethod);
    }

    @Test
    public void testSendSlackNotificationReviewedTestRun() {
        TestRunServiceAPIImplV1 testRunServiceAPIImplV1 = new TestRunServiceAPIImplV1();
        testRunId = testRunServiceAPIImplV1.create();
        new TestServiceAPIV1Impl().createTest(testRunId);
        testRunServiceAPIImplV1.finishTestRun(testRunId);
        new TestRunServiceAPIImpl().reviewTestRun(testRunId);
        GetSlackNotificationReviewedByTestRunIdMethod reviewedByTestRunIdMethod
                = new GetSlackNotificationReviewedByTestRunIdMethod(testRunId);
        apiExecutor.expectStatus(reviewedByTestRunIdMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(reviewedByTestRunIdMethod);
    }

    @Test
    public void testSendNotificationFinishTestRun() {
        testRunId = new TestRunServiceAPIImplV1().create();
        String ciRunId = new TestRunServiceAPIImplV1().getCiRunId(testRunId);
        new TestServiceAPIV1Impl().createTest(testRunId);
        new TestRunServiceAPIImplV1().finishTestRun(testRunId);
        GetNotificationFinishTestRunByCiRunIdMethod getNotificationFinishTestRun
                = new GetNotificationFinishTestRunByCiRunIdMethod(ciRunId);
        apiExecutor.expectStatus(getNotificationFinishTestRun, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getNotificationFinishTestRun);
    }

    @Test
    public void testSendNotificationReviewedTestRun() {
        testRunId = new TestRunServiceAPIImplV1().create();
        new TestServiceAPIV1Impl().createTest(testRunId);
        new TestRunServiceAPIImpl().reviewTestRun(testRunId);
        GetNotificationReviewedByTestRunIdMethod getNotificationReviewedTestRun
                = new GetNotificationReviewedByTestRunIdMethod(testRunId);
        apiExecutor.expectStatus(getNotificationReviewedTestRun, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getNotificationReviewedTestRun);
    }

}

