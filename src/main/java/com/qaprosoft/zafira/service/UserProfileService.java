package com.qaprosoft.zafira.service;

import com.qaprosoft.zafira.gui.LoginPage;
import com.qaprosoft.zafira.gui.component.modals.UploadUserPhotoModal;

public interface UserProfileService {

    LoginPage clickLogoutButton();

    UploadUserPhotoModal clickUserPhotoButton();

    void changePassword(String oldPassword, String newPassword);

    String generateAccessToken();

    void copyAccessToken();
}
