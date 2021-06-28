package com.qaprosoft.zafira.gui.desktop.component.user;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class ChangePasswordWindow extends AbstractUIObject {
    @FindBy(id = "modalTitle")
    private ExtendedWebElement title;

    @FindBy(id = "close")
    private ExtendedWebElement close;

    @FindBy(id = "password")
    private ExtendedWebElement password;

    @FindBy(xpath = ".//i[@class='fa fa-eye']")
    private ExtendedWebElement showPass;

    @FindBy(id = "change")
    private ExtendedWebElement apply;

    public ChangePasswordWindow(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }
}
