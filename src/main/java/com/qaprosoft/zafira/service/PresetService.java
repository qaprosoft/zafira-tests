package com.qaprosoft.zafira.service;

public interface PresetService {

    int create(int launcherId);

    String getWebhookUrl(int launcherId, int presetId);
}
