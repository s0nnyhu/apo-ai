package org.shu.core;

import io.github.cdimascio.dotenv.Dotenv;
import org.apache.commons.text.StringEscapeUtils;
import org.shu.utils.HTTPClient;

import java.util.HashMap;
import java.util.Map;

public class ChatGPT {
    Dotenv dotenv = Dotenv.load();
    private String API_ENDPOINT = dotenv.get("API_ENDPOINT_CHATGPT");
    private String API_TOKEN = dotenv.get("API_TOKEN_CHATGPT");
    private String PROMPT = "You are an helpful assistant.";
    private String MODEL = "gpt-3.5-turbo";

    private final int MAX_TOKENS = 3500;

    public void setPrompt(String prompt) {
    }

    public void setModel(String model) {
        model = model;
    }

    public String ask(String text) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", String.format("Bearer %s", API_TOKEN));
        headers.put("Content-Type", "application/json");

        String bb = "{\"model\":\"gpt-3.5-turbo\"}";
        String body = getCompletionsTemplateBody();

        text = StringEscapeUtils.escapeJava(text);

        body = body.replace("%CONTENT%", text);

        String response = null;
        try {
            response = HTTPClient.post(String.format("%s/v1/chat/completions", API_ENDPOINT), body, headers);
        } catch (Exception e) {
            System.out.println("[ChatGPT] Error occured " + e);
        }
        return response;
    }

    public String getCompletionsTemplateBody() {
        return "{\n" +
                "    \"model\": \"gpt-3.5-turbo\",\n" +
                "    \"max_tokens\": 1000,\n" +
                "    \"messages\": [\n" +
                "        {\n" +
                "            \"role\": \"system\",\n" +
                "            \"content\": \"You are an assistant\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"role\": \"user\",\n" +
                "            \"content\": \"%CONTENT%\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
    }
}
