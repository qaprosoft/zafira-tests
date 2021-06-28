package com.qaprosoft.zafira.gui.desktop.component.member;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

//div[@class='users-table__row ng-scope']
public class MemberCard extends AbstractUIObject {
    @FindBy(xpath = ".//div[@class='profile-photo_content']")
    private ExtendedWebElement photo;

    @FindBy(xpath = ".//div[@class='users-table__col _username']")
    private ExtendedWebElement name;

    @FindBy(xpath = ".//div[@class='users-table__col _role']")
    private ExtendedWebElement role;

    @FindBy(xpath = ".//div[@class='users-table__col _added']")
    private ExtendedWebElement addedDate;

    @FindBy(xpath = ".//div[@class='users-table__col _bucket']")
    private ExtendedWebElement deleteButton;

    @FindBy(xpath = "//div[@class='md-select-menu-container md-active md-clickable']//md-option[@ng-value='role.name']")
    private List<ExtendedWebElement> roles;

    public MemberCard(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }
}
