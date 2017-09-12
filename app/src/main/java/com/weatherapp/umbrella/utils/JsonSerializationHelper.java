package com.weatherapp.umbrella.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;

/**
 * Created by CodeWord on 9/11/2017.
 */

public class JsonSerializationHelper {

    public static <T> T deserializeObject(Class<T> objectClass, String json) {
        try {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            JsonReader reader = new JsonReader(new StringReader(json));
            reader.setLenient(true);
            return gson.fromJson(json, objectClass);
        } catch (JsonParseException jsonParseException) {
            jsonParseException.printStackTrace();
            return null;
        }
    }
}
