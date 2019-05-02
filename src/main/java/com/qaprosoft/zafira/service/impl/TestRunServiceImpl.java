package com.qaprosoft.zafira.service.impl;

import com.qaprosoft.zafira.gui.TestRunPage;
import com.qaprosoft.zafira.gui.component.modals.DateRangeModal;
import com.qaprosoft.zafira.gui.component.search.TestRunSearchBlock;
import com.qaprosoft.zafira.models.dto.TestRunType;
import com.qaprosoft.zafira.service.DateRangePickerService;
import com.qaprosoft.zafira.service.TestRunService;
import com.qaprosoft.zafira.service.builder.Builder;
import com.qaprosoft.zafira.service.builder.TestRunBuilder;
import org.openqa.selenium.WebDriver;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

public class TestRunServiceImpl extends BaseService<TestRunPage> implements TestRunService {

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
        }
    }

    @Override
    public CompletableFuture<List<TestRunType>> generateTestRuns(int count, int passedCount, int failedCount, int knownIssueCount, int blockerCount,
                                              int skippedCount, int abortedCount, int inProgressCount, boolean toCount) {
        return CompletableFuture.supplyAsync(() -> {
            List<TestRunType> testRunTypes = new ArrayList<>();
            TestRunPage testRunPage = getUIObject(driver);
            int totalCount = testRunPage.getSubHeader().getItemsCount();
            int countToAdd = toCount ? count - totalCount > 0 ? count - totalCount : 0 : count;
            IntStream.range(0, countToAdd).forEach(index -> testRunTypes.add(buildTestRun(passedCount, failedCount, knownIssueCount, blockerCount, skippedCount, abortedCount, inProgressCount)));
            return testRunTypes;
        });
    }

    @Override
    public CompletableFuture<List<TestRunType>> generateTestRuns(int count, boolean toCount) {
        int defaultStatusItemCount = 2;
        return generateTestRuns(count, defaultStatusItemCount, defaultStatusItemCount, defaultStatusItemCount, defaultStatusItemCount,
                defaultStatusItemCount, defaultStatusItemCount, defaultStatusItemCount, toCount);
    }

    private TestRunType buildTestRun(int passedCount, int failedCount, int knownIssueCount, int blockerCount, int skippedCount, int abortedCount, int inProgressCount) {
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
