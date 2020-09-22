package com.qaprosoft.zafira.gui.component.table;

import com.qaprosoft.zafira.gui.component.table.row.TestRunTableRow;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class TestRunTable extends BaseTable {

    @FindBy(className = "test-run-card__wrapper")
    private List<TestRunTableRow> rows;

    public TestRunTable(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public List<TestRunTableRow> getRows() {
        return rows;
    }

}
