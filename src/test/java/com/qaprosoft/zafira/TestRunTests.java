package com.qaprosoft.zafira;

import com.qaprosoft.carina.core.foundation.utils.ownership.MethodOwner;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.zafira.domain.Config;
import com.qaprosoft.zafira.domain.TestRunCollector;
import com.qaprosoft.zafira.gui.DashboardPage;
import com.qaprosoft.zafira.gui.TestRunItemPage;
import com.qaprosoft.zafira.gui.TestRunPage;
import com.qaprosoft.zafira.gui.component.modals.ReviewedModal;
import com.qaprosoft.zafira.gui.component.modals.SendAsEmailModal;
import com.qaprosoft.zafira.gui.component.search.TestRunSearchBlock;
import com.qaprosoft.zafira.gui.component.subheader.TestRunSubHeader;
import com.qaprosoft.zafira.gui.component.table.row.TestRunTableRow;
import com.qaprosoft.zafira.gui.component.table.row.TestTableRow;
import com.qaprosoft.zafira.models.db.Status;
import com.qaprosoft.zafira.models.dto.TestType;
import com.qaprosoft.zafira.service.PaginationService;
import com.qaprosoft.zafira.service.SidebarService;
import com.qaprosoft.zafira.service.TestRunService;
import com.qaprosoft.zafira.service.builder.TestBuilder;
import com.qaprosoft.zafira.service.impl.PaginationServiceImpl;
import com.qaprosoft.zafira.service.impl.SidebarServiceImpl;
import com.qaprosoft.zafira.service.impl.TestRunServiceImpl;
import com.qaprosoft.zafira.util.CommonUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

import static com.qaprosoft.zafira.service.builder.TestBuilder.BuildStatus.IN_PROGRESS;
import static com.qaprosoft.zafira.util.CommonUtils.getSelectValue;
import static com.qaprosoft.zafira.util.CommonUtils.isChecked;
import static com.qaprosoft.zafira.util.CommonUtils.scrollTop;
import static com.qaprosoft.zafira.util.CommonUtils.waitForCompletableFuture;

public class TestRunTests extends BaseTest {

    @BeforeMethod
    public void setup() {
        DashboardPage dashboardPage = signin();
        SidebarService sidebarService = new SidebarServiceImpl(getDriver(), dashboardPage);
        sidebarService.goToTestRunPage();
    }

    @Test
    @MethodOwner(owner = "brutskov")
    public void verifyTestRunRegistrationTest() {
        TestRunService testRunService = new TestRunServiceImpl(getDriver());
        TestRunPage testRunPage = new TestRunPage(getDriver());
        int currentCount = testRunPage.getSubHeader().getItemsCount();
        List<TestRunCollector> testRunCollectors = waitForCompletableFuture(testRunService.generateTestRuns(2, false, false));
        Assert.assertEquals(testRunPage.getSubHeader().getItemsCount(), currentCount + testRunCollectors.size(), "Test run total count is not changed after new test runs registration");
        verifyGeneratedTestRuns(testRunPage, testRunCollectors);
    }

    @Test
    @MethodOwner(owner = "brutskov")
    public void verifyNavigationTest() {
        TestRunService testRunService = new TestRunServiceImpl(getDriver());
        CompletableFuture<List<TestRunCollector>> testRunsBuildFuture = testRunService.generateTestRuns(2, true, true);
        TestRunPage testRunPage = new TestRunPage(getDriver());
        TestRunSubHeader subHeader = testRunPage.getSubHeader();
        Assert.assertTrue(subHeader.getTitleText().matches("Test runs \\([0-9]+\\)"), "Incorrect test runs page title");
        Assert.assertTrue(subHeader.getItemsCountLabel().isElementPresent(1), "Test runs total count label is not present");
        Assert.assertTrue(subHeader.getItemsCount() >= 0, "Test runs total count is negative");
        Assert.assertTrue(subHeader.getLauncherButton().isElementPresent(1), "Test runs launcher button is not present");

        waitForCompletableFuture(testRunsBuildFuture);
        List<TestRunTableRow> testRunTableRows = testRunPage.getTable().getRows();
        TestRunSearchBlock searchBlock = testRunPage.getSearchBlock();
        testRunTableRows.forEach(testRunTableRow -> {
            ExtendedWebElement checkbox = IN_PROGRESS.equals(testRunTableRow.getStatus()) ? testRunTableRow.getInProgressCircularProgressBar() : testRunTableRow.getSelectedCheckbox();
            Assert.assertFalse(isChecked(checkbox), "Test run checkbox is checked by default");
            checkbox.click();
        });
        Assert.assertTrue(isChecked(searchBlock.getMainCheckbox()), "Main checkbox is unchecked after all test run checkboxes was checked");

        scrollTop(getDriver());

        testRunTableRows.forEach(testRunTableRow -> {
            Assert.assertTrue(isChecked(testRunTableRow.getSelectedCheckbox()), "Test run checkbox is not checked after checking");
            testRunTableRow.clickSelectedCheckbox();
            ExtendedWebElement checkbox = IN_PROGRESS.equals(testRunTableRow.getStatus()) ? testRunTableRow.getInProgressCircularProgressBar() : testRunTableRow.getSelectedCheckbox();
            Assert.assertFalse(isChecked(checkbox), "Test run checkbox is checked after unchecking");
        });
        Assert.assertFalse(isChecked(searchBlock.getMainCheckbox()), "Main checkbox is checked after all test run checkboxes was unchecked");

        scrollTop(getDriver());

        searchBlock.clickMainCheckbox();
        testRunTableRows.forEach(testRunTableRow -> {
            Assert.assertTrue(isChecked(testRunTableRow.getSelectedCheckbox()), "Test run checkbox is not checked after main checkbox checking");
        });

        searchBlock.clickMainCheckbox();
        testRunTableRows.forEach(testRunTableRow -> {
            Assert.assertFalse(isChecked(testRunTableRow.getSelectedCheckbox()), "Test run checkbox is checked after main checkbox unchecking");
        });

        testRunTableRows.get(0).clickSelectedCheckbox();
        Assert.assertFalse(isChecked(searchBlock.getMainCheckbox()), "Main checkbox is checked after one of the test run checkboxes was checked");

        searchBlock.clickMainCheckbox();
        testRunTableRows.forEach(testRunTableRow -> {
            Assert.assertTrue(isChecked(testRunTableRow.getSelectedCheckbox()), "Test run checkbox is not checked after main checkbox checking");
        });

        searchBlock.clickMainCheckbox();
        testRunTableRows.forEach(testRunTableRow -> {
            Assert.assertFalse(isChecked(testRunTableRow.getSelectedCheckbox()), "Test run checkbox is checked after main checkbox unchecking");
        });
    }

    @Test
    @MethodOwner(owner = "brutskov")
    public void verifySearchTest() {
        TestRunService testRunService = new TestRunServiceImpl(getDriver());
        CompletableFuture<List<TestRunCollector>> testRunsBuildFuture = testRunService.generateTestRuns(1, 2, 0, 0, 0, 0, 0, 0, false, false);
        CompletableFuture<CompletableFuture<List<TestRunCollector>>> testRunsBuildSecondFuture = testRunsBuildFuture.thenApply(testRunCollectors -> testRunService.generateTestRuns(1, false, false));
        TestRunPage testRunPage = new TestRunPage(getDriver());
        TestRunSearchBlock searchBlock = testRunPage.getSearchBlock();
        Assert.assertTrue(searchBlock.getMainCheckbox().isElementPresent(1), "Main checkbox is not present");
        Assert.assertTrue(searchBlock.getQuerySearchInput().isElementPresent(1), "Search query input is not present");
        Assert.assertTrue(searchBlock.getReviewedButton().isElementPresent(1), "Search reviewed is not present");
        Assert.assertTrue(searchBlock.getStatusSelect().isElementPresent(1), "Search status select is not present");
        Assert.assertEquals(getSelectValue(searchBlock.getStatusSelect()), "Status", "Search status select is not empty");
        Assert.assertTrue(searchBlock.getEnvironmentSelect().isElementPresent(1), "Search Environment select is not present");
        Assert.assertEquals(getSelectValue(searchBlock.getEnvironmentSelect()), "Environment", "Search environment select is not empty");
        Assert.assertTrue(searchBlock.getPlatformSelect().isElementPresent(1), "Search platform select is not present");
        Assert.assertEquals(getSelectValue(searchBlock.getPlatformSelect()), "Platform", "Search platform select is not empty");
        Assert.assertTrue(searchBlock.getDatePickerButton().isElementPresent(1), "Search date picker button is not present");
        Assert.assertFalse(searchBlock.getResetButton().isElementPresent(1), "Search reset button is present");

        testRunPage.getSearchBlock().typeSearchQuery("test");
        Assert.assertTrue(searchBlock.getResetButton().isElementPresent(1), "Search reset button is not present");
        Assert.assertTrue(searchBlock.getResetButton().getElement().isEnabled(), "Search reset button is disabled");
        testRunService.clearSearch();

        List<TestRunCollector> testRunCollectors = waitForCompletableFuture(testRunsBuildFuture);
        waitForCompletableFuture(
                waitForCompletableFuture(testRunsBuildSecondFuture));
        TestRunCollector testRunCollector = testRunCollectors.get(0);

        testRunService.markAsReviewed(1, "test");

        testRunService.search(testRunCollector.getTestSuiteType().getName().substring(0, 5), false, null, null, null, null);
        verifyGeneratedTestRuns(testRunPage, testRunCollectors);
        testRunService.clearSearch();

        TestRunTableRow row = testRunPage.getTable().getRows().get(1);
        Assert.assertEquals(row.getTestSuiteNameLableText(), testRunCollector.getTestSuiteType().getName(), "Search clear logic is incorrect");

        testRunService.search(testRunCollector.getJobType().getJobURL(), false, null, null, null, null);
        verifyGeneratedTestRuns(testRunPage, testRunCollectors);
        testRunService.clearSearch();

        testRunService.search(Config.CONFIG_XML_APP_VERSION.getValue(), false, "PASSED", null, null, null);
        verifyGeneratedTestRuns(testRunPage, testRunCollectors);
        testRunService.clearSearch();

        testRunService.search(null, true, "PASSED", null, null, null);
        verifyGeneratedTestRuns(testRunPage, testRunCollectors);
        testRunService.clearSearch();

        testRunService.search(null, false, "PASSED", Config.CONFIG_XML_ENVIRONMENT.getConfigXmlValue(), null, null);
        verifyGeneratedTestRuns(testRunPage, testRunCollectors);
        testRunService.clearSearch();

        testRunService.search(null, false, "PASSED", null, Config.CONFIG_XML_BROWSER.getConfigXmlValue(), null);
        verifyGeneratedTestRuns(testRunPage, testRunCollectors);
        testRunService.clearSearch();

        testRunService.search(null, false, "PASSED", null, null, LocalDateTime.now());
        verifyGeneratedTestRuns(testRunPage, testRunCollectors);
        testRunService.clearSearch();
    }

    @Test
    @MethodOwner(owner = "brutskov")
    public void verifyPaginationTest() {
        TestRunService testRunService = new TestRunServiceImpl(getDriver());
        CompletableFuture<List<TestRunCollector>> testRunsBuildFuture = testRunService.generateTestRuns(25, true, true);

        TestRunPage testRunPage = new TestRunPage(getDriver());
        Assert.assertTrue(testRunPage.getTable().getPagination().getCountLabel().isElementPresent(1), "Pagination count label is not present");
        Assert.assertTrue(testRunPage.getTable().getPagination().getNavigateFirstButton().isElementPresent(1), "Pagination navigate first button is not present");
        Assert.assertTrue(testRunPage.getTable().getPagination().getNavigateBeforeButton().isElementPresent(1), "Pagination navigate before button is not present");
        Assert.assertTrue(testRunPage.getTable().getPagination().getNavigateNextButton().isElementPresent(1), "Pagination navigate next button is not present");
        Assert.assertTrue(testRunPage.getTable().getPagination().getNavigateLastButton().isElementPresent(1), "Pagination navigate last button is not present");

        waitForCompletableFuture(testRunsBuildFuture);

        PaginationService paginationService = new PaginationServiceImpl(getDriver(), testRunPage.getTable());

        int count = testRunPage.getSubHeader().getItemsCount();

        int page = 0;
        boolean isNextEnabled = true;

        while(isNextEnabled) {
            isNextEnabled = verifyPaginationInfo(testRunPage, paginationService, page, count);
            testRunPage.getTable().getPagination().clickNavigateNextButton();
            page ++;
        }

        int totalPageCount = page;
        isNextEnabled = true;

        while(isNextEnabled) {
            page --;
            verifyPaginationInfo(testRunPage, paginationService, page, count);
            testRunPage.getTable().getPagination().clickNavigateBeforeButton();
            isNextEnabled = page != 0;
        }

        testRunPage.getTable().getPagination().clickNavigateLastButton();
        page = totalPageCount;
        verifyPaginationInfo(testRunPage, paginationService, page, count);

        testRunPage.getTable().getPagination().clickNavigateFirstButton();
        page = 0;
        verifyPaginationInfo(testRunPage, paginationService, page, count);
    }

    @Test
    @MethodOwner(owner = "brutskov")
    public void verifyTestRunOpenTest() {
        TestRunService testRunService = new TestRunServiceImpl(getDriver());
        List<TestRunCollector> testRunCollectors = waitForCompletableFuture(testRunService.generateTestRuns(1, false, false));
        TestRunCollector testRunCollector = testRunCollectors.get(0);
        testRunService.search(testRunCollector.getTestSuiteType().getName());
        Set<String> handles = getDriver().getWindowHandles();
        TestRunItemPage testRunItemPage = testRunService.open(0, testRunCollector.getTestRunType().getId());
        Assert.assertTrue(testRunItemPage.isPageOpened(), "Test run open logic is not correct");
        Assert.assertFalse(handles.contains(getDriver().getWindowHandle()), "Test run is not opened in new tab");
        verifyTestTable(testRunItemPage, testRunCollector);
    }

    @Test
    @MethodOwner(owner = "brutskov")
    public void verifyTestRunCopyLinkTest() {
        TestRunService testRunService = new TestRunServiceImpl(getDriver());
        List<TestRunCollector> testRunCollectors = waitForCompletableFuture(testRunService.generateTestRuns(1, false, false));
        TestRunCollector testRunCollector = testRunCollectors.get(0);
        testRunService.search(testRunCollector.getTestSuiteType().getName());
        String generatedUrl = testRunService.copyLink(0, testRunCollector.getTestRunType().getId());

        TestRunPage testRunPage = new TestRunPage(getDriver());
        testRunPage.getSearchBlock().getQuerySearchInput().getElement().clear();
        Assert.assertTrue(CommonUtils.getInputText(testRunPage.getSearchBlock().getQuerySearchInput()).isEmpty(), "Cannot clear access token input to test clipboard");
        CommonUtils.pastTo(testRunPage.getSearchBlock().getQuerySearchInput());
        String copiedUrl = CommonUtils.getInputText(testRunPage.getSearchBlock().getQuerySearchInput());
        Assert.assertEquals(copiedUrl, generatedUrl, "Test run copied link is incorrect");
    }

    @Test
    @MethodOwner(owner = "brutskov")
    public void verifyTestRunMarkAsReviewedTest() {
        TestRunService testRunService = new TestRunServiceImpl(getDriver());
        List<TestRunCollector> testRunCollectors = waitForCompletableFuture(testRunService.generateTestRuns(1, 2, 0, 0, 0, 0, 0, 0, false, false));
        TestRunCollector testRunCollector = testRunCollectors.get(0);
        testRunService.search(testRunCollector.getTestSuiteType().getName());
        TestRunPage testRunPage = new TestRunPage(getDriver());
        TestRunTableRow row = testRunPage.getTable().getRows().get(0);
        ReviewedModal reviewedModal = testRunService.clickMarkAsReviewedButton(row);

        Assert.assertEquals(reviewedModal.getTitleText(), "Comments", "Mark as reviewed modal has incorrect title");
        Assert.assertTrue(reviewedModal.getCommentTextarea().getElement().isEnabled(), "Comment textarea is disabled");
        Assert.assertTrue(reviewedModal.getMarkAsReviewedButton().getElement().isEnabled(), "Mark as reviewed button is disabled");

        reviewedModal.typeComment("test");
        reviewedModal.clickMarkAsReviewedButton();
        reviewedModal.getMarkAsReviewedButton().waitUntilElementDisappear(5);

        Assert.assertEquals(testRunPage.getSuccessAlertText(), "Test run #" + testRunCollector.getTestRunType().getId() + " marked as reviewed", "Mark as reviewed success alert has incorrect text");
        Assert.assertTrue(row.getReviewedIcon().isElementPresent(1), "Reviewed icon is not present");
        Assert.assertTrue(row.getCommentIcon().isElementPresent(1), "Comment icon is not present");

        pause(ANIMATION_TIMEOUT);
        row.clickCommentIcon();
        Assert.assertTrue(reviewedModal.getTitle().isElementPresent(1), "Reviewed modal is not opened after comment icon click");

        pause(ANIMATION_TIMEOUT);
        reviewedModal.typeComment("");
        reviewedModal.clickMarkAsReviewedButton();

        Assert.assertEquals(testRunPage.getSuccessAlertText(), "Test run #" + testRunCollector.getTestRunType().getId() + " marked as reviewed", "Mark as reviewed success alert has incorrect text");
    }

    // TODO: 9/11/19 enable test after bug connected with integrations update handling will be fixed 
    @Test(enabled = false)
    @MethodOwner(owner = "brutskov")
    public void verifyTestRunSendEmailTest() {
        TestRunService testRunService = new TestRunServiceImpl(getDriver());
        List<TestRunCollector> testRunCollectors = waitForCompletableFuture(testRunService.generateTestRuns(1, false, false));
        TestRunCollector testRunCollector = testRunCollectors.get(0);
        testRunService.search(testRunCollector.getTestSuiteType().getName());

        TestRunPage testRunPage = new TestRunPage(getDriver());
        testRunPage.waitProgressLinear();
        SendAsEmailModal sendAsEmailModal = testRunService.clickSendAsEmailButton(0);

        Assert.assertEquals(sendAsEmailModal.getTitleText(), "Email", "Send as email modal title is incorrect");
        Assert.assertTrue(sendAsEmailModal.getEmailsInput().getElement().isEnabled(), "Emails input is disabled");
        Assert.assertTrue(sendAsEmailModal.getSendButton().getElement().isEnabled(), "Send email button is disabled");
        sendAsEmailModal.clickSendButton();
        Assert.assertTrue(testRunPage.isElementWithTextPresent(testRunPage.getErrorAlert(), "Add a recipient!"), "Send email error alert text is incorrect");
        sendAsEmailModal.getEmailsInput().click();
        sendAsEmailModal.typeRecipients("test@test.test");
        sendAsEmailModal.clickSendButton();
        sendAsEmailModal.getSendButton().waitUntilElementDisappear(5);
        Assert.assertEquals(testRunPage.getSuccessAlertText(), "Email was successfully sent!", "Send email success alert text is incorrect");
    }

    @Test
    @MethodOwner(owner = "brutskov")
    public void verifyTestRunDeleteTest() {
        TestRunService testRunService = new TestRunServiceImpl(getDriver());
        List<TestRunCollector> testRunCollectors = waitForCompletableFuture(testRunService.generateTestRuns(1, false, false));
        TestRunCollector testRunCollector = testRunCollectors.get(0);
        testRunService.search(testRunCollector.getTestSuiteType().getName());
        TestRunPage testRunPage = new TestRunPage(getDriver());
        TestRunTableRow row = testRunPage.getTable().getRows().get(0);

        Assert.assertEquals(row.getTestSuiteNameLableText(), testRunCollector.getTestSuiteType().getName(), "Test run was not generated");

        testRunService.delete(0);
        testRunService.clearSearch();

        Assert.assertEquals(testRunPage.getSuccessAlertText(), "Test run #" + testRunCollector.getTestRunType().getId() + " removed", "Test run delete success alert is not valid");
        testRunPage = new TestRunPage(getDriver());
        row = testRunPage.getTable().getRows().get(0);
        Assert.assertNotEquals(row.getTestSuiteNameLableText(), testRunCollector.getTestSuiteType().getName(), "Test run is present after deleting");
    }

    private void verifyGeneratedTestRuns(TestRunPage testRunPage, List<TestRunCollector> testRunCollectors) {
        TestRunService testRunService = new TestRunServiceImpl(getDriver());
        IntStream.range(0, testRunCollectors.size()).forEach(index -> {
            int revertedIndex = testRunCollectors.size() - index - 1;
            TestRunCollector testRunCollector = testRunCollectors.get(revertedIndex);
            TestRunTableRow row = testRunPage.getTable().getRows().get(index);
            Assert.assertTrue(row.getSelectedCheckbox().isElementPresent(1), "Test run checkbox is not present");
            Assert.assertTrue(row.getRootElement().getAttribute("class").contains(testRunCollector.getTestRunType().getStatus().name()), "Test run status label is incorrect");

            verifyTestRunCommonInfo(row, testRunCollector);

            TestRunItemPage testRunItemPage = testRunService.clickTestRunRowByIndex(index, testRunCollector.getTestRunType().getId());
            Assert.assertTrue(testRunItemPage.isPageOpened(), "Test run item page is not opened");
            verifyTestTable(testRunItemPage, testRunCollector);
        });
    }

    private void verifyTestTable(TestRunItemPage testRunItemPage, TestRunCollector testRunCollector) {
        Assert.assertTrue(testRunItemPage.getTestRunItemSubHeader().getBackButton().isElementPresent(1), "Test run item back button is not present");
        Assert.assertEquals(testRunItemPage.getTestRunItemSubHeader().getTitleText(), "Test results", "Test run item title is incorrect");

        verifyTestRunCommonInfo(testRunItemPage.getTestRunInfo(), testRunCollector);

        List<TestTableRow> testTableRows = testRunItemPage.getTestTable().getTestTableRows();
        IntStream.range(0, testTableRows.size()).forEach(index -> {
            TestTableRow row = testTableRows.get(index);
            TestType testType = testRunCollector.getTestTypes().get(index);
            Assert.assertEquals(row.getNameLabelText(), testType.getName(), "Test name is incorrect");
            Assert.assertEquals(row.getOwnerLabelText(), Config.ADMIN_USERNAME.getValue(), "Test owner name is incorrect");
            Assert.assertEquals(row.getTaskTicketLinkText(), testType.getWorkItems().get(0), "Test work item title is incorrect");
            Assert.assertTrue(row.getDetailsButton().isElementPresent(1), "Test details button is not present");
            Assert.assertTrue(row.getMenuButton().isElementPresent(1), "Test menu button is not present");
        });

        testRunItemPage.getTestRunItemSubHeader().clickBackButton();
        testRunItemPage.waitProgressLinear();
    }

    private void verifyTestRunCommonInfo(TestRunTableRow row, TestRunCollector testRunCollector) {
        TestRunService testRunService = new TestRunServiceImpl(getDriver());
        Assert.assertEquals(row.getTestSuiteNameLableText(), testRunCollector.getTestSuiteType().getName(), "Test run name is incorrect");
        Assert.assertEquals(row.getTestSuiteNameLableText(), testRunCollector.getTestSuiteType().getName(), "Test run name is incorrect");
        Assert.assertEquals(row.getJobNameLabel().getAttribute("href"), testRunCollector.getJobType().getJobURL()
                + "/" + testRunCollector.getTestRunType().getBuildNumber(), "Test run job url is incorrect");
        Assert.assertEquals(row.getAppNameLabelText(), Config.CONFIG_XML_APP_VERSION.getConfigXmlValue(), "Test run app name is incorrect");
        Assert.assertEquals(row.getEnvironmentLabelText(), Config.CONFIG_XML_ENVIRONMENT.getConfigXmlValue(), "Test run environment label is incorrect");
        Assert.assertTrue(row.getBrowserIcon().getAttribute("class").contains(Config.CONFIG_XML_BROWSER.getConfigXmlValue()), "Test run browser label is incorrect");

        int passedCount = testRunService.getStatisticItem(TestBuilder.BuildStatus.PASSED, row);
        int failedCount = testRunService.getStatisticItem(TestBuilder.BuildStatus.FAILED, row);
        int skippedCount = testRunService.getStatisticItem(TestBuilder.BuildStatus.SKIPPED, row);
        int abortedCount = testRunService.getStatisticItem(TestBuilder.BuildStatus.ABORTED, row);
        Assert.assertEquals(passedCount, testRunCollector.getTestTypes().stream().filter(testType -> testType.getStatus().equals(Status.PASSED)).count(), "Passed count is not valid");
        Assert.assertEquals(failedCount, testRunCollector.getTestTypes().stream().filter(testType -> testType.getStatus().equals(Status.FAILED)).count(), "Failed count is not valid");
        Assert.assertEquals(skippedCount, testRunCollector.getTestTypes().stream().filter(testType -> testType.getStatus().equals(Status.SKIPPED)).count(), "Skipped count is not valid");
        Assert.assertEquals(abortedCount, testRunCollector.getTestTypes().stream().filter(testType -> testType.getStatus().equals(Status.ABORTED)).count(), "Aborted count is not valid");

        Assert.assertTrue(row.getStartedAtLabel().isElementPresent(1), "Started at label is not present");
        Assert.assertTrue(row.getMenuButton().isElementPresent(1), "Menu button is not present");
    }

    /**
     *
     * @param paginationService - service instance
     * @param pageCount - page count from 0
     * @param totalCount - total itemsCount
     * @return isNextButtonEnabled
     */
    private boolean verifyPaginationInfo(TestRunPage testRunPage, PaginationService paginationService, int pageCount, int totalCount) {

        int defaultFromValue = 1;
        int defaultPaginationItemsCount = 20;

        int fromValue = pageCount * defaultPaginationItemsCount + defaultFromValue;
        int toValue = pageCount * defaultPaginationItemsCount + defaultPaginationItemsCount;
        fromValue = fromValue > totalCount ? totalCount - (totalCount % defaultPaginationItemsCount) + defaultFromValue : fromValue;
        toValue = toValue > totalCount ? totalCount : toValue;

        boolean isFirstButtonMustEnabled = fromValue - defaultFromValue != 0;
        boolean isFirstButtonEnabled = testRunPage.getTable().getPagination().getNavigateFirstButton().getElement().isEnabled();
        boolean isBeforeButtonMustEnabled = fromValue - defaultFromValue != 0;
        boolean isBeforeButtonEnabled = testRunPage.getTable().getPagination().getNavigateBeforeButton().getElement().isEnabled();
        boolean isNextButtonMustEnabled = toValue != totalCount;
        boolean isNextButtonEnabled = testRunPage.getTable().getPagination().getNavigateNextButton().getElement().isEnabled();
        boolean isLastButtonMustEnabled = toValue != totalCount;
        boolean isLastButtonEnabled = testRunPage.getTable().getPagination().getNavigateLastButton().getElement().isEnabled();

        isFirstButtonEnabled = isFirstButtonMustEnabled == isFirstButtonEnabled;
        isBeforeButtonEnabled = isBeforeButtonMustEnabled == isBeforeButtonEnabled;
        isNextButtonEnabled = isNextButtonMustEnabled == isNextButtonEnabled;
        isLastButtonEnabled = isLastButtonMustEnabled == isLastButtonEnabled;

        Assert.assertEquals(paginationService.getFromItemValue(), fromValue, "Start from item value is incorrect");
        Assert.assertEquals(paginationService.getToItemValue(), toValue, "Start to item value is incorrect");
        Assert.assertEquals(paginationService.getTotalItemsValue(), totalCount, "Total item value is incorrect");

        Assert.assertTrue(isFirstButtonEnabled, "Navigation first button is " + (isFirstButtonEnabled ? "disabled" : "enabled"));
        Assert.assertTrue(isBeforeButtonEnabled, "Navigation before button is " + (isBeforeButtonEnabled ? "disabled" : "enabled"));
        Assert.assertTrue(isNextButtonEnabled, "Navigation next button is " + (isNextButtonEnabled ? "disabled" : "enabled"));
        Assert.assertTrue(isLastButtonEnabled, "Navigation last button is " + (isLastButtonEnabled ? "disabled" : "enabled"));

        return toValue != totalCount;
    }

}
