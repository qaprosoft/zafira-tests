package com.qaprosoft.zafira.gui.desktop.page.tenant;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractPage;
import com.qaprosoft.zafira.gui.desktop.component.common.HelpMenu;
import com.qaprosoft.zafira.gui.desktop.component.common.Pagination;
import com.qaprosoft.zafira.gui.desktop.component.common.TenantHeader;
import com.qaprosoft.zafira.gui.desktop.component.user.UserCard;
import com.qaprosoft.zafira.gui.desktop.component.user.UserProcessWindow;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class UsersPage extends AbstractPage {
    @FindBy(id = "header")
    private TenantHeader header;

    @FindBy(id = "pagination")
    private Pagination pagination;

    @FindBy(xpath = "//div[@class='users-table__row ng-scope']")
    private List<UserCard> userCards;

    @FindBy(xpath = "//md-dialog[@aria-label='User profile']")
    private UserProcessWindow newUserWindow;

    @FindBy(xpath = "//div[@data-embed='helpCenterForm']")
    private HelpMenu helpMenu;

    @FindBy(xpath = "//button[@aria-label='Help']")
    private ExtendedWebElement helpButton;

    @FindBy(id = "pageTitle")
    private ExtendedWebElement pageTitle;

    @FindBy(xpath = "//small[@class='fixed-page-header__additional-text ng-binding ng-scope']")
    private ExtendedWebElement usersNumber;

    @FindBy(xpath = "//div[@class='users-table users-table__header']//div[text()='ID']")
    private ExtendedWebElement idTitle;

    @FindBy(xpath = "//div[@class='users-table users-table__header']//div[text()='Username']")
    private ExtendedWebElement usernameTitle;

    @FindBy(xpath = "//div[@class='users-table users-table__header']//div[text()='Email']")
    private ExtendedWebElement emailTitle;

    @FindBy(xpath = "//div[@class='users-table users-table__header']//div[text()='Full name']")
    private ExtendedWebElement fullNameTitle;

    @FindBy(xpath = "//div[@class='users-table users-table__header']//div[text()='Group']")
    private ExtendedWebElement groupTitle;

    @FindBy(xpath = "//div[@class='users-table users-table__header']//div[text()='Source']")
    private ExtendedWebElement sourceTitle;

    @FindBy(xpath = "//div[@class='users-table users-table__header']//div[text()='Registration/ last activity']")
    private ExtendedWebElement lastActivityTitle;

    @FindBy(xpath = "//div[@class='users-table users-table__header']//span[@class='status-menu__button-text']")
    private ExtendedWebElement statusTitle;

    @FindBy(xpath = "//span[@class='status-icon active status-menu__dropdown-text']/ancestor::button")
    private ExtendedWebElement activeStatus;

    @FindBy(xpath = "//span[@class='status-icon inactive status-menu__dropdown-text']/ancestor::button")
    private ExtendedWebElement inactiveStatus;

    @FindBy(xpath = "//span[@class='status-icon  status-menu__dropdown-text']/ancestor::button")
    private ExtendedWebElement allStatus;

    @FindBy(xpath = "//div[@class='fixed-page-header-container_options ng-scope layout-align-start-center layout-row']//input[contains(@class,'md-input')]")
    private ExtendedWebElement searchUserField;

    @FindBy(xpath = "//md-icon[@class='input-close-icon material-icons']")
    private ExtendedWebElement flushSearchField;

    @FindBy(xpath = "//button[@aria-label='New user']")
    private ExtendedWebElement newUserButton;

    @FindBy(xpath = "//span[text()='Invite user']/ancestor::button")
    private ExtendedWebElement inviteUserButton;

    public UsersPage(WebDriver driver) {
        super(driver);
    }
}
