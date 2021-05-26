package com.qaprosoft.zafira.util;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;

public class AbstractAPIMethodUtil {
    public static void deleteQuery(AbstractApiMethodV2 abstractApiMethodV2){
        abstractApiMethodV2
                .setMethodPath(
                        abstractApiMethodV2.getMethodPath()
                                .split("\\?")[0]);
    }
}
