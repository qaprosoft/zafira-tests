package com.qaprosoft.zafira.gui;

import com.qaprosoft.carina.core.foundation.AbstractTest;
import com.qaprosoft.zafira.domain.Config;
import com.qaprosoft.zafira.gui.BasePage;
import com.qaprosoft.zafira.gui.DashboardPage;
import com.qaprosoft.zafira.service.AuthService;
import com.qaprosoft.zafira.service.builder.ServiceSingleton;
import com.qaprosoft.zafira.service.impl.AuthServiceImpl;
import com.qaprosoft.zafira.util.CommonUtils;

import java.util.function.Function;
import java.util.function.Supplier;

public class BaseTest extends AbstractTest {

    protected static final long SMALL_TIMEOUT = 2;
    protected static final long ANIMATION_TIMEOUT = 1;

    protected static final String ADMIN_USERNAME = Config.ADMIN_USERNAME.getValue();
    protected static final String ADMIN_PASSWORD = Config.ADMIN_PASSWORD.getValue();

    protected <T extends BasePage> T goToPage(Supplier<T> basePageSupplier, Function<DashboardPage, T> basePageFunction) {
        T result;
        if(ServiceSingleton.INSTANCE.getAuthToken() != null) {
            CommonUtils.setAuthorizationCookie(getDriver(), ServiceSingleton.INSTANCE.getAuthToken());
            result = basePageSupplier.get();
            result.open();
        } else {
            DashboardPage dashboardPage = signin();
            result = basePageFunction.apply(dashboardPage);
        }
        return result;
    }

    protected DashboardPage signin() {
        AuthService authService = new AuthServiceImpl(getDriver());
        return authService.signin(ADMIN_USERNAME, ADMIN_PASSWORD);
    }

}
