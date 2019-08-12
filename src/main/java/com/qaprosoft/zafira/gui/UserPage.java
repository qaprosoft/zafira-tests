package com.qaprosoft.zafira.gui;

import com.qaprosoft.zafira.domain.Route;
import com.qaprosoft.zafira.gui.component.subheader.UserSubHeader;
import com.qaprosoft.zafira.gui.component.table.UserTable;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class UserPage extends BasePage {

    @FindBy(className = "fixed-page-header")
    private UserSubHeader userSubHeader;

    @FindBy(xpath = "//tbody[@name = 'usersTable']")
    private UserTable userTable;

    public UserPage(WebDriver driver) {
        super(driver, Route.USERS);
    }

    public UserSubHeader getUserSubHeader() {
        return userSubHeader;
    }

    public UserTable getUserTable() {
        return userTable;
    }

}
