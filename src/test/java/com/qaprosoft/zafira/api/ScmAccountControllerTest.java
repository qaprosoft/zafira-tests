package com.qaprosoft.zafira.api;

import com.jayway.restassured.path.json.JsonPath;
import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.scmAccountController.*;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.impl.SCMAccountServiceImpl;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.List;

public class ScmAccountControllerTest extends ZafiraAPIBaseTest {
    private static final int EXISTING_ACCOUNT_ID = 86;
    private static int scmAccountId;

    @AfterMethod
    public void testDeleteSCMAccount() {
        new SCMAccountServiceImpl().deleteSCMAccount(scmAccountId);
    }

    @Test
    public void testGetAllSCMAccounts() {
        GetAllSCMAccountsMethod getAllSCMAccountsMethod = new GetAllSCMAccountsMethod();
        apiExecutor.expectStatus(getAllSCMAccountsMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getAllSCMAccountsMethod);
        apiExecutor.validateResponse(getAllSCMAccountsMethod, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testGetSCMAccountById() {
        GetSCMAccountByIdMethod getSCMAccountByIdMethod = new GetSCMAccountByIdMethod(EXISTING_ACCOUNT_ID);
        apiExecutor.expectStatus(getSCMAccountByIdMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getSCMAccountByIdMethod);
        apiExecutor.validateResponse(getSCMAccountByIdMethod, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testGetGithubConfig() {
        GetGithubConfigMethod getGithubConfigMethod = new GetGithubConfigMethod();
        apiExecutor.expectStatus(getGithubConfigMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getGithubConfigMethod);
        apiExecutor.validateResponse(getGithubConfigMethod, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testCreateSCMAccounts() {
        PostSCMAccountMethod postSCMAccountMethod = new PostSCMAccountMethod();
        apiExecutor.expectStatus(postSCMAccountMethod, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postSCMAccountMethod);
        scmAccountId = JsonPath.from(rs).getInt(JSONConstant.ID_KEY);
        apiExecutor.validateResponse(postSCMAccountMethod, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testUpdateSCMAccounts() {
        SCMAccountServiceImpl scmAccountService = new SCMAccountServiceImpl();
        scmAccountId = scmAccountService.createSCMAccount();
        PutSCMAccountMethod putSCMAccountMethod = new PutSCMAccountMethod(scmAccountId);
        apiExecutor.expectStatus(putSCMAccountMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(putSCMAccountMethod);
        apiExecutor.validateResponse(putSCMAccountMethod, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testDeleteSCMAccounts() {
        SCMAccountServiceImpl scmAccountService = new SCMAccountServiceImpl();
        scmAccountId = scmAccountService.createSCMAccount();
        DeleteSCMAccountMethod deleteSCMAccountMethod = new DeleteSCMAccountMethod(scmAccountId);
        apiExecutor.expectStatus(deleteSCMAccountMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(deleteSCMAccountMethod);
        List allSCMAccounts = scmAccountService.getAllSCMAccounts();
        System.out.println(allSCMAccounts.toString());
        Assert.assertFalse(allSCMAccounts.contains(scmAccountId));
    }

}
