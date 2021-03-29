package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.testRunFilterController.DeleteTestRunFilterByIdMethod;
import com.qaprosoft.zafira.api.testRunFilterController.GetTestRunFiltersMethod;
import com.qaprosoft.zafira.api.testRunFilterController.PatchTestRunFilterMethod;
import com.qaprosoft.zafira.api.testRunFilterController.PostTestRunFilterMethod;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.TestRunFilterV1ServiceImpl;
import io.restassured.path.json.JsonPath;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

public class TestRunFilterControllerTest extends ZafiraAPIBaseTest {
    private static final Logger LOGGER = Logger.getLogger(TestRunFilterControllerTest.class);
    private static int id;
    private static final String EMPTY_NAME = "";

    @AfterMethod
    public void deleteTestRunFilter() {
        new TestRunFilterV1ServiceImpl().deleteFilterById(id);
        LOGGER.info("Filter with id = " + id + " is deleted!");
    }

    @Test
    public void testCreateTestRunFilter() {
        String filterName = "TestFilter_".concat(RandomStringUtils.randomAlphabetic(7));
        PostTestRunFilterMethod postTestRunFilterMethod = new PostTestRunFilterMethod(filterName);
        apiExecutor.expectStatus(postTestRunFilterMethod, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postTestRunFilterMethod);
        apiExecutor.validateResponse(postTestRunFilterMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        id = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        List<Integer> allFiltersIds = new TestRunFilterV1ServiceImpl().getAllFiltersIds();
        Assert.assertTrue(allFiltersIds.contains(id), "Filter was not created!");
    }

    @Test()
    public void testCreateTestRunFilterWithEmptyName() {
        PostTestRunFilterMethod postTestRunFilterMethod = new PostTestRunFilterMethod(EMPTY_NAME);
        apiExecutor.expectStatus(postTestRunFilterMethod, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postTestRunFilterMethod);
    }

    @Test
    public void testCreateTestRunFilterWithTheSameParametersAndName() {
        String filterName = "TestFilter_".concat(RandomStringUtils.randomAlphabetic(7));
        id = new TestRunFilterV1ServiceImpl().createFilter(filterName);
        PostTestRunFilterMethod postTestRunFilterMethod = new PostTestRunFilterMethod(filterName);
        apiExecutor.expectStatus(postTestRunFilterMethod, HTTPStatusCodeType.FORBIDDEN);
        apiExecutor.callApiMethod(postTestRunFilterMethod);
    }

    @Test
    public void testGetTestRunFilters() {
        String filterName = "TestFilter_".concat(RandomStringUtils.randomAlphabetic(15));
        id = new TestRunFilterV1ServiceImpl().createFilter(filterName);
        GetTestRunFiltersMethod testRunFiltersMethod = new GetTestRunFiltersMethod();
        apiExecutor.expectStatus(testRunFiltersMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(testRunFiltersMethod);
        apiExecutor.validateResponse(testRunFiltersMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testDeleteFilterById() {
        String filterName = "TestFilter_".concat(RandomStringUtils.randomAlphabetic(7));
        TestRunFilterV1ServiceImpl filterService = new TestRunFilterV1ServiceImpl();
        id = filterService.createFilter(filterName);
        DeleteTestRunFilterByIdMethod deleteFilterMethod = new DeleteTestRunFilterByIdMethod(id);
        apiExecutor.expectStatus(deleteFilterMethod, HTTPStatusCodeType.NO_CONTENT);
        apiExecutor.callApiMethod(deleteFilterMethod);
        List<Integer> allFiltersIds = filterService.getAllFiltersIds();
        Assert.assertFalse(allFiltersIds.contains(id), "Filter was not delete!");
    }

    @DataProvider(name = "data-provider-update-filter")
    public Object[][] dataProviderMethod() {
        return new Object[][]{{"/isFavorite", false}, {"/isPrivate", true}, {"/items", R.TESTDATA.get(ConfigConstant.VALUE_FOR_UPDATE_FILTER)}};
    }

    @Test(dataProvider = "data-provider-update-filter")
    public void testUpdateFilter(String path, Object value) {
        String filterName = "TestFilter_".concat(RandomStringUtils.randomAlphabetic(15));
        id = new TestRunFilterV1ServiceImpl().createFilter(filterName);
        PatchTestRunFilterMethod patchTestRunFilterMethod = new PatchTestRunFilterMethod(id, path, value);
        apiExecutor.expectStatus(patchTestRunFilterMethod, HTTPStatusCodeType.NO_CONTENT);
        apiExecutor.callApiMethod(patchTestRunFilterMethod);
    }
}
