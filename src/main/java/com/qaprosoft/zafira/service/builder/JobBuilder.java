package com.qaprosoft.zafira.service.builder;

import com.qaprosoft.zafira.domain.Config;
import com.qaprosoft.zafira.exception.BuilderException;
import com.qaprosoft.zafira.models.dto.JobType;

public class JobBuilder extends BaseBuilder {

    static JobType buildJob() {
        return callItem(client -> client.createJob(generateJob()))
                .orElseThrow(() -> new BuilderException("Job was not created"));
    }

    private static JobType generateJob() {
        String name = generateRandomString();
        String jobUrl = "http://" + generateRandomString().toLowerCase();
        String jenkinsHost = generateRandomString();
        long adminId = Config.ADMIN_ID.getLongValue();
        return new JobType(name, jobUrl, jenkinsHost, adminId);
    }

}
