package com.qaprosoft.zafira.gui.desktop.component.member;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import com.qaprosoft.zafira.constant.WebConstant;
import com.qaprosoft.zafira.util.WaitUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

//div[@class='users-table']//div[contains(@class,'users-table__row')]
public class MemberCard extends AbstractUIObject {
    @FindBy(xpath = ".//div[@class='profile-photo_content']")
    private ExtendedWebElement photo;

    @FindBy(xpath = ".//div[@class='users-table__col _username']")
    private ExtendedWebElement name;

    @FindBy(xpath = ".//div[@class='users-table__col _role']//md-select-value//span[contains(@class,'users-table__role-name')]")
    private ExtendedWebElement role;

    @FindBy(xpath = ".//div[@class='users-table__col _added']")
    private ExtendedWebElement addedDate;

    @FindBy(xpath = ".//div[@class='users-table__col _bucket']")
    private ExtendedWebElement deleteButton;

    @FindBy(xpath = "//span[text()='delete']/ancestor::button")
    private ExtendedWebElement deleteConfirmationButton;

    @FindBy(xpath = "//div[@class='md-select-menu-container md-active md-clickable']//div[@class='md-text']//span[contains(@class,'users-table__role-name')]")
    private List<ExtendedWebElement> roles;

    public MemberCard(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public String getUsername() {
        waitUntil(ExpectedConditions.visibilityOf(name.getElement()), WebConstant.TIME_TO_LOAD_ELEMENT);
        return name.getText().trim();
    }

    public boolean delete() {
        deleteButton.click();
        waitUntil(ExpectedConditions.elementToBeClickable(deleteConfirmationButton.getElement()), WebConstant.TIME_TO_LOAD_ELEMENT);
        return deleteConfirmationButton.clickIfPresent();
    }

    public void changeRole(String roleName) {
        role.click();
        pause(WebConstant.TIME_TO_LOAD_ELEMENT);
        for (ExtendedWebElement roleEl : roles) {
            if (roleEl.getText().trim().equalsIgnoreCase(roleName)) {
                roleEl.click();
                return;
            }
        }
        throw new RuntimeException("Can't find role " + roleName);
    }

    public String getRole() {
        return role.getText().trim();
    }
}
