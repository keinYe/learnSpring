package com.keinye.spring.common.utils;

import java.io.IOException;
import java.io.Reader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtil {
	private static final ObjectMapper OM = new ObjectMapper().findAndRegisterModules()
			.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	
	public static String toJson(Object object) {
		try {
			return OM.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return null;
		}
	}
	
	public static <T> T fromJson(@NonNull String json, Class<T> type) {
		try {
			return OM.readValue(json, type);
		} catch (JsonProcessingException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return null;
		}
	}
	
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T fromJson(@NonNull String json, TypeReference type) {
        try {
            return (T) OM.readValue(json, type);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static <T> T fromJson(@NonNull Reader json, Class<T> type) {
        try {
            return OM.readValue(json, type);
        } catch (IOException e) {
            log.error("error on deserialize", e);
            return null;
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T fromJson(@NonNull Reader json, TypeReference type) {
        try {
            return (T) OM.readValue(json, type);
        } catch (IOException e) {
            log.error("error on deserialize", e);
            return null;
        }
    }

    public static ObjectMapper getObjectMapper() {
        return OM;
    }
}
