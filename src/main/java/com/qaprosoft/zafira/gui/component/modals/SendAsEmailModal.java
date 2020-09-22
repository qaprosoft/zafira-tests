package com.qaprosoft.zafira.gui.component.modals;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.zafira.gui.component.Modal;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.Arrays;

public class SendAsEmailModal extends Modal {

    @FindBy(xpath = ".//input[@type = 'search']")
    private ExtendedWebElement emailsInput;

    @FindBy(id = "send")
    private ExtendedWebElement sendButton;

    public SendAsEmailModal(WebDriver driver) {
        super(driver);
    }

    public ExtendedWebElement getEmailsInput() {
        return emailsInput;
    }

    public void typeRecipients(String... emails) {
        Arrays.stream(emails).forEach(email -> {
            emailsInput.type(email);
            emailsInput.sendKeys(Keys.ENTER);
        });
    }

    public ExtendedWebElement getSendButton() {
        return sendButton;
    }

    public void clickSendButton() {
        sendButton.click();
    }

}
