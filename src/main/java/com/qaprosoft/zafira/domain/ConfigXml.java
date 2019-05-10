package com.qaprosoft.zafira.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "config")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConfigXml {

    @XmlElement(name = "arg")
    private List<ConfigXmlArg> configXmlArgs;

    public List<ConfigXmlArg> getConfigXmlArgs() {
        return configXmlArgs;
    }

    public void setConfigXmlArgs(List<ConfigXmlArg> configXmlArgs) {
        this.configXmlArgs = configXmlArgs;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class ConfigXmlArg {

        @XmlElement(name = "key")
        private String key;

        @XmlElement(name = "value")
        private String value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

    }

}
