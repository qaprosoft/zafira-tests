package com.qaprosoft.zafira.gui;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.zafira.domain.Route;
import com.qaprosoft.zafira.gui.component.subheader.UserProfileSubHeader;
import com.qaprosoft.zafira.gui.message.InputMessage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import static com.qaprosoft.zafira.util.CommonUtils.getInputText;

public class UserProfilePage extends BasePage {

    @FindBy(className = "fixed-page-header")
    private UserProfileSubHeader subHeader;

    @FindBy(className = "profile-img")
    private ExtendedWebElement profilePhotoContainer;

    @FindBy(css = ".profile-img i")
    private ExtendedWebElement profilePhotoIconButton;

    @FindBy(id = "userRole")
    private ExtendedWebElement userRoleLabel;

    @FindBy(id = "userUsername")
    private ExtendedWebElement usernameInput;

    @FindBy(id = "userFirstName")
    private ExtendedWebElement firstNameInput;

    @FindBy(id = "userLastName")
    private ExtendedWebElement lastNameInput;

    @FindBy(id = "userEmail")
    private ExtendedWebElement emailInput;

    @FindBy(id = "saveUserInfo")
    private ExtendedWebElement saveInfoButton;

    @FindBy(id = "userOldPassword")
    private ExtendedWebElement oldPasswordInput;

    @FindBy(id = "userPassword")
    private ExtendedWebElement newPasswordInput;

    @FindBy(id = "userPassword")
    private InputMessage inputMessage;

    @FindBy(xpath = ".//*[contains(@class, 'fa-eye')]")
    private ExtendedWebElement eyePasswordButton;

    @FindBy(id = "changePassword")
    private ExtendedWebElement changePasswordButton;

    @FindBy(id = "userAccessToken")
    private ExtendedWebElement accessTokenInput;

    @FindBy(id = "generateToken")
    private ExtendedWebElement generateTokenButton;

    @FindBy(id = "copyToken")
    private ExtendedWebElement copyTokenButton;

    public UserProfilePage(WebDriver driver) {
        super(driver, Route.USER_PROFILE);
    }

    public UserProfileSubHeader getSubHeader() {
        return subHeader;
    }

    public ExtendedWebElement getProfilePhotoContainer() {
        return profilePhotoContainer;
    }

    public ExtendedWebElement getProfilePhotoIconButton() {
        return profilePhotoIconButton;
    }

    public void clickProfilePhotoIconButton() {
        profilePhotoIconButton.click();
    }

    public ExtendedWebElement getUserRoleLabel() {
        return userRoleLabel;
    }

    public String getUserRoleLabelText() {
        return userRoleLabel.getText();
    }

    public ExtendedWebElement getUsernameInput() {
        return usernameInput;
    }

    public void typeUsername(String username) {
        usernameInput.type(username);
    }

    public ExtendedWebElement getFirstNameInput() {
        return firstNameInput;
    }

    public void typeFirstName(String firstName) {
        firstNameInput.type(firstName);
    }

    public ExtendedWebElement getLastNameInput() {
        return lastNameInput;
    }

    public void typeLastName(String lastName) {
        lastNameInput.type(lastName);
    }

    public ExtendedWebElement getEmailInput() {
        return emailInput;
    }

    public void typeEmail(String email) {
        emailInput.type(email);
    }

    public ExtendedWebElement getSaveInfoButton() {
        return saveInfoButton;
    }

    public void clickSaveInfoButton() {
        saveInfoButton.click();
    }

    public ExtendedWebElement getOldPasswordInput() {
        return oldPasswordInput;
    }

    public void typeOldPassword(String oldPassword) {
        oldPasswordInput.type(oldPassword);
    }

    public ExtendedWebElement getNewPasswordInput() {
        return newPasswordInput;
    }

    public void typeNewPassword(String newPassword) {
        newPasswordInput.type(newPassword);
    }

    public InputMessage getInputMessage() {
        return inputMessage;
    }

    public ExtendedWebElement getEyePasswordButton() {
        return eyePasswordButton;
    }

    public void clickEyePasswordButton() {
        eyePasswordButton.click();
    }

    public ExtendedWebElement getChangePasswordButton() {
        return changePasswordButton;
    }

    public void clickChangePasswordButton() {
        changePasswordButton.click();
    }

    public ExtendedWebElement getAccessTokenInput() {
        return accessTokenInput;
    }

    public String getAccessTokenInputText() {
        return getInputText(accessTokenInput);
    }

    public ExtendedWebElement getGenerateTokenButton() {
        return generateTokenButton;
    }

    public void clickGenerateTokenButton() {
        generateTokenButton.click();
    }

    public ExtendedWebElement getCopyTokenButton() {
        return copyTokenButton;
    }

    public void clickCopyTokenButton() {
        copyTokenButton.click();
    }

}
