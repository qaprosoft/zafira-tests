package com.qaprosoft.zafira.gui.component.modals;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.zafira.gui.component.Modal;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class ChangePasswordModal extends Modal {

    @FindBy(id = "password")
    private ExtendedWebElement passwordInput;

    @FindBy(className = "fa-eye")
    private ExtendedWebElement showPasswordIcon;

    @FindBy(id = "change")
    private ExtendedWebElement applyButton;

    public ChangePasswordModal(WebDriver driver) {
        super(driver);
    }

    public ExtendedWebElement getPasswordInput() {
        return passwordInput;
    }

    public void typePassword(String password) {
        passwordInput.type(password);
    }

    public ExtendedWebElement getShowPasswordIcon() {
        return showPasswordIcon;
    }

    public void clickShowPasswordIcon() {
        showPasswordIcon.click();
    }

    public ExtendedWebElement getApplyButton() {
        return applyButton;
    }

    public void clickApplyButton() {
        applyButton.click();
    }

}
