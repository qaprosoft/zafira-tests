package com.qaprosoft.zafira.gui.desktop.component.testrun;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

//md-dialog[@aria-label='Email']
public class EmailForm extends AbstractUIObject {
    @FindBy(id = "modalTitle")
    private ExtendedWebElement modalTitle;

    @FindBy(xpath = ".//label[@class='ng-binding']")
    private ExtendedWebElement numOfRecipients;

    @FindBy(xpath = ".//input[@role='textbox']")
    private ExtendedWebElement inputEmailField;

    @FindBy(xpath = ".//md-chip[@class='ng-scope']//span")
    private List<ExtendedWebElement> emailRecipients;

    @FindBy(xpath = ".//div[@class='md-chip-remove-container ng-scope']")
    private List<ExtendedWebElement> emailRecipientsDeleteButton;

    @FindBy(id = "send")
    private ExtendedWebElement sendButton;

    public EmailForm(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }
}
