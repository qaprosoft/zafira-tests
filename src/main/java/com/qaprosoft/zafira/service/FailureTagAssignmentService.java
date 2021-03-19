package com.qaprosoft.zafira.service;

public interface FailureTagAssignmentService {

    int assignFailureTag(int testId);

    void deleteFailureTag(int tagId);
}
