package com.starlight.dot.framework.network.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GsonUtil {
    private static GsonUtil gsonUtil;

    private static Gson gson;

    private GsonUtil(){
        gson = new GsonBuilder().registerTypeAdapter(HashMap.class, new JsonDeserializer<HashMap>() {
            public HashMap deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                    throws JsonParseException {
                HashMap<String,Object> resultMap=new HashMap<>();
                JsonObject jsonObject = json.getAsJsonObject();
                Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
                for (Map.Entry<String, JsonElement> entry : entrySet) {
                    resultMap.put(entry.getKey(),entry.getValue());
                }
                return resultMap;
            }
        }).create();
    }

    public static GsonUtil getInstance(){
        synchronized (GsonUtil.class){
            if(gsonUtil == null){
                gsonUtil = new GsonUtil();
            }
            return gsonUtil;
        }
    }

    public Gson getGson(){
        return gson;
    }

}
