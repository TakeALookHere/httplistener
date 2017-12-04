package com.miskevich.httplistener.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public abstract class JsonConverter {
    private static final Logger LOG = LoggerFactory.getLogger(JsonConverter.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> String toJson(T value) {
        try {
            LOG.info("Object was converted to Json");
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            LOG.error("ERROR: ", e);
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
