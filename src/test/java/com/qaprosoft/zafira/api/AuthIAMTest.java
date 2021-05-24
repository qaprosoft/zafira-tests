package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.authIAM.GetAccessTokenMethod;
import com.qaprosoft.zafira.api.authIAM.PostGenerateAuthTokenMethodIAM;
import com.qaprosoft.zafira.api.authIAM.PostRefreshTokenMethodIAM;
import com.qaprosoft.zafira.api.authIAM.PostVerifyPermissionsMethodIAM;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.manager.APIContextManager;
import com.qaprosoft.zafira.service.impl.AuthServiceApiIamImpl;
import com.qaprosoft.zafira.util.CryptoUtil;
import io.restassured.path.json.JsonPath;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.invoke.MethodHandles;
import java.util.List;

public class AuthIAMTest extends ZafiraAPIBaseTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static AuthServiceApiIamImpl authServiceApiIam = new AuthServiceApiIamImpl();
    private static String permissions = authServiceApiIam.getPermissionsList();
    private static List userPermissions = authServiceApiIam.getUserPermissionsList();
    private final static String INVALID_PASSWORD = "test";
    private final static String EMPTY_AUTHTOKEN = "";

    @Test
    public void testGenerateAuthToken() {
        String username = CryptoUtil.decrypt(R.TESTDATA.get(ConfigConstant.AUTH_USERNAME_KEY));
        String password = CryptoUtil.decrypt(R.TESTDATA.get(ConfigConstant.AUTTH_PASSWORD_KEY));
        PostGenerateAuthTokenMethodIAM postGenerateAuthTokenMethodIAM
                = new PostGenerateAuthTokenMethodIAM(username, password);
        apiExecutor.expectStatus(postGenerateAuthTokenMethodIAM, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postGenerateAuthTokenMethodIAM);
        apiExecutor.validateResponse(postGenerateAuthTokenMethodIAM, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testGenerateAuthTokenWithInvalidPassword() {
        String username = CryptoUtil.decrypt(R.TESTDATA.get(ConfigConstant.AUTH_USERNAME_KEY));
        PostGenerateAuthTokenMethodIAM postGenerateAuthTokenMethodIAM
                = new PostGenerateAuthTokenMethodIAM(username, INVALID_PASSWORD);
        apiExecutor.expectStatus(postGenerateAuthTokenMethodIAM, HTTPStatusCodeType.UNAUTHORIZED);
        apiExecutor.callApiMethod(postGenerateAuthTokenMethodIAM);
    }

    @Test
    public void testRefreshToken() {
        PostRefreshTokenMethodIAM postRefreshTokenMethodIAM
                = new PostRefreshTokenMethodIAM();
        apiExecutor.expectStatus(postRefreshTokenMethodIAM, HTTPStatusCodeType.OK);
        String rs = apiExecutor.callApiMethod(postRefreshTokenMethodIAM);
        apiExecutor.validateResponse(postRefreshTokenMethodIAM, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        List<Object> projectAssignments = JsonPath.from(rs).getList(JSONConstant.PROJECT_ASSIGNMENTS);
        Assert.assertNotEquals(0, projectAssignments.size());
        LOGGER.info(projectAssignments.toString());
    }

    @Test
    public void testGenerateApiToken() {
        GetAccessTokenMethod getAccessTokenMethod = new GetAccessTokenMethod();
        apiExecutor.expectStatus(getAccessTokenMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(getAccessTokenMethod);
        apiExecutor.validateResponse(getAccessTokenMethod, JSONCompareMode.STRICT,
                JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testVerifyPermissions() {
        String authToken = new APIContextManager().getAccessToken();
        String tenantName = authServiceApiIam.getTenantName();
        PostVerifyPermissionsMethodIAM postVerifyPermissionsMethodIAM
                = new PostVerifyPermissionsMethodIAM(authToken, permissions,tenantName);
        apiExecutor.expectStatus(postVerifyPermissionsMethodIAM, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postVerifyPermissionsMethodIAM);
    }

    @Test
    public void testVerifyPermissionsWithoutAuthToken() {
        String tenantName = authServiceApiIam.getTenantName();
        PostVerifyPermissionsMethodIAM postVerifyPermissionsMethodIAM
                = new PostVerifyPermissionsMethodIAM(EMPTY_AUTHTOKEN, permissions,tenantName);
        apiExecutor.expectStatus(postVerifyPermissionsMethodIAM, HTTPStatusCodeType.BAD_REQUEST);
        apiExecutor.callApiMethod(postVerifyPermissionsMethodIAM);
    }
}
