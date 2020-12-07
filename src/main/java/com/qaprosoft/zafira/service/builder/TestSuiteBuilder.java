//package com.qaprosoft.zafira.service.builder;
//
//import com.qaprosoft.zafira.domain.Config;
//import com.qaprosoft.zafira.exception.BuilderException;
//import com.qaprosoft.zafira.models.dto.TestSuiteType;
//
//public class TestSuiteBuilder extends BaseBuilder {
//
//    static TestSuiteType buildTestSuite() {
//        return callItem(client -> client.createTestSuite(generateTestSuite()))
//                .orElseThrow(() -> new BuilderException("Test suite was not created"));
//    }
//
//    private static TestSuiteType generateTestSuite() {
//        String name = generateRandomString();
//        String fileName = generateRandomString();
//        long adminId = Config.ADMIN_ID.getLongValue();
//        return new TestSuiteType(name, fileName, adminId);
//    }
//
//}
