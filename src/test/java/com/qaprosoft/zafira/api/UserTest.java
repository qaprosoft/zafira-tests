package com.qaprosoft.zafira.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.user.v1.*;
import com.qaprosoft.zafira.bo.User;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.constant.ConstantName;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.domain.EmailMsg;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.manager.EmailManager;
import com.qaprosoft.zafira.service.impl.GroupServiceIamImpl;
import com.qaprosoft.zafira.service.impl.InvitationServiceV1Impl;
import com.qaprosoft.zafira.service.impl.UserV1ServiceAPIImpl;
import com.qaprosoft.zafira.util.CryptoUtil;
import io.restassured.path.json.JsonPath;
import org.apache.commons.lang3.RandomStringUtils;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class UserTest extends ZafiraAPIBaseTest {
    private static final Logger LOGGER = LoggerFactory.getLogger((UserTest.class));
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
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private int userId;

    @AfterMethod
    public void testDeleteUser() {
        new UserV1ServiceAPIImpl().deleteUserById(userId);
    }

    @Test
    public void testSearchUserByCriteria() {
        GetUserByCriteriaV1Method getUserByCriteriaV1Method = new GetUserByCriteriaV1Method(QUERY, STATUS);
        apiExecutor.expectStatus(getUserByCriteriaV1Method, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getUserByCriteriaV1Method);
        apiExecutor.validateResponse(getUserByCriteriaV1Method, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testCreateUser() throws IOException {
        final String USER_NAME = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        final String PASSWORD = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        final String EMAIL = "TEST_".concat(RandomStringUtils.randomAlphabetic(15)).concat("@gmail.com");
        PostUserMethodV1 postCreateUserV1Method = new PostUserMethodV1(USER_NAME, PASSWORD, EMAIL);
        apiExecutor.expectStatus(postCreateUserV1Method, HTTPStatusCodeType.CREATED);
        String rs = apiExecutor.callApiMethod(postCreateUserV1Method);
        apiExecutor.validateResponse(postCreateUserV1Method,
                JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        User user = MAPPER.readValue(rs, User.class);
        userId = user.getId();
        Assert.assertEquals(EMAIL, user.getEmail(), "Email is not as expected!");
        LOGGER.info("Email is " + user.getEmail());
        LOGGER.info("UserId is " + user.getId());
        String getUser = new UserV1ServiceAPIImpl().getUserById(userId);
        User userActual = MAPPER.readValue(getUser, User.class);
        Assert.assertEquals(userActual.getEmail(), user.getEmail());
    }

    @Test
    public void testCreateUserByInvitationToken() throws IOException {
        final String USER_NAME = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        final String PASSWORD = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        final String EMAIL = "TEST_".concat(RandomStringUtils.randomAlphabetic(15)).concat("@gmail.com");
        final String invitationToken = new InvitationServiceV1Impl().getInvitationToken(EMAIL);
        PostUserByInvitationTokenV1Method postUserByInvitationTokenV1Method =
                new PostUserByInvitationTokenV1Method(USER_NAME, PASSWORD, invitationToken, EMAIL);
        apiExecutor.expectStatus(postUserByInvitationTokenV1Method, HTTPStatusCodeType.CREATED);
        String rs = apiExecutor.callApiMethod(postUserByInvitationTokenV1Method);
        userId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        apiExecutor.validateResponse(postUserByInvitationTokenV1Method, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        User user = MAPPER.readValue(rs, User.class);
        String getUser = new UserV1ServiceAPIImpl().getUserById(userId);
        User userActual = MAPPER.readValue(getUser, User.class);
        Assert.assertEquals(userActual.getEmail(), user.getEmail());
    }

    @Test
    public void testGetUserByUsernameV1() throws IOException {
        final String USER_NAME = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        final String PASSWORD = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        final String EMAIL = "TEST_".concat(RandomStringUtils.randomAlphabetic(15)).concat("@gmail.com");
        userId = new UserV1ServiceAPIImpl().createAndGetId(USER_NAME, PASSWORD, EMAIL);
        GetUserByUsernameV1Method getUserByUsernameV1Method = new GetUserByUsernameV1Method(USER_NAME);
        apiExecutor.expectStatus(getUserByUsernameV1Method, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getUserByUsernameV1Method);
        User user = MAPPER.readValue(rs, User.class);
        Assert.assertEquals(EMAIL, user.getEmail(), "Email is not as expected!");
        apiExecutor.validateResponse(getUserByUsernameV1Method, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testGetUserByNonExistingUsername() {
        final String USER_NAME = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        final String PASSWORD = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        final String EMAIL = "TEST_".concat(RandomStringUtils.randomAlphabetic(15)).concat("@gmail.com");
        userId = new UserV1ServiceAPIImpl().createAndGetId(USER_NAME, PASSWORD, EMAIL);
        new UserV1ServiceAPIImpl().deleteUserById(userId);
        GetUserByUsernameV1Method getUserByUsernameV1Method = new GetUserByUsernameV1Method(USER_NAME);
        apiExecutor.expectStatus(getUserByUsernameV1Method, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(getUserByUsernameV1Method);
    }

    @Test
    public void testGetUserByIdV1() {
        final String USER_NAME = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        final String PASSWORD = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        final String EMAIL = "TEST_".concat(RandomStringUtils.randomAlphabetic(15)).concat("@gmail.com");
        new UserV1ServiceAPIImpl().create(USER_NAME, PASSWORD, EMAIL);
        userId = new UserV1ServiceAPIImpl().getUserId(USER_NAME);
        GetUserByIdV1Method getUserByIdV1Method = new GetUserByIdV1Method(userId);
        apiExecutor.expectStatus(getUserByIdV1Method, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getUserByIdV1Method);
        apiExecutor.validateResponse(getUserByIdV1Method, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testGetUserByByNonExistingIdV1() {
        final String USER_NAME = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        final String PASSWORD = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        final String EMAIL = "TEST_".concat(RandomStringUtils.randomAlphabetic(15)).concat("@gmail.com");
        new UserV1ServiceAPIImpl().create(USER_NAME, PASSWORD, EMAIL);
        userId = new UserV1ServiceAPIImpl().getUserId(USER_NAME);
        new UserV1ServiceAPIImpl().deleteUserById(userId);
        GetUserByIdV1Method getUserByIdV1Method = new GetUserByIdV1Method(userId);
        apiExecutor.expectStatus(getUserByIdV1Method, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(getUserByIdV1Method);
    }

    @Test
    public void testDeleteUserFromGroupV1() {
        final String USER_NAME = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        final String PASSWORD = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        final String EMAIL = "TEST_".concat(RandomStringUtils.randomAlphabetic(15)).concat("@gmail.com");
        userId = new UserV1ServiceAPIImpl().createAndGetId(USER_NAME, PASSWORD, EMAIL);
        GroupServiceIamImpl groupService = new GroupServiceIamImpl();
        String allGroupsRs = groupService.getAllGroupsString();
        Assert.assertTrue(allGroupsRs.contains(USER_NAME), "User was not add to group!");
        checkUserExistAndDeleteFromGroupV1(userId);
        String groupAfterDel = groupService.getAllGroupsString();
        Assert.assertFalse(groupAfterDel.contains(USER_NAME), "User was not delete from group");
    }

    @Test
    public void testDeleteUserByIdV1() {
        final String USER_NAME = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        final String PASSWORD = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        final String EMAIL = "TEST_".concat(RandomStringUtils.randomAlphabetic(15)).concat("@gmail.com");
        userId = new UserV1ServiceAPIImpl().createAndGetId(USER_NAME, PASSWORD, EMAIL);
        DeleteUserByIdV1Method getUserByCriteriaV1Method = new DeleteUserByIdV1Method(userId);
        apiExecutor.expectStatus(getUserByCriteriaV1Method, HTTPStatusCodeType.NO_CONTENT);
        apiExecutor.callApiMethod(getUserByCriteriaV1Method);
        List<Integer> userIds = new UserV1ServiceAPIImpl().getAllUserIds();
        LOGGER.info(userIds.toString());
        Assert.assertFalse(userIds.contains(userId), "User with id= " + userId + "was not deleted!");
    }

    @Test
    public void testDeleteUserByByNonExistingIdV1() {
        final String USER_NAME = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        final String PASSWORD = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        final String EMAIL = "TEST_".concat(RandomStringUtils.randomAlphabetic(15)).concat("@gmail.com");
        userId = new UserV1ServiceAPIImpl().createAndGetId(USER_NAME, PASSWORD, EMAIL);
        new UserV1ServiceAPIImpl().deleteUserById(userId);
        DeleteUserByIdV1Method getUserByCriteriaV1Method = new DeleteUserByIdV1Method(userId);
        apiExecutor.expectStatus(getUserByCriteriaV1Method, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(getUserByCriteriaV1Method);
    }

    @Test
    public void testAddUserToGroupV1() {
        final String USER_NAME = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        final String PASSWORD = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        final String EMAIL = "TEST_".concat(RandomStringUtils.randomAlphabetic(15)).concat("@gmail.com");
        userId = new UserV1ServiceAPIImpl().createAndGetId(USER_NAME, PASSWORD, EMAIL);
        checkUserExistAndAddToGroupV1(USER_NAME, userId);
    }

    @Test
    public void testUpdateUserPassword() {
        final String USER_NAME = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        final String PASSWORD = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        final String EMAIL = "TEST_".concat(RandomStringUtils.randomAlphabetic(15)).concat("@gmail.com");
        final String NEW_PASSWORD = "new111";
        userId = new UserV1ServiceAPIImpl().createAndGetId(USER_NAME, PASSWORD, EMAIL);
        PostNewUserPasswordMethodV1 postNewUserPasswordMethodV1 = new PostNewUserPasswordMethodV1(userId, PASSWORD, NEW_PASSWORD);
        apiExecutor.expectStatus(postNewUserPasswordMethodV1, HTTPStatusCodeType.NO_CONTENT);
        apiExecutor.callApiMethod(postNewUserPasswordMethodV1);
    }

    private void checkUserExistAndAddToGroupV1(String username, int userId) {
        GroupServiceIamImpl groupService = new GroupServiceIamImpl();
        List<Integer> allGroupsIds = groupService.getAllGroupsIds();
        List<Integer> allUserGroupIds = new UserV1ServiceAPIImpl().getAllUserGroupIds(userId);
        LOGGER.info("All allGroupsIds: " + allGroupsIds);
        LOGGER.info("All userGroup ids: " + allUserGroupIds);
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
        LOGGER.info("All group ids: " + allGroupsIds);
        LOGGER.info("All userGroup ids: " + allUserGroupIds);
        for (int i = 1; i <= Collections.max(allGroupsIds); ++i) {
            if ((allGroupsIds.contains(i)) & (allUserGroupIds.contains(i))) {
                DeleteUserFromGroupV1Method deleteUserFromGroupV1Method = new DeleteUserFromGroupV1Method(i, userId);
                apiExecutor.expectStatus(deleteUserFromGroupV1Method, HTTPStatusCodeType.NO_CONTENT);
                apiExecutor.callApiMethod(deleteUserFromGroupV1Method);
            }
        }
    }

    @Test
    public void testUpdateUserEmail() throws IOException {
        UserV1ServiceAPIImpl userV1ServiceAPIImpl = new UserV1ServiceAPIImpl();
        final String USER_NAME = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        final String PASSWORD = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        final String EMAIL = "TEST_".concat(RandomStringUtils.randomAlphabetic(15)).concat("@gmail.com");
        userId = new UserV1ServiceAPIImpl().createAndGetId(USER_NAME, PASSWORD, EMAIL);
        final String NEW_EMAIL = "new".concat(EMAIL);
        PatchUserV1Method patchUserV1Method = new PatchUserV1Method(userId, EMAIL_KEY_FOR_UPDATE, NEW_EMAIL);
        apiExecutor.expectStatus(patchUserV1Method, HTTPStatusCodeType.NO_CONTENT);
        apiExecutor.callApiMethod(patchUserV1Method);
        String actualEmail = userV1ServiceAPIImpl.getEmail(USER_NAME);
        Assert.assertEquals(actualEmail, NEW_EMAIL, "Email is not changed!");
        String getUser = new UserV1ServiceAPIImpl().getUserById(userId);
        User userActual = MAPPER.readValue(getUser, User.class);
        Assert.assertEquals(userActual.getEmail(), NEW_EMAIL,"Email is not changed!");
    }

    @Test
    public void testUpdateUserStatusWithPatch() throws IOException {
        UserV1ServiceAPIImpl userV1ServiceAPIImpl = new UserV1ServiceAPIImpl();
        final String USER_NAME = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        final String PASSWORD = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        final String EMAIL = "TEST_".concat(RandomStringUtils.randomAlphabetic(15)).concat("@gmail.com");
        userId = new UserV1ServiceAPIImpl().createAndGetId(USER_NAME, PASSWORD, EMAIL);
        final String NEW_STATUS = ConstantName.INACTIVE_STATUS;
        PatchUserV1Method patchUserV1Method = new PatchUserV1Method(userId, STATUS_KEY_FOR_UPDATE, NEW_STATUS);
        apiExecutor.expectStatus(patchUserV1Method, HTTPStatusCodeType.NO_CONTENT);
        apiExecutor.callApiMethod(patchUserV1Method);
        String actualStatus = userV1ServiceAPIImpl.getStatus(USER_NAME);
        Assert.assertEquals(actualStatus, NEW_STATUS, "Status was not changed!");
        String getUser = new UserV1ServiceAPIImpl().getUserById(userId);
        User userActual = MAPPER.readValue(getUser, User.class);
        Assert.assertEquals(userActual.getStatus(), NEW_STATUS,"Status was not changed!");
    }

    @Test
    public void testUpdateUserStatusWithPatchWithInvalidStatus() {
        final String USER_NAME = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        final String PASSWORD = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        final String EMAIL = "TEST_".concat(RandomStringUtils.randomAlphabetic(15)).concat("@gmail.com");
        userId = new UserV1ServiceAPIImpl().createAndGetId(USER_NAME, PASSWORD, EMAIL);
        final String NEW_STATUS = ConstantName.INACTIVE_STATUS + 1;
        PatchUserV1Method patchUserV1Method = new PatchUserV1Method(userId, STATUS_KEY_FOR_UPDATE, NEW_STATUS);
        apiExecutor.expectStatus(patchUserV1Method, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(patchUserV1Method);
    }

    @Test
    public void testCreateUserWithEmptyUserName() {
        final String PASSWORD = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        final String EMAIL = "TEST_".concat(RandomStringUtils.randomAlphabetic(15)).concat("@gmail.com");
        PostUserMethodV1 postCreateUserV1Method = new PostUserMethodV1(EMPTY_USERNAME, PASSWORD, EMAIL);
        apiExecutor.expectStatus(postCreateUserV1Method, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postCreateUserV1Method);
    }

    @Test
    public void testCreateUserWithExistingUserName() {
        final String USER_NAME = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        final String PASSWORD = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        final String EMAIL = "TEST_".concat(RandomStringUtils.randomAlphabetic(15)).concat("@gmail.com");
        userId = new UserV1ServiceAPIImpl().createAndGetId(USER_NAME, PASSWORD, EMAIL);
        PostUserMethodV1 postCreateUserV1Method = new PostUserMethodV1(USER_NAME, PASSWORD, EMAIL);
        apiExecutor.expectStatus(postCreateUserV1Method, HTTPStatusCodeType.FORBIDDEN);
        apiExecutor.callApiMethod(postCreateUserV1Method);
    }

    @Test
    public void testCreateUserWithInvalidUserName() {
        final String PASSWORD = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        final String EMAIL = "TEST_".concat(RandomStringUtils.randomAlphabetic(15)).concat("@gmail.com");
        PostUserMethodV1 postCreateUserV1Method = new PostUserMethodV1(INVALID_USERNAME, PASSWORD, EMAIL);
        apiExecutor.expectStatus(postCreateUserV1Method, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postCreateUserV1Method);
    }

    @Test
    public void testCreateUserWithEmptyEmail() {
        final String USER_NAME = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        final String PASSWORD = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        PostUserMethodV1 postCreateUserV1Method = new PostUserMethodV1(USER_NAME, PASSWORD, EMPTY_EMAIL);
        apiExecutor.expectStatus(postCreateUserV1Method, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postCreateUserV1Method);
    }

    @Test
    public void testCreateUserWithInvalidEmail() {
        final String USER_NAME = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        final String PASSWORD = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        PostUserMethodV1 postCreateUserV1Method = new PostUserMethodV1(USER_NAME, PASSWORD, INVALID_EMAIL);
        apiExecutor.expectStatus(postCreateUserV1Method, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postCreateUserV1Method);
    }

    @Test
    public void testCreateUserWithEmptyPassword() {
        final String USER_NAME = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        final String PASSWORD = "";
        final String EMAIL = "TEST_".concat(RandomStringUtils.randomAlphabetic(15)).concat("@gmail.com");
        PostUserMethodV1 postCreateUserV1Method = new PostUserMethodV1(USER_NAME, PASSWORD, EMAIL);
        apiExecutor.expectStatus(postCreateUserV1Method, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postCreateUserV1Method);
    }

    @Test
    public void testUpdateUserEmailWithEmptyEmail() {
        final String USER_NAME = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        final String PASSWORD = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        final String EMAIL = "TEST_".concat(RandomStringUtils.randomAlphabetic(15)).concat("@gmail.com");
        userId = new UserV1ServiceAPIImpl().createAndGetId(USER_NAME, PASSWORD, EMAIL);
        final String NEW_EMAIL = EMPTY_EMAIL;
        PatchUserV1Method patchUserV1Method = new PatchUserV1Method(userId, EMAIL_KEY_FOR_UPDATE, NEW_EMAIL);
        apiExecutor.expectStatus(patchUserV1Method, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(patchUserV1Method);
    }

    @Test
    public void testUpdateUserEmailWithInvalidEmail() {
        final String USER_NAME = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        final String PASSWORD = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        final String EMAIL = "TEST_".concat(RandomStringUtils.randomAlphabetic(15)).concat("@gmail.com");
        userId = new UserV1ServiceAPIImpl().createAndGetId(USER_NAME, PASSWORD, EMAIL);
        final String newEmail = INVALID_EMAIL;
        PatchUserV1Method patchUserV1Method = new PatchUserV1Method(userId, EMAIL_KEY_FOR_UPDATE, newEmail);
        apiExecutor.expectStatus(patchUserV1Method, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(patchUserV1Method);
    }

    @Test
    public void testUpdateUserStatusWithPatchWithIncorrectData() {
        UserV1ServiceAPIImpl userV1ServiceAPIImpl = new UserV1ServiceAPIImpl();
        final String USER_NAME = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        final String PASSWORD = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        final String EMAIL = "TEST_".concat(RandomStringUtils.randomAlphabetic(15)).concat("@gmail.com");
        userId = new UserV1ServiceAPIImpl().createAndGetId(USER_NAME, PASSWORD, EMAIL);
        final String NEW_STATUS = "NEW_STATUS";
        PatchUserV1Method patchUserV1Method = new PatchUserV1Method(userId, STATUS_KEY_FOR_UPDATE, NEW_STATUS);
        apiExecutor.expectStatus(patchUserV1Method, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(patchUserV1Method);
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
        final String USER_NAME = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        final String PASSWORD = "TEST_".concat(RandomStringUtils.randomAlphabetic(10));
        userId = new UserV1ServiceAPIImpl().createAndGetId(USER_NAME, PASSWORD, GMAIL_EMAIL);
        PostPasswordResetMethodV1 postPasswordResetMethodV1 = new PostPasswordResetMethodV1(GMAIL_EMAIL);
        apiExecutor.expectStatus(postPasswordResetMethodV1, HTTPStatusCodeType.ACCEPTED);
        apiExecutor.callApiMethod(postPasswordResetMethodV1);
        verifyIfEmailWasDelivered(EXPECTED_MESSAGE);
    }
}


