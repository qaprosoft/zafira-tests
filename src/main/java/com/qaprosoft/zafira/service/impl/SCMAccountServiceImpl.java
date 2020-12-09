package com.qaprosoft.zafira.service.impl;
import io.restassured.path.json.JsonPath;
import com.qaprosoft.zafira.api.scmAccountController.DeleteSCMAccountMethod;
import com.qaprosoft.zafira.api.scmAccountController.GetAllSCMAccountsMethod;
import com.qaprosoft.zafira.api.scmAccountController.PostSCMAccountMethod;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.service.SCMAccountService;

import java.util.List;

public class SCMAccountServiceImpl implements SCMAccountService {
    private ExecutionServiceImpl apiExecutor = new ExecutionServiceImpl();


    @Override
    public int createSCMAccount() {
        PostSCMAccountMethod postSCMAccountMethod = new PostSCMAccountMethod();
        apiExecutor.expectStatus(postSCMAccountMethod, HTTPStatusCodeType.OK);
        String response = apiExecutor.callApiMethod(postSCMAccountMethod);
        return JsonPath.from(response).getInt(JSONConstant.ID_KEY);
    }

    @Override
    public void deleteSCMAccount(int id) {
        DeleteSCMAccountMethod deleteUserFromGroupMethod = new DeleteSCMAccountMethod(id);
        apiExecutor.expectStatus(deleteUserFromGroupMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(deleteUserFromGroupMethod);
    }

    @Override
    public List<Integer> getAllSCMAccounts() {
        GetAllSCMAccountsMethod getAllSCMAccountsMethod = new GetAllSCMAccountsMethod();
        apiExecutor.expectStatus(getAllSCMAccountsMethod, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(getAllSCMAccountsMethod);
        return JsonPath.from(rs).getList(JSONConstant.ID_KEY);
    }
}
