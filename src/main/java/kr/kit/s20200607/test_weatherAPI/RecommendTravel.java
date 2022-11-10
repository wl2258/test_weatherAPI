package kr.kit.s20200607.test_weatherAPI;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@RestController
public class RecommendTravel {

    public String xmlToJson(String str) {
        try{
            String xml = str;
            JSONObject object = XML.toJSONObject(xml);
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            Object json = mapper.readValue(object.toString(), Object.class);
            String output = mapper.writeValueAsString(json);
            return output;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/recommendTest")
    public String recommendTravel() {
        String location = "대구";
        String resultTravel = "";
        String apiUrl = "http://api.kcisa.kr/openapi/service/rest/convergence2019/getConver01";
        String serviceKey = "9b75f05a-875f-4139-8bb6-68ba1f356869";

        StringBuilder urlBuilder = new StringBuilder(apiUrl);
        try {
            urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + serviceKey);
            urlBuilder.append("&" + URLEncoder.encode("keyword", "UTF-8") + "=" + URLEncoder.encode(location, "UTF-8"));

            URL url = new URL(urlBuilder.toString());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            System.out.println("Response code: " + conn.getResponseCode());

            BufferedReader rd;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();

            resultTravel = sb.toString();

        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println(e.getMessage());
        }

        String output = xmlToJson(resultTravel);
        System.out.println(output);

        JSONObject jsonObj = new JSONObject(output);
        JSONObject response = jsonObj.getJSONObject("response");

        //response로부터 body 찾기
        JSONObject header = response.getJSONObject("header");
        JSONObject body = response.getJSONObject("body");

        JSONObject items = body.getJSONObject("items");
        JSONArray itemsJSONArray = items.getJSONArray("item");

        for (int i = 0; i < itemsJSONArray.length(); i++) {
            JSONObject itemObj = itemsJSONArray.getJSONObject(i);
            String title = itemObj.getString("title");
            String url = itemObj.getString("url");

            System.out.println("title = " + title);
            System.out.println("``url`` = " + url);
        }

        return output;
    }
}
