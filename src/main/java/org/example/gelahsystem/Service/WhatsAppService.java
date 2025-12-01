package org.example.gelahsystem.Service;

import jakarta.annotation.PostConstruct;
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
        HttpResponse<String> response = Unirest.post(INSTANCE_URL)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .field("token", TOKEN)
                .field("to", to)
                .field("body",body)
                .asString();

        return response.getBody();
    }

        @PostConstruct
        public void init() {
            Unirest.config().reset();
            Unirest.config()
                    .socketTimeout(5000)
                    .connectTimeout(5000)
                    .verifySsl(false);
        }

}
