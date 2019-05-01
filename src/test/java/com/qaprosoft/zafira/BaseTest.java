package com.qaprosoft.zafira;

import com.qaprosoft.carina.core.foundation.AbstractTest;
import com.qaprosoft.zafira.domain.Config;
import com.qaprosoft.zafira.gui.DashboardPage;
import com.qaprosoft.zafira.service.AuthService;
import com.qaprosoft.zafira.service.impl.AuthServiceImpl;

public class BaseTest extends AbstractTest {

    protected static final long SMALL_TIMEOUT = 2;
    protected static final long ANIMATION_TIMEOUT = 1;

    protected static final String ADMIN_USERNAME = Config.ADMIN_USERNAME.getValue();
    protected static final String ADMIN_PASSWORD = Config.ADMIN_PASSWORD.getValue();

    protected DashboardPage signin() {
        AuthService authService = new AuthServiceImpl(getDriver());
        return authService.signin(ADMIN_USERNAME, ADMIN_PASSWORD);
    }

}
