package com.qaprosoft.zafira.api;

import com.jayway.restassured.path.json.JsonPath;
import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.group.*;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.GroupServiceImpl;
import com.qaprosoft.zafira.service.impl.UserServiceAPIImpl;
import org.apache.commons.lang3.RandomStringUtils;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;

public class GroupTest extends ZafiraAPIBaseTest {

    @AfterTest
    public void deleteCreatedGroups() {
        GroupServiceImpl groupService = new GroupServiceImpl();
        List<Integer> allGroupsIds = groupService.getAllGroupsIds();
        for (int i = 4; i <= Collections.max(allGroupsIds); ++i) {
            if (allGroupsIds.contains(i)) {
                String response = groupService.getGroupById(i);
                List<Integer> allUserIds = JsonPath.from(response).get(JSONConstant.USERS_IDS_KEY);
                if (!allUserIds.isEmpty()) {
                    for (int j = 0; j <= Collections.max(allUserIds); ++j) {
                        if (allUserIds.contains(j)) {
                            new UserServiceAPIImpl().deleteUserFromGroup(i, j);
                        }
                    }
                }
                groupService.deleteGroupById(i);
            }
        }
    }

    @Test
    public void testGetAllGroups() {
        GetAllGroupsMethod getAllGroupsMethod = new GetAllGroupsMethod();
        apiExecutor.expectStatus(getAllGroupsMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getAllGroupsMethod);
        apiExecutor.validateResponse(getAllGroupsMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testGetGroupById() {
        String groupName = "TestGroup_".concat(RandomStringUtils.randomAlphabetic(10));
        int groupId = new GroupServiceImpl().createGroup(groupName);

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
        List<Integer> allGroupsIds = groupService.getAllGroupsIds();
        Assert.assertFalse(allGroupsIds.contains(groupId), "Group was not delete!");
    }

    @Test
    public void testUpdateGroupPermission() {
        String groupName = "TestGroup_".concat(RandomStringUtils.randomAlphabetic(10));
        int groupId = new GroupServiceImpl().createGroup(groupName);
        int permissionId = R.TESTDATA.getInt(ConfigConstant.EXPECTED_PERMISSION_ID_KEY);

        PutUpdateUserPermissionMethod postUpdateUserPermissionMethod = new PutUpdateUserPermissionMethod(groupId,
                permissionId, groupName);
        apiExecutor.expectStatus(postUpdateUserPermissionMethod, HTTPStatusCodeType.OK);
        String response = apiExecutor.callApiMethod(postUpdateUserPermissionMethod);
        List<Integer> allPermissionIds = JsonPath.from(response).getList(JSONConstant.PERMISSIONS_IDS_KEY);
        apiExecutor.validateResponse(postUpdateUserPermissionMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        Assert.assertTrue(allPermissionIds.contains(permissionId));
    }

    @Test
    public void testAddPermission() {
        String groupName = "TestGroup_".concat(RandomStringUtils.randomAlphabetic(10));
        int groupId = new GroupServiceImpl().createGroup(groupName);
        int permissionId = R.TESTDATA.getInt(ConfigConstant.EXPECTED_PERMISSION_ID_KEY);

        PostAddPermissionMethod postAddPermissionMethod = new PostAddPermissionMethod(groupId, permissionId);
        apiExecutor.expectStatus(postAddPermissionMethod, HTTPStatusCodeType.OK);
        String response = apiExecutor.callApiMethod(postAddPermissionMethod);
        List<Integer> allPermissionIds = JsonPath.from(response).getList(JSONConstant.PERMISSIONS_IDS_KEY);
        apiExecutor.validateResponse(postAddPermissionMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        Assert.assertTrue(allPermissionIds.contains(permissionId));
    }
}
