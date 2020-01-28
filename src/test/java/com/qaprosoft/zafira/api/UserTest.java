package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.UserMethods.PostSearchUserByCriteriaMethod;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import org.apache.log4j.Logger;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.annotations.Test;

public class UserTest extends ZariraAPIBaseTest {
    private final static Logger LOGGER = Logger.getLogger(UserTest.class);

    @Test
    public void testSeacrhUserByCriteria() {
        String query = "";
        PostSearchUserByCriteriaMethod postSearchUserByCriteriaMethod = new PostSearchUserByCriteriaMethod(query);
        apiExecutor.expectStatus(postSearchUserByCriteriaMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postSearchUserByCriteriaMethod);
        apiExecutor.validateResponse(postSearchUserByCriteriaMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }
}
