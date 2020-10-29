package com.qaprosoft.zafira.api;

import com.jayway.restassured.path.json.JsonPath;
import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.user.PutUserMethod;
import com.qaprosoft.zafira.api.user.PutUserProfileMethod;
import com.qaprosoft.zafira.api.user.PutUserStatusMethod;
import com.qaprosoft.zafira.api.user.v1.*;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.domain.EmailMsg;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.manager.EmailManager;
import com.qaprosoft.zafira.service.impl.*;
import com.qaprosoft.zafira.util.CryptoUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class UserTest extends ZafiraAPIBaseTest {
    private final static Logger LOGGER = Logger.getLogger(UserTest.class);
    private final static String QUERY = "anon";
    private final static String STATUS = "INACTIVE";
    private final static String EMPTY_USERNAME = "";
    private final static String INVALID_USERNAME = "!".concat(RandomStringUtils.randomAlphabetic(10));
    private final static String EMPTY_EMAIL = "";
    private final static String INVALID_EMAIL = ";".concat(RandomStringUtils.randomAlphabetic(3)).concat("@gmail.com");
    private final static String EMAIL_KEY_FOR_UPDATE = "/email";
    private final static String STATUS_KEY_FOR_UPDATE = "/status";
    private final EmailManager EMAIL = new EmailManager(
            CryptoUtil.decrypt(R.TESTDATA.get(ConfigConstant.GMAIL_USERNAME_KEY)),
            CryptoUtil.decrypt(R.TESTDATA.get(ConfigConstant.GMAIL_PASSWORD_KEY)));
    private final static String GMAIL_EMAIL = CryptoUtil.decrypt(R.TESTDATA.get(ConfigConstant.GMAIL_USERNAME_KEY));
    private final static String EXPECTED_MESSAGE = "Password reset";

    @Test
    public void testSearchUserByCriteria() {
        GetUserByCriteriaV1Method getUserByCriteriaV1Method = new GetUserByCriteriaV1Method(QUERY, STATUS);
        apiExecutor.expectStatus(getUserByCriteriaV1Method, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getUserByCriteriaV1Method);
        apiExecutor.validateResponse(getUserByCriteriaV1Method, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testCreateUser() {
        String username = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        String password = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        String email = "TEST_".concat(RandomStringUtils.randomAlphabetic(15)).concat("@gmail.com");
        PostUserMethodV1 postCreateUserV1Method = new PostUserMethodV1(username, password, email);
        apiExecutor.expectStatus(postCreateUserV1Method, HTTPStatusCodeType.CREATED);
        apiExecutor.callApiMethod(postCreateUserV1Method);
        apiExecutor.validateResponse(postCreateUserV1Method, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testCreateUserByInvitationToken() {
        String username = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        String password = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        String email = "TEST_".concat(RandomStringUtils.randomAlphabetic(15)).concat("@gmail.com");
        String invitationToken = new InvitationServiceV1Impl().getInvitationToken(email);
        PostUserByInvitationTokenV1Method postUserByInvitationTokenV1Method =
                new PostUserByInvitationTokenV1Method(username, password, invitationToken, email);
        apiExecutor.expectStatus(postUserByInvitationTokenV1Method, HTTPStatusCodeType.CREATED);
        apiExecutor.callApiMethod(postUserByInvitationTokenV1Method);
        apiExecutor.validateResponse(postUserByInvitationTokenV1Method, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testGetUserByUsernameV1() {
        String username = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        String password = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        String EMAIL = "TEST_".concat(RandomStringUtils.randomAlphabetic(15)).concat("@gmail.com");
        new UserV1ServiceAPIImpl().create(username, password, EMAIL);
        GetUserByUsernameV1Method getUserByUsernameV1Method = new GetUserByUsernameV1Method(username);
        apiExecutor.expectStatus(getUserByUsernameV1Method, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getUserByUsernameV1Method);
        apiExecutor.validateResponse(getUserByUsernameV1Method, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testGetUserByIdV1() {
        String username = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        String password = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        String EMAIL = "TEST_".concat(RandomStringUtils.randomAlphabetic(15)).concat("@gmail.com");
        new UserV1ServiceAPIImpl().create(username, password, EMAIL);
        int id = new UserV1ServiceAPIImpl().getUserId(username);
        GetUserByIdV1Method getUserByIdV1Method = new GetUserByIdV1Method(id);
        apiExecutor.expectStatus(getUserByIdV1Method, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getUserByIdV1Method);
        apiExecutor.validateResponse(getUserByIdV1Method, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testDeleteUserFromGroupV1() {
        String username = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        String password = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        String EMAIL = "TEST_".concat(RandomStringUtils.randomAlphabetic(15)).concat("@gmail.com");
        new UserV1ServiceAPIImpl().create(username, password, EMAIL);
        int userId = new UserV1ServiceAPIImpl().getUserId(username);
        GroupServiceIamImpl groupService = new GroupServiceIamImpl();
        String allGroupsRs = groupService.getAllGroupsString();
        Assert.assertTrue(allGroupsRs.contains(username), "User was not add to group!");
        checkUserExistAndDeleteFromGroupV1(userId);
        String groupAfterDel = groupService.getAllGroupsString();
        Assert.assertFalse(groupAfterDel.contains(username), "User was not delete from group");
    }

    @Test
    public void testAddUserToGroupV1() {
        String username = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        String password = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        String EMAIL = "TEST_".concat(RandomStringUtils.randomAlphabetic(15)).concat("@gmail.com");
        new UserV1ServiceAPIImpl().create(username, password, EMAIL);
        int userId = new UserV1ServiceAPIImpl().getUserId(username);

        checkUserExistAndAddToGroupV1(username, userId);
    }

    @Test
    public void testUpdateUserPassword() {
        String username = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        String password = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        String EMAIL = "TEST_".concat(RandomStringUtils.randomAlphabetic(15)).concat("@gmail.com");
        String newPassword = "new111";
        new UserV1ServiceAPIImpl().create(username, password, EMAIL);
        int userId = new UserV1ServiceAPIImpl().getUserId(username);
        PostNewUserPasswordMethodV1 postNewUserPasswordMethodV1 = new PostNewUserPasswordMethodV1(userId, password, newPassword);
        apiExecutor.expectStatus(postNewUserPasswordMethodV1, HTTPStatusCodeType.NO_CONTENT);
        apiExecutor.callApiMethod(postNewUserPasswordMethodV1);
    }

    @Test(enabled = false)
    public void testUpdateUserProfile() {
        String username = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        int userId = new UserServiceAPIImpl().create(username);
        String expextedLastName = R.TESTDATA.get(ConfigConstant.EXPECTED_LAST_NAME_KEY);
        PutUserProfileMethod putUserProfileMethod = new PutUserProfileMethod(userId, expextedLastName, username);
        apiExecutor.expectStatus(putUserProfileMethod, HTTPStatusCodeType.OK);
        String response = apiExecutor.callApiMethod(putUserProfileMethod);
        apiExecutor.validateResponse(putUserProfileMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        String lastName = JsonPath.from(response).get(JSONConstant.LAST_NAME_KEY);
        Assert.assertEquals(expextedLastName, lastName, "Profile was not update!");
    }

    @Test(enabled = false)
    public void testUpdateUserStatus() {
        String username = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        int userId = new UserServiceAPIImpl().create(username);
        String expextedUserStatus = R.TESTDATA.get(ConfigConstant.EXPECTED_USER_STATUS_KEY);
        PutUserStatusMethod putUserStatusMethod = new PutUserStatusMethod(userId, expextedUserStatus, username);
        apiExecutor.expectStatus(putUserStatusMethod, HTTPStatusCodeType.OK);
        String response = apiExecutor.callApiMethod(putUserStatusMethod);
        apiExecutor.validateResponse(putUserStatusMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        String actualStatus = JsonPath.from(response).get(JSONConstant.STATUS_KEY);
        Assert.assertEquals(expextedUserStatus, actualStatus, "Profile was not update!");
    }

    @Test(enabled = false)
    public void testUpdateUser() {
        String username = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        int userId = new UserServiceAPIImpl().create(username);
        File uploadFile = new File(R.TESTDATA.get(ConfigConstant.IMAGE_PATH_KEY));
        String imageUrl = JsonPath.from(new FileUtilServiceImpl().getUrl(uploadFile)).get(JSONConstant.IMAGE_URL_KEY);
        PutUserMethod putUserMethod = new PutUserMethod(userId, username, imageUrl);
        apiExecutor.expectStatus(putUserMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(putUserMethod);
        apiExecutor.validateResponse(putUserMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    private void checkUserExistAndAddToGroupV1(String username, int userId) {
        GroupServiceIamImpl groupService = new GroupServiceIamImpl();
        List<Integer> allGroupsIds = groupService.getAllGroupsIds();
        List<Integer> allUserGroupIds = new UserV1ServiceAPIImpl().getAllUserGroupIds(userId);
        LOGGER.info(allGroupsIds);
        for (int i = 1; i <= Collections.max(allGroupsIds); ++i) {
            if (allGroupsIds.contains(i)) {
                String rs = groupService.getGroupById(i);
                if (!rs.contains(username) & !(allUserGroupIds.contains(i))) {
                    PutAddUserToGroupV1Method putAddUserToGroupMethod = new PutAddUserToGroupV1Method(i, userId);
                    apiExecutor.expectStatus(putAddUserToGroupMethod, HTTPStatusCodeType.NO_CONTENT);
                    apiExecutor.callApiMethod(putAddUserToGroupMethod);
                    break;
                }
            }
        }
    }

    private void checkUserExistAndDeleteFromGroupV1(int userId) {
        GroupServiceIamImpl groupService = new GroupServiceIamImpl();
        List<Integer> allGroupsIds = groupService.getAllGroupsIds();
        List<Integer> allUserGroupIds = new UserV1ServiceAPIImpl().getAllUserGroupIds(userId);
        LOGGER.info(allGroupsIds);
        LOGGER.info(allUserGroupIds);
        for (int i = 1; i <= Collections.max(allGroupsIds); ++i) {
            if ((allGroupsIds.contains(i)) & (allUserGroupIds.contains(i))) {
                DeleteUserFromGroupV1Method deleteUserFromGroupV1Method = new DeleteUserFromGroupV1Method(i, userId);
                apiExecutor.expectStatus(deleteUserFromGroupV1Method, HTTPStatusCodeType.NO_CONTENT);
                apiExecutor.callApiMethod(deleteUserFromGroupV1Method);
            }
        }
    }

    @Test
    public void testUpdateUserEmail() {
        UserV1ServiceAPIImpl userV1ServiceAPIImpl = new UserV1ServiceAPIImpl();
        String username = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        String password = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        String email = "TEST_".concat(RandomStringUtils.randomAlphabetic(15)).concat("@gmail.com");
        userV1ServiceAPIImpl.create(username, password, email);
        int userId = userV1ServiceAPIImpl.getUserId(username);
        String newEmail = "new".concat(email);
        PatchUserV1Method patchUserV1Method = new PatchUserV1Method(userId, EMAIL_KEY_FOR_UPDATE, newEmail);
        apiExecutor.expectStatus(patchUserV1Method, HTTPStatusCodeType.NO_CONTENT);
        apiExecutor.callApiMethod(patchUserV1Method);
        String actualEmail = userV1ServiceAPIImpl.getEmail(username);
        Assert.assertEquals(actualEmail, newEmail, "Email is not change!");
    }

    @Test
    public void testUpdateUserStatusWithPatch() {
        UserV1ServiceAPIImpl userV1ServiceAPIImpl = new UserV1ServiceAPIImpl();
        String username = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        String password = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        String email = "TEST_".concat(RandomStringUtils.randomAlphabetic(15)).concat("@gmail.com");
        userV1ServiceAPIImpl.create(username, password, email);
        int userId = userV1ServiceAPIImpl.getUserId(username);
        String newStatus = "INACTIVE";
        PatchUserV1Method patchUserV1Method = new PatchUserV1Method(userId, STATUS_KEY_FOR_UPDATE, newStatus);
        apiExecutor.expectStatus(patchUserV1Method, HTTPStatusCodeType.NO_CONTENT);
        apiExecutor.callApiMethod(patchUserV1Method);
        String actualStatus = userV1ServiceAPIImpl.getStatus(username);
        Assert.assertEquals(actualStatus, newStatus, "Status is not change!");
    }

    @Test
    public void testCreateUserWithEmptyUserName() {
        String password = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        String email = "TEST_".concat(RandomStringUtils.randomAlphabetic(15)).concat("@gmail.com");
        PostUserMethodV1 postCreateUserV1Method = new PostUserMethodV1(EMPTY_USERNAME, password, email);
        apiExecutor.expectStatus(postCreateUserV1Method, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postCreateUserV1Method);
    }

    @Test
    public void testCreateUserWithExistingUserName() {
        String username = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        String password = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        String email = "TEST_".concat(RandomStringUtils.randomAlphabetic(15)).concat("@gmail.com");
        new UserV1ServiceAPIImpl().create(username, password, email);
        PostUserMethodV1 postCreateUserV1Method = new PostUserMethodV1(username, password, email);
        apiExecutor.expectStatus(postCreateUserV1Method, HTTPStatusCodeType.FORBIDDEN);
        apiExecutor.callApiMethod(postCreateUserV1Method);
    }

    @Test
    public void testCreateUserWithInvalidUserName() {
        String password = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        String email = "TEST_".concat(RandomStringUtils.randomAlphabetic(15)).concat("@gmail.com");
        PostUserMethodV1 postCreateUserV1Method = new PostUserMethodV1(INVALID_USERNAME, password, email);
        apiExecutor.expectStatus(postCreateUserV1Method, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postCreateUserV1Method);
    }

    @Test
    public void testCreateUserWithEmptyEmail() {
        String username = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        String password = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        PostUserMethodV1 postCreateUserV1Method = new PostUserMethodV1(username, password, EMPTY_EMAIL);
        apiExecutor.expectStatus(postCreateUserV1Method, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postCreateUserV1Method);
    }

    @Test
    public void testCreateUserWithInvalidEmail() {
        String username = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        String password = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        PostUserMethodV1 postCreateUserV1Method = new PostUserMethodV1(username, password, INVALID_EMAIL);
        apiExecutor.expectStatus(postCreateUserV1Method, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postCreateUserV1Method);
    }

    @Test
    public void testCreateUserWithEmptyPassword() {
        String username = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        String password = "";
        String email = "TEST_".concat(RandomStringUtils.randomAlphabetic(15)).concat("@gmail.com");
        PostUserMethodV1 postCreateUserV1Method = new PostUserMethodV1(username, password, email);
        apiExecutor.expectStatus(postCreateUserV1Method, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postCreateUserV1Method);
    }

    @Test
    public void testUpdateUserEmailWithEmptyEmail() {
        UserV1ServiceAPIImpl userV1ServiceAPIImpl = new UserV1ServiceAPIImpl();
        String username = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        String password = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        String email = "TEST_".concat(RandomStringUtils.randomAlphabetic(15)).concat("@gmail.com");
        userV1ServiceAPIImpl.create(username, password, email);
        int userId = userV1ServiceAPIImpl.getUserId(username);
        String newEmail = EMPTY_EMAIL;
        PatchUserV1Method patchUserV1Method = new PatchUserV1Method(userId, EMAIL_KEY_FOR_UPDATE, newEmail);
        apiExecutor.expectStatus(patchUserV1Method, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(patchUserV1Method);
    }

    @Test
    public void testUpdateUserEmailWithInvalidEmail() {
        UserV1ServiceAPIImpl userV1ServiceAPIImpl = new UserV1ServiceAPIImpl();
        String username = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        String password = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        String email = "TEST_".concat(RandomStringUtils.randomAlphabetic(15)).concat("@gmail.com");
        userV1ServiceAPIImpl.create(username, password, email);
        int userId = userV1ServiceAPIImpl.getUserId(username);
        String newEmail = INVALID_EMAIL;
        PatchUserV1Method patchUserV1Method = new PatchUserV1Method(userId, EMAIL_KEY_FOR_UPDATE, newEmail);
        apiExecutor.expectStatus(patchUserV1Method, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(patchUserV1Method);
    }

    @Test
    public void testUpdateUserStatusWithPatchWithIncorrectData() {
        UserV1ServiceAPIImpl userV1ServiceAPIImpl = new UserV1ServiceAPIImpl();
        String username = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        String password = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        String email = "TEST_".concat(RandomStringUtils.randomAlphabetic(15)).concat("@gmail.com");
        userV1ServiceAPIImpl.create(username, password, email);
        int userId = userV1ServiceAPIImpl.getUserId(username);
        String newStatus = "INACTIVE";
        PatchUserV1Method patchUserV1Method = new PatchUserV1Method(userId, STATUS_KEY_FOR_UPDATE, newStatus);
        apiExecutor.expectStatus(patchUserV1Method, HTTPStatusCodeType.NO_CONTENT);
        apiExecutor.callApiMethod(patchUserV1Method);
        String actualStatus = userV1ServiceAPIImpl.getStatus(username);
        Assert.assertEquals(actualStatus, newStatus, "Status is not change!");
    }

    private boolean verifyIfEmailWasDelivered(String expStatus) {
        final int lastEmailIndex = 0;
        final int emailsCount = 1;
        LOGGER.info("Will get last email from inbox.");
        EMAIL.waitForEmailDeliveredResetPassword(new Date(), expStatus); // decency from connection, wait a little bit
        EmailMsg email = EMAIL.getInbox(emailsCount)[lastEmailIndex];
        return email.getContent().contains(expStatus);
    }

    @Test
    public void testSendPasswordReset() {
        PostPasswordResetMethodV1 postPasswordResetMethodV1 = new PostPasswordResetMethodV1(GMAIL_EMAIL);
        apiExecutor.expectStatus(postPasswordResetMethodV1, HTTPStatusCodeType.ACCEPTED);
        apiExecutor.callApiMethod(postPasswordResetMethodV1);
        verifyIfEmailWasDelivered(EXPECTED_MESSAGE);
    }
}


