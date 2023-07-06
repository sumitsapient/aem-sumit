package com.aem.sumit.core.servlets.openai;

import com.aem.sumit.core.chatgpt.ChatGptRequest;
import com.aem.sumit.core.chatgpt.ChatGptResponse;
import com.aem.sumit.core.constants.OpenAIConstants;
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
import java.util.Objects;
import java.util.Optional;

import static com.aem.sumit.core.constants.OpenAIConstants.CONTENT_TYPE_JSON;

@Slf4j
@Component(
        immediate = true,
        service = Servlet.class,
        property = {
                Constants.SERVICE_DESCRIPTION + "=ChatGPT Integration",
                "sling.servlet.methods=" + HttpConstants.METHOD_GET,
                "sling.servlet.paths=" + "/bin/chat",
                "sling.servlet.extensions={\"json\"}"
        }
)
public class OpenAIServlet extends SlingSafeMethodsServlet {
    @Reference
    private OpenAIConfig openAIConfig;
    private static final HttpClient client = HttpClients.createDefault();
    private static final ObjectMapper MAPPER = new ObjectMapper();
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        String prompt = request.getParameter(OpenAIConstants.PROMPT);
        String type =  request.getParameter(OpenAIConstants.TYPE);
        String marker =  request.getParameter(OpenAIConstants.MARKER);
       // String keyword = request.getParameter(OpenAIConstants.KEYWORD);

        Optional.ofNullable(prompt).filter(p -> Objects.nonNull(marker) && Objects.nonNull(type)).ifPresent(p -> {
            StringBuilder stringBuilder = new StringBuilder(p);
            String message = null;
            String descPrefix = "";

            if (type.equalsIgnoreCase(OpenAIConstants.SHORT)) {
                if (marker.equalsIgnoreCase(OpenAIConstants.WARMTH)) {
                    descPrefix = OpenAIConstants.AI_SHORT_DESC_WARMTH;
                } else if (marker.equalsIgnoreCase(OpenAIConstants.AGGRESSIVE)) {
                    descPrefix = OpenAIConstants.AI_SHORT_DESC_AGGRESSIVE;
                } else if (marker.equalsIgnoreCase(OpenAIConstants.FORMAL)) {
                    descPrefix = OpenAIConstants.AI_SHORT_DESC_FORMAL;
                }
            } else if (type.equalsIgnoreCase(OpenAIConstants.DETAILED)) {
                if (marker.equalsIgnoreCase(OpenAIConstants.WARMTH)) {
                    descPrefix = OpenAIConstants.AI_DETAILED_DESC_WARMTH;
                } else if (marker.equalsIgnoreCase(OpenAIConstants.AGGRESSIVE)) {
                    descPrefix = OpenAIConstants.AI_DETAILED_DESC_AGGRESSIVE;
                } else if (marker.equalsIgnoreCase(OpenAIConstants.FORMAL)) {
                    descPrefix = OpenAIConstants.AI_DETAILED_DESC_FORMAL;
                }
            }

            stringBuilder.insert(0, descPrefix);

            try {
                message = generateMessage(openAIConfig, stringBuilder.toString());
            } catch (IOException e) {
                log.error("Error occurred while generating message", e);
            }

            if (Objects.nonNull(message) && !message.isEmpty()) {
                int lastIndex = message.lastIndexOf(".");
                String output = (lastIndex != -1) ? message.substring(0, lastIndex + 1) : message;
                try {
                    writeResponse(response, output);
                } catch (IOException e) {
                    log.error("Error occurred while generating response", e);
                }
            }
        });


    }

    private void writeResponse(SlingHttpServletResponse response, String message) throws IOException {
        try (PrintWriter writer = response.getWriter()) {
            writer.write(message);
        }
    }

    private static String generateMessage(OpenAIConfig openAIConfig, String prompt) throws IOException {
        String requestBody = MAPPER.writeValueAsString(new ChatGptRequest(prompt, "gpt-3.5-turbo", "user"));
        HttpPost request = new HttpPost(openAIConfig.getHostName() + openAIConfig.getUriType());
        request.addHeader("Authorization", "Bearer " + openAIConfig.getApiKey());
        request.addHeader("Content-Type", CONTENT_TYPE_JSON);
        request.setEntity(new StringEntity(requestBody));
        HttpResponse response = client.execute(request);
        if (response.getStatusLine().getStatusCode() != 200) {
            return "Sorry! ChatGPT server is busy. Please try after some time";
        } else {
            ChatGptResponse chatGptResponse = MAPPER.readValue(EntityUtils.toString(response.getEntity()), ChatGptResponse.class);
            return chatGptResponse.getChoices().get(0).getMessage().getContent();
        }

    }
}
