package com.qaprosoft.zafira.util;

import com.qaprosoft.carina.core.foundation.commons.SpecialKeywords;
import com.qaprosoft.carina.core.foundation.crypto.CryptoTool;
import com.qaprosoft.carina.core.foundation.utils.Configuration;

import java.util.regex.Pattern;

public class CryptoUtil {
    private static CryptoTool cryptoTool = new CryptoTool(Configuration.get(Configuration.Parameter.CRYPTO_KEY_PATH));
    private static Pattern CRYPTO_PATTERN = Pattern.compile(SpecialKeywords.CRYPT);

    public static String decrypt(String string) {
        return cryptoTool.decryptByPattern(string, CRYPTO_PATTERN);
    }
}