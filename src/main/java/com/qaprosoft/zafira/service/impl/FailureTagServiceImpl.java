package com.qaprosoft.zafira.service.impl;


import com.qaprosoft.zafira.api.failureTag.*;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.FailureTagService;
import io.restassured.path.json.JsonPath;
import org.apache.log4j.Logger;

import java.util.Comparator;
import java.util.List;

public class FailureTagServiceImpl implements FailureTagService {
    private static final Logger LOGGER = Logger.getLogger(FailureTagServiceImpl.class);
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

    public ExecutionServiceImpl getExecutor() {
        return apiExecutor;
    }

    @Override
    public int createFailureTag(String name) {
        PostFailureTagMethod postFailureTagsMethod = new PostFailureTagMethod(name);
        apiExecutor.expectStatus(postFailureTagsMethod, HTTPStatusCodeType.CREATED);
        String rs = apiExecutor.callApiMethod(postFailureTagsMethod);
        return JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
    }

    @Override
    public void updateFallbackFailureTag(int tagId, Boolean fallback, String name) {
        PutFailureTagMethod putFailureTagMethod = new PutFailureTagMethod(tagId);
        putFailureTagMethod.addProperty(JSONConstant.FALLBACK, fallback);
        putFailureTagMethod.addProperty(JSONConstant.NAME, name);
        apiExecutor.expectStatus(putFailureTagMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(putFailureTagMethod);
    }

    @Override
    public void deleteFailureTag(int tagId) {
        DeleteFailureTagMethod deleteFailureTagMethod = new DeleteFailureTagMethod(tagId);
        apiExecutor.callApiMethod(deleteFailureTagMethod);
    }

    @Override
    public List<Integer> getAllFailureTagIds() {
        GetFailureTagsMethod getFailureTagsMethod = new GetFailureTagsMethod();
        apiExecutor.expectStatus(getFailureTagsMethod, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getFailureTagsMethod);
        List<Integer> tagIds = JsonPath.from(rs).getList("items.id");
        LOGGER.info(tagIds);
        return tagIds;
    }

    @Override
    public void setFallbackTrueToAnotherFailureTag() {
        List<Integer> tagIds = getAllFailureTagIds();
        tagIds.sort(Comparator.naturalOrder());
        PatchFailureTagMethod patchFailureTagAssignmentMethod = new PatchFailureTagMethod(true, tagIds.get(0));
        apiExecutor.expectStatus(patchFailureTagAssignmentMethod, HTTPStatusCodeType.ACCEPTED);
        apiExecutor.callApiMethod(patchFailureTagAssignmentMethod);
    }
}
