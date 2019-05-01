package com.qaprosoft.zafira.service;

import com.qaprosoft.zafira.gui.DashboardPage;
import com.qaprosoft.zafira.gui.LoginPage;

public interface AuthService {

    DashboardPage signin(String usernameOrEmail, String password);

    LoginPage logout();

}
