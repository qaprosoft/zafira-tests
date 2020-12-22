package com.qaprosoft.zafira.service.impl;

import com.qaprosoft.zafira.api.filter.DeleteFilterMethod;
import com.qaprosoft.zafira.api.filter.GetAllPublicFiltersMethods;
import com.qaprosoft.zafira.api.filter.PostFilterMethod;
import com.qaprosoft.zafira.api.testRunFilterController.DeleteTestRunFilterByIdMethod;
import com.qaprosoft.zafira.api.testRunFilterController.GetTestRunFiltersMethod;
import com.qaprosoft.zafira.api.testRunFilterController.PostTestRunFilterMethod;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.FilterService;
import com.qaprosoft.zafira.service.TestRunFilterV1Service;
import io.restassured.path.json.JsonPath;
import org.apache.log4j.Logger;

import java.util.List;

public class TestRunFilterV1ServiceImpl implements TestRunFilterV1Service {
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();
    private static final Logger LOGGER = Logger.getLogger(TestRunFilterV1ServiceImpl.class);

    @Override
    public int createFilter(String filterName) {
        PostTestRunFilterMethod postTestRunFilterMethod = new PostTestRunFilterMethod(filterName);
        apiExecutor.expectStatus(postTestRunFilterMethod, HTTPStatusCodeType.OK);
        String response = apiExecutor.callApiMethod(postTestRunFilterMethod);
        LOGGER.info("Filter is created!");
        return JsonPath.from(response).getInt(JSONConstant.ID_KEY);
    }

    @Override
    public List<Integer> getAllFiltersIds() {
        GetTestRunFiltersMethod testRunFiltersMethod = new GetTestRunFiltersMethod();
        apiExecutor.expectStatus(testRunFiltersMethod, HTTPStatusCodeType.OK);
        String response = apiExecutor.callApiMethod(testRunFiltersMethod);
        List<Integer> allFiltersIds = JsonPath.from(response).getList(JSONConstant.ID_KEY);
        LOGGER.info("AllFiltersIds:  " + allFiltersIds);
        return allFiltersIds;
    }

    @Override
    public void deleteFilterById(int id) {
        DeleteTestRunFilterByIdMethod deleteTestRunFilterByIdMethod = new DeleteTestRunFilterByIdMethod(id);
        apiExecutor.callApiMethod(deleteTestRunFilterByIdMethod);
        LOGGER.info("Filter with id = " + id + " is deleted!");
    }
}
