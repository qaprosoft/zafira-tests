package com.qaprosoft.zafira.gui.component.table;

import com.qaprosoft.zafira.gui.component.table.row.UserTableRow;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class UserTable extends BaseTable {

    @FindBy(tagName = "tr")
    private List<UserTableRow> userTableRows;

    public UserTable(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public List<UserTableRow> getUserTableRows() {
        return userTableRows;
    }

}
