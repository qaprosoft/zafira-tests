package com.qaprosoft.zafira.api;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.jayway.restassured.path.json.JsonPath;
import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.user.*;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.FileUtilServiceImpl;
import com.qaprosoft.zafira.service.impl.GroupServiceImpl;
import com.qaprosoft.zafira.service.impl.UserServiceAPIImpl;

public class UserTest extends ZafiraAPIBaseTest {
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
        PostUserMethod putCreateUserMethod = new PostUserMethod(username);
        apiExecutor.expectStatus(putCreateUserMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(putCreateUserMethod);
        apiExecutor.validateResponse(putCreateUserMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testDeleteUserFromGroup() {
        String username = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        int userId = new UserServiceAPIImpl().getUserId(username);
        GroupServiceImpl groupService = new GroupServiceImpl();
        String allGroupsRs = groupService.getAllGroupsString();
        Assert.assertTrue(allGroupsRs.contains(username), "User was not add to group!");

        checkUserExistAndDeleteFromGroup(allGroupsRs, username, userId);
        String groupAfterDel = groupService.getAllGroupsString();
        Assert.assertFalse(groupAfterDel.contains(username), "User was not delete from group");
    }

    @Test
    public void testAddUserToGroup() {
        String username = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        int userId = new UserServiceAPIImpl().getUserId(username);

        checkUserExistAndAddToGroup(username, userId);
    }

    @Test
    public void testUpdateUserPassword() {
        String username = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        int userId = new UserServiceAPIImpl().getUserId(username);
        PutUserPasswordMethod putUserPasswordMethod = new PutUserPasswordMethod(userId);
        apiExecutor.expectStatus(putUserPasswordMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(putUserPasswordMethod);
    }

    @Test
    public void testUpdateUserProfile() {
        String username = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        int userId = new UserServiceAPIImpl().getUserId(username);
        String expextedLastName = R.TESTDATA.get(ConfigConstant.EXPECTED_LAST_NAME_KEY);

        PutUserProfileMethod putUserProfileMethod = new PutUserProfileMethod(userId, expextedLastName, username);
        apiExecutor.expectStatus(putUserProfileMethod, HTTPStatusCodeType.OK);
        String response = apiExecutor.callApiMethod(putUserProfileMethod);
        apiExecutor.validateResponse(putUserProfileMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        String lastName = JsonPath.from(response).get(JSONConstant.LAST_NAME_KEY);
        Assert.assertEquals(expextedLastName, lastName, "Profile was not update!");
    }

    @Test
    public void testUpdateUserStatus() {
        String username = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        int userId = new UserServiceAPIImpl().getUserId(username);
        String expextedUserStatus = R.TESTDATA.get(ConfigConstant.EXPECTED_USER_STATUS_KEY);

        PutUserStatusMethod putUserStatusMethod = new PutUserStatusMethod(userId, expextedUserStatus, username);
        apiExecutor.expectStatus(putUserStatusMethod, HTTPStatusCodeType.OK);
        String response = apiExecutor.callApiMethod(putUserStatusMethod);
        apiExecutor.validateResponse(putUserStatusMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        String actualStatus = JsonPath.from(response).get(JSONConstant.STATUS_KEY);
        Assert.assertEquals(expextedUserStatus, actualStatus, "Profile was not update!");
    }

    @Test
    public void testUpdateUser() {
        String username = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        int userId = new UserServiceAPIImpl().getUserId(username);
        File uploadFile = new File(R.TESTDATA.get(ConfigConstant.IMAGE_PATH_KEY));
        String imageUrl = JsonPath.from(new FileUtilServiceImpl().getUrl(uploadFile)).get(JSONConstant.IMAGE_URL_KEY);

        PutUserMethod putUserMethod = new PutUserMethod(userId, username, imageUrl);
        apiExecutor.expectStatus(putUserMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(putUserMethod);
        apiExecutor.validateResponse(putUserMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    private void checkUserExistAndAddToGroup(String username, int userId) {
        GroupServiceImpl groupService = new GroupServiceImpl();
        List<Integer> allGroupsIds = groupService.getAllGroupsIds();
        LOGGER.info(allGroupsIds);
        for (int i = 1; i <= Collections.max(allGroupsIds); ++i) {
            if (allGroupsIds.contains(i)) {
                String rs = groupService.getGroupById(i);
                if (!rs.contains(username)) {
                    PutAddUserToGroupMethod putAddUserToGroupMethod = new PutAddUserToGroupMethod(i, userId);
                    apiExecutor.expectStatus(putAddUserToGroupMethod, HTTPStatusCodeType.OK);
                    apiExecutor.callApiMethod(putAddUserToGroupMethod);
                    apiExecutor.validateResponse(putAddUserToGroupMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
                }
            }
        }
    }

    private void checkUserExistAndDeleteFromGroup(String allGroupsRs, String username, int userId) {
        List<Integer> allGroupsIds = JsonPath.from(allGroupsRs).getList(JSONConstant.ID_KEY);
        LOGGER.info(allGroupsIds);
        for (int i = 1; i <= Collections.max(allGroupsIds); ++i) {
            if (allGroupsIds.contains(i)) {
                String rs = new GroupServiceImpl().getGroupById(i);
                if (rs.contains(username)) {
                    DeleteUserFromGroupMethod deleteUserFromGroupMethod = new DeleteUserFromGroupMethod(i, userId);
                    apiExecutor.expectStatus(deleteUserFromGroupMethod, HTTPStatusCodeType.OK);
                    apiExecutor.callApiMethod(deleteUserFromGroupMethod);
                }
            }
        }
    }
}


