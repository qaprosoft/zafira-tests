package com.qaprosoft.zafira.api.authIAM;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.manager.APIContextManager;
import com.qaprosoft.zafira.util.CryptoUtil;

public class PostRefreshTokenMethodIAM extends AbstractApiMethodV2 {
    public PostRefreshTokenMethodIAM() {
        super("api/authIAM/_post/rq_for_refresh.json", "api/authIAM/_post/rs_for_refresh.json", "api/auth.properties");
        replaceUrlPlaceholder("base_api_url", APIContextManager.BASE_URL);
        addProperty("refreshToken",CryptoUtil.decrypt(R.TESTDATA.get(ConfigConstant.REFRESH_TOKEN_KEY)));
    }
}
