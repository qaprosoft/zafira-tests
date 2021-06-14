package com.qaprosoft.zafira.gui.desktop.component;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class LandingRegistrationComp extends AbstractUIObject {

    @FindBy(xpath = ".//input[@name='name']")
    private ExtendedWebElement orgNameInput;

    @FindBy(xpath = ".//input[@name='ownerName']")
    private ExtendedWebElement ownerNameInput;

    @FindBy(xpath = ".//input[@name='email']")
    private ExtendedWebElement emailInput;

    @FindBy(xpath = ".//label[@for='form-subscribe-checkbox']")
    private ExtendedWebElement newsCheckbox;

    @FindBy(xpath = ".//div[@name='registration']//button[@type='submit']")
    private ExtendedWebElement submitButton;

    public LandingRegistrationComp(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public void createFreeWorkspace(String orgName, String ownerName, String email){
        orgNameInput.type(orgName);
        ownerNameInput.type(ownerName);
        emailInput.type(email);
        newsCheckbox.click();
        submitButton.click();
    }

}
