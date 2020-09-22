package com.qaprosoft.zafira.util;

import com.qaprosoft.zafira.domain.ConfigXml;

public class ConfigXmlUtil {

    private static final ConfigXml CONFIG_XML;

    static {
        CONFIG_XML = XmlParserUtil.parseConfigXml();
    }

    public static String getConfigXmlValue(String key) {
        return CONFIG_XML.getConfigXmlArgs().stream()
                         .filter(configXmlArg -> configXmlArg.getKey().equals(key))
                         .findAny()
                         .map(ConfigXml.ConfigXmlArg::getValue)
                         .orElse(null);
    }

}
