package com.qaprosoft.zafira.gui.component.table;

import com.qaprosoft.zafira.gui.component.table.row.TestTableRow;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class TestTable extends BaseTable {

    @FindBy(className = "test")
    private List<TestTableRow> testTableRows;

    public TestTable(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public List<TestTableRow> getTestTableRows() {
        return testTableRows;
    }

}
