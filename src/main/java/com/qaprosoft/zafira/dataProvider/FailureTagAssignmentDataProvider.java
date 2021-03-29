package com.qaprosoft.zafira.dataProvider;

import com.qaprosoft.zafira.enums.UserFeedback;
import org.testng.annotations.DataProvider;

public class FailureTagAssignmentDataProvider {
    @DataProvider(name = "user-feedback")
    public static Object[][] getUserFeedback() {
        return new Object[][]{
                {UserFeedback.DISLIKE.toString()},
                {UserFeedback.NONE.toString()},
                {UserFeedback.LIKE.toString()}};
    }
}
