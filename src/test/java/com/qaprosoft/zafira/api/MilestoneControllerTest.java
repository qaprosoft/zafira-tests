package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.milestones.*;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.MilestoneServiceImpl;
import com.zebrunner.agent.core.annotation.Maintainer;
import io.restassured.path.json.JsonPath;
import org.apache.commons.lang3.RandomStringUtils;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Maintainer("obabich")
public class MilestoneControllerTest extends ZafiraAPIBaseTest {
    private static MilestoneServiceImpl milestoneService = new MilestoneServiceImpl();
    private final int nonexistentProjectId = -2;

    private int projectId = 1;
    private int milestoneId;

    @DataProvider(name = "isCompleted")
    public static Object[][] getAcceptableRoles() {
        return new Object[][]{{true}, {false}};
    }

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
        Assert.assertTrue(milestoneService.getAllMilestoneIdsInProject(projectId)
                .contains(milestoneId), "Milestone was not find!");
    }

    @Test(groups = {"negative"})
    public void testPostMilestoneWithoutQueryParams() {
        String milestoneName = "MilestoneName".concat(RandomStringUtils.randomAlphabetic(7));
        milestoneId = milestoneService.create(projectId, milestoneName);

        PostMilestoneMethod postMilestoneMethod =
                new PostMilestoneMethod(projectId, milestoneName);
        postMilestoneMethod
                .setMethodPath(
                        postMilestoneMethod.getMethodPath()
                                .split("\\?")[0]);
        apiExecutor.expectStatus(postMilestoneMethod, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postMilestoneMethod);
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
    public void testPostMilestoneWithExistingName() {
        String milestoneName = "MilestoneName".concat(RandomStringUtils.randomAlphabetic(7));
        milestoneId = milestoneService.create(projectId, milestoneName);
        PostMilestoneMethod postMilestoneMethod =
                new PostMilestoneMethod(projectId, milestoneName);
        apiExecutor.expectStatus(postMilestoneMethod, HTTPStatusCodeType.FORBIDDEN);
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
    public void testPostMilestoneWithEmptyRq() {
        String milestoneName = "MilestoneName".concat(RandomStringUtils.randomAlphabetic(7));

        PostMilestoneMethod postMilestoneMethod =
                new PostMilestoneMethod(projectId, milestoneName);
        postMilestoneMethod.setRequestTemplate(R.TESTDATA.get(ConfigConstant.EMPTY_RQ_PATH));
        apiExecutor.expectStatus(postMilestoneMethod, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postMilestoneMethod);
    }

    @Test(groups = {"negative"})
    public void testPostMilestoneWithNonExistentProjectId() {
        String milestoneName = "MilestoneName".concat(RandomStringUtils.randomAlphabetic(7));

        PostMilestoneMethod postMilestoneMethod =
                new PostMilestoneMethod(nonexistentProjectId, milestoneName);
        apiExecutor.expectStatus(postMilestoneMethod, HTTPStatusCodeType.FORBIDDEN);
        apiExecutor.callApiMethod(postMilestoneMethod);
    }

    @Test
    public void testListMilestones() {
        String milestoneName = "MilestoneName".concat(RandomStringUtils.randomAlphabetic(7));
        milestoneService.create(projectId, milestoneName);
        GetMilestonesByProjectIdMethod getMilestonesByProjectIdMethod =
                new GetMilestonesByProjectIdMethod(projectId);
        apiExecutor.expectStatus(getMilestonesByProjectIdMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getMilestonesByProjectIdMethod);
        apiExecutor.validateResponse(getMilestonesByProjectIdMethod, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey() + "items");
    }

    @Test(groups = {"negative"})
    public void testListMilestonesWithNonExistentProjectId() {
        String milestoneName = "MilestoneName".concat(RandomStringUtils.randomAlphabetic(7));
        milestoneService.create(projectId, milestoneName);
        GetMilestonesByProjectIdMethod getMilestonesByProjectIdMethod =
                new GetMilestonesByProjectIdMethod(nonexistentProjectId);
        apiExecutor.expectStatus(getMilestonesByProjectIdMethod, HTTPStatusCodeType.FORBIDDEN);
        apiExecutor.callApiMethod(getMilestonesByProjectIdMethod);
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

    @Test(groups = {"negative"})
    public void testGetMilestonesByIdAndProjectIdMethodWithNonExistentProjectId() {
        String milestoneName = "MilestoneName".concat(RandomStringUtils.randomAlphabetic(7));
        milestoneId = milestoneService.create(projectId, milestoneName);

        GetMilestonesByIdAndProjectIdMethod getMilestonesByIdAndProjectIdMethod =
                new GetMilestonesByIdAndProjectIdMethod(nonexistentProjectId, milestoneId);
        apiExecutor.expectStatus(getMilestonesByIdAndProjectIdMethod, HTTPStatusCodeType.FORBIDDEN);
        apiExecutor.callApiMethod(getMilestonesByIdAndProjectIdMethod);
    }

    @Test(groups = {"negative"})
    public void testGetMilestonesByIdAndProjectIdMethodWithNonExistentMilestoneId() {
        String milestoneName = "MilestoneName".concat(RandomStringUtils.randomAlphabetic(7));
        milestoneId = milestoneService.create(projectId, milestoneName);
        milestoneService.delete(projectId, milestoneId);

        GetMilestonesByIdAndProjectIdMethod getMilestonesByIdAndProjectIdMethod =
                new GetMilestonesByIdAndProjectIdMethod(projectId, milestoneId);
        apiExecutor.expectStatus(getMilestonesByIdAndProjectIdMethod, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(getMilestonesByIdAndProjectIdMethod);
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

    @Test(groups = {"negative"})
    public void testDeleteMilestoneByIdAndProjectIdWithNonExistentProjectId() {
        String milestoneName = "MilestoneName".concat(RandomStringUtils.randomAlphabetic(7));
        milestoneId = milestoneService.create(projectId, milestoneName);

        DeleteMilestoneByIdAndProjectIdMethod deleteMilestoneByIdAndProjectId =
                new DeleteMilestoneByIdAndProjectIdMethod(nonexistentProjectId, milestoneId);
        apiExecutor.expectStatus(deleteMilestoneByIdAndProjectId, HTTPStatusCodeType.FORBIDDEN);
        apiExecutor.callApiMethod(deleteMilestoneByIdAndProjectId);
    }

    @Test(groups = {"negative"})
    public void testDeleteMilestoneByIdAndProjectIdWithNonExistentMilestoneId() {
        String milestoneName = "MilestoneName".concat(RandomStringUtils.randomAlphabetic(7));
        milestoneId = milestoneService.create(projectId, milestoneName);
        milestoneService.delete(projectId, milestoneId);

        DeleteMilestoneByIdAndProjectIdMethod deleteMilestoneByIdAndProjectId =
                new DeleteMilestoneByIdAndProjectIdMethod(projectId, milestoneId);
        apiExecutor.expectStatus(deleteMilestoneByIdAndProjectId, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(deleteMilestoneByIdAndProjectId);
    }

    @Test
    public void testUpdateMilestone() {
        String milestoneName = "MilestoneName".concat(RandomStringUtils.randomAlphabetic(7));
        milestoneId = milestoneService.create(projectId, milestoneName);

        PutMilestoneMethod putMilestoneMethod =
                new PutMilestoneMethod(projectId, milestoneId, "New_".concat(milestoneName));
        apiExecutor.expectStatus(putMilestoneMethod, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(putMilestoneMethod);
        milestoneId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        apiExecutor.validateResponse(putMilestoneMethod, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        Assert.assertEquals(milestoneService.getMilestoneNameById(projectId, milestoneId),
                "New_".concat(milestoneName), "Milestone was not updated!");
    }

    @Test(groups = {"negative"})
    public void testUpdateMilestoneWithEmptyName() {
        String milestoneName = "MilestoneName".concat(RandomStringUtils.randomAlphabetic(7));
        milestoneId = milestoneService.create(projectId, milestoneName);

        PutMilestoneMethod putMilestoneMethod =
                new PutMilestoneMethod(projectId, milestoneId, "");
        apiExecutor.expectStatus(putMilestoneMethod, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(putMilestoneMethod);
    }

    @Test(groups = {"negative"})
    public void testUpdateMilestoneNameOnExistingName() {
        String milestoneName = "MilestoneName".concat(RandomStringUtils.randomAlphabetic(7));
        milestoneId = milestoneService.create(projectId, milestoneName);
        int milestoneId1 = milestoneService.create(projectId, milestoneName + 1);

        PutMilestoneMethod putMilestoneMethod =
                new PutMilestoneMethod(projectId, milestoneId1, milestoneName);
        apiExecutor.expectStatus(putMilestoneMethod, HTTPStatusCodeType.FORBIDDEN);
        apiExecutor.callApiMethod(putMilestoneMethod);
        milestoneService.delete(projectId, milestoneId1);
    }

    @Test(groups = {"negative"})
    public void testUpdateMilestoneWithoutName() {
        String milestoneName = "MilestoneName".concat(RandomStringUtils.randomAlphabetic(7));
        milestoneId = milestoneService.create(projectId, milestoneName);

        PutMilestoneMethod putMilestoneMethod =
                new PutMilestoneMethod(projectId, milestoneId, "New");
        putMilestoneMethod.removeProperty(JSONConstant.NAME);
        apiExecutor.expectStatus(putMilestoneMethod, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(putMilestoneMethod);
    }

    @Test(groups = {"negative"})
    public void testUpdateMilestoneWithoutEmptyRq() {
        String milestoneName = "MilestoneName".concat(RandomStringUtils.randomAlphabetic(7));
        milestoneId = milestoneService.create(projectId, milestoneName);

        PutMilestoneMethod putMilestoneMethod =
                new PutMilestoneMethod(projectId, milestoneId, "New");
        putMilestoneMethod.setRequestTemplate(R.TESTDATA.get(ConfigConstant.EMPTY_RQ_PATH));
        apiExecutor.expectStatus(putMilestoneMethod, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(putMilestoneMethod);
    }

    @Test(groups = {"negative"})
    public void testUpdateMilestoneWithoutQueryParams() {
        String milestoneName = "MilestoneName".concat(RandomStringUtils.randomAlphabetic(7));
        milestoneId = milestoneService.create(projectId, milestoneName);

        PutMilestoneMethod putMilestoneMethod =
                new PutMilestoneMethod(projectId, milestoneId, milestoneName);
        putMilestoneMethod
                .setMethodPath(
                        putMilestoneMethod.getMethodPath()
                                .split("\\?")[0]);
        apiExecutor.expectStatus(putMilestoneMethod, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(putMilestoneMethod);
    }

    @Test(dataProvider = "isCompleted")
    public void testPatchCompletedMilestone(Boolean isCompleted) {
        String milestoneName = "MilestoneName".concat(RandomStringUtils.randomAlphabetic(7));
        milestoneId = milestoneService.create(projectId, milestoneName);

        PatchMilestoneMethod patchMilestoneMethod =
                new PatchMilestoneMethod(projectId, milestoneId, isCompleted);
        apiExecutor.expectStatus(patchMilestoneMethod, HTTPStatusCodeType.NO_CONTENT);
        apiExecutor.callApiMethod(patchMilestoneMethod);

        Boolean actualValue = JsonPath.from(milestoneService.getMilestoneById(projectId, milestoneId)).getBoolean(JSONConstant.COMPLETED);
        Assert.assertEquals(actualValue, isCompleted, "Milestone was not completed!");
    }

    @Test(groups = {"negative"})
    public void testPatchCompletedMilestoneWithNonExistentMilestoneId() {
        String milestoneName = "MilestoneName".concat(RandomStringUtils.randomAlphabetic(7));
        milestoneId = milestoneService.create(projectId, milestoneName);
        milestoneService.delete(projectId, milestoneId);

        PatchMilestoneMethod patchMilestoneMethod =
                new PatchMilestoneMethod(projectId, milestoneId, false);
        apiExecutor.expectStatus(patchMilestoneMethod, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(patchMilestoneMethod);
    }

    @Test(groups = {"negative"})
    public void testPatchCompletedMilestoneWithNonExistentProjectId() {
        String milestoneName = "MilestoneName".concat(RandomStringUtils.randomAlphabetic(7));
        milestoneId = milestoneService.create(projectId, milestoneName);

        PatchMilestoneMethod patchMilestoneMethod =
                new PatchMilestoneMethod(nonexistentProjectId, milestoneId, false);
        apiExecutor.expectStatus(patchMilestoneMethod, HTTPStatusCodeType.FORBIDDEN);
        apiExecutor.callApiMethod(patchMilestoneMethod);
    }
}
