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

    @FindBy(xpath = "//div[@class='users-table users-table__header']//div[contains(@class,'users-table__col _role')]//span")
    private ExtendedWebElement roleTableHeader;

    @FindBy(xpath = "//div[@class='users-table users-table__header']//div[contains(@class,'users-table__col _added')]//span")
    private ExtendedWebElement addedTableHeader;

    public MembersPage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(addMemberButton);
    }

    public String getTitle() {
        return pageTitle.getText().trim();
    }

    public boolean isSearchFieldPresent() {
        return searchField.isVisible() && searchField.isClickable();
    }

    public boolean isAddMemberButtonActive() {
        return addMemberButton.isClickable() && addMemberButton.isVisible();
    }

    public String getUsernameTableTitle() {
        return usernameTableHeader.getText().trim();
    }

    public String getRoleTableTitle() {
        return roleTableHeader.getText().trim().replace("unfold_more", "");
    }

    public String getAddingDate() {
        return addedTableHeader.getText().trim().replace("unfold_more", "");
    }

    public List<MemberCard> getMemberCards() {
        return memberCards;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public AddMemberWindow openAddMemberWindow() {
        addMemberButton.click();
        return addMemberWindow;
    }

    public TenantHeader getHeader() {
        return header;
    }

    public NavigationMenu getNavigationMenu() {
        return navigationMenu;
    }

    public boolean isMemberPresent(String username) {
        for (MemberCard card : memberCards) {
            if (card.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    public boolean deleteMember(String username) {
        for (MemberCard card : memberCards) {
            if (card.getUsername().equalsIgnoreCase(username)) {
                return card.delete();
            }
        }
        return false;
    }
}
