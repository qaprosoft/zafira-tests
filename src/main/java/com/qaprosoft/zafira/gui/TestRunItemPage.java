package com.qaprosoft.zafira.gui;

import com.qaprosoft.zafira.domain.Route;
import com.qaprosoft.zafira.gui.component.subheader.TestRunItemSubHeader;
import com.qaprosoft.zafira.gui.component.table.TestTable;
import com.qaprosoft.zafira.gui.component.table.row.TestRunTableRow;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class TestRunItemPage extends BasePage {

    @FindBy(className = "fixed-page-header")
    private TestRunItemSubHeader testRunItemSubHeader;

    @FindBy(className = "test-run-card")
    private TestRunTableRow testRunInfo;

    @FindBy(tagName = "table")
    private TestTable testTable;

    public TestRunItemPage(WebDriver driver, long id) {
        super(driver, Route.TEST_RUNS_ITEM, id);
    }

    public TestRunItemSubHeader getTestRunItemSubHeader() {
        return testRunItemSubHeader;
    }

    public TestRunTableRow getTestRunInfo() {
        return testRunInfo;
    }

    public TestTable getTestTable() {
        return testTable;
    }

}
