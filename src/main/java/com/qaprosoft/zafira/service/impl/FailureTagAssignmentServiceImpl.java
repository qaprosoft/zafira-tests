package com.qaprosoft.zafira.service.impl;


import com.qaprosoft.zafira.api.failureTagAssignment.DeleteFailureTagAssignmentMethod;
import com.qaprosoft.zafira.api.failureTagAssignment.GetFailureTagAssignmentMethod;
import com.qaprosoft.zafira.api.failureTagAssignment.PostFailureTagAssignmentMethod;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.FailureTagAssignmentService;
import io.restassured.path.json.JsonPath;
import org.apache.log4j.Logger;

import java.util.List;

public class FailureTagAssignmentServiceImpl implements FailureTagAssignmentService {
    private static final Logger LOGGER = Logger.getLogger(FailureTagAssignmentServiceImpl.class);
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

    public ExecutionServiceImpl getExecutor() {
        return apiExecutor;
    }

    @Override
    public int assignFailureTag(int testId,int tagId) {
        PostFailureTagAssignmentMethod postFailureTagsMethod = new PostFailureTagAssignmentMethod(testId,tagId);
        apiExecutor.expectStatus(postFailureTagsMethod, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postFailureTagsMethod);
        return JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
    }

    @Override
    public void deleteFailureTagAssignment(int tagId) {
        DeleteFailureTagAssignmentMethod deleteFailureTagAssignmentMethod = new DeleteFailureTagAssignmentMethod(tagId);
        apiExecutor.expectStatus(deleteFailureTagAssignmentMethod, HTTPStatusCodeType.NO_CONTENT);
        apiExecutor.callApiMethod(deleteFailureTagAssignmentMethod);
    }

    @Override
    public int getFailureTag(int testId) {
        GetFailureTagAssignmentMethod getFailureTagsMethod = new GetFailureTagAssignmentMethod(testId);
        apiExecutor.expectStatus(getFailureTagsMethod, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getFailureTagsMethod);
        return JsonPath.from(rs).get("items[0].tag.id");
    }
}
