package com.qaprosoft.zafira.util;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class Base64Util {
    // Encode
    public static String encodeString(String text)
            throws UnsupportedEncodingException {
        byte[] bytes = text.getBytes("UTF-8");
        String encodeString = java.util.Base64.getEncoder().encodeToString(bytes);
        return encodeString;
    }

    // Decode
    public static String decodeString(String encodeText)
            throws UnsupportedEncodingException {
        byte[] decodeBytes = Base64.getDecoder().decode(encodeText);
        String str = new String(decodeBytes, "UTF-8");
        return str;
    }

}
