package org.example.gelahsystem.Service;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WhatsAppService {

    private final String INSTANCE_URL = "https://api.ultramsg.com/instance153263/messages/chat";
    private final String TOKEN = "y655goujvn72sxbn";

    public String sendMessage(String to , String body){
        Unirest.config().socketTimeout(0).connectTimeout(0);
        HttpResponse<String> response = Unirest.post(INSTANCE_URL)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .field("token", TOKEN)
                .field("to", to)
                .field("body",body)
                .asString();

        return response.getBody();
    }


}
