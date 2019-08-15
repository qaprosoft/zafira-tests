package com.qaprosoft.zafira.service.impl;

import com.qaprosoft.zafira.domain.TestRunCollector;
import com.qaprosoft.zafira.gui.TestRunItemPage;
import com.qaprosoft.zafira.gui.TestRunPage;
import com.qaprosoft.zafira.gui.component.modals.DateRangeModal;
import com.qaprosoft.zafira.gui.component.modals.ReviewedModal;
import com.qaprosoft.zafira.gui.component.modals.SendAsEmailModal;
import com.qaprosoft.zafira.gui.component.search.TestRunSearchBlock;
import com.qaprosoft.zafira.gui.component.table.row.TestRunTableRow;
import com.qaprosoft.zafira.service.DateRangePickerService;
import com.qaprosoft.zafira.service.TestRunService;
import com.qaprosoft.zafira.service.builder.Builder;
import com.qaprosoft.zafira.service.builder.TestBuilder;
import com.qaprosoft.zafira.service.builder.TestRunBuilder;
import com.qaprosoft.zafira.util.CommonUtils;
import org.openqa.selenium.WebDriver;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

public class TestRunServiceImpl extends BaseService<TestRunPage> implements TestRunService {

    private static final double SEARCH_TIMEOUT = 0.8;

    public TestRunServiceImpl(WebDriver driver) {
        super(driver, TestRunPage.class);
    }

    public TestRunServiceImpl(WebDriver driver, TestRunPage page) {
        super(driver, page, TestRunPage.class);
    }

    @Override
    public void search(String query, boolean reviewed, String status, String environment, String platform, LocalDateTime date) {
        TestRunPage testRunPage = getUIObject(driver);
        TestRunSearchBlock searchBlock = testRunPage.getSearchBlock();
        if(query != null) {
            searchBlock.typeSearchQuery(query);
        }

        boolean reviewedBlank = searchBlock.getReviewedButton().getAttribute("class").contains("label-blank");
        if(reviewed == reviewedBlank) {
            searchBlock.clickReviewedButton();
        }

        if(status != null) {
            searchBlock.selectStatus(driver, status);
        }
        if (environment != null) {
            searchBlock.selectEnvironment(driver, environment);
        }
        if(platform != null) {
            searchBlock.selectPlatform(driver, platform);
        }
        if(date != null) {
            DateRangeModal dateRangeModal = clickDatePickerButton();
            DateRangePickerService dateRangePickerService = new DateRangePickerServiceImpl(driver, testRunPage);
            dateRangePickerService.chooseDate(dateRangeModal, date, null);
            dateRangeModal.clickSubmitButton();
        }
        testRunPage.pause(SEARCH_TIMEOUT);
        testRunPage.waitProgressLinear();
    }

    @Override
    public void search(String query) {
        search(query, false, null, null, null, null);
    }

    @Override
    public void clearSearch() {
        TestRunPage testRunPage = getUIObject(driver);
        TestRunSearchBlock searchBlock = testRunPage.getSearchBlock();
        searchBlock.clickResetButton();
        testRunPage.pause(SEARCH_TIMEOUT);
        testRunPage.waitProgressLinear();
    }

    @Override
    public CompletableFuture<List<TestRunCollector>> generateTestRuns(int count, int passedCount, int failedCount, int knownIssueCount, int blockerCount,
                                                                      int skippedCount, int abortedCount, int inProgressCount, boolean toCount, boolean parallel) {
        return CompletableFuture.supplyAsync(() -> {
            List<TestRunCollector> testRunTypes = new ArrayList<>();
            TestRunPage testRunPage = getUIObject(driver);
            int totalCount = testRunPage.getSubHeader().getItemsCount();
            int countToAdd = toCount ? Math.max(count - totalCount, 0) : count;
            IntStream stream = parallel ? IntStream.range(0, countToAdd).parallel() : IntStream.range(0, countToAdd);
            stream.forEach(index -> testRunTypes.add(buildTestRun(passedCount, failedCount, knownIssueCount, blockerCount, skippedCount, abortedCount, inProgressCount)));
            return testRunTypes;
        });
    }

    @Override
    public CompletableFuture<List<TestRunCollector>> generateTestRuns(int count, boolean toCount, boolean parallel) {
        int defaultStatusItemCount = 2;
        return generateTestRuns(count, defaultStatusItemCount, defaultStatusItemCount, defaultStatusItemCount, defaultStatusItemCount,
                defaultStatusItemCount, defaultStatusItemCount, 0, toCount, parallel);
    }

    @Override
    public Integer getStatisticItem(TestBuilder.BuildStatus status, TestRunTableRow row) {
        String result = "0";
        switch (status) {
            case PASSED:
                result = row.getPassedLabelText();
                break;
            case FAILED:
                result = row.getFailedLabelText().split("\\|")[0];
                break;
            case KNOWN_ISSUE:
                result = row.getFailedLabelText().split("\\|")[1];
                break;
            case BLOCKER:
                result = row.getFailedLabelText().split("\\|")[2];
                break;
            case SKIPPED:
                if(row.getSkippedLabel().isElementPresent(1)) {
                    result = row.getSkippedLabelText();
                }
                break;
            case ABORTED:
                if(row.getAbortedLabel().isElementPresent(1)) {
                    result = row.getAbortedLabelText();
                }
                break;
            case IN_PROGRESS:
                if(row.getInProgressLabel().isElementPresent(1)) {
                    result = row.getInProgressLabelText();
                }
                break;
        }
        return Integer.valueOf(result.trim().split("\n")[0]);
    }

    @Override
    public TestRunItemPage clickTestRunRowByIndex(int index, long id) {
        TestRunPage testRunPage = getUIObject(driver);
        testRunPage.getTable().getRows().get(index).clickClickableArea();
        return new TestRunItemPage(driver, id);
    }

    @Override
    public ReviewedModal clickMarkAsReviewedButton(TestRunTableRow row) {
        row.clickMenuButton();
        CommonUtils.clickMenuItem(row.getMenuButton(), driver, "Mark as reviewed");
        return new ReviewedModal(driver);
    }

    @Override
    public TestRunItemPage open(int index, long id) {
        TestRunPage testRunPage = getUIObject(driver);
        TestRunTableRow row = testRunPage.getTable().getRows().get(index);
        Set<String> handles = driver.getWindowHandles();
        row.clickMenuButton();
        CommonUtils.clickMenuItem(row.getMenuButton(), driver, "Open");
        Set<String> newHandles = driver.getWindowHandles();
        newHandles.removeAll(handles);
        if(newHandles.size() == 1) {
            driver.switchTo().window(newHandles.iterator().next());
        }
        return new TestRunItemPage(driver, id);
    }

    @Override
    public String copyLink(int index, long id) {
        TestRunPage testRunPage = getUIObject(driver);
        TestRunTableRow row = testRunPage.getTable().getRows().get(index);
        row.clickMenuButton();
        CommonUtils.clickMenuItem(row.getMenuButton(), driver, "Copy link");
        return new TestRunItemPage(driver, id).getPageURL();
    }

    @Override
    public void markAsReviewed(int index, String comment) {
        TestRunPage testRunPage = getUIObject(driver);
        TestRunTableRow row = testRunPage.getTable().getRows().get(index);
        ReviewedModal reviewedModal = clickMarkAsReviewedButton(row);
        reviewedModal.typeComment(comment);
        reviewedModal.clickMarkAsReviewedButton();
    }

    @Override
    public SendAsEmailModal clickSendAsEmailButton(int index) {
        TestRunPage testRunPage = getUIObject(driver);
        TestRunTableRow row = testRunPage.getTable().getRows().get(index);
        row.clickMenuButton();
        waitProgressLinear();
        CommonUtils.clickMenuItem(row.getMenuButton(), driver, "Send as email");
        return new SendAsEmailModal(driver);
    }

    @Override
    public void exportToHtml(int index) {

    }

    @Override
    public void delete(int index) {
        TestRunPage testRunPage = getUIObject(driver);
        TestRunTableRow row = testRunPage.getTable().getRows().get(index);
        row.clickMenuButton();
        CommonUtils.clickMenuItem(row.getMenuButton(), driver, "Delete");
        driver.switchTo().alert().accept();
    }

    private TestRunCollector buildTestRun(int passedCount, int failedCount, int knownIssueCount, int blockerCount, int skippedCount, int abortedCount, int inProgressCount) {
        TestRunBuilder builder = new TestRunBuilder.Builder()
                .setPassedCount(passedCount)
                .setFailedCount(failedCount)
                .setKnownIssueCount(knownIssueCount)
                .setBlockerCount(blockerCount)
                .setSkippedCount(skippedCount)
                .setAbortedCount(abortedCount)
                .setInProgressCount(inProgressCount)
                .build();
        return Builder.TEST_RUN.build(builder.getBuilder());
    }

    private DateRangeModal clickDatePickerButton() {
        TestRunPage testRunPage = getUIObject(driver);
        TestRunSearchBlock searchBlock = testRunPage.getSearchBlock();
        searchBlock.clickDatePickerButton();
        return new DateRangeModal(driver);
    }

}
