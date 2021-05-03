package com.qaprosoft.zafira.service.impl;

import com.qaprosoft.zafira.api.milestones.DeleteMilestoneByIdAndProjectIdMethod;
import com.qaprosoft.zafira.api.milestones.GetMilestonesByIdAndProjectIdMethod;
import com.qaprosoft.zafira.api.milestones.GetMilestonesByProjectIdMethod;
import com.qaprosoft.zafira.api.milestones.PostMilestoneMethod;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.MilestoneService;
import io.restassured.path.json.JsonPath;
import org.apache.log4j.Logger;

import java.util.List;

public class MilestoneServiceImpl implements MilestoneService {
    private static final Logger LOGGER = Logger.getLogger(MilestoneServiceImpl.class);
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();

    public ExecutionServiceImpl getExecutor() {
        return apiExecutor;
    }

    @Override
    public int create(int projectId, String milestoneName) {
        PostMilestoneMethod postMilestoneMethod =
                new PostMilestoneMethod(projectId, milestoneName);
        apiExecutor.expectStatus(postMilestoneMethod, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postMilestoneMethod);
        return JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
    }

    @Override
    public void delete(int projectId, int milestoneNameId) {
        DeleteMilestoneByIdAndProjectIdMethod deleteMilestoneByIdAndProjectId =
                new DeleteMilestoneByIdAndProjectIdMethod(projectId, milestoneNameId);
        apiExecutor.callApiMethod(deleteMilestoneByIdAndProjectId);
    }

    @Override
    public List<Integer> getAllMilestoneIdsInProject(int projectId) {
        GetMilestonesByProjectIdMethod getMilestonesByProjectIdMethod =
                new GetMilestonesByProjectIdMethod(projectId);
        apiExecutor.expectStatus(getMilestonesByProjectIdMethod, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getMilestonesByProjectIdMethod);
        List<Integer> milestoneIds = JsonPath.from(rs).getList(JSONConstant.ITEMS_ID);
        LOGGER.info("Actual milestone ids:  " + milestoneIds);
        return milestoneIds;
    }

    @Override
    public String getMilestoneNameById(int projectId, int milestoneId) {
        GetMilestonesByIdAndProjectIdMethod getMilestonesByProjectIdMethod =
                new GetMilestonesByIdAndProjectIdMethod(projectId, milestoneId);
        apiExecutor.expectStatus(getMilestonesByProjectIdMethod, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getMilestonesByProjectIdMethod);
        String name = JsonPath.from(rs).getString(JSONConstant.NAME);
        LOGGER.info("Actual milestone name:  " + name);
        return name;
    }

    @Override
    public String getMilestoneById(int projectId, int milestoneId) {
        GetMilestonesByIdAndProjectIdMethod getMilestonesByProjectIdMethod =
                new GetMilestonesByIdAndProjectIdMethod(projectId, milestoneId);
        apiExecutor.expectStatus(getMilestonesByProjectIdMethod, HTTPStatusCodeType.OK);
        String milestone = apiExecutor.callApiMethod(getMilestonesByProjectIdMethod);
        LOGGER.info("Actual milestone:  " + milestone);
        return milestone;
    }
}
