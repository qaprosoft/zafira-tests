package com.qaprosoft.zafira.api;

import com.jayway.restassured.path.json.JsonPath;
import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.GroupMethods.*;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.GroupServiceImpl;
import org.apache.commons.lang3.RandomStringUtils;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class GroupTest extends ZafiraAPIBaseTest {

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
        String response = apiExecutor.callApiMethod(postGroupMethod);
        apiExecutor.validateResponse(postGroupMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        new GroupServiceImpl().deleteGroupById(JsonPath.from(response).getInt(JSONConstant.ID_KEY));
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

    @Test
    public void testUpdateGroupPermission() {
        String groupName = "TestGroup_".concat(RandomStringUtils.randomAlphabetic(10));
        GroupServiceImpl groupService = new GroupServiceImpl();
        int groupId = groupService.createGroup(groupName);
        int permissionId = R.TESTDATA.getInt(ConfigConstant.EXPECTED_PERMISSION_ID_KEY);

        PutUpdateUserPermissionMethod postUpdateUserPermissionMethod = new PutUpdateUserPermissionMethod(groupId,
                permissionId, groupName);
        apiExecutor.expectStatus(postUpdateUserPermissionMethod, HTTPStatusCodeType.OK);
        String response = apiExecutor.callApiMethod(postUpdateUserPermissionMethod);
        List<Integer> allPermissionIds = JsonPath.from(response).getList(JSONConstant.PERMISSIONS_IDS_KEY);
        apiExecutor.validateResponse(postUpdateUserPermissionMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        Assert.assertTrue(allPermissionIds.contains(permissionId));
        groupService.deleteGroupById(groupId);
    }

    @Test
    public void testAddPermission() {
        String groupName = "TestGroup_".concat(RandomStringUtils.randomAlphabetic(10));
        GroupServiceImpl groupService = new GroupServiceImpl();
        int groupId = groupService.createGroup(groupName);
        int permissionId = R.TESTDATA.getInt(ConfigConstant.EXPECTED_PERMISSION_ID_KEY);

        PostAddPermissionMethod postAddPermissionMethod = new PostAddPermissionMethod(groupId, permissionId);
        apiExecutor.expectStatus(postAddPermissionMethod, HTTPStatusCodeType.OK);
        String response = apiExecutor.callApiMethod(postAddPermissionMethod);
        List<Integer> allPermissionIds = JsonPath.from(response).getList(JSONConstant.PERMISSIONS_IDS_KEY);
        apiExecutor.validateResponse(postAddPermissionMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        Assert.assertTrue(allPermissionIds.contains(permissionId));
        groupService.deleteGroupById(groupId);
    }
}
