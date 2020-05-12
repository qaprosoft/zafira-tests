package com.qaprosoft.zafira.api.mock;

import java.util.Properties;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.constant.ConfigConstant;

public class GetAllJobs extends AbstractApiMethodV2 {
    public GetAllJobs() {
        super(null, "api/mock/jenkins/_get/rs.json", (Properties) null);
        replaceUrlPlaceholder("jenkins_mock_url", R.CONFIG.get(ConfigConstant.JENKINS_MOCK_HOST_KEY));
    }
}
