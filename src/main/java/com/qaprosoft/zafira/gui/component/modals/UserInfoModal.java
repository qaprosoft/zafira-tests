package com.qaprosoft.zafira.gui.component.modals;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.zafira.gui.component.Modal;
import com.qaprosoft.zafira.gui.message.InputMessage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class UserInfoModal extends Modal {

    @FindBy(id = "username")
    private ExtendedWebElement usernameInput;

    @FindBy(id = "username")
    private InputMessage usernameInputMessage;

    @FindBy(id = "firstName")
    private ExtendedWebElement firstNameInput;

    @FindBy(id = "firstName")
    private InputMessage firstNameInputMessage;

    @FindBy(id = "lastName")
    private ExtendedWebElement lastNameInput;

    @FindBy(id = "lastName")
    private InputMessage lastNameInputMessage;

    @FindBy(id = "email")
    private ExtendedWebElement emailInput;

    @FindBy(id = "email")
    private InputMessage emailInputMessage;

    @FindBy(id = "password")
    private ExtendedWebElement passwordInput;

    @FindBy(id = "password")
    private InputMessage passwordInputMessage;

    @FindBy(xpath = ".//button[contains(text(), 'Create')]")
    private ExtendedWebElement createButton;

    @FindBy(xpath = ".//button[contains(text(), 'Deactivate')]")
    private ExtendedWebElement deactivateButton;

    @FindBy(xpath = ".//button[contains(text(), 'Activate')]")
    private ExtendedWebElement activateButton;

    @FindBy(xpath = ".//button[contains(text(), 'Save')]")
    private ExtendedWebElement saveButton;

    public UserInfoModal(WebDriver driver) {
        super(driver);
    }

    public ExtendedWebElement getUsernameInput() {
        return usernameInput;
    }

    public void typeUsername(String username) {
        usernameInput.type(username);
    }

    public InputMessage getUsernameInputMessage() {
        return usernameInputMessage;
    }

    public ExtendedWebElement getFirstNameInput() {
        return firstNameInput;
    }

    public void typeFirstName(String firstName) {
        firstNameInput.type(firstName);
    }

    public InputMessage getFirstNameInputMessage() {
        return firstNameInputMessage;
    }

    public ExtendedWebElement getLastNameInput() {
        return lastNameInput;
    }

    public void typeLastName(String lastName) {
        lastNameInput.type(lastName);
    }

    public InputMessage getLastNameInputMessage() {
        return lastNameInputMessage;
    }

    public ExtendedWebElement getEmailInput() {
        return emailInput;
    }

    public void typeEmail(String email) {
        emailInput.type(email);
    }

    public InputMessage getEmailInputMessage() {
        return emailInputMessage;
    }

    public ExtendedWebElement getPasswordInput() {
        return passwordInput;
    }

    public void typePassword(String password) {
        passwordInput.type(password);
    }

    public InputMessage getPasswordInputMessage() {
        return passwordInputMessage;
    }

    public ExtendedWebElement getCreateButton() {
        return createButton;
    }

    public void clickCreateButton() {
        createButton.click();
    }

    public ExtendedWebElement getDeactivateButton() {
        return deactivateButton;
    }

    public void clickDeactivateButton() {
        deactivateButton.click();
    }

    public ExtendedWebElement getActivateButton() {
        return activateButton;
    }

    public void clickActivateButton() {
        activateButton.click();
    }

    public ExtendedWebElement getSaveButton() {
        return saveButton;
    }

    public void clickSaveButton() {
        saveButton.click();
    }

}
