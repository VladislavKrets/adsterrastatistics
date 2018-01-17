package online.omnia.statistics;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class HttpMethodUtils {
    private static CloseableHttpClient httpClient;

    static {
        httpClient = HttpClients.createDefault();
    }
    public HttpMethodUtils() {

    }
    public static String getToken(String token) throws IOException {
        String line;
        StringBuilder lineBuilder;
        byte[] outputBytes;
        OutputStream os;
        BufferedReader reader;

        HttpURLConnection httpcon = (HttpURLConnection) ((new URL("https://api.exoclick.com/v2/login").openConnection()));
        httpcon.setDoOutput(true);
        httpcon.setRequestProperty("Content-Type", "application/json");
        httpcon.setRequestMethod("POST");
        httpcon.connect();

        String str = "{\"api_token\": \"" + token +"\"}";
        outputBytes = str.getBytes("UTF-8");
        os = httpcon.getOutputStream();
        os.write(outputBytes);
        os.close();
        reader = new BufferedReader(new InputStreamReader(httpcon.getInputStream()));

        lineBuilder = new StringBuilder();
        System.out.println("Getting answer");
        while ((line = reader.readLine()) != null) {
            lineBuilder.append(line);
        }
        reader.close();
        return lineBuilder.toString();
    }
    public static String getMethod(String urlPath){
        System.out.println(urlPath);
        try {
            if (urlPath == null) return null;
            HttpGet httpGet = new HttpGet(urlPath);
            return getResponse(httpGet);
        } catch (IOException e) {
            System.out.println("Input output exception during executing get request:");
            System.out.println(e.getMessage());
        }
        return "";
    }

    public static String postMethod(String urlPath, List<NameValuePair> params){
        try {
            if (urlPath == null || params == null) return null;
            HttpPost httpPost = new HttpPost(urlPath);

            httpPost.setEntity(new UrlEncodedFormEntity(params));

            return getResponse(httpPost);
        } catch (IOException e) {
            System.out.println("Input output exception during executing post request:");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return "";
    }

    private static String getResponse(HttpUriRequest httpRequest) throws IOException {
        CloseableHttpResponse response = httpClient.execute(httpRequest);
        StringBuilder serverAnswer = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
        String answer;
        while ((answer = reader.readLine()) != null) {
            serverAnswer.append(answer);
        }
        reader.close();
        response.close();
        return serverAnswer.toString();
    }


}
