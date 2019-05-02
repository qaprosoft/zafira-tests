package com.qaprosoft.zafira;

import com.qaprosoft.carina.core.foundation.utils.ownership.MethodOwner;
import com.qaprosoft.zafira.gui.DashboardPage;
import com.qaprosoft.zafira.gui.TestRunPage;
import com.qaprosoft.zafira.gui.component.search.TestRunSearchBlock;
import com.qaprosoft.zafira.gui.component.subheader.TestRunSubHeader;
import com.qaprosoft.zafira.models.dto.TestRunType;
import com.qaprosoft.zafira.service.SidebarService;
import com.qaprosoft.zafira.service.TestRunService;
import com.qaprosoft.zafira.service.impl.SidebarServiceImpl;
import com.qaprosoft.zafira.service.impl.TestRunServiceImpl;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.qaprosoft.zafira.util.CommonUtils.getSelectValue;
import static com.qaprosoft.zafira.util.CommonUtils.waitForCompletableFuture;

public class TestRunTests extends BaseTest {

    private TestRunPage testRunPage;

    @BeforeMethod
    public void setup() {
        DashboardPage dashboardPage = signin();
        SidebarService sidebarService = new SidebarServiceImpl(getDriver(), dashboardPage);
        this.testRunPage = sidebarService.goToTestRunPage();
    }

    @Test
    @MethodOwner(owner = "brutskov")
    public void verifyNavigationTest() {
        TestRunService testRunService = new TestRunServiceImpl(getDriver());
        CompletableFuture<List<TestRunType>> testRunsBuildFuture = testRunService.generateTestRuns(2, false);
        TestRunSubHeader subHeader = testRunPage.getSubHeader();
        Assert.assertTrue(subHeader.getTitleText().matches("Test runs \\([0-9]+\\)"), "Incorrect test runs page title");
        Assert.assertTrue(subHeader.getItemsCountLabel().isElementPresent(1), "Test runs total count label is not present");
        Assert.assertTrue(subHeader.getItemsCount() >= 0, "Test runs total count is negative");
        Assert.assertTrue(subHeader.getLauncherButton().isElementPresent(1), "Test runs launcher button is not present");

        List<TestRunType> testRuns = waitForCompletableFuture(testRunsBuildFuture);
    }

    @Test
    @MethodOwner(owner = "brutskov")
    public void verifySearchTest() {
        TestRunSearchBlock searchBlock = testRunPage.getSearchBlock();
        Assert.assertTrue(searchBlock.getMainCheckbox().isElementPresent(1), "Main checkbox is not present");
        Assert.assertTrue(searchBlock.getQuerySearchInput().isElementPresent(1), "Search query input is not present");
        Assert.assertTrue(searchBlock.getReviewedButton().isElementPresent(1), "Search reviewed is not present");
        Assert.assertTrue(searchBlock.getStatusSelect().isElementPresent(1), "Search status select is not present");
        Assert.assertEquals(getSelectValue(searchBlock.getStatusSelect()), "Status", "Search status select is not empty");
        Assert.assertTrue(searchBlock.getEnvironmentSelect().isElementPresent(1), "Search Environment select is not present");
        Assert.assertEquals(getSelectValue(searchBlock.getEnvironmentSelect()), "Environment", "Search environment select is not empty");
        Assert.assertTrue(searchBlock.getPlatformSelect().isElementPresent(1), "Search platform select is not present");
        Assert.assertEquals(getSelectValue(searchBlock.getStatusSelect()), "Platform", "Search platform select is not empty");
        Assert.assertTrue(searchBlock.getDatePickerButton().isElementPresent(1), "Search date picker button is not present");
        Assert.assertTrue(searchBlock.getResetButton().isElementPresent(1), "Search reset button is not present");
        Assert.assertTrue(searchBlock.getResetButton().getElement().isEnabled(), "Search reset button is disabled");
    }

}
