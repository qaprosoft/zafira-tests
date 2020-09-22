package com.qaprosoft.zafira.api.mock;

import java.util.Properties;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.constant.ConfigConstant;

public class GetCreateJob extends AbstractApiMethodV2 {
    public GetCreateJob(String jobName) {
        super(null, "api/mock/jenkins/_get/rs_for_create.json", (Properties) null);
        replaceUrlPlaceholder("jenkins_mock_url", R.CONFIG.get(ConfigConstant.JENKINS_MOCK_HOST_KEY));
        replaceUrlPlaceholder("jobName", jobName);
    }
}
