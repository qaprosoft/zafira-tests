package com.qaprosoft.zafira.service.impl;

import com.jayway.restassured.path.json.JsonPath;
import com.qaprosoft.zafira.api.filter.DeleteFilterMethod;
import com.qaprosoft.zafira.api.filter.GetAllPublicFiltersMethods;
import com.qaprosoft.zafira.api.filter.PostFilterMethod;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.FilterService;

import java.util.List;

public class FilterServiceImpl implements FilterService {
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

    @Override
    public int createFilter(String filterName) {
        PostFilterMethod postFilterMethod = new PostFilterMethod(filterName);
        apiExecutor.expectStatus(postFilterMethod, HTTPStatusCodeType.OK);
        String response = apiExecutor.callApiMethod(postFilterMethod);
        return JsonPath.from(response).getInt(JSONConstant.ID_KEY);
    }

    @Override
    public List<Integer> getAllPublicFiltersIds() {
        GetAllPublicFiltersMethods getAllPublicFiltersMethods = new GetAllPublicFiltersMethods();
        apiExecutor.expectStatus(getAllPublicFiltersMethods, HTTPStatusCodeType.OK);
        String response = apiExecutor.callApiMethod(getAllPublicFiltersMethods);
        return JsonPath.from(response).getList(JSONConstant.ID_KEY);
    }

    @Override
    public void deleteFilterById(int filterId) {
        DeleteFilterMethod deleteFilterMethod = new DeleteFilterMethod(filterId);
        apiExecutor.expectStatus(deleteFilterMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(deleteFilterMethod);
    }
}
