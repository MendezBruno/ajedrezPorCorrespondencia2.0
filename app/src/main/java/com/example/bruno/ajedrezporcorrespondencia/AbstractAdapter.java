package com.example.bruno.ajedrezporcorrespondencia;


import com.example.bruno.ajedrezporcorrespondencia.piezas.Pieza;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by maria on 12/11/2016.
 */

public class AbstractAdapter implements JsonSerializer<Pieza>, JsonDeserializer<Pieza> {

    @Override
        public JsonElement serialize(Pieza src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.add("type", new JsonPrimitive(src.getClass().getSimpleName()));
        result.add("properties", context.serialize(src, src.getClass()));
        return result;
        }
    @Override
        public Pieza deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            String type = jsonObject.get("type").getAsString();
            JsonElement element = jsonObject.get("properties");
        try {
            return context.deserialize(element, Class.forName("com.example.bruno.ajedrezporcorrespondencia.piezas." + type));
            } catch (ClassNotFoundException cnfe) {
            throw new JsonParseException("Unknown element type: " + type, cnfe);
            }
        }
}
