package com.qaprosoft.zafira.gui.component.sidebarmenu;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.zafira.gui.component.SidebarMenu;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class UserSidebarMenu extends SidebarMenu {

    @FindBy(xpath = ".//ul//*[text() = 'Users']")
    private ExtendedWebElement userButton;

    @FindBy(xpath = ".//ul//*[text() = 'Groups']")
    private ExtendedWebElement groupButton;

    @FindBy(xpath = ".//ul//*[text() = 'Invitations']")
    private ExtendedWebElement invitationButton;

    public UserSidebarMenu(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public ExtendedWebElement getUserButton() {
        return userButton;
    }

    public void clickUserButton() {
        userButton.click();
    }

    public ExtendedWebElement getGroupButton() {
        return groupButton;
    }

    public void clickGroupButton() {
        groupButton.click();
    }

    public ExtendedWebElement getInvitationButton() {
        return invitationButton;
    }

    public void clickInvitationButton() {
        invitationButton.click();
    }

}
