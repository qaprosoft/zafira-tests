package com.qaprosoft.zafira.service;

import java.util.List;

public interface FailureTagService {

   int createFailureTag(String name);

    void updateFallbackFailureTag(int tagId, Boolean fallback,String name);

    void deleteFailureTag(int tagId);

    List<Integer> getAllFailureTagIds();
}
