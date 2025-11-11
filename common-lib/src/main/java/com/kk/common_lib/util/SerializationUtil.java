package com.kk.common_lib.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.management.RuntimeErrorException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SerializationUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static byte[] toByteArray(Object object) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(object);
            oos.flush();
            return bos.toByteArray();
        } catch (IOException ex) {
            throw new RuntimeErrorException(null);
        }
    }

    public static byte[] toJsonByteArray(Object object) throws IOException {
        return objectMapper.writeValueAsBytes(object);
    }

    public static <T> T fromJsonByteArray(byte[] jsonBytes, Class<T> classType) throws IOException {
        return objectMapper.readValue(jsonBytes, classType);
    }

    public static <T> T convertValue(Object object, Class<T> classType) {
        return objectMapper.convertValue(object, classType);
    }

    public static <T> T convertValue(byte[] jsonBytes, Class<T> classType) throws IOException {
        return objectMapper.readValue(jsonBytes, classType);
    }
}
