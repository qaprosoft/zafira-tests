package com.qaprosoft.zafira.service;

public interface PresetService {

    int create(String accessToken, int launcherId);

    String getWebhookUrl(String accessToken, int launcherId, int presetId);
}
