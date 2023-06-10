package org.shu.core;

import org.apache.commons.text.StringEscapeUtils;
import org.apache.log4j.LogManager;
import org.shu.utils.HTTPClient;

import java.util.HashMap;
import java.util.Map;

public class ChatGPT {
    static {
        Log4j.init();
    }

    private static org.apache.log4j.Logger logger = LogManager.getLogger(Commander.class.getName());
    private static String API_ENDPOINT;
    private static String API_TOKEN;
    private static String PROMPT;
    private static String MODEL;

    private final int MAX_TOKENS = 3500;


    public ChatGPT(String apiEndpoint, String apiToken, String prompt, String model) {
        API_ENDPOINT = apiEndpoint;
        API_TOKEN = apiToken;
        PROMPT = prompt;
        MODEL = model;
    }

    public static void setApiEndpoint(String apiEndpoint) {
        API_ENDPOINT = apiEndpoint;
    }

    public static void setApiToken(String apiToken) {
        API_TOKEN = apiToken;
    }

    public static void setPrompt(String prompt) {
        PROMPT = prompt;
    }

    public static void setModel(String model) {
        MODEL = model;
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
            logger.info("[REQUEST] calling" + String.format("%s/v1/chat/completions", API_ENDPOINT));
            logger.info("[REQUEST] with headers " + headers);
            logger.info("[REQUEST] with body " + body);
            response = HTTPClient.post(String.format("%s/v1/chat/completions", API_ENDPOINT), body, headers);
        } catch (Exception e) {
            logger.error("Error occured in ask(..):" + e);
        }

        logger.info("[RESPONSE] " + response);
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
