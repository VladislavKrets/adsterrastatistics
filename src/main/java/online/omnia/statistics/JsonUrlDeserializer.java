package online.omnia.statistics;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Created by lollipop on 03.12.2017.
 */
public class JsonUrlDeserializer implements JsonDeserializer<String>{
    @Override
    public String deserialize(JsonElement jsonElement, Type type,
                              JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();
        JsonArray array = object.get("items").getAsJsonArray();
        for (JsonElement element : array) {
            return element.getAsJsonObject().get("url").getAsString();
        }
        return null;
    }
}
