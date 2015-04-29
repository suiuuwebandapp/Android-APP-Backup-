package com.minglang.suiuu.utils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

public class JsonUtil {
	public static final String TAG = JsonUtil.class.getName();
	private static Object lock = new Object();
	private static JsonUtil jsonUtil;
	private Gson gson;

	private JsonUtil() {
		GsonBuilder gb = new GsonBuilder();
		gb.serializeNulls();
		gb.setExclusionStrategies(new ExclusionStrategy() {

			@Override
			public boolean shouldSkipField(FieldAttributes arg0) {
				if (arg0.getAnnotation(ExcludeJSON.class) != null) {
					return true;
				}
				return false;
			}

			@Override
			public boolean shouldSkipClass(Class<?> arg0) {
				if (arg0.getAnnotation(ExcludeJSON.class) != null) {
					return true;
				}
				return false;
			}
		});
		gson = gb.create();
	}

	public String toJSON(Object obj) {
		if (obj == null) {
			return "";
		}
		return gson.toJson(obj);
	}

	public <T> T fromJSON(Class<T> clazz, String json) {
		if (json == null || json.trim().length() == 0) {
			return null;
		}
		return gson.fromJson(json, clazz);
	}

	public <T> T fromJSON(Type type, String json) {
		if (json == null) {
			return null;
		}
		return gson.fromJson(json, type);
	}

	public static JsonUtil getInstance() {
		synchronized (lock) {
			if (jsonUtil == null) {
				jsonUtil = new JsonUtil();
			}
		}
		return jsonUtil;
	}

}