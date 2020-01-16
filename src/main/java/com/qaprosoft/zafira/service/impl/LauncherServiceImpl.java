package com.qaprosoft.zafira.service.impl;

import com.jayway.restassured.path.json.JsonPath;
import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.*;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.LauncherService;
import org.apache.log4j.Logger;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.util.List;

public class LauncherServiceImpl implements LauncherService {
    private static final Logger LOGGER = Logger.getLogger(LauncherServiceImpl.class);
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

    public ExecutionServiceImpl getExecutor() {
        return apiExecutor;
    }

    /***
     *
     * @param accessToken
     * @return list of launcher ids
     */
    @Override
    public List<Integer> getAll(String accessToken) {
        String getAllLaunchersRs = apiExecutor.callApiMethod(new GetAllLaunchersMethod(accessToken),
                HTTPStatusCodeType.OK, false, null);

        return JsonPath.from(getAllLaunchersRs).getList(JSONConstant.ID_KEY);
    }

    @Override
    public int getById(String accessToken, int id) {
        String getLauncherByIdRs = apiExecutor.callApiMethod(new GetLauncherByIdMethod(accessToken, id),
                HTTPStatusCodeType.OK, true, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());

        return JsonPath.from(getLauncherByIdRs).getInt(JSONConstant.ID_KEY);
    }

    /**
     * @param accessToken
     * @param autoServerId
     * @param accountTypeId
     * @return id of launcher
     */
    @Override
    public int post(String accessToken, int autoServerId, int accountTypeId) {
        String postLauncherRs = apiExecutor.callApiMethod(
                new PostLauncherMethod(accessToken, autoServerId, accountTypeId), HTTPStatusCodeType.OK, true,
                JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        return JsonPath.from(postLauncherRs).getInt(JSONConstant.ID_KEY);
    }

    @Override
    public int put(String accessToken, int id, String valueToUpdate) {
        String putLauncherRs = apiExecutor.callApiMethod(new PutLauncherMethod(accessToken, id, valueToUpdate),
                HTTPStatusCodeType.OK, true, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        return JsonPath.from(putLauncherRs).getInt(JSONConstant.ID_KEY);
    }

    @Override
    public void deleteById(String accessToken, int id) {
        new DeleteLauncherMethod(accessToken, id).callAPI();
    }

    @Override
    public String getQueueItemUrl(String accessToken, int scmAccountId) {
        String putLauncherRs = apiExecutor.callApiMethod(new PostScanLaucherMethod(accessToken, scmAccountId),
                HTTPStatusCodeType.OK, true, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        return JsonPath.from(putLauncherRs).getString(JSONConstant.QUEUE_ITEM_URL_KEY);
    }

    @Override
    public String getBuildNumber(String accessToken, String queueItemUrl) {
        String buildNumber = apiExecutor
                .callApiMethod(new GetBuildNumberMethod(accessToken, queueItemUrl), HTTPStatusCodeType.OK, false, null)
                .toString();
        LOGGER.info(String.format("Build number = %s", buildNumber));
        return buildNumber;
    }
}
