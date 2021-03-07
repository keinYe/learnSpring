package com.keinye.spring.common;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.persistence.AttributeConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;


public class CommonConverters {
	public static abstract class CommonConverter<T> implements AttributeConverter<T, byte[]> {
		private static final ThreadLocal<ObjectMapper> MAPPER = ThreadLocal.withInitial(ObjectMapper::new);
		
		private final TypeReference<T> type;
		
		public CommonConverter(TypeReference<T> type) {
			this.type = type;
		}
		
		@Override
		public byte[] convertToDatabaseColumn(T attribute) {
			if (attribute == null) {
				return null;
			}
			
			try {
				return MAPPER.get().writeValueAsString(attribute).getBytes(Charsets.UTF_8);
			} catch (JsonProcessingException e) {
				throw new IllegalStateException();
			}
		}
		
		@Override
		public T convertToEntityAttribute(byte[] dbData) {
			if (dbData == null) {
				return null;
			}
			
			try {
				return MAPPER.get().readValue(new String(dbData, Charsets.UTF_8), type);
			} catch (IOException e) {
				throw new IllegalStateException();
			} 
		}
	}
	
	public static class ListStringConverter extends CommonConverter<List<String>> {
		public ListStringConverter() {
			super(new TypeReference<List<String>>() {});
		}
	}
	
	public static class MapStringStringConverter extends CommonConverter<Map<String, String>> {
		public MapStringStringConverter() {
			super(new TypeReference<Map<String, String>>(){});
		}
	}
}
