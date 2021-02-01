package com.qaprosoft.zafira.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface ArtifactsControllerV1Service {

    String createLogRecord(int testRunId, int testId);

    String createScreenshot(int testRunId, int testId, String filePath) throws IOException;

    String createScreenshotWithHeader(int testRunId, int testId, String filePath, int time);

    int getTotalResults(int testRunId, int testId, String filePath) throws UnsupportedEncodingException;
}
