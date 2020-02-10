package com.qaprosoft.zafira.api;

import com.jayway.restassured.path.json.JsonPath;
import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.filter.*;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.FilterServiceImpl;
import org.apache.commons.lang3.RandomStringUtils;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.util.List;

public class FilterTest extends ZafiraAPIBaseTest {

    @AfterTest
    public void deleteAllPublicFilters() {
        FilterServiceImpl filterService = new FilterServiceImpl();
        List<Integer> allFiltersIds = filterService.getAllPublicFiltersIds();
        allFiltersIds.forEach(filterService::deleteFilterById);
    }

    @Test
    public void testCreateFilter() {
        String filterName = "TestFilter_".concat(RandomStringUtils.randomAlphabetic(15));
        PostFilterMethod postFilterMethod = new PostFilterMethod(filterName);
        apiExecutor.expectStatus(postFilterMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postFilterMethod);
        apiExecutor.validateResponse(postFilterMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testGetAllPublicFilters() {
        String filterName = "TestFilter_".concat(RandomStringUtils.randomAlphabetic(15));
        new FilterServiceImpl().createFilter(filterName);
        GetAllPublicFiltersMethods getAllPublicFiltersMethods = new GetAllPublicFiltersMethods();
        apiExecutor.expectStatus(getAllPublicFiltersMethods, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getAllPublicFiltersMethods);
        apiExecutor.validateResponse(getAllPublicFiltersMethods, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testDeleteFilterById() {
        String filterName = "TestFilter_".concat(RandomStringUtils.randomAlphabetic(15));
        FilterServiceImpl filterService = new FilterServiceImpl();
        int filterId = filterService.createFilter(filterName);
        DeleteFilterMethod deleteFilterMethod = new DeleteFilterMethod(filterId);
        apiExecutor.expectStatus(deleteFilterMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(deleteFilterMethod);

        List<Integer> allFiltersIds = filterService.getAllPublicFiltersIds();
        Assert.assertFalse(allFiltersIds.contains(filterId), "Filter was not delete!");
    }

    @Test
    public void testGetFilterBuilder() {
        String filterName = "TestFilter_".concat(RandomStringUtils.randomAlphabetic(15));
        new FilterServiceImpl().createFilter(filterName);
        String subjectName = R.TESTDATA.get(ConfigConstant.SUBJECT_NAME_KEY);

        GetFilterBuilderMethod getFilterBuilderMethod = new GetFilterBuilderMethod(subjectName);
        apiExecutor.expectStatus(getFilterBuilderMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getFilterBuilderMethod);
        apiExecutor.validateResponse(getFilterBuilderMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testUpdateFilter() {
        String filterName = "TestFilter_".concat(RandomStringUtils.randomAlphabetic(15));
        int filterId = new FilterServiceImpl().createFilter(filterName);
        String expectedFilterName = R.TESTDATA.get(ConfigConstant.EXPECTED_FILTER_NAME_KEY);

        PutFilterMethod putFilterMethod = new PutFilterMethod(filterId, expectedFilterName);
        apiExecutor.expectStatus(putFilterMethod, HTTPStatusCodeType.OK);
        String response = apiExecutor.callApiMethod(putFilterMethod);
        apiExecutor.validateResponse(putFilterMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        String actualFilterName = JsonPath.from(response).get(JSONConstant.FILTER_NAME_KEY);
        Assert.assertEquals(expectedFilterName, actualFilterName, "Filter name was not update!");
    }
}
