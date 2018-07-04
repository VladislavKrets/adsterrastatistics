package online.omnia.statistics;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lollipop on 03.12.2017.
 */
public class Main {
    public static int days;
    public static long deltaTime = 24 * 60 * 60 * 1000;

    public static void main(String[] args) throws UnsupportedEncodingException {
        if (args.length != 1) {
            return;
        }
        if (!args[0].matches("\\d+")) return;
        if (Integer.parseInt(args[0]) == 0) {
            deltaTime = 0;
        }
        days = Integer.parseInt(args[0]);

        List<AccountsEntity> accountsEntities = MySQLDaoImpl.getInstance().getAccountsEntities("adsterra");
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(List.class, new JsonCampaignListDeserializer());
        builder.registerTypeAdapter(String.class, new JsonUrlDeserializer());
        Gson gson = builder.create();
        builder.registerTypeAdapter(List.class, new JsonSourceListDeserializer());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String answer;
        Gson sourceGson = builder.create();
        List<JsonCampaignEntity> jsonCampaignEntities = null;
        List<JsonSourceEntity> jsonSourceEntities;
        SourceStatisticsEntity sourceStatisticsEntity;
        Map<String, String> parameters;
        SourceStatisticsEntity entity;
        String url;
        AdsetEntity adsetEntity;
        AdsetEntity tempEntity;
        int afid = 0;
        for (AccountsEntity accountsEntity : accountsEntities) {
            try {
                answer = HttpMethodUtils.getMethod("https://api3.adsterratools.com/advertiser/"
                        + accountsEntity.getApiKey()
                        /*+ "4ef9d2ee6fcad88611ecf37e6591c757"*/
                        + "/campaigns.json");
                jsonCampaignEntities = gson.fromJson(answer, List.class);
            } catch (Exception e) {
                Utils.writeLog(e.toString());
                continue;
            }
            System.out.println(jsonCampaignEntities);
            for (JsonCampaignEntity jsonCampaignEntity : jsonCampaignEntities) {
                answer = HttpMethodUtils.getMethod("https://api3.adsterratools.com/advertiser/"
                        + accountsEntity.getApiKey()
                        /*+ "4ef9d2ee6fcad88611ecf37e6591c757"*/
                        + "/campaign/"
                        + jsonCampaignEntity.getId()
                        + "/landings.json");
                url = URLDecoder.decode(gson.fromJson(answer, String.class), "UTF-8");
                parameters = Utils.getUrlParameters(url);
                System.out.println(url);
                if (parameters.containsKey("cab")) {
                    System.out.println(parameters.get("cab"));
                    if (parameters.get("cab").matches("\\d+")
                            && MySQLDaoImpl.getInstance().getAffiliateByAfid(Integer.parseInt(parameters.get("cab"))) != null) {
                        afid = Integer.parseInt(parameters.get("cab"));
                    } else {
                        afid = 0;
                    }
                } else afid = 2;

                try {
                    answer = HttpMethodUtils.getMethod("https://api3.adsterratools.com/advertiser/"
                            + accountsEntity.getApiKey()
                            /*+ "4ef9d2ee6fcad88611ecf37e6591c757"*/
                            + "/stats.json?campaign="
                            + jsonCampaignEntity.getId()
                            + "&start_date="
                            + simpleDateFormat.format(new Date(System.currentTimeMillis() - deltaTime - days * 24L * 60 * 60 * 1000))
                            + "&finish_date="
                            + simpleDateFormat.format(new Date(System.currentTimeMillis() - deltaTime)));
                    jsonSourceEntities = sourceGson.fromJson(answer, List.class);
                } catch (Exception e) {
                    Utils.writeLog(e.toString());
                    continue;
                }
                System.out.println(jsonSourceEntities);
                for (JsonSourceEntity jsonSourceEntity : jsonSourceEntities) {

                    sourceStatisticsEntity = new SourceStatisticsEntity();
                    sourceStatisticsEntity.setDate(jsonSourceEntity.getDate());
                    sourceStatisticsEntity.setReceiver("API");
                    sourceStatisticsEntity.setImpressions(jsonSourceEntity.getImpressions());
                    sourceStatisticsEntity.setCtr(jsonSourceEntity.getCtr());
                    sourceStatisticsEntity.setConversions(jsonSourceEntity.getConversions());
                    sourceStatisticsEntity.setClicks(jsonSourceEntity.getClicks());
                    sourceStatisticsEntity.setCpm(jsonSourceEntity.getCpm());
                    sourceStatisticsEntity.setSpent(jsonSourceEntity.getSpent());
                    sourceStatisticsEntity.setCampaignId(String.valueOf(jsonCampaignEntity.getId()));
                    sourceStatisticsEntity.setCampaignName(jsonCampaignEntity.getName());
                    sourceStatisticsEntity.setAccount_id(accountsEntity.getAccountId());
                    sourceStatisticsEntity.setBuyerId(accountsEntity.getBuyerId());
                    sourceStatisticsEntity.setAfid(afid);
                    if (Main.days != 0) {
                        entity = MySQLDaoImpl.getInstance().getSourceStatistics(sourceStatisticsEntity.getAccount_id(),
                                sourceStatisticsEntity.getCampaignName(), sourceStatisticsEntity.getDate());
                        if (entity != null) {
                            sourceStatisticsEntity.setId(entity.getId());
                            MySQLDaoImpl.getInstance().updateSourceStatistics(sourceStatisticsEntity);
                            entity = null;
                        } else MySQLDaoImpl.getInstance().addSourceStatistics(sourceStatisticsEntity);
                    }
                    else {
                        tempEntity = MySQLDaoImpl.getInstance().isDateInTodayAdsets(sourceStatisticsEntity.getDate(), sourceStatisticsEntity.getAccount_id(), sourceStatisticsEntity.getCampaignId());
                        adsetEntity = Utils.getAdset(sourceStatisticsEntity);
                        if (tempEntity != null){
                            adsetEntity.setId(tempEntity.getId());
                            MySQLDaoImpl.getInstance().updateTodayAdset(adsetEntity);
                        } else MySQLDaoImpl.getInstance().addTodayAdset(adsetEntity);

                    }
                }
            }
        }

        MySQLDaoImpl.getSessionFactory().close();
    }
}
