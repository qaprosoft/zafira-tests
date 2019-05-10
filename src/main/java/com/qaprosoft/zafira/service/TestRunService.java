package com.qaprosoft.zafira.service;

import com.qaprosoft.zafira.domain.TestRunCollector;
import com.qaprosoft.zafira.gui.TestRunItemPage;
import com.qaprosoft.zafira.gui.component.modals.ReviewedModal;
import com.qaprosoft.zafira.gui.component.modals.SendAsEmailModal;
import com.qaprosoft.zafira.gui.component.table.row.TestRunTableRow;
import com.qaprosoft.zafira.service.builder.TestBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface TestRunService {

    void search(String query, boolean reviewed, String status, String environment, String platform, LocalDateTime date);

    void clearSearch();

    CompletableFuture<List<TestRunCollector>> generateTestRuns(int count, int passedCount, int failedCount, int knownIssueCount, int blockerCount,
                                                         int skippedCount, int abortedCount, int inProgressCount, boolean toCount, boolean parallel);

    CompletableFuture<List<TestRunCollector>> generateTestRuns(int count, boolean toCount, boolean parallel);

    Integer getStatisticItem(TestBuilder.BuildStatus status, TestRunTableRow row);

    TestRunItemPage clickTestRunRowByIndex(int index, long id);

    ReviewedModal clickMarkAsReviewedButton(TestRunTableRow row);

    TestRunItemPage open(int index, long id);

    String copyLink(int index, long id);

    void markAsReviewed(int index, String comment);

    SendAsEmailModal clickSendAsEmailButton(int index);

    void exportToHtml(int index);

    void delete(int index);

}
