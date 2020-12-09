package com.qaprosoft.zafira.api;

import io.restassured.path.json.JsonPath;
import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.groupIAM.*;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.GroupServiceIamImpl;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.List;

public class GroupTestIAM extends ZafiraAPIBaseTest {
    private static Logger LOGGER = Logger.getLogger(GroupTestIAM.class);
    private int groupId;


    @AfterMethod
    public void testDeleteGroup() {
        new GroupServiceIamImpl().deleteGroupById(groupId);
    }

    @Test
    public void testGetAllGroups() {
        GetAllGroupsMethodIAM getAllGroupsMethod = new GetAllGroupsMethodIAM();
        apiExecutor.expectStatus(getAllGroupsMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getAllGroupsMethod);
        apiExecutor.validateResponse(getAllGroupsMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testCreateGroup() {
        String groupName = "TestGroup_".concat(RandomStringUtils.randomAlphabetic(10));
        PostGroupMethodIAM postGroupMethod = new PostGroupMethodIAM(groupName);
        apiExecutor.expectStatus(postGroupMethod, HTTPStatusCodeType.CREATED);
        String rs = apiExecutor.callApiMethod(postGroupMethod);
        apiExecutor.validateResponse(postGroupMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        groupId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
    }

    @Test
    public void testGetGroupById() {
        String groupName = "TestGroup_".concat(RandomStringUtils.randomAlphabetic(10));
        groupId = new GroupServiceIamImpl().getGroupId(groupName);
        GetGroupByIdMethodIAM getGroupByIdMethod = new GetGroupByIdMethodIAM(groupId);
        apiExecutor.expectStatus(getGroupByIdMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getGroupByIdMethod);
        apiExecutor.validateResponse(getGroupByIdMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testUpdateGroupPermission() {
        String groupName = "TestGroup_".concat(RandomStringUtils.randomAlphabetic(10));
        groupId = new GroupServiceIamImpl().getGroupId(groupName);
        String permissionName = R.TESTDATA.get(ConfigConstant.EXPECTED_PERMISSION_NAME);
        PutUpdateUserPermissionMethodIAM postUpdateUserPermissionMethod = new PutUpdateUserPermissionMethodIAM
                (groupId, permissionName, groupName);
        apiExecutor.expectStatus(postUpdateUserPermissionMethod, HTTPStatusCodeType.OK);
        String response = apiExecutor.callApiMethod(postUpdateUserPermissionMethod);
        apiExecutor.validateResponse(postUpdateUserPermissionMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        Assert.assertTrue(response.contains(permissionName));
    }

    @Test
    public void testDeleteGroupById() {
        String groupName = "TestGroup_".concat(RandomStringUtils.randomAlphabetic(10));
        GroupServiceIamImpl groupService = new GroupServiceIamImpl();
        int groupId = groupService.getGroupId(groupName);
        DeleteGroupByIdMethodIAM deleteGroupByIdMethod = new DeleteGroupByIdMethodIAM(groupId);
        apiExecutor.expectStatus(deleteGroupByIdMethod, HTTPStatusCodeType.NO_CONTENT);
        apiExecutor.callApiMethod(deleteGroupByIdMethod);
        List<Integer> allGroupsIds = groupService.getAllGroupsIds();
        LOGGER.info(allGroupsIds);
        Assert.assertFalse(allGroupsIds.contains(groupId), "Group was not delete!");
    }
}
