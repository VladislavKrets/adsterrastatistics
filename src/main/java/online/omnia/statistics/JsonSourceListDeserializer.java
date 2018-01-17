package online.omnia.statistics;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lollipop on 03.12.2017.
 */
public class JsonSourceListDeserializer implements JsonDeserializer<List<JsonSourceEntity>>{
    @Override
    public List<JsonSourceEntity> deserialize(JsonElement jsonElement, Type type,
                                              JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();
        JsonArray array = object.get("items").getAsJsonArray();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<JsonSourceEntity> jsonSourceEntities = new ArrayList<>();
        JsonSourceEntity jsonSourceEntity;

        for (JsonElement element : array) {
            jsonSourceEntity = new JsonSourceEntity();
            try {
                jsonSourceEntity.setDate(new Date(dateFormat
                        .parse(element.getAsJsonObject().get("date").getAsString()).getTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            jsonSourceEntity.setImpressions(element.getAsJsonObject().get("impressions").getAsInt());
            jsonSourceEntity.setConversions(element.getAsJsonObject().get("conversions").getAsInt());
            jsonSourceEntity.setClicks(element.getAsJsonObject().get("clicks").getAsInt());
            jsonSourceEntity.setCtr(element.getAsJsonObject().get("ctr").getAsDouble());
            jsonSourceEntity.setCpm(element.getAsJsonObject().get("cpm").getAsDouble());
            jsonSourceEntity.setSpent(element.getAsJsonObject().get("spent").getAsDouble());
            jsonSourceEntities.add(jsonSourceEntity);
        }
        return jsonSourceEntities;
    }
}
