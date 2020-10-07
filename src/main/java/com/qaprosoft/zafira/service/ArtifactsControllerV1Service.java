package com.qaprosoft.zafira.service;

import java.io.IOException;

public interface ArtifactsControllerV1Service {

    String createLogRecord(int testRunId, int testId);

    String createScreenshot(int testRunId, int testId, String filePath) throws IOException;
}
