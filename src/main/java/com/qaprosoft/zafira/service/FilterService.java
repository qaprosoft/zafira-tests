package com.qaprosoft.zafira.service;

import java.util.List;

public interface FilterService {

    int createFilter(String filterName);

    List <Integer> getAllPublicFiltersIds();

    void deleteFilterById(int filterId);
}
