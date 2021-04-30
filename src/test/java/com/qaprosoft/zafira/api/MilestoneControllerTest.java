package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.milestones.DeleteMilestoneByIdAndProjectIdMethod;
import com.qaprosoft.zafira.api.milestones.GetMilestonesByIdAndProjectIdMethod;
import com.qaprosoft.zafira.api.milestones.GetMilestonesByProjectIdMethod;
import com.qaprosoft.zafira.api.milestones.PostMilestoneMethod;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.MilestoneServiceImpl;
import com.zebrunner.agent.core.annotation.Maintainer;
import io.restassured.path.json.JsonPath;
import org.apache.commons.lang3.RandomStringUtils;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

@Maintainer("obabich")
public class MilestoneControllerTest extends ZafiraAPIBaseTest {
    private static MilestoneServiceImpl milestoneService = new MilestoneServiceImpl();

    private int projectId = 1;
    private int milestoneId;

    @AfterMethod
    private void deleteMilestone() {
        milestoneService.delete(projectId, milestoneId);
    }

    @Test
    public void testPostMilestone() {
        String milestoneName = "MilestoneName".concat(RandomStringUtils.randomAlphabetic(7));

        PostMilestoneMethod postMilestoneMethod =
                new PostMilestoneMethod(projectId, milestoneName);
        apiExecutor.expectStatus(postMilestoneMethod, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postMilestoneMethod);
        milestoneId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        apiExecutor.validateResponse(postMilestoneMethod, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        milestoneService.getAllMilestoneIdsInProject(projectId);
        Assert.assertTrue(milestoneService.getAllMilestoneIdsInProject(projectId)
                .contains(milestoneId), "Milestone was not find!");
    }

    @Test(groups = {"negative"})
    public void testPostMilestoneWithEmptyName() {
        String milestoneName = "MilestoneName".concat(RandomStringUtils.randomAlphabetic(7));

        PostMilestoneMethod postMilestoneMethod =
                new PostMilestoneMethod(projectId, milestoneName);
        postMilestoneMethod.addProperty(JSONConstant.NAME, "");
        apiExecutor.expectStatus(postMilestoneMethod, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postMilestoneMethod);
    }

    @Test(groups = {"negative"})
    public void testPostMilestoneWithoutName() {
        String milestoneName = "MilestoneName".concat(RandomStringUtils.randomAlphabetic(7));

        PostMilestoneMethod postMilestoneMethod =
                new PostMilestoneMethod(projectId, milestoneName);
        postMilestoneMethod.removeProperty(JSONConstant.NAME);
        apiExecutor.expectStatus(postMilestoneMethod, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postMilestoneMethod);
    }

    @Test(groups = {"negative"})
    public void testPostMilestoneWitNonExistentProjectId() {
        String milestoneName = "MilestoneName".concat(RandomStringUtils.randomAlphabetic(7));

        PostMilestoneMethod postMilestoneMethod =
                new PostMilestoneMethod(2, milestoneName);
        apiExecutor.expectStatus(postMilestoneMethod, HTTPStatusCodeType.FORBIDDEN);
        apiExecutor.callApiMethod(postMilestoneMethod);
    }

    @Test
    public void testListMilestones() {
        String milestoneName = "MilestoneName".concat(RandomStringUtils.randomAlphabetic(7));
        milestoneService.create(projectId,milestoneName);
        GetMilestonesByProjectIdMethod getMilestonesByProjectIdMethod =
                new GetMilestonesByProjectIdMethod(projectId);
        apiExecutor.expectStatus(getMilestonesByProjectIdMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getMilestonesByProjectIdMethod);
        apiExecutor.validateResponse(getMilestonesByProjectIdMethod, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey() + "items");
    }

    @Test
    public void testGetMilestonesByIdAndProjectIdMethod() {
        String milestoneName = "MilestoneName".concat(RandomStringUtils.randomAlphabetic(7));
        milestoneId = milestoneService.create(projectId, milestoneName);

        GetMilestonesByIdAndProjectIdMethod getMilestonesByIdAndProjectIdMethod =
                new GetMilestonesByIdAndProjectIdMethod(projectId, milestoneId);
        apiExecutor.expectStatus(getMilestonesByIdAndProjectIdMethod, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getMilestonesByIdAndProjectIdMethod);
        apiExecutor.validateResponse(getMilestonesByIdAndProjectIdMethod, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        String actualName = JsonPath.from(rs).getString(JSONConstant.NAME);
        Assert.assertEquals(actualName, milestoneName, "Name is not as expected!");
    }

    @Test
    public void testDeleteMilestoneByIdAndProjectId() {
        String milestoneName = "MilestoneName".concat(RandomStringUtils.randomAlphabetic(7));
        milestoneId = milestoneService.create(projectId, milestoneName);

        DeleteMilestoneByIdAndProjectIdMethod deleteMilestoneByIdAndProjectId =
                new DeleteMilestoneByIdAndProjectIdMethod(projectId, milestoneId);
        apiExecutor.expectStatus(deleteMilestoneByIdAndProjectId, HTTPStatusCodeType.NO_CONTENT);
        apiExecutor.callApiMethod(deleteMilestoneByIdAndProjectId);
        Assert.assertFalse(milestoneService.getAllMilestoneIdsInProject(projectId).contains(milestoneId));
    }
}
