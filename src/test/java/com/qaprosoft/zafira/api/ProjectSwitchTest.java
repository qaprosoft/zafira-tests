package com.qaprosoft.zafira.api;

import com.qaprosoft.zafira.api.projectsV1.PutProjectSwitchV1Method;
import com.qaprosoft.zafira.constant.TestRailConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.ProjectV1ServiceImpl;
import com.zebrunner.agent.core.annotation.Maintainer;
import com.zebrunner.agent.core.annotation.TestLabel;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;


public class ProjectSwitchTest extends ZafiraAPIBaseTest {
    private static Logger LOGGER = Logger.getLogger(ProjectSwitchTest.class);
    private static ProjectV1ServiceImpl projectV1Service = new ProjectV1ServiceImpl();


    @Test
    @Maintainer("obabich")
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40806")
    public void testSwitchProject() {
        List<Integer> allPublicProjectIds = projectV1Service.getAllPublicProjectIds();
        int projectId = allPublicProjectIds.get(1);

        PutProjectSwitchV1Method putProjectSwitchV1Method = new PutProjectSwitchV1Method(projectId);
        apiExecutor.expectStatus(putProjectSwitchV1Method, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(putProjectSwitchV1Method);

        Assert.assertTrue(projectV1Service.getProjectSortBySwitchedAte().contains(projectId));
    }

    @Test()
    @Maintainer("obabich")
    @TestLabel(name = TestRailConstant.TESTCASE_ID, value = "40807")
    public void testSwitchProjectWithNonexistentProjectId() {
        int projectId = projectV1Service.createProject();
        projectV1Service.deleteProjectById(projectId);

        PutProjectSwitchV1Method putProjectSwitchV1Method = new PutProjectSwitchV1Method(projectId);
        apiExecutor.expectStatus(putProjectSwitchV1Method, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(putProjectSwitchV1Method);

        Assert.assertFalse(projectV1Service.getProjectSortBySwitchedAte().contains(projectId));
    }
}

