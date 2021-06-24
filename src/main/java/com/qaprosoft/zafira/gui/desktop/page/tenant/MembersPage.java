package com.qaprosoft.zafira.gui.desktop.page.tenant;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractPage;
import com.qaprosoft.zafira.gui.desktop.component.common.HelpMenu;
import com.qaprosoft.zafira.gui.desktop.component.common.NavigationMenu;
import com.qaprosoft.zafira.gui.desktop.component.common.Pagination;
import com.qaprosoft.zafira.gui.desktop.component.common.TenantHeader;
import com.qaprosoft.zafira.gui.desktop.component.member.AddMemberWindow;
import com.qaprosoft.zafira.gui.desktop.component.member.DeleteMemberWindow;
import com.qaprosoft.zafira.gui.desktop.component.member.MemberCard;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class MembersPage extends AbstractPage {
    @FindBy(id = "nav-container")
    private NavigationMenu navigationMenu;

    @FindBy(id = "header")
    private TenantHeader header;

    @FindBy(xpath = "//div[@data-embed='helpCenterForm']")
    private HelpMenu helpMenu;

    @FindBy(id = "pagination")
    private Pagination pagination;

    @FindBy(xpath = "//md-dialog[@class='users-modal styled-modal _md background-clear-white md-transition-in']")
    private AddMemberWindow addMemberWindow;

    @FindBy(xpath = "//md-dialog[@class='users-modal styled-modal _md background-clear-white md-transition-in']")
    private DeleteMemberWindow deleteMemberWindow;

    @FindBy(xpath = "//div[@class='users-table__row ng-scope']")
    private List<MemberCard> memberCards;

    @FindBy(xpath = "//button[@aria-label='Help']")
    private ExtendedWebElement helpButton;

    @FindBy(id = "pageTitle")
    private ExtendedWebElement pageTitle;

    @FindBy(xpath = "//md-input-container[contains(@class,'controls__input input')]/input")
    private ExtendedWebElement searchField;

    @FindBy(xpath = "//span[contains(text(),'add members')]/ancestor::button")
    private ExtendedWebElement addMemberButton;

    @FindBy(xpath = "//div[@class='users-table users-table__header']//div[contains(@class,'users-table__col _username')]")
    private ExtendedWebElement usernameTableHeader;

    @FindBy(xpath = "//div[@class='users-table users-table__header']//div[contains(@class,'users-table__col _role')]")
    private ExtendedWebElement roleTableHeader;

    @FindBy(xpath = "//div[@class='users-table users-table__header']//div[contains(@class,'users-table__col _added')]")
    private ExtendedWebElement addedTableHeader;

    public MembersPage(WebDriver driver) {
        super(driver);
    }
}
