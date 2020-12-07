//package com.qaprosoft.zafira.service.builder;
//
//import com.qaprosoft.zafira.domain.Config;
//import com.qaprosoft.zafira.exception.BuilderException;
//import com.qaprosoft.zafira.models.dto.TestCaseType;
//
//public class TestCaseBuilder extends BaseBuilder {
//
//    static TestCaseType buildTestCase(long testSuiteId) {
//        return callItem(client -> client.createTestCase(generateTestCase(testSuiteId)))
//                .orElseThrow(() -> new BuilderException("Test case was not created"));
//    }
//
//    private static TestCaseType generateTestCase(long testSuiteId) {
//        String testClass = generateRandomString();
//        String testMethod = generateRandomString();
//        String info = generateRandomString();
//        long primaryOwnerId = Config.ADMIN_ID.getLongValue();
//        return new TestCaseType(testClass, testMethod, info, testSuiteId, primaryOwnerId);
//    }
//
//}
