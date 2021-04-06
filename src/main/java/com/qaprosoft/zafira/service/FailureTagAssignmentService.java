package com.qaprosoft.zafira.service;

import java.util.List;

public interface FailureTagAssignmentService {

    int assignFailureTag(int testId, int tagId);

    void deleteFailureTagAssignment(int tagId);

    int getFailureTag(int testId);

    String getFailureTagAssignmentsFeedback(int testId);

    List <Integer> getAllFailureTagAssignments(int testId);
}
