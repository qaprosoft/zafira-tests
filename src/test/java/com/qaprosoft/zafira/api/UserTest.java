package com.qaprosoft.zafira.api;

import com.jayway.restassured.path.json.JsonPath;
import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.UserMethods.DeleteUserFromGroupMethod;
import com.qaprosoft.zafira.api.UserMethods.PostSearchUserByCriteriaMethod;
import com.qaprosoft.zafira.api.UserMethods.PutAddUserToGroupMethod;
import com.qaprosoft.zafira.api.UserMethods.PutCreateUserMethod;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.GroupServiceImpl;
import com.qaprosoft.zafira.service.impl.UserServiceAPIImpl;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;

public class UserTest extends ZariraAPIBaseTest {
    private final static Logger LOGGER = Logger.getLogger(UserTest.class);

    @Test
    public void testSeacrhUserByCriteria() {
        String query = "";
        PostSearchUserByCriteriaMethod postSearchUserByCriteriaMethod = new PostSearchUserByCriteriaMethod(query);
        apiExecutor.expectStatus(postSearchUserByCriteriaMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postSearchUserByCriteriaMethod);
        apiExecutor.validateResponse(postSearchUserByCriteriaMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testCreateUser() {
        String username = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        PutCreateUserMethod putCreateUserMethod = new PutCreateUserMethod(username);
        apiExecutor.expectStatus(putCreateUserMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(putCreateUserMethod);
        apiExecutor.validateResponse(putCreateUserMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testDeleteUserFromGroup() {
        String username = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        String userRs = new UserServiceAPIImpl().getUserId(username);
        int userId = JsonPath.from(userRs).getInt("id");

        GroupServiceImpl groupService = new GroupServiceImpl();
        String allGroupsRs = groupService.getAllGroups();
        Assert.assertTrue(allGroupsRs.contains(username), "User was not add to group!");
        String groupRs = JsonPath.from(allGroupsRs).getString("[0]");
        if (groupRs.contains(username)) {
            int groupId = JsonPath.from(allGroupsRs).getInt("id[0]");
            DeleteUserFromGroupMethod deleteUserFromGroupMethod = new DeleteUserFromGroupMethod(groupId, userId);
            apiExecutor.expectStatus(deleteUserFromGroupMethod, HTTPStatusCodeType.OK);
            apiExecutor.callApiMethod(deleteUserFromGroupMethod);
            String groupAfterDel = groupService.getAllGroups();
            Assert.assertFalse(groupAfterDel.contains(username), "User was not delete from group");
        } else {
            int groupId = JsonPath.from(allGroupsRs).getInt("id[1]");
            DeleteUserFromGroupMethod deleteUserFromGroupMethod = new DeleteUserFromGroupMethod(groupId, userId);
            apiExecutor.expectStatus(deleteUserFromGroupMethod, HTTPStatusCodeType.OK);
            apiExecutor.callApiMethod(deleteUserFromGroupMethod);
            String groupAfterDel = groupService.getAllGroups();
            Assert.assertFalse(groupAfterDel.contains(username), "User was not delete from group");
        }
    }

    @Test
    public void testAddUserToGroup() {
        String username = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        String userRs = new UserServiceAPIImpl().getUserId(username);
        int userId = JsonPath.from(userRs).getInt(JSONConstant.ID_KEY);

        GroupServiceImpl groupService = new GroupServiceImpl();
        String groupRs = groupService.getAllGroups();
        List<Integer> allGroupsIds = JsonPath.from(groupRs).getList(JSONConstant.ID_KEY);
        for (int i = 1; i < Collections.max(allGroupsIds); ++i) {
            if (allGroupsIds.contains(i)) {
                String rs = groupService.getGroupById(i);
                if (rs.contains(username))
                    continue;
                PutAddUserToGroupMethod putAddUserToGroupMethod = new PutAddUserToGroupMethod(i, userId);
                apiExecutor.expectStatus(putAddUserToGroupMethod, HTTPStatusCodeType.OK);
                apiExecutor.callApiMethod(putAddUserToGroupMethod);
                apiExecutor.validateResponse(putAddUserToGroupMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
            }
        }
    }
}


