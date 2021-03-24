package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.failureTag.DeleteFailureTagMethod;
import com.qaprosoft.zafira.api.failureTag.GetFailureTagsMethod;
import com.qaprosoft.zafira.api.failureTag.PostFailureTagMethod;
import com.qaprosoft.zafira.api.failureTag.PutFailureTagMethod;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.FailureTagServiceImpl;
import com.zebrunner.agent.core.registrar.CurrentTestRun;
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
    public void testGetFailureTag() {
        GetFailureTagsMethod getFailureTagsMethod = new GetFailureTagsMethod();
        apiExecutor.expectStatus(getFailureTagsMethod, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getFailureTagsMethod);
        apiExecutor.validateResponse(getFailureTagsMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        LOGGER.info(JsonPath.from(rs).get(JSONConstant.ITEMS).toString());
        Assert.assertNotNull(JsonPath.from(rs).get(JSONConstant.ITEMS_0));
    }

    @Test()
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
    public void testPostFailureTagWithoutNameInBodeRq() {
        String name = "Tag_".concat(RandomStringUtils.randomAlphabetic(5));
        PostFailureTagMethod postFailureTagMethod = new PostFailureTagMethod(name);
        postFailureTagMethod.removeProperty(JSONConstant.NAME);
        apiExecutor.expectStatus(postFailureTagMethod, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postFailureTagMethod);
    }

    @Test()
    public void testDeleteFailureTag() {
        String name = "Tag_".concat(RandomStringUtils.randomAlphabetic(5));
        tagId = failureTagService.createFailureTag(name);
        DeleteFailureTagMethod deleteFailureTagMethod = new DeleteFailureTagMethod(tagId);
        apiExecutor.expectStatus(deleteFailureTagMethod, HTTPStatusCodeType.NO_CONTENT);
        apiExecutor.callApiMethod(deleteFailureTagMethod);
        CurrentTestRun.setBuild("hhh");
        List<Integer> tagIds = failureTagService.getAllFailureTagIds();
        Assert.assertFalse(tagIds.contains(tagId));
    }

    @Test()
    public void testDeleteNonExistingFailureTag() {
        String name = "Tag_".concat(RandomStringUtils.randomAlphabetic(5));
        tagId = failureTagService.createFailureTag(name);
        failureTagService.deleteFailureTag(tagId);
        DeleteFailureTagMethod deleteFailureTagMethod = new DeleteFailureTagMethod(tagId);
        apiExecutor.expectStatus(deleteFailureTagMethod, HTTPStatusCodeType.NOT_FOUND);
        apiExecutor.callApiMethod(deleteFailureTagMethod);
    }

    @Test()
    public void testUpdateFailureTag() {
        String name = "Tag_".concat(RandomStringUtils.randomAlphabetic(5));
        tagId = failureTagService.createFailureTag(name);
        PutFailureTagMethod putFailureTagMethod = new PutFailureTagMethod(tagId);
        putFailureTagMethod.addProperty(JSONConstant.NAME,"New_"+name);
        putFailureTagMethod.addProperty(JSONConstant.FALLBACK, true);
        apiExecutor.expectStatus(putFailureTagMethod, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(putFailureTagMethod);
        tagId = JsonPath.from(rs).get(JSONConstant.ID_KEY);
        apiExecutor.validateResponse(putFailureTagMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    //    failureTagService.updateFallbackFailureTag(tagId,false,name);
    }
}
