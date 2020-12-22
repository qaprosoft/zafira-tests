package com.qaprosoft.zafira.service;

import java.util.List;

public interface TestRunFilterV1Service {

    int createFilter(String filterName);

    List <Integer> getAllFiltersIds();

    void deleteFilterById(int filterId);
}
