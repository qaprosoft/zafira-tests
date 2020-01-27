package com.qaprosoft.zafira.api;

import com.jayway.restassured.path.json.JsonPath;
import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.InvitationMethods.DeleteInvitationByEmailMethod;
import com.qaprosoft.zafira.api.InvitationMethods.GetInvitationByKeywordMethod;
import com.qaprosoft.zafira.api.InvitationMethods.PostInvitesUserMethod;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.domain.EmailMsg;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.enums.IntegrationGroupType;
import com.qaprosoft.zafira.manager.APIContextManager;
import com.qaprosoft.zafira.manager.EmailManager;
import com.qaprosoft.zafira.service.impl.IntegrationServiceImpl;
import com.qaprosoft.zafira.service.impl.InvitationServiceImpl;
import com.qaprosoft.zafira.util.CryptoUtil;
import org.apache.log4j.Logger;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InvitationTest extends ZariraAPIBaseTest {
    private final static Logger LOGGER = Logger.getLogger(InvitationTest.class);

    @Test
    public void testGetInvitationByKeyword() {
        String keyword = R.TESTDATA.get(ConfigConstant.TEST_EMAIL_KEY);
        GetInvitationByKeywordMethod getInvitationByKeywordMethod = new GetInvitationByKeywordMethod(keyword);
        apiExecutor.expectStatus(getInvitationByKeywordMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getInvitationByKeywordMethod);
        apiExecutor.validateResponse(getInvitationByKeywordMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testPostInvitesUser() {
        String email = R.TESTDATA.get(ConfigConstant.TEST_EMAIL_KEY);
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String time = parser.format(new Date());

        PostInvitesUserMethod postInvitesUserMethod = new PostInvitesUserMethod(email, time);
        apiExecutor.expectStatus(postInvitesUserMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postInvitesUserMethod);
        apiExecutor.validateResponse(postInvitesUserMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        new InvitationServiceImpl().deleteInviteByEmail(email);
    }

    @Test
    public void testDeleteInvitationByEmail() {
        String email = R.TESTDATA.get(ConfigConstant.TEST_EMAIL_KEY);
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String time = parser.format(new Date());
        InvitationServiceImpl invitationServiceImpl = new InvitationServiceImpl();
        invitationServiceImpl.postInvite(email, time);

        String response = invitationServiceImpl.getInvitations(email);
        int invitationId = JsonPath.from(response).get(JSONConstant.ALL_ID_FROM_RESULTS_KEY);
        Assert.assertNotEquals(0, invitationId, "Invite was not send!");

        DeleteInvitationByEmailMethod deleteInvitationByEmailMethod = new DeleteInvitationByEmailMethod(email);
        apiExecutor.expectStatus(deleteInvitationByEmailMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(deleteInvitationByEmailMethod);

        String responseAfterDelete = invitationServiceImpl.getInvitations(email);
        Assert.assertFalse(responseAfterDelete.contains(String.valueOf(invitationId)), "Invite was not delete!");
    }
}
