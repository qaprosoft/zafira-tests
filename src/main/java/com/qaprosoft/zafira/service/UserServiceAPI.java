package com.qaprosoft.zafira.service;

public interface UserServiceAPI {

    String getUserByCriteria(String query);

    String getUserId(String username);
}
