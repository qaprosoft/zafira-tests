package com.qaprosoft.zafira.gui;

import com.qaprosoft.zafira.domain.Route;
import com.qaprosoft.zafira.gui.component.search.TestRunFilterBlock;
import com.qaprosoft.zafira.gui.component.search.TestRunSearchBlock;
import com.qaprosoft.zafira.gui.component.subheader.TestRunSubHeader;
import com.qaprosoft.zafira.gui.component.table.TestRunTable;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class TestRunPage extends BasePage {

    @FindBy(className = "fixed-page-header")
    private TestRunSubHeader subHeader;

    @FindBy(className = "_filters-wrapper")
    private TestRunFilterBlock filterBlock;

    @FindBy(id = "search")
    private TestRunSearchBlock searchBlock;

    @FindBy(tagName = "md-card")
    private TestRunTable table;

    public TestRunPage(WebDriver driver) {
        super(driver, Route.TEST_RUNS);
    }

    public TestRunSubHeader getSubHeader() {
        return subHeader;
    }

    public TestRunFilterBlock getFilterBlock() {
        return filterBlock;
    }

    public TestRunSearchBlock getSearchBlock() {
        return searchBlock;
    }

    public TestRunTable getTable() {
        return table;
    }

}
