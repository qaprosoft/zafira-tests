package com.qaprosoft.zafira.service;

import java.util.List;

public interface SCMAccountService {
    int createSCMAccount();
    void deleteSCMAccount(int id);

    List<Integer> getAllSCMAccounts();
}
