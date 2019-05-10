package com.qaprosoft.zafira.util;

import com.qaprosoft.zafira.domain.ConfigXml;
import com.qaprosoft.zafira.service.builder.TestRunBuilder;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class XmlParserUtil {

    private static final Logger LOGGER = Logger.getLogger(XmlParserUtil.class);
    private static final File CONFIG_XML = new File(XmlParserUtil.class.getResource("/config.xml").getPath());

    public static ConfigXml parseConfigXml() {
        return parse(CONFIG_XML, ConfigXml.class);
    }

    @SuppressWarnings("unchecked")
    private static <T> T parse(File file, Class<T> targetClass) {
        T result = null;
        JAXBContext jc;
        try {
            jc = JAXBContext.newInstance(targetClass);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            result = (T) unmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return result;
    }

}
