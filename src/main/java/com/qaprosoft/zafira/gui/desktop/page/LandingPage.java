package com.qaprosoft.zafira.gui.desktop.page;

import com.qaprosoft.carina.core.gui.AbstractPage;
import com.qaprosoft.zafira.gui.desktop.component.LandingRegistrationComp;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class LandingPage extends AbstractPage {

    @FindBy(xpath = "//div[@name='registration']")
    private LandingRegistrationComp registrationComponent;

    public LandingPage(WebDriver driver) {
        super(driver);
    }

    public LandingRegistrationComp getRegistrationComponent() {
        return registrationComponent;
    }
}
