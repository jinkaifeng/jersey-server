/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.neocdtv.jetty.base.control;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 *
 * @author xix
 */
public class JacksonObjectMapper {

    private static ObjectMapper objectMapperWithType;
    private static ObjectMapper objectMapperWithoutType;
    
    public static ObjectMapper getInstanceWithType() {
        if (objectMapperWithType == null) {
            objectMapperWithType = buildBaseMapper();
            objectMapperWithType.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        }
        return objectMapperWithType;
    }
    
    public static ObjectMapper getInstanceWithoutType() {
        if (objectMapperWithoutType == null) {
            objectMapperWithoutType = buildBaseMapper();
        }
        return objectMapperWithoutType;
    }
    
    private static ObjectMapper buildBaseMapper() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        
        return objectMapper;
    }
}
