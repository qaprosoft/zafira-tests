package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.milestones.PostMilestoneMethod;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import org.apache.commons.lang3.RandomStringUtils;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.annotations.Test;

public class MilestoneControllerTest extends ZafiraAPIBaseTest {

    private int projectId = 1;

    @Test
    public void testPostMilestone() {
        String milestoneName = "MilestoneName".concat(RandomStringUtils.randomAlphabetic(7));

        PostMilestoneMethod postMilestoneMethod =
                new PostMilestoneMethod(1, milestoneName);
        apiExecutor.expectStatus(postMilestoneMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postMilestoneMethod);
        apiExecutor.validateResponse(postMilestoneMethod, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testPostMilestoneWithEmptyName() {
        String milestoneName = "MilestoneName".concat(RandomStringUtils.randomAlphabetic(7));

        PostMilestoneMethod postMilestoneMethod =
                new PostMilestoneMethod(projectId, milestoneName);
        postMilestoneMethod.addProperty(JSONConstant.NAME, "");
        apiExecutor.expectStatus(postMilestoneMethod, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postMilestoneMethod);
    }

    @Test
    public void testPostMilestoneWithoutName() {
        String milestoneName = "MilestoneName".concat(RandomStringUtils.randomAlphabetic(7));

        PostMilestoneMethod postMilestoneMethod =
                new PostMilestoneMethod(projectId, milestoneName);
        postMilestoneMethod.removeProperty(JSONConstant.NAME);
        apiExecutor.expectStatus(postMilestoneMethod, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postMilestoneMethod);
    }

    @Test
    public void testPostMilestoneWitNonExistentProjectId() {
        String milestoneName = "MilestoneName".concat(RandomStringUtils.randomAlphabetic(7));

        PostMilestoneMethod postMilestoneMethod =
                new PostMilestoneMethod(projectId, milestoneName);
        apiExecutor.expectStatus(postMilestoneMethod, HTTPStatusCodeType.FORBIDDEN);
        apiExecutor.callApiMethod(postMilestoneMethod);
    }
}
