package org.darkend.slutprojekt_java_ee.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class ObjectToJson {

    private static final ObjectWriter ow = new ObjectMapper().writer()
            .withDefaultPrettyPrinter();

    public static String convert(Object o) {
        try {
            return "\r\n" + ow.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting object to JSON");
        }
    }
}
