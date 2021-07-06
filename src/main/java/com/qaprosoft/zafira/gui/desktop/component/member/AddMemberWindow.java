package com.qaprosoft.zafira.gui.desktop.component.member;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import com.qaprosoft.zafira.constant.WebConstant;
import com.qaprosoft.zafira.util.WaitUtil;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

//md-dialog[@class='users-modal styled-modal _md background-clear-white md-transition-in']
public class AddMemberWindow extends AbstractUIObject {
    @FindBy(xpath = ".//h2[@class='modal-header__title ng-binding']")
    private ExtendedWebElement title;

    @FindBy(xpath = ".//md-icon[@aria-label='Close dialog']")
    private ExtendedWebElement closeButton;

    @FindBy(xpath = ".//input[@type='text']")
    private ExtendedWebElement enterUsernameField;

    @FindBy(xpath = ".//md-select[@id='userRole']")
    private ExtendedWebElement roleMenu;

    @FindBy(xpath = ".//md-chips-wrap//md-chip")
    private List<ExtendedWebElement> usernameList;

    @FindBy(xpath = "//div[@class='md-select-menu-container users-modal__select-list md-active md-clickable']//md-option[@ng-value='role.name']//span")
    private List<ExtendedWebElement> roles;

    @FindBy(xpath = ".//span[@class='ng-scope' and text()='cancel']")
    private ExtendedWebElement cancelButton;

    @FindBy(xpath = ".//span[@class='ng-scope' and text()='save']/ancestor::button")
    private ExtendedWebElement saveButton;

    @FindBy(xpath = ".//span[@name='suggestion']")
    private ExtendedWebElement firstSuggestedUser;

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

    public boolean isCancelButtonPresent() {
        return cancelButton.isClickable() && cancelButton.isVisible();
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

    public void typeUsername(String username) {
        enterUsernameField.type(username);
        pause(WebConstant.TIME_TO_LOAD_ELEMENT);
        waitUntil(ExpectedConditions.visibilityOf(firstSuggestedUser.getElement()), WebConstant.TIME_TO_LOAD_HEAVY_ELEMENT);
        firstSuggestedUser.click();
        pause(WebConstant.TIME_TO_LOAD_ELEMENT);
    }

    public boolean isUsernamePresent(String username) {
        WaitUtil.waitListToLoad(usernameList);
        for (ExtendedWebElement el : usernameList) {
            String actualUsername = el.getText().replace("account_circle", "").trim();
            if (actualUsername.equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    public void chooseRole(String role) {
        roleMenu.click();
        for (ExtendedWebElement el : roles) {
            if (el.getText().equalsIgnoreCase(role)) {
                el.click();
                return;
            }
        }
        throw new RuntimeException("Can't find role " + role);
    }

    public void createMember(String username, String role) {
        typeUsername(username);
        chooseRole(role);
        clickSaveButton();
        pause(WebConstant.TIME_TO_LOAD_PAGE);
    }

    public void clickSaveButton() {
        saveButton.click();
    }
}
