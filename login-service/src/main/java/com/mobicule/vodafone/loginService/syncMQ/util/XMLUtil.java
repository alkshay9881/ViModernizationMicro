package com.mobicule.vodafone.loginService.syncMQ.util;

import com.mobicule.vodafone.loginService.syncMQ.model.response.ReadWriteRspGBOXML;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.StringReader;
import java.io.StringWriter;

public class XMLUtil {

    public static String toXML(Object obj) throws Exception {

        if (obj instanceof java.util.Map) {
            throw new IllegalArgumentException("Map cannot be marshalled to XML. Use JAXB annotated class.");
        }

        JAXBContext context = JAXBContext.newInstance(obj.getClass());
        Marshaller marshaller = context.createMarshaller();

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        StringWriter writer = new StringWriter();
        marshaller.marshal(obj, writer);

        return writer.toString();
    }

    public static Object fromXML(String xml, Class<?> responseClass) throws Exception {

        JAXBContext context = JAXBContext.newInstance(responseClass);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        return unmarshaller.unmarshal(new StringReader(xml));
    }
}