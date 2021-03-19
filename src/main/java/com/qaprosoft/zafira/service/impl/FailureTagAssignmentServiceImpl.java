package com.qaprosoft.zafira.service.impl;


import com.qaprosoft.zafira.api.failureTagAssignment.DeleteFailureTagsMethod;
import com.qaprosoft.zafira.api.failureTagAssignment.PostFailureTagsMethod;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.FailureTagAssignmentService;
import io.restassured.path.json.JsonPath;
import org.apache.log4j.Logger;

public class FailureTagAssignmentServiceImpl implements FailureTagAssignmentService {
    private static final Logger LOGGER = Logger.getLogger(FailureTagAssignmentServiceImpl.class);
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

    public ExecutionServiceImpl getExecutor() {
        return apiExecutor;
    }

    @Override
    public int assignFailureTag(int testId) {
        PostFailureTagsMethod postFailureTagsMethod = new PostFailureTagsMethod(testId);
        apiExecutor.expectStatus(postFailureTagsMethod, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postFailureTagsMethod);
        return JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
    }

    @Override
    public void deleteFailureTag(int tagId) {
        DeleteFailureTagsMethod deleteFailureTagsMethod = new DeleteFailureTagsMethod(tagId);
        apiExecutor.expectStatus(deleteFailureTagsMethod, HTTPStatusCodeType.NO_CONTENT);
        apiExecutor.callApiMethod(deleteFailureTagsMethod);
    }
}
