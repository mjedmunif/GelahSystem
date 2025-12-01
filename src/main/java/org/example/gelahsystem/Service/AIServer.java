package org.example.gelahsystem.Service;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.JsonNode;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AIServer {

    @Value("${openai.api-key}")
    private String apiKey;

    public String getCampingSuggestions(String userMessage) {

        try {
            String prompt = "استخرج اسم المدينة من النص التالي: \"" +
                    userMessage +
                    "\". ثم اعطني أشهر أماكن الكشتات البرية في هذه المدينة داخل السعودية فقط واجب اجابه مختصره لا تطل وخل الرد يكون باللغه العاميه وصغيره ولا تذكر اسم المدينه بالرساله احفظه وجاوب بشكل مباشر.";

            // Build JSON safely
            JSONObject contentObj = new JSONObject();
            contentObj.put("role", "user");
            contentObj.put("content", prompt);

            JSONArray inputArray = new JSONArray();
            inputArray.put(contentObj);

            JSONObject body = new JSONObject();
            body.put("model", "gpt-4o-mini");
            body.put("input", inputArray);

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.openai.com/v1/responses"))
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("AI RAW RESPONSE = " + response.body());

            JSONObject obj = new JSONObject(response.body());

            return obj
                    .getJSONArray("output")
                    .getJSONObject(0)
                    .getJSONArray("content")
                    .getJSONObject(0)
                    .getString("text");

        } catch (Exception e) {
            return "AI Error: " + e.getMessage();
        }
    }
}