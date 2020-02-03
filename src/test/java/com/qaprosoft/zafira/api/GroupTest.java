package com.qaprosoft.zafira.api;

import com.jayway.restassured.path.json.JsonPath;
import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.GroupMethods.DeleteGroupByIdMethod;
import com.qaprosoft.zafira.api.GroupMethods.GetAllGroupsMethod;
import com.qaprosoft.zafira.api.GroupMethods.GetGroupByIdMethod;
import com.qaprosoft.zafira.api.GroupMethods.PostGroupMethod;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.GroupServiceImpl;
import org.apache.commons.lang3.RandomStringUtils;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class GroupTest extends ZariraAPIBaseTest {

    @Test
    public void testGetAllGroups() {
        GetAllGroupsMethod getAllGroupsMethod = new GetAllGroupsMethod();
        apiExecutor.expectStatus(getAllGroupsMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getAllGroupsMethod);
        apiExecutor.validateResponse(getAllGroupsMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testGetGroupById() {
        GroupServiceImpl groupService = new GroupServiceImpl();
        String groupRs = groupService.getAllGroups();
        List<Integer> allGroupsIds = JsonPath.from(groupRs).getList(JSONConstant.ID_KEY);
        Assert.assertFalse(allGroupsIds.isEmpty(), "Groups were not create!");
        int groupId = allGroupsIds.get(0);

        GetGroupByIdMethod getGroupByIdMethod = new GetGroupByIdMethod(groupId);
        apiExecutor.expectStatus(getGroupByIdMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getGroupByIdMethod);
        apiExecutor.validateResponse(getGroupByIdMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testCreateGroup() {
        String groupName = "TestGroup_".concat(RandomStringUtils.randomAlphabetic(10));
        PostGroupMethod postGroupMethod = new PostGroupMethod(groupName);
        apiExecutor.expectStatus(postGroupMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postGroupMethod);
        apiExecutor.validateResponse(postGroupMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testDeleteGroupById() {
        String groupName = "TestGroup_".concat(RandomStringUtils.randomAlphabetic(10));
        GroupServiceImpl groupService = new GroupServiceImpl();
        int groupId = groupService.createGroup(groupName);

        DeleteGroupByIdMethod deleteGroupByIdMethod = new DeleteGroupByIdMethod(groupId);
        apiExecutor.expectStatus(deleteGroupByIdMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(deleteGroupByIdMethod);
        String groupRs = groupService.getAllGroups();
        List<Integer> allGroupsIds = JsonPath.from(groupRs).getList(JSONConstant.ID_KEY);
        Assert.assertFalse(allGroupsIds.contains(groupId), "Group was not delete!");
    }
}
