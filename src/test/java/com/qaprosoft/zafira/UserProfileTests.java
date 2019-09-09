package com.qaprosoft.zafira;

import com.qaprosoft.carina.core.foundation.utils.ownership.MethodOwner;
import com.qaprosoft.zafira.gui.DashboardPage;
import com.qaprosoft.zafira.gui.LoginPage;
import com.qaprosoft.zafira.gui.UserProfilePage;
import com.qaprosoft.zafira.gui.component.modals.UploadUserPhotoModal;
import com.qaprosoft.zafira.models.dto.user.UserType;
import com.qaprosoft.zafira.service.AuthService;
import com.qaprosoft.zafira.service.SidebarService;
import com.qaprosoft.zafira.service.UserProfileService;
import com.qaprosoft.zafira.service.impl.AuthServiceImpl;
import com.qaprosoft.zafira.service.impl.SidebarServiceImpl;
import com.qaprosoft.zafira.service.impl.UserProfileServiceImpl;
import com.qaprosoft.zafira.service.impl.UserServiceImpl;
import com.qaprosoft.zafira.util.CommonUtils;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static com.qaprosoft.zafira.util.CommonUtils.clickAndHold;
import static com.qaprosoft.zafira.util.CommonUtils.getInputText;
import static com.qaprosoft.zafira.util.CommonUtils.releaseAction;

public class UserProfileTests extends BaseTest {

    private static final Map<String, String> PASSWORD_TEST_DATA = new HashMap<>();

    private static final String ERR_MSG_LENGTH = "Password must be between 5 and 50 characters";
    private static final String ERR_MDG_SYMBOLS = "Password must have only latin letters, numbers or special symbols";

    static {
        PASSWORD_TEST_DATA.put("q", ERR_MSG_LENGTH);
        PASSWORD_TEST_DATA.put("qwer", ERR_MSG_LENGTH);
        PASSWORD_TEST_DATA.put("q-we", ERR_MSG_LENGTH);
        PASSWORD_TEST_DATA.put("qwertyqwertyqwertyqwertyqwertyqwertyqwertyqwertyqwe", ERR_MSG_LENGTH);
        PASSWORD_TEST_DATA.put("q-wertyqw※", ERR_MDG_SYMBOLS);
    }

    @BeforeMethod
    public void setup() {
        DashboardPage dashboardPage = signin();
        SidebarService sidebarService = new SidebarServiceImpl(getDriver(), dashboardPage);
        sidebarService.goToUserProfilePage();
    }

    @Test
    @MethodOwner(owner = "brutskov")
    public void verifyUserPhotoModalTest() {
        UserProfilePage userProfilePage = new UserProfilePage(getDriver());
        userProfilePage.waitUntilPageIsLoaded();
        Assert.assertTrue(userProfilePage.getProfilePhotoContainer().isElementPresent(1), "User profile photo container is not present");

        UserProfileService userProfileService = new UserProfileServiceImpl(getDriver());
        UploadUserPhotoModal uploadUserPhotoModal = userProfileService.clickUserPhotoButton();

        Assert.assertEquals(uploadUserPhotoModal.getTitleText(), "Upload image", "Upload user profile image modal has invalid header");
        Assert.assertFalse(uploadUserPhotoModal.getUploadButton().getElement().isEnabled(), "Upload image button is enabled");

        uploadUserPhotoModal.clickCloseButton();
        pause(ANIMATION_TIMEOUT);
        Assert.assertTrue(uploadUserPhotoModal.getTitle().isElementNotPresent(1), "Upload user profile image modal is present after closing");
    }

    @Test
    @MethodOwner(owner = "brutskov")
    public void verifyChangeProfileInformationTest() {
        UserProfilePage userProfilePage = new UserProfilePage(getDriver());
        Assert.assertEquals(userProfilePage.getUserRoleLabelText(), "ROLE_ADMIN", "User role label text is invalid");

        Assert.assertFalse(userProfilePage.getEmailInput().getElement().isEnabled(), "Username input is enabled");
        Assert.assertTrue(userProfilePage.getFirstNameInput().getElement().isEnabled(), "First name input is disabled");
        Assert.assertTrue(userProfilePage.getLastNameInput().getElement().isEnabled(), "Last name input is disabled");
        Assert.assertTrue(userProfilePage.getLastNameInput().getElement().isEnabled(), "Last name input is disabled");
        Assert.assertFalse(userProfilePage.getEmailInput().getElement().isEnabled(), "Email input is enabled");
        Assert.assertTrue(userProfilePage.getSaveInfoButton().getElement().isEnabled(), "Save info button is disabled");

        Assert.assertEquals(getInputText(userProfilePage.getUsernameInput()), ADMIN_USERNAME, "Username input has invalid text");

        String oldFirstName = getInputText(userProfilePage.getFirstNameInput());
        String oldLastName = getInputText(userProfilePage.getLastNameInput());
        String username = getInputText(userProfilePage.getUsernameInput());
        String email = getInputText(userProfilePage.getEmailInput());
        String newFirstName = "newfirstname";
        String newLastName = "newlastname";
        userProfilePage.typeFirstName(newFirstName);
        userProfilePage.typeLastName(newLastName);
        userProfilePage.clickSaveInfoButton();
        Assert.assertEquals(userProfilePage.getSuccessAlertText(), "User profile updated");
        Assert.assertEquals(getInputText(userProfilePage.getFirstNameInput()), newFirstName, "First name is invalid after changing without page reload");
        Assert.assertEquals(getInputText(userProfilePage.getLastNameInput()), newLastName, "Last name is invalid after changing without page reload");
        Assert.assertEquals(getInputText(userProfilePage.getUsernameInput()), username, "Username is invalid after changing without page reload");
        Assert.assertEquals(getInputText(userProfilePage.getEmailInput()), email, "Email is invalid after changing without page reload");

        userProfilePage.refresh();
        userProfilePage.waitUntilPageIsLoaded();
        userProfilePage.waitProgressLinear();

        Assert.assertEquals(getInputText(userProfilePage.getFirstNameInput()), newFirstName, "First name is invalid after changing with page reload");
        Assert.assertEquals(getInputText(userProfilePage.getLastNameInput()), newLastName, "Last name is invalid after changing with page reload");
        Assert.assertEquals(getInputText(userProfilePage.getUsernameInput()), username, "Username is invalid after changing with page reload");
        Assert.assertEquals(getInputText(userProfilePage.getEmailInput()), email, "Email is invalid after changing with page reload");
    }

    @Test(enabled = false)
    @MethodOwner(owner = "brutskov")
    public void verifyChangePasswordTest() {
        UserServiceImpl userService = new UserServiceImpl(getDriver());
        UserType userType = userService.buildUsers(1).get(0);

        AuthService authService = new AuthServiceImpl(getDriver());
        authService.logout();
        DashboardPage dashboardPage = authService.signin(userType.getUsername(), userType.getPassword());

        SidebarService sidebarService = new SidebarServiceImpl(getDriver(), dashboardPage);
        UserProfilePage userProfilePage = sidebarService.goToUserProfilePage();

        String newPassword = "newpassw";
        Assert.assertTrue(userProfilePage.getOldPasswordInput().getElement().isEnabled(), "Old password input is disabled");
        Assert.assertEquals(userProfilePage.getOldPasswordInput().getAttribute("type"), "password", "Old password input type is not 'password'");
        Assert.assertTrue(getInputText(userProfilePage.getOldPasswordInput()).isEmpty(), "Old password input is not empty");
        Assert.assertTrue(userProfilePage.getNewPasswordInput().getElement().isEnabled(), "New password input is disabled");
        Assert.assertEquals(userProfilePage.getNewPasswordInput().getAttribute("type"), "password", "New password input type is not 'password'");
        Assert.assertTrue(getInputText(userProfilePage.getNewPasswordInput()).isEmpty(), "New password input is not empty");
        Assert.assertFalse(userProfilePage.getChangePasswordButton().getElement().isEnabled(), "Change password button is enabled with empty inputs");
        Assert.assertTrue(userProfilePage.getEyePasswordButton().isPresent(1), "Password eye button is not present");

        Assert.assertEquals(userProfilePage.getOldPasswordInput().getAttribute("type"), "password", "Old password input has not type 'password'");
        Assert.assertEquals(userProfilePage.getNewPasswordInput().getAttribute("type"), "password", "New password input has not type 'password'");

        userProfilePage.typeNewPassword("qwerty");
        userProfilePage.getNewPasswordInput().getElement().clear();
        pause(ANIMATION_TIMEOUT);
        Assert.assertEquals(userProfilePage.getInputMessage().getMessages().size(), 1, "There are more then 1 message if new password input is empty");
        Assert.assertEquals(userProfilePage.getInputMessage().getMessages().get(0).getText(), "Password required", "New password required message is invalid");

        PASSWORD_TEST_DATA.forEach((text, message) -> {
            userProfilePage.typeNewPassword(text);
            pause(ANIMATION_TIMEOUT);
            String assertMessageLength = "There are more then 1 message if '%s'";
            String assertMessage = "New password '%s' message is invalid";
            String assertMessageChangePasswordButton = "Change password button is enabled if text is '%s'";
            Assert.assertEquals(userProfilePage.getInputMessage().getMessages().size(), 1, String.format(assertMessageLength, message));
            Assert.assertEquals(userProfilePage.getInputMessage().getMessages().get(0).getText(), message, String.format(assertMessage, message));
            Assert.assertFalse(userProfilePage.getChangePasswordButton().getElement().isEnabled(), String.format(assertMessageChangePasswordButton, text));
            userProfilePage.getNewPasswordInput().getElement().clear();
        });

        userProfilePage.typeOldPassword(userType.getPassword());
        userProfilePage.typeNewPassword(newPassword);
        pause(ANIMATION_TIMEOUT);
        Assert.assertEquals(userProfilePage.getInputMessage().getMessages().size(), 0, "Error message is displayed with valid password");
        Assert.assertTrue(userProfilePage.getChangePasswordButton().getElement().isEnabled(), "Change password button is disabled with valid password");

        Actions action = clickAndHold(userProfilePage.getEyePasswordButton(), getDriver());
        Assert.assertEquals(getInputText(userProfilePage.getNewPasswordInput()), newPassword, "New password eye button doesn't work");
        Assert.assertEquals(userProfilePage.getNewPasswordInput().getAttribute("type"), "text", "New password eye button doesn't work");

        releaseAction(action, getDriver());
        Assert.assertEquals(getInputText(userProfilePage.getNewPasswordInput()), newPassword, "New password eye button doesn't work");
        Assert.assertEquals(userProfilePage.getNewPasswordInput().getAttribute("type"), "password", "New password eye button doesn't work");

        userProfilePage.clickChangePasswordButton();
        Assert.assertEquals(userProfilePage.getSuccessAlertText(), "Password changed", "Success alert has invalid text");

        UserProfileService userProfileService = new UserProfileServiceImpl(getDriver());
        LoginPage loginPage = userProfileService.clickLogoutButton();
        pause(SMALL_TIMEOUT);
        authService = new AuthServiceImpl(getDriver(), loginPage);
        dashboardPage = authService.signin(userType.getUsername(), newPassword);
        Assert.assertTrue(dashboardPage.isPageOpened(SMALL_TIMEOUT), "Cannot login with new password");
    }

    @Test
    @MethodOwner(owner = "brutskov")
    public void verifyGenerateAccessTokenTest() {
        UserProfilePage userProfilePage = new UserProfilePage(getDriver());
        String accessToken = userProfilePage.getAccessTokenInputText();
        Assert.assertTrue(accessToken.isEmpty(), "Access token input is not empty on page load");

        Assert.assertTrue(userProfilePage.getGenerateTokenButton().getElement().isEnabled(), "Generate access token button is disabled");
        Assert.assertTrue(userProfilePage.getCopyTokenButton().isElementNotPresent(1), "Copy access token button is present");

        userProfilePage.clickGenerateTokenButton();
        Assert.assertTrue(userProfilePage.getSuccessAlert().isElementNotPresent(1), "Success alert is present on generate access token button clicking");

        UserProfileServiceImpl userProfileService = new UserProfileServiceImpl(getDriver());
        userProfileService.waitProgressLinear();
        accessToken = userProfilePage.getAccessTokenInputText();
        Assert.assertFalse(accessToken.isEmpty(), "Access token is not generated");
        Assert.assertTrue(userProfilePage.getCopyTokenButton().isElementPresent(1), "Copy access token button is not present");

        userProfilePage.clickCopyTokenButton();
        Assert.assertTrue(userProfilePage.getSuccessAlert().isElementPresent(2), "Success alert is not visible");

        userProfilePage.getAccessTokenInput().getElement().clear();
        Assert.assertTrue(userProfilePage.getAccessTokenInputText().isEmpty(), "Cannot clear access token input to test clipboard");
        CommonUtils.pastTo(userProfilePage.getAccessTokenInput());
        String copiedToken = userProfilePage.getAccessTokenInputText();
        Assert.assertEquals(copiedToken, accessToken, "Access token is invalid");
    }

}
