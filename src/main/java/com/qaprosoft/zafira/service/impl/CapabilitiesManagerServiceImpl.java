package com.qaprosoft.zafira.service.impl;

import com.qaprosoft.zafira.api.testRunController.GetTestByTestRunIdMethod;
import com.qaprosoft.zafira.api.testSessionController.GetSessionByTestRunIdV1Method;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.CapabilitiesManagerServiceI;
import io.restassured.path.json.JsonPath;
import org.apache.log4j.Logger;

import java.util.List;

public class CapabilitiesManagerServiceImpl implements CapabilitiesManagerServiceI {
    private static final Logger LOGGER = Logger.getLogger(CapabilitiesManagerServiceImpl.class);
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

    public ExecutionServiceImpl getExecutor() {
        return apiExecutor;
    }

    @Override
    public String getTestsInSessionsName(int testRunId, int testSessionId) {
        GetSessionByTestRunIdV1Method getSessionByTestRunIdV1Method = new GetSessionByTestRunIdV1Method(testRunId);
        apiExecutor.expectStatus(getSessionByTestRunIdV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getSessionByTestRunIdV1Method);
        int testSessionIdIndex = JsonPath.from(rs).getList(JSONConstant.ITEMS_ID).indexOf(testSessionId);
        String actualName = JsonPath.from(rs).get("items[" + testSessionIdIndex + "].name");
        return actualName;
    }

    @Override
    public String getTestsInSessionsBrowserName(int testRunId, int testSessionId) {
        GetSessionByTestRunIdV1Method getSessionByTestRunIdV1Method = new GetSessionByTestRunIdV1Method(testRunId);
        apiExecutor.expectStatus(getSessionByTestRunIdV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getSessionByTestRunIdV1Method);
        int testSessionIdIndex = JsonPath.from(rs).getList(JSONConstant.ITEMS_ID).indexOf(testSessionId);
        String actualName = JsonPath.from(rs).get("items[" + testSessionIdIndex + "].browserName");
        return actualName;
    }

    @Override
    public String getTestsInSessionsBrowserVersion(int testRunId, int testSessionId) {
        GetSessionByTestRunIdV1Method getSessionByTestRunIdV1Method = new GetSessionByTestRunIdV1Method(testRunId);
        apiExecutor.expectStatus(getSessionByTestRunIdV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getSessionByTestRunIdV1Method);
        int testSessionIdIndex = JsonPath.from(rs).getList(JSONConstant.ITEMS_ID).indexOf(testSessionId);
        String actualName = JsonPath.from(rs).get("items[" + testSessionIdIndex + "].browserVersion");
        return actualName;
    }

    @Override
    public String getTestsInSessionsPlatformName(int testRunId, int testSessionId) {
        GetSessionByTestRunIdV1Method getSessionByTestRunIdV1Method = new GetSessionByTestRunIdV1Method(testRunId);
        apiExecutor.expectStatus(getSessionByTestRunIdV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getSessionByTestRunIdV1Method);
        int testSessionIdIndex = JsonPath.from(rs).getList(JSONConstant.ITEMS_ID).indexOf(testSessionId);
        String actualName = JsonPath.from(rs).get("items[" + testSessionIdIndex + "].platformName");
        return actualName;
    }

    @Override
    public String getTestsInSessionsPlatformVersion(int testRunId, int testSessionId) {
        GetSessionByTestRunIdV1Method getSessionByTestRunIdV1Method = new GetSessionByTestRunIdV1Method(testRunId);
        apiExecutor.expectStatus(getSessionByTestRunIdV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getSessionByTestRunIdV1Method);
        int testSessionIdIndex = JsonPath.from(rs).getList(JSONConstant.ITEMS_ID).indexOf(testSessionId);
        String actualName = JsonPath.from(rs).get("items[" + testSessionIdIndex + "].platformVersion");
        return actualName;
    }

    @Override
    public String getSessionDeviseName(int testRunId, int testSessionId) {
        GetSessionByTestRunIdV1Method getSessionByTestRunIdV1Method = new GetSessionByTestRunIdV1Method(testRunId);
        apiExecutor.expectStatus(getSessionByTestRunIdV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getSessionByTestRunIdV1Method);
        int testSessionIdIndex = JsonPath.from(rs).getList(JSONConstant.ITEMS_ID).indexOf(testSessionId);
        String actualName = JsonPath.from(rs).get("items[" + testSessionIdIndex + "].deviceName");
        return actualName;
    }

    @Override
    public String getArtifactReferences(int testRunId, int testSessionId, String name) {
        String actualLinkValue = null;
        GetSessionByTestRunIdV1Method getSessionByTestRunIdV1Method = new GetSessionByTestRunIdV1Method(testRunId);
        apiExecutor.expectStatus(getSessionByTestRunIdV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getSessionByTestRunIdV1Method);
        int testSessionIdIndex = JsonPath.from(rs).getList(JSONConstant.ITEMS_ID).indexOf(testSessionId);
        List artifactList = JsonPath.from(rs).get("items[" + testSessionIdIndex + "].artifactReferences.name");
        LOGGER.info("ListOfArtifactReferencesNames: " + artifactList);
        int artifactReferencesIndex = artifactList.indexOf(name);
        LOGGER.info("ArtifactReferenceIndex: " + artifactReferencesIndex);
        if (artifactReferencesIndex >= 0) {
            actualLinkValue = JsonPath.from(rs).get("items[" + testSessionIdIndex + "].artifactReferences[" + artifactReferencesIndex + "].value");
        }
        LOGGER.info("Actual link: " + String.valueOf(actualLinkValue));
        return actualLinkValue;
    }

    @Override
    public String getArtifactReferencesInTest(int testRunId, int testId, int testSessionId, String name) {
        String actualLinkValue = null;
        GetTestByTestRunIdMethod getTestByTestRunIdMethod = new GetTestByTestRunIdMethod(testRunId);
        apiExecutor.expectStatus(getTestByTestRunIdMethod, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getTestByTestRunIdMethod);
        int testIdIndex = JsonPath.from(rs).getList("id").indexOf(testId);
        LOGGER.info("TestId= " + testId);
        List artifactList = JsonPath.from(rs).get("[" + testIdIndex + "].artifacts.name");
        LOGGER.info("ListOfArtifactReferencesNames: " + artifactList);
        int artifactReferencesIndex = artifactList.indexOf(name);
        LOGGER.info("ArtifactReferenceIndex: " + artifactReferencesIndex);
        if (artifactReferencesIndex >= 0) {
            actualLinkValue = JsonPath.from(rs).get("[" + testIdIndex + "].artifacts[" + artifactReferencesIndex + "].link");
        }
        LOGGER.info("Actual link: " + String.valueOf(actualLinkValue));
        return actualLinkValue;
    }

    @Override
    public String getVNCLink(int testRunId, int testSessionId) {
        String actualLinkValue ;
        GetSessionByTestRunIdV1Method getSessionByTestRunIdV1Method = new GetSessionByTestRunIdV1Method(testRunId);
        apiExecutor.expectStatus(getSessionByTestRunIdV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getSessionByTestRunIdV1Method);
        int testSessionIdIndex = JsonPath.from(rs).getList(JSONConstant.ITEMS_ID).indexOf(testSessionId);

            actualLinkValue = JsonPath.from(rs).get("items[0].artifactReferences[0].value");

        LOGGER.info("Actual link: " + String.valueOf(actualLinkValue));
        return actualLinkValue;
    }
}
