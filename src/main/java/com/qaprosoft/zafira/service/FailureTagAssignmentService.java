package com.qaprosoft.zafira.service;

public interface FailureTagAssignmentService {

    int assignFailureTag(int testId, int tagId);

    void deleteFailureTagAssignment(int tagId);
}
