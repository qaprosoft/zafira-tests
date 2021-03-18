package com.qaprosoft.zafira.service;

public interface CapabilitiesManagerServiceI {
    String getTestsInSessionsName(int testRunId,int testSessionId);

    String getTestsInSessionsBrowserName(int testRunId, int testSessionId);

    String getTestsInSessionsBrowserVersion(int testRunId, int testSessionId);

    String getTestsInSessionsPlatformName(int testRunId, int testSessionId);

    String getTestsInSessionsPlatformVersion(int testRunId, int testSessionId);

    String getSessionDeviseName(int testRunId, int testSessionId);

    String getArtifactReferences(int testRunId, int testSessionId, String name);

    String getArtifactReferencesInTest(int testRunId, int testId, int testSessionId, String name);

    String getVNCLink(int testRunId, int testSessionId);

    String getVNCArtifactName(int testRunId);
}
