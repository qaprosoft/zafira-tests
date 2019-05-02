package com.qaprosoft.zafira.gui.component.table;

import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class BaseTable extends AbstractUIObject {

    @FindBy(id = "pagination")
    private Pagination pagination;

    public BaseTable(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public Pagination getPagination() {
        return pagination;
    }

}
