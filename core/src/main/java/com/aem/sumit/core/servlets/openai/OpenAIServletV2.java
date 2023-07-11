package com.aem.sumit.core.servlets.openai;

import com.aem.sumit.core.chatgpt.ChatGptRequest;
import com.aem.sumit.core.chatgpt.ChatGptResponse;
import com.aem.sumit.core.constants.OpenAIConstants;
import com.aem.sumit.core.dto.AIRequest;
import com.aem.sumit.core.services.openai.OpenAIConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.aem.sumit.core.constants.OpenAIConstants.CONTENT_TYPE_JSON;

@Slf4j
@Component(
        immediate = true,
        service = Servlet.class,
        property = {
                Constants.SERVICE_DESCRIPTION + "=ChatGPT Integration",
                "sling.servlet.methods=" + HttpConstants.METHOD_GET,
                "sling.servlet.paths=" + "/bin/openai",
                "sling.servlet.extensions={\"json\"}"
        }
)
public class OpenAIServletV2 extends SlingSafeMethodsServlet {
    @Reference
    private OpenAIConfig openAIConfig;
    private static final HttpClient client = HttpClients.createDefault();
    private static final ObjectMapper MAPPER = new ObjectMapper();
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        String prompt = request.getParameter(OpenAIConstants.PAYLOAD);
        String keyword = request.getParameter(OpenAIConstants.KEYWORD);
        String summary = request.getParameter(OpenAIConstants.SUMMARY);


        if(Objects.nonNull(prompt)) {
            AIRequest aiRequest = MAPPER.readValue(prompt,AIRequest.class);
            String message = concatenateWithDot(aiRequest.getMessage());
            StringBuilder query = new StringBuilder(message);
            String result = null;
            String descPrefix = "";
            if(aiRequest.type.equalsIgnoreCase(OpenAIConstants.DEFAULT)) {
                descPrefix = OpenAIConstants.AI_DETAIL_DESC;
            }
            else if(aiRequest.type.equalsIgnoreCase(OpenAIConstants.CUSTOM)) {
                descPrefix = OpenAIConstants.AI_CUSTOM_DESC+aiRequest.count+OpenAIConstants.WORDS;
            }
            query.insert(0, descPrefix);
            result = generateMessage(openAIConfig, query.toString());
            if (Objects.nonNull(result) && !result.isEmpty()) {
                int lastIndex = result.lastIndexOf(".");
                String output = (lastIndex != -1) ? result.substring(0, lastIndex + 1) : result;
                try {
                    writeResponse(response, output);
                } catch (IOException e) {
                    log.error("Error occurred while generating response", e);
                }
            }
        }
        if(Objects.nonNull(keyword)){
            StringBuilder query = new StringBuilder(keyword);
            query.insert(0, OpenAIConstants.AI_KEYWORDS);
            String result = generateMessage(openAIConfig, query.toString());
            writeResponse(response, result);

        }
        if(Objects.nonNull(summary)){
            StringBuilder query = new StringBuilder(summary);
            query.insert(0, OpenAIConstants.AI_SUMMARY);
            String result = generateMessage(openAIConfig, query.toString());
            writeResponse(response, result);

        }



    }

    private void writeResponse(SlingHttpServletResponse response, String message) throws IOException {
        try (PrintWriter writer = response.getWriter()) {
            writer.write(message);
        }
    }

    public static String concatenateWithDot(List<String> inputList) {
        List<String> modifiedList = inputList.stream()
                .map(s -> s.endsWith(".") ? s : s + ".")
                .collect(Collectors.toList());

        return String.join("", modifiedList);
    }

    private static String generateMessage(OpenAIConfig openAIConfig, String prompt) throws IOException {
        String requestBody = MAPPER.writeValueAsString(new ChatGptRequest(prompt, "gpt-3.5-turbo", "user"));
        HttpPost request = new HttpPost(openAIConfig.getHostName() + openAIConfig.getUriType());
        request.addHeader("Authorization", "Bearer " + openAIConfig.getApiKey());
        request.addHeader("Content-Type", CONTENT_TYPE_JSON);
        request.setEntity(new StringEntity(requestBody));
        HttpResponse response = client.execute(request);
        if (response.getStatusLine().getStatusCode() != 200) {
            return "Sorry! OpenAI server is busy. Please try again";
        } else {
            ChatGptResponse chatGptResponse = MAPPER.readValue(EntityUtils.toString(response.getEntity()), ChatGptResponse.class);
            return chatGptResponse.getChoices().get(0).getMessage().getContent();
        }

    }
}
