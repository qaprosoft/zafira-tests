package com.qaprosoft.zafira.gui.desktop.component.member;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

//md-dialog[@class='users-modal styled-modal _md background-clear-white md-transition-in']
public class AddMemberWindow extends AbstractUIObject {
    @FindBy(xpath = ".//h2[@class='modal-header__title ng-binding']")
    private ExtendedWebElement title;

    @FindBy(xpath = ".//input[@type='text']")
    private ExtendedWebElement enterUsernameField;

    @FindBy(xpath = ".//md-select[@id='userRole']")
    private ExtendedWebElement roleMenu;

    @FindBy(xpath = "//div[@class='md-select-menu-container md-active md-clickable']//md-option[@ng-value='role.name']")
    private List<ExtendedWebElement> roles;

    @FindBy(xpath = ".//span[@class='ng-scope' and text()='cancel']")
    private ExtendedWebElement cancelButton;

    @FindBy(xpath = "//span[@class='ng-scope' and text()='save']")
    private ExtendedWebElement saveButton;

    public AddMemberWindow(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }
}
