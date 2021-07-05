package com.qaprosoft.zafira.gui.desktop.component.member;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import com.qaprosoft.zafira.constant.WebConstant;
import com.qaprosoft.zafira.util.WaitUtil;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

//md-dialog[@class='users-modal styled-modal _md background-clear-white md-transition-in']
public class AddMemberWindow extends AbstractUIObject {
    @FindBy(xpath = ".//h2[@class='modal-header__title ng-binding']")
    private ExtendedWebElement title;

    @FindBy(xpath = "//md-icon[@aria-label='Close dialog']")
    private ExtendedWebElement closeButton;

    @FindBy(xpath = ".//input[@type='text']")
    private ExtendedWebElement enterUsernameField;

    @FindBy(xpath = ".//md-select[@id='userRole']")
    private ExtendedWebElement roleMenu;

    @FindBy(xpath = "//div[@class='md-select-menu-container users-modal__select-list md-active md-clickable']//md-option[@ng-value='role.name']//span")
    private List<ExtendedWebElement> roles;

    @FindBy(xpath = ".//span[@class='ng-scope' and text()='cancel']")
    private ExtendedWebElement cancelButton;

    @FindBy(xpath = "//span[@class='ng-scope' and text()='save']/ancestor::button")
    private ExtendedWebElement saveButton;

    public AddMemberWindow(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public String getTitle() {
        return title.getText().trim();
    }

    public boolean isCloseButtonPresent() {
        return closeButton.isVisible() && closeButton.isClickable();
    }

    public boolean isSaveButtonActive() {
        return saveButton.isClickable(WebConstant.TIME_TO_LOAD_PAGE);
    }

    public boolean isUsernameFieldPresent() {
        return enterUsernameField.isVisible() && enterUsernameField.isClickable();
    }

    public boolean isRoleFieldPresent() {
        return roleMenu.isVisible() && roleMenu.isClickable();
    }

    public List<String> getRoles() {
        roleMenu.click();
        WaitUtil.waitListToLoad(roles);
        List<String> rolesList = new ArrayList<>();
        for (ExtendedWebElement el : roles) {
            rolesList.add(el.getText().trim());
        }
        return rolesList;
    }
}
