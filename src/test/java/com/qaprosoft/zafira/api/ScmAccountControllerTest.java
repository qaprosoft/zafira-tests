package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.zafira.api.scmAccountController.GetAllSCMAccountsMethod;
import com.qaprosoft.zafira.api.scmAccountController.GetGithubConfigMethod;
import com.qaprosoft.zafira.api.scmAccountController.GetSCMAccountByIdMethod;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.annotations.Test;

public class ScmAccountControllerTest extends ZafiraAPIBaseTest {
    private static final int EXISTING_ACCOUNT_ID = 37;

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
}
