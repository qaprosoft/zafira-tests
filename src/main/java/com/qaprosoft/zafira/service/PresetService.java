package com.qaprosoft.zafira.service;

public interface PresetService {

    int post(String accessToken, int launcherId);

    String getWebhookUrl(String accessToken, int launcherId, int presetId);
}
