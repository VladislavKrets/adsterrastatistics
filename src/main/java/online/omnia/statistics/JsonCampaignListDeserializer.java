package online.omnia.statistics;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lollipop on 03.12.2017.
 */
public class JsonCampaignListDeserializer implements JsonDeserializer<List<JsonCampaignEntity>>{
    @Override
    public List<JsonCampaignEntity> deserialize(JsonElement jsonElement, Type type,
                                                JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();
        JsonArray array = object.get("items").getAsJsonArray();
        List<JsonCampaignEntity> jsonCampaignEntities = new ArrayList<>();
        JsonCampaignEntity jsonCampaignEntity;
        for (JsonElement element : array) {
            jsonCampaignEntity = new JsonCampaignEntity();
            jsonCampaignEntity.setId(element.getAsJsonObject().get("id").getAsInt());
            jsonCampaignEntity.setName(element.getAsJsonObject().get("alias").isJsonNull() ? null : element.getAsJsonObject().get("alias").getAsString());
            jsonCampaignEntities.add(jsonCampaignEntity);
        }
        return jsonCampaignEntities;
    }
}
