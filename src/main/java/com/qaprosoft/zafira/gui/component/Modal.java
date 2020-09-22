package com.qaprosoft.zafira.gui.component;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class Modal extends AbstractUIObject {

    @FindBy(css = "md-toolbar h2")
    private ExtendedWebElement title;

    @FindBy(xpath = "//md-toolbar//button[./*[text() = 'close']]")
    private ExtendedWebElement closeButton;

    public Modal(WebDriver driver) {
        super(driver, driver);
    }

    public ExtendedWebElement getTitle() {
        return title;
    }

    public String getTitleText() {
        return title.getText();
    }

    public ExtendedWebElement getCloseButton() {
        return closeButton;
    }

    public void clickCloseButton() {
        closeButton.click();
    }
}
