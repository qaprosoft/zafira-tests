package com.qaprosoft.zafira.service.impl;

import com.jayway.restassured.path.json.JsonPath;
import com.qaprosoft.zafira.api.launcher.*;
import com.qaprosoft.zafira.api.testRunController.GetBuildNumberMethod;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.service.LauncherService;
import org.apache.log4j.Logger;

import java.util.List;

public class LauncherServiceImpl implements LauncherService {
    private static final Logger LOGGER = Logger.getLogger(LauncherServiceImpl.class);
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

    public ExecutionServiceImpl getExecutor() {
        return apiExecutor;
    }

    /***
     *
     * @return list of launcher ids
     */
    @Override
    public List<Integer> getAll() {
        String getAllLaunchersRs = apiExecutor.callApiMethod(new GetAllLaunchersMethod());

        return JsonPath.from(getAllLaunchersRs).getList(JSONConstant.ID_KEY);
    }

    @Override
    public int getById(int id) {
        String getLauncherByIdRs = apiExecutor.callApiMethod(new GetLauncherByIdMethod(id));

        return JsonPath.from(getLauncherByIdRs).getInt(JSONConstant.ID_KEY);
    }

    @Override
    public int create(int jobId, int accountTypeId) {
        String postLauncherRs = apiExecutor.callApiMethod(new PostLauncherMethod(jobId, accountTypeId));
        return JsonPath.from(postLauncherRs).getInt(JSONConstant.ID_KEY);
    }

    @Override
    public int update(int id, String valueToUpdate) {
        String putLauncherRs = apiExecutor.callApiMethod(new PutLauncherMethod(id, valueToUpdate));
        return JsonPath.from(putLauncherRs).getInt(JSONConstant.ID_KEY);
    }

    @Override
    public void deleteById(int id) {
        new DeleteLauncherMethod(id).callAPI();
    }

    @Override
    public String getQueueItemUrl(int scmAccountId) {
        String putLauncherRs = apiExecutor.callApiMethod(new PostScanLaucherMethod(scmAccountId));
        return JsonPath.from(putLauncherRs).getString(JSONConstant.QUEUE_ITEM_URL_KEY);
    }

    @Override
    public String getBuildNumber(String queueItemUrl) {
        String buildNumber = apiExecutor
                .callApiMethod(new GetBuildNumberMethod(queueItemUrl));
        LOGGER.info(String.format("Build number = %s", buildNumber));
        return buildNumber;
    }
}
