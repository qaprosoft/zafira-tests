package com.qaprosoft.zafira.service;

import java.util.List;

public interface WidgetService {

    List<Integer> getAllWidgetIds();

    void deleteWidget(int widgetId);

    int createWidget(String widgetName);
}
