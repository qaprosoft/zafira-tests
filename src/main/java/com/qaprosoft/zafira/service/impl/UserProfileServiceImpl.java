package com.qaprosoft.zafira.service.impl;

import com.qaprosoft.zafira.gui.LoginPage;
import com.qaprosoft.zafira.gui.UserProfilePage;
import com.qaprosoft.zafira.gui.component.modals.UploadUserPhotoModal;
import com.qaprosoft.zafira.service.UserProfileService;
import com.qaprosoft.zafira.util.CommonUtils;
import org.openqa.selenium.WebDriver;

public class UserProfileServiceImpl extends BaseService<UserProfilePage> implements UserProfileService {

    public UserProfileServiceImpl(WebDriver driver) {
        super(driver, UserProfilePage.class);
    }

    public UserProfileServiceImpl(WebDriver driver, UserProfilePage page) {
        super(driver, page, UserProfilePage.class);
    }

    @Override
    public LoginPage clickLogoutButton() {
        UserProfilePage userProfilePage = getUIObject(driver);
        userProfilePage.getSubHeader().clickLogoutButton();
        return new LoginPage(driver);
    }

    @Override
    public UploadUserPhotoModal clickUserPhotoButton() {
        UserProfilePage userProfilePage = getUIObject(driver);
        CommonUtils.hoverAndClickOn(userProfilePage.getProfilePhotoContainer(), userProfilePage.getProfilePhotoIconButton(), driver);
        UploadUserPhotoModal uploadUserPhotoModal = new UploadUserPhotoModal(driver);
        CommonUtils.waitUntilModalIsOpened(driver, uploadUserPhotoModal);
        return uploadUserPhotoModal;
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        UserProfilePage userProfilePage = getUIObject(driver);
        userProfilePage.typeOldPassword(oldPassword);
        userProfilePage.typeNewPassword(newPassword);
        userProfilePage.clickChangePasswordButton();
    }

    @Override
    public String generateAccessToken() {
        UserProfilePage userProfilePage = getUIObject(driver);
        userProfilePage.clickGenerateTokenButton();
        return userProfilePage.getAccessTokenInputText();
    }

    @Override
    public void copyAccessToken() {
        UserProfilePage userProfilePage = getUIObject(driver);
        userProfilePage.clickGenerateTokenButton();
    }

}
