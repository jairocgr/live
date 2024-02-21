package io.taxi.live.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Component;

@Component
public class JsonSerializer {

    private final Gson gson;

    public JsonSerializer() {
        this.gson = new GsonBuilder().create();
    }

    public String toJson(final Object source) {
        return this.gson.toJson(source);
    }

    public <T> T fromJson(final String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }
}
