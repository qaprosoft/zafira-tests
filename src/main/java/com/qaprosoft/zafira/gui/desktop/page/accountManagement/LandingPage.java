package com.qaprosoft.zafira.gui.desktop.page.accountManagement;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractPage;
import com.qaprosoft.zafira.gui.desktop.component.landing.LandingRegistrationComp;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class LandingPage extends AbstractPage {

    @FindBy(xpath = "//div[@name='registration']")
    private LandingRegistrationComp registrationComponent;

    @FindBy(xpath = "//button[@class='privacy-popup__button-cancel gtm-close-popup']")
    private ExtendedWebElement cookiesNotificationCloseButton;

    @FindBy(xpath = "//span[@class='recaptcha-checkbox goog-inline-block recaptcha-checkbox-unchecked rc-anchor-checkbox']")
    private List<ExtendedWebElement> captchaCheckbox;

    public LandingPage(WebDriver driver) {
        super(driver);
    }

    public LandingRegistrationComp getRegistrationComponent() {
        return registrationComponent;
    }

    public void closeCookiesNotification() {
        cookiesNotificationCloseButton.click();
    }

    public void clickOnCaptcha() {
        captchaCheckbox.get(1).click();
    }
}
