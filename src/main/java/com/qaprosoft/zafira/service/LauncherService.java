package com.qaprosoft.zafira.service;

import java.util.List;

public interface LauncherService {

    List<Integer> getAll();

    int getById(int id);

    int create(int autoServerId, int accountTypeId);

    int update(int id, String valueToUpdate);

    void deleteById(int id);

    String getQueueItemUrl(int scmAccountId);

    String getBuildNumber(String queueItemUrl);

}
