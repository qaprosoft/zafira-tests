package com.qaprosoft.zafira.service;

import java.util.List;

public interface WidgetService {

    List<Integer> getAllWidgetIds();

    void deleteWidget(int widgetId);

    int createWidget(String widgetName, int widgetTemplateId);

    String createWidgetToDashboard(String widgetName, int widgetTemplateId);

    List<Integer> getAllWidgetTemplateIds();
}
