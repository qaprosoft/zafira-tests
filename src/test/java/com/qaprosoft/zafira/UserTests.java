package com.qaprosoft.zafira;

import com.qaprosoft.carina.core.foundation.utils.ownership.MethodOwner;
import com.qaprosoft.zafira.gui.DashboardPage;
import com.qaprosoft.zafira.gui.UserPage;
import com.qaprosoft.zafira.gui.component.modals.ChangePasswordModal;
import com.qaprosoft.zafira.gui.component.modals.UserInfoModal;
import com.qaprosoft.zafira.gui.component.subheader.UserSubHeader;
import com.qaprosoft.zafira.gui.component.table.Pagination;
import com.qaprosoft.zafira.gui.component.table.row.UserTableRow;
import com.qaprosoft.zafira.gui.message.InputMessage;
import com.qaprosoft.zafira.models.dto.user.UserType;
import com.qaprosoft.zafira.service.AuthService;
import com.qaprosoft.zafira.service.PaginationService;
import com.qaprosoft.zafira.service.SidebarService;
import com.qaprosoft.zafira.service.UserService;
import com.qaprosoft.zafira.service.impl.AuthServiceImpl;
import com.qaprosoft.zafira.service.impl.PaginationServiceImpl;
import com.qaprosoft.zafira.service.impl.SidebarServiceImpl;
import com.qaprosoft.zafira.service.impl.UserServiceImpl;
import com.qaprosoft.zafira.util.CommonUtils;
import org.apache.commons.collections.keyvalue.AbstractKeyValue;
import org.apache.commons.collections.keyvalue.DefaultKeyValue;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

import static com.qaprosoft.zafira.models.db.User.Status.INACTIVE;

public class UserTests extends BaseTest {

    private static final String USERNAME_REQUIRED_ERROR_MESSAGE = "Username required";
    private static final String USERNAME_LENGTH_ERROR_MESSAGE = "Username must be between 3 and 50 characters";
    private static final String USERNAME_PATTERN_ERROR_MESSAGE = "Username must have only latin letters, numbers and special characters";
    private static final String FIRST_LAST_NAME_LENGTH_ERROR_MESSAGE = "Must be between 2 and 50 characters";
    private static final String FIRST_LAST_NAME_PATTERN_ERROR_MESSAGE = "Must have only latin letters";
    private static final String EMAIL_REQUIRED_ERROR_MESSAGE = "Email required";
    private static final String EMAIL_PATTERN_ERROR_MESSAGE = "Invalid format";
    private static final String PASSWORD_REQUIRED_ERROR_MESSAGE = "Password required";
    private static final String PASSWORD_LENGTH_ERROR_MESSAGE = "Password must be between 5 and 50 characters";

    private static final List<AbstractKeyValue> USER_MODAL_INPUTS_TEST_DATA = List.of(
            new DefaultKeyValue((Function<UserInfoModal, InputMessage>) userInfoModal -> {CommonUtils.clickElementAndOutside(userInfoModal.getUsernameInput());return userInfoModal.getUsernameInputMessage();}, USERNAME_REQUIRED_ERROR_MESSAGE),
            new DefaultKeyValue((Function<UserInfoModal, InputMessage>) userInfoModal -> {userInfoModal.typeUsername("te");return userInfoModal.getUsernameInputMessage();}, USERNAME_LENGTH_ERROR_MESSAGE),
            new DefaultKeyValue((Function<UserInfoModal, InputMessage>) userInfoModal -> {userInfoModal.typeUsername(RandomStringUtils.randomAlphabetic(51));return userInfoModal.getUsernameInputMessage();}, USERNAME_LENGTH_ERROR_MESSAGE),
            new DefaultKeyValue((Function<UserInfoModal, InputMessage>) userInfoModal -> {userInfoModal.typeUsername("/test");return userInfoModal.getUsernameInputMessage();}, USERNAME_PATTERN_ERROR_MESSAGE),
            new DefaultKeyValue((Function<UserInfoModal, InputMessage>) userInfoModal -> {userInfoModal.typeFirstName("t");return userInfoModal.getFirstNameInputMessage();}, FIRST_LAST_NAME_LENGTH_ERROR_MESSAGE),
            new DefaultKeyValue((Function<UserInfoModal, InputMessage>) userInfoModal -> {userInfoModal.typeFirstName(RandomStringUtils.randomAlphabetic(51));return userInfoModal.getFirstNameInputMessage();}, FIRST_LAST_NAME_LENGTH_ERROR_MESSAGE),
            new DefaultKeyValue((Function<UserInfoModal, InputMessage>) userInfoModal -> {userInfoModal.typeFirstName("/test");return userInfoModal.getFirstNameInputMessage();}, FIRST_LAST_NAME_PATTERN_ERROR_MESSAGE),
            new DefaultKeyValue((Function<UserInfoModal, InputMessage>) userInfoModal -> {userInfoModal.typeLastName("t");return userInfoModal.getLastNameInputMessage();}, FIRST_LAST_NAME_LENGTH_ERROR_MESSAGE),
            new DefaultKeyValue((Function<UserInfoModal, InputMessage>) userInfoModal -> {userInfoModal.typeLastName(RandomStringUtils.randomAlphabetic(51));return userInfoModal.getLastNameInputMessage();}, FIRST_LAST_NAME_LENGTH_ERROR_MESSAGE),
            new DefaultKeyValue((Function<UserInfoModal, InputMessage>) userInfoModal -> {userInfoModal.typeLastName("/test");return userInfoModal.getLastNameInputMessage();}, FIRST_LAST_NAME_PATTERN_ERROR_MESSAGE),
            new DefaultKeyValue((Function<UserInfoModal, InputMessage>) userInfoModal -> {CommonUtils.clickElementAndOutside(userInfoModal.getEmailInput());return userInfoModal.getEmailInputMessage();}, EMAIL_REQUIRED_ERROR_MESSAGE),
            new DefaultKeyValue((Function<UserInfoModal, InputMessage>) userInfoModal -> {userInfoModal.typeEmail("test");return userInfoModal.getEmailInputMessage();}, EMAIL_PATTERN_ERROR_MESSAGE),
            new DefaultKeyValue((Function<UserInfoModal, InputMessage>) userInfoModal -> {CommonUtils.clickElementAndOutside(userInfoModal.getPasswordInput());return userInfoModal.getPasswordInputMessage();}, PASSWORD_REQUIRED_ERROR_MESSAGE),
            new DefaultKeyValue((Function<UserInfoModal, InputMessage>) userInfoModal -> {userInfoModal.typePassword("test");return userInfoModal.getPasswordInputMessage();}, PASSWORD_LENGTH_ERROR_MESSAGE),
            new DefaultKeyValue((Function<UserInfoModal, InputMessage>) userInfoModal -> {userInfoModal.typePassword(RandomStringUtils.randomAlphabetic(51));;return userInfoModal.getPasswordInputMessage();}, PASSWORD_LENGTH_ERROR_MESSAGE)
    );

    @BeforeMethod
    public void setup() {
        DashboardPage dashboardPage = signin();
        SidebarService sidebarService = new SidebarServiceImpl(getDriver(), dashboardPage);
        sidebarService.goToUserPage();
    }

    @Test
    @MethodOwner(owner = "brutskov")
    public void verifyNavigationTest() {
        UserPage userPage = new UserPage(getDriver());
        UserSubHeader userSubHeader = userPage.getUserSubHeader();
        Assert.assertEquals(userSubHeader.getTitleText(), "Users", "User page subheader title is incorrect");
        Assert.assertTrue(userSubHeader.getSearchInput().isElementPresent(2), "User page subheader search input does not exist");
        Assert.assertTrue(userSubHeader.getNewUserButton().isElementPresent(2), "User page subheader bew user button does not present");
    }

    @Test
    @MethodOwner(owner = "brutskov")
    public void verifyUserCreationTest() {
        UserServiceImpl userService = new UserServiceImpl(getDriver());
        UserInfoModal userInfoModal = userService.clickNewUserButton();

        Assert.assertEquals(userInfoModal.getTitleText(), "User", "New user modal title is incorrect");
        Assert.assertTrue(userInfoModal.getCloseButton().isElementPresent(2), "New user modal close button is not present");

        Assert.assertTrue(userInfoModal.getUsernameInput().isElementPresent(2), "Username input does not present");
        Assert.assertTrue(userInfoModal.getUsernameInput().getElement().isEnabled(), "Username input is disabled");

        Assert.assertTrue(userInfoModal.getFirstNameInput().isElementPresent(2), "First name input does not present");
        Assert.assertTrue(userInfoModal.getFirstNameInput().getElement().isEnabled(), "First name input is disabled");

        Assert.assertTrue(userInfoModal.getLastNameInput().isElementPresent(2), "Last name input does not present");
        Assert.assertTrue(userInfoModal.getLastNameInput().getElement().isEnabled(), "Last name input is disabled");

        Assert.assertTrue(userInfoModal.getEmailInput().isElementPresent(2), "Email input does not present");
        Assert.assertTrue(userInfoModal.getEmailInput().getElement().isEnabled(), "Email input is disabled");

        Assert.assertTrue(userInfoModal.getPasswordInput().isElementPresent(2), "Password input does not present");
        Assert.assertTrue(userInfoModal.getPasswordInput().getElement().isEnabled(), "Password input is disabled");

        Assert.assertTrue(userInfoModal.getCreateButton().isElementPresent(2), "Create button does not present");
        Assert.assertFalse(userInfoModal.getCreateButton().getElement().isEnabled(), "Create button is enabled");

        USER_MODAL_INPUTS_TEST_DATA.forEach(errorMessageObject -> {
            InputMessage inputMessage = ((Function<UserInfoModal, InputMessage>) errorMessageObject.getKey()).apply(userInfoModal);
            CommonUtils.clickOutside(userInfoModal.getTitle().getElement(), getDriver());
            pause(0.2);
            Assert.assertEquals(inputMessage.getMessages().get(0).getText(), errorMessageObject.getValue().toString(), "Invalid error message");
        });

        String username = RandomStringUtils.randomAlphabetic(4);
        String password = RandomStringUtils.randomAlphabetic(20);

        userInfoModal.typeUsername(username);
        userInfoModal.typeFirstName(RandomStringUtils.randomAlphabetic(4));
        userInfoModal.typeLastName(RandomStringUtils.randomAlphabetic(4));
        userInfoModal.typeEmail(RandomStringUtils.randomAlphabetic(4) + "@gmail.com");
        userInfoModal.typePassword(password);
        Assert.assertTrue(userInfoModal.getCreateButton().getElement().isEnabled(), "Create user button is disabled");

        userInfoModal.clickCreateButton();
        userService.waitProgressLinear();
        UserPage userPage = new UserPage(getDriver());
        Assert.assertEquals(userPage.getSuccessAlertText(), "User created", "Success message is incorrect");

        AuthService authService = new AuthServiceImpl(getDriver());
        authService.logout();
        DashboardPage dashboardPage = authService.signin(username, password);
        Assert.assertTrue(dashboardPage.isPageOpened(), "Cannot login with created user");
    }

    @Test
    @MethodOwner(owner = "brutskov")
    public void verifySearchTest() {
        UserService userService = new UserServiceImpl(getDriver());
        List<UserType> userTypes = userService.buildUsers(1);
        UserType userType = userTypes.get(0);

        UserPage userPage = userService.search(Long.toString(userType.getId()));
        UserTableRow row = userPage.getUserTable().getUserTableRows().get(0);
        verifyUserTableRow(row, userType);

        userType = userService.buildUsers(1).get(0);
        userPage = userService.search(userType.getUsername());
        row = userPage.getUserTable().getUserTableRows().get(0);
        verifyUserTableRow(row, userType);

        userType = userService.buildUsers(1).get(0);
        userPage = userService.search(userType.getFirstName());
        row = userPage.getUserTable().getUserTableRows().get(0);
        verifyUserTableRow(row, userType);

        userType = userService.buildUsers(1).get(0);
        userPage = userService.search(userType.getLastName());
        row = userPage.getUserTable().getUserTableRows().get(0);
        verifyUserTableRow(row, userType);

        userService.deactivateUser(row);
        pause(ANIMATION_TIMEOUT);
        userPage.getUserSubHeader().getSearchInput().getElement().clear();
        pause(0.8);
        userPage.waitProgressLinear();
        userType.setStatus(INACTIVE);
        userPage = userService.search(INACTIVE.name());
        row = userPage.getUserTable().getUserTableRows().get(0);
        verifyUserTableRow(row, userType);

        userService.activateUser(row);
    }

    @Test
    @MethodOwner(owner = "brutskov")
    public void verifyPaginationTest() {
        UserPage userPage = new UserPage(getDriver());
        PaginationService paginationService = new PaginationServiceImpl(getDriver(), userPage.getUserTable());
        int totalUsers = paginationService.getToItemValue();

        int countToGenerate = totalUsers < 25 ? 25 - totalUsers : totalUsers;
        UserService userService = new UserServiceImpl(getDriver());
        userService.buildUsers(countToGenerate);
        userPage.refresh();

        Pagination pagination = userPage.getUserTable().getPagination();

        verifyPagination(userPage, pagination::clickNavigateNextButton);
        verifyPagination(userPage, pagination::clickNavigateBeforeButton);
        verifyPagination(userPage, pagination::clickNavigateLastButton);
        verifyPagination(userPage, pagination::clickNavigateFirstButton);
    }

    @Test
    @MethodOwner(owner = "brutskov")
    public void verifyEditUserTest() {
        UserServiceImpl userService = new UserServiceImpl(getDriver());
        UserType userType = userService.buildUsers(1).get(0);

        UserPage userPage = userService.search(userType.getUsername());

        UserInfoModal userInfoModal = userService.clickEditUserButton(userPage.getUserTable().getUserTableRows().get(0));
        verifyUserEditModal(userInfoModal, userType);

        userType.setFirstName(RandomStringUtils.randomAlphabetic(15));
        userType.setLastName(RandomStringUtils.randomAlphabetic(15));
        userType.setEmail(RandomStringUtils.randomAlphabetic(15) + "@gmail.com");
        userInfoModal.typeFirstName(userType.getFirstName());
        userInfoModal.typeLastName(userType.getLastName());
        userInfoModal.typeEmail(userType.getEmail());
        userInfoModal.clickSaveButton();
        Assert.assertEquals(userPage.getSuccessAlertText(), "Profile changed", "Alert text is incorrect after profile editing");

        userPage.getSuccessAlert().waitUntilElementDisappear(5);
        verifyUserTableRow(userPage.getUserTable().getUserTableRows().get(0), userType);

        userPage.getUserSubHeader().getSearchInput().getElement().clear();
        userService.waitProgressLinear();

        userPage = userService.search(userType.getUsername());
        userInfoModal = userService.clickEditUserButton(userPage.getUserTable().getUserTableRows().get(0));
        verifyUserEditModal(userInfoModal, userType);
    }

    @Test
    @MethodOwner(owner = "brutskov")
    public void verifyDeactivationAndActivationTest() {
        UserServiceImpl userService = new UserServiceImpl(getDriver());
        UserType userType = userService.buildUsers(1).get(0);

        UserPage userPage = userService.search(userType.getUsername());
        userService.deactivateUser(userPage.getUserTable().getUserTableRows().get(0));
        pause(ANIMATION_TIMEOUT);
        Assert.assertEquals(userPage.getUserTable().getUserTableRows().get(0).getStatusLabelText(), "INACTIVE", "User status in incorrect after user status change");
        userPage.getUserSubHeader().getSearchInput().getElement().clear();
        userService.waitProgressLinear();

        userPage = userService.search(userType.getUsername());
        Assert.assertEquals(userPage.getUserTable().getUserTableRows().get(0).getStatusLabelText(), "INACTIVE", "User status in incorrect after users search");
        UserInfoModal userInfoModal = userService.clickEditUserButton(userPage.getUserTable().getUserTableRows().get(0));
        Assert.assertFalse(userInfoModal.getDeactivateButton().isElementPresent(2), "Deactivate button is present if user in inactive");
        userInfoModal.clickCloseButton();
        userService.activateUser(userPage.getUserTable().getUserTableRows().get(0));
        pause(ANIMATION_TIMEOUT);

        Assert.assertEquals(userPage.getUserTable().getUserTableRows().get(0).getStatusLabelText(), "ACTIVE", "User status in incorrect after user status change");
        userPage.getUserSubHeader().getSearchInput().getElement().clear();
        userService.waitProgressLinear();
        userPage = userService.search(userType.getUsername());
        Assert.assertEquals(userPage.getUserTable().getUserTableRows().get(0).getStatusLabelText(), "ACTIVE", "User status in incorrect after users search");
    }

    @Test
    @MethodOwner(owner = "brutskov")
    public void verifyChangePasswordTest() {
        UserServiceImpl userService = new UserServiceImpl(getDriver());
        UserType userType = userService.buildUsers(1).get(0);
        UserPage userPage = userService.search(userType.getUsername());

        ChangePasswordModal changePasswordModal = userService.clickChangePasswordButton(userPage.getUserTable().getUserTableRows().get(0));
        Assert.assertEquals(changePasswordModal.getTitleText(), "Change password", "Change password modal title is incorrect");
        Assert.assertTrue(changePasswordModal.getPasswordInput().getElement().isEnabled(), "Password input is disabled");
        Assert.assertTrue(changePasswordModal.getShowPasswordIcon().isElementPresent(2), "Show password icon is not present");
        Assert.assertFalse(changePasswordModal.getApplyButton().getElement().isEnabled(), "Apply password button is enabled");

        String newPassword = RandomStringUtils.randomAlphabetic(15);
        changePasswordModal.typePassword(newPassword);
        Assert.assertEquals(changePasswordModal.getPasswordInput().getAttribute("type"), "password", "Password input has not type password by default");
        CommonUtils.clickAndHold(changePasswordModal.getShowPasswordIcon(), getDriver());
        Assert.assertEquals(changePasswordModal.getPasswordInput().getAttribute("type"), "text", "Password input has not type text after show password button clicking");
        Assert.assertTrue(changePasswordModal.getApplyButton().getElement().isEnabled(), "Apply password button is disabled");
        changePasswordModal.clickApplyButton();
        Assert.assertEquals(userPage.getSuccessAlertText(), "Password changed", "Success alert after password changing has incorrect text");

        AuthService authService = new AuthServiceImpl(getDriver());
        authService.logout();
        DashboardPage dashboardPage = authService.signin(userType.getUsername(), newPassword);
        Assert.assertTrue(dashboardPage.isPageOpened(), "Password was not changed");
    }

    @Test
    @MethodOwner(owner = "brutskov")
    public void verifyPerformanceDashboardNavigationTest() {
        UserServiceImpl userService = new UserServiceImpl(getDriver());
        UserType userType = userService.buildUsers(1).get(0);
        UserPage userPage = userService.search(userType.getUsername());

        DashboardPage dashboardPage = userService.clickPerformanceButton(userPage.getUserTable().getUserTableRows().get(0));
        userService.waitProgressLinear();
        Assert.assertEquals(getDriver().getCurrentUrl(), dashboardPage.getPageURL() + "?userId=" + userType.getId(), "Performance button does not work correctly");
    }

    private void verifyUserTableRow(UserTableRow row, UserType userType) {
        UserService userService = new UserServiceImpl(getDriver());
        CommonUtils.hoverOn(row.getEmptyPhotoIcon(), getDriver());
        Assert.assertEquals(CommonUtils.getTooltipText(getDriver()), "#" + userType.getId(), "Id is incorrect");
        Assert.assertEquals(row.getUsernameLabelText(), userType.getUsername(), "Username is incorrect");
        Assert.assertEquals(row.getEmailLabelText(), userType.getEmail(), "Email is incorrect");
        Assert.assertEquals(userService.getFirstName(row), userType.getFirstName(), "First name is incorrect");
        Assert.assertEquals(userService.getLastName(row), userType.getLastName(), "Last name is incorrect");
        Assert.assertEquals(row.getStatusLabelText(), userType.getStatus().name(), "Status is incorrect");
        Assert.assertEquals(row.getSourceLabelText(), userType.getSource().name(), "Source is incorrect");
        Assert.assertEquals(row.getRegistrationDateLabelText(), CommonUtils.formatDate(new Date(), "MMM d, yyyy"), "Created date is incorrect");
        Assert.assertTrue(row.getMenuButton().isElementPresent(2), "Menu button is not present");
    }

    private void verifyPagination(UserPage userPage, Runnable paginationAction) {
        String firstRowUsername = userPage.getUserTable().getUserTableRows().get(0).getUsernameLabelText();
        paginationAction.run();
        UserServiceImpl userService = new UserServiceImpl(getDriver());
        userService.waitProgressLinear();
        UserTableRow firstRowAfterPagination = userPage.getUserTable().getUserTableRows().get(0);
        Assert.assertNotEquals(firstRowUsername, firstRowAfterPagination.getUsernameLabelText(), "Pagination does not work");
    }

    private void verifyUserEditModal(UserInfoModal userInfoModal, UserType userType) {
        Assert.assertFalse(userInfoModal.getUsernameInput().getElement().isEnabled(), "Username input is enabled in edit mode");
        Assert.assertTrue(userInfoModal.getFirstNameInput().getElement().isEnabled(), "First name input is disabled in edit mode");
        Assert.assertTrue(userInfoModal.getLastNameInput().getElement().isEnabled(), "Last name input is disabled in edit mode");
        Assert.assertTrue(userInfoModal.getEmailInput().getElement().isEnabled(), "Email input is disabled in edit mode");
        Assert.assertFalse(userInfoModal.getCreateButton().isElementPresent(2), "Create button is present in edit mode");
        Assert.assertTrue(userInfoModal.getSaveButton().getElement().isEnabled(), "Save button is disabled in edit mode");
        Assert.assertTrue(userInfoModal.getDeactivateButton().getElement().isEnabled(), "Deactivate button is disabled in edit mode");

        Assert.assertEquals(CommonUtils.getInputText(userInfoModal.getUsernameInput()), userType.getUsername(), "Username input text is incorrect in edit mode");
        Assert.assertEquals(CommonUtils.getInputText(userInfoModal.getFirstNameInput()), userType.getFirstName(), "First name input text is incorrect in edit mode");
        Assert.assertEquals(CommonUtils.getInputText(userInfoModal.getLastNameInput()), userType.getLastName(), "Last name input text is incorrect in edit mode");
        Assert.assertEquals(CommonUtils.getInputText(userInfoModal.getEmailInput()), userType.getEmail(), "Email input text is incorrect in edit mode");
    }

}
