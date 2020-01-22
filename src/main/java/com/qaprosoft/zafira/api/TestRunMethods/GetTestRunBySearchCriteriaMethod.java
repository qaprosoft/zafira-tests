package com.qaprosoft.zafira.api.TestRunMethods;

import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.ZafiraBaseApiMethodWithAuth;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.manager.APIContextManager;

import java.util.Properties;

public class GetTestRunBySearchCriteriaMethod extends ZafiraBaseApiMethodWithAuth {
    public GetTestRunBySearchCriteriaMethod(String searchCriteriaType, int testSuiteId) {
        super(null, "api/test_run/_get/rs_for_get_by_search_criteria.json", (Properties) null);
        replaceUrlPlaceholder("pageSize", R.TESTDATA.get(ConfigConstant.PAGE_SIZE_KEY));
        replaceUrlPlaceholder("base_api_url", APIContextManager.API_URL);
        replaceUrlPlaceholder("searchCriteriaType", searchCriteriaType);
        replaceUrlPlaceholder("searchCriteriaId", String.valueOf(testSuiteId));
    }
}
