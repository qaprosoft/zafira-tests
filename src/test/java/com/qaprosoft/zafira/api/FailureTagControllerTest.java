package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.failureTag.*;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.constant.TestRailConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.FailureTagServiceImpl;
import com.zebrunner.agent.core.annotation.TestLabel;
import io.restassured.path.json.JsonPath;
import org.apache.commons.lang3.RandomStringUtils;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.lang.invoke.MethodHandles;
import java.util.List;

public class FailureTagControllerTest extends ZafiraAPIBaseTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private int tagId;
    private FailureTagServiceImpl failureTagService = new FailureTagServiceImpl();

    @AfterMethod
    public void deleteFailureTag() {
        failureTagService.deleteFailureTag(tagId);
    }

    @Test()
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40123")
    public void testGetFailureTag() {
        GetFailureTagsMethod getFailureTagsMethod = new GetFailureTagsMethod();
        apiExecutor.expectStatus(getFailureTagsMethod, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getFailureTagsMethod);
        apiExecutor.validateResponse(getFailureTagsMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        LOGGER.info(JsonPath.from(rs).get(JSONConstant.ITEMS).toString());
        Assert.assertNotNull(JsonPath.from(rs).get(JSONConstant.ITEMS_0));
    }

    @Test()
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40124")
    public void testPostFailureTag() {
        String name = "Tag_".concat(RandomStringUtils.randomAlphabetic(5));
        PostFailureTagMethod postFailureTagMethod = new PostFailureTagMethod(name);
        apiExecutor.expectStatus(postFailureTagMethod, HTTPStatusCodeType.CREATED);
        String rs = apiExecutor.callApiMethod(postFailureTagMethod);
        tagId = JsonPath.from(rs).get(JSONConstant.ID_KEY);
        apiExecutor.validateResponse(postFailureTagMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        List<Integer> tagIds = failureTagService.getAllFailureTagIds();
        Assert.assertTrue(tagIds.contains(tagId));
    }

    @Test(description = "negative")
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40125")
    public void testPostFailureTagWithoutNameInBodeRq() {
        String name = "Tag_".concat(RandomStringUtils.randomAlphabetic(5));
        PostFailureTagMethod postFailureTagMethod = new PostFailureTagMethod(name);
        postFailureTagMethod.removeProperty(JSONConstant.NAME);
        apiExecutor.expectStatus(postFailureTagMethod, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postFailureTagMethod);
    }

    @Test()
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40137")
    public void testPostFailureTagWithoutFallbackInBodeRq() {
        String name = "Tag_".concat(RandomStringUtils.randomAlphabetic(5));
        PostFailureTagMethod postFailureTagMethod = new PostFailureTagMethod(name);
        postFailureTagMethod.removeProperty(JSONConstant.FALLBACK);
        apiExecutor.expectStatus(postFailureTagMethod, HTTPStatusCodeType.CREATED);
        String rs = apiExecutor.callApiMethod(postFailureTagMethod);
        tagId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        Boolean fallback = JsonPath.from(rs).getBoolean(JSONConstant.FALLBACK);
        Assert.assertFalse(fallback, "Fallback is not as expected!");
        List<Integer> tagIds = failureTagService.getAllFailureTagIds();
        Assert.assertTrue(tagIds.contains(tagId));
    }

    @Test()
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40138")
    public void testDeleteFailureTag() {
        String name = "Tag_".concat(RandomStringUtils.randomAlphabetic(5));
        tagId = failureTagService.createFailureTag(name);
        DeleteFailureTagMethod deleteFailureTagMethod = new DeleteFailureTagMethod(tagId);
        apiExecutor.expectStatus(deleteFailureTagMethod, HTTPStatusCodeType.NO_CONTENT);
        apiExecutor.callApiMethod(deleteFailureTagMethod);
        List<Integer> tagIds = failureTagService.getAllFailureTagIds();
        Assert.assertFalse(tagIds.contains(tagId));
    }

    @Test()
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40139")
    public void testDeleteNonExistingFailureTag() {
        String name = "Tag_".concat(RandomStringUtils.randomAlphabetic(5));
        tagId = failureTagService.createFailureTag(name);
        failureTagService.deleteFailureTag(tagId);
        DeleteFailureTagMethod deleteFailureTagMethod = new DeleteFailureTagMethod(tagId);
        apiExecutor.expectStatus(deleteFailureTagMethod, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(deleteFailureTagMethod);
    }

    @Test()
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40140")
    public void testUpdateFailureTag() {
        String name = "Tag_".concat(RandomStringUtils.randomAlphabetic(5));
        tagId = failureTagService.createFailureTag(name);
        PutFailureTagMethod putFailureTagMethod = new PutFailureTagMethod(tagId);
        putFailureTagMethod.addProperty(JSONConstant.NAME, "New_" + name);
        putFailureTagMethod.addProperty(JSONConstant.FALLBACK, String.valueOf(true));
        putFailureTagMethod.addProperty(JSONConstant.DESCRIPTION, "New_description" + name);
        apiExecutor.expectStatus(putFailureTagMethod, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(putFailureTagMethod);
        tagId = JsonPath.from(rs).get(JSONConstant.ID_KEY);
        apiExecutor.validateResponse(putFailureTagMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        failureTagService.setFallbackTrueToAnotherFailureTag();
    }

    @Test()
  //  @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40140")
    public void testUpdateFailureTagToFallbackFalse() {
        String name = "Tag_".concat(RandomStringUtils.randomAlphabetic(5));
        tagId = failureTagService.createFailureTag(name);
        failureTagService.updateFallbackFailureTag(tagId, true, name + "_new");

        PutFailureTagMethod putFailureTagMethod = new PutFailureTagMethod(tagId);
        putFailureTagMethod.addProperty(JSONConstant.NAME, "New_" + name);
        putFailureTagMethod.addProperty(JSONConstant.FALLBACK, String.valueOf(false));
        putFailureTagMethod.addProperty(JSONConstant.DESCRIPTION, "New_description" + name);
        apiExecutor.expectStatus(putFailureTagMethod, HTTPStatusCodeType.FORBIDDEN);
        String rs = apiExecutor.callApiMethod(putFailureTagMethod);

        failureTagService.setFallbackTrueToAnotherFailureTag();
    }

    @Test()
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40141")
    public void testPatchFailureTag() {
        String name = "Tag_".concat(RandomStringUtils.randomAlphabetic(5));
        tagId = failureTagService.createFailureTag(name);
        PatchFailureTagMethod patchFailureTagAssignmentMethod = new PatchFailureTagMethod(true, tagId);
        apiExecutor.expectStatus(patchFailureTagAssignmentMethod, HTTPStatusCodeType.ACCEPTED);
        apiExecutor.callApiMethod(patchFailureTagAssignmentMethod);
        failureTagService.setFallbackTrueToAnotherFailureTag();
    }

    @Test(enabled = false)
    public void testPatchFailureTagFallBackFalse() {
        String name = "Tag_".concat(RandomStringUtils.randomAlphabetic(5));
        tagId = failureTagService.createFailureTag(name);
        failureTagService.updateFallbackFailureTag(tagId, true, name);
        PatchFailureTagMethod patchFailureTagAssignmentMethod = new PatchFailureTagMethod(false, tagId);
        apiExecutor.expectStatus(patchFailureTagAssignmentMethod, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(patchFailureTagAssignmentMethod);
        failureTagService.setFallbackTrueToAnotherFailureTag();
    }
}
