package com.qaprosoft.zafira.gui.desktop.component.member;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

//md-dialog[@class='users-modal styled-modal _md background-clear-white md-transition-in']
public class DeleteMemberWindow extends AbstractUIObject {
    @FindBy(xpath = ".//h2[@class='modal-header__title ng-binding _warning']")
    private ExtendedWebElement deleteTitle;

    @FindBy(xpath = ".//div[@class='modal-content__message _warning ng-binding']")
    private ExtendedWebElement warningMessage; //should contain name of deleting member

    @FindBy(xpath = ".//button[@id='close']")
    private ExtendedWebElement closeButton;

    @FindBy(xpath = ".//span[@class='ng-scope' and text()='cancel']")
    private ExtendedWebElement cancelButton;

    @FindBy(xpath = ".//span[@class='ng-scope' and text()='delete']")
    private ExtendedWebElement deleteButton;

    public DeleteMemberWindow(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }
}
