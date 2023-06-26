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
        String style = request.getParameter(OpenAIConstants.STYLE);
        String keyword = request.getParameter(OpenAIConstants.KEYWORD);

        Optional.ofNullable(prompt).ifPresent(p -> Optional.ofNullable(style).ifPresent(s -> {
            StringBuilder stringBuilder = new StringBuilder(p);

            switch (s.toLowerCase()) {
                case OpenAIConstants.SHORT:
                    stringBuilder.insert(0, OpenAIConstants.AI_SHORT_DESC);
                    break;
                case OpenAIConstants.DETAILED:
                    stringBuilder.insert(0, OpenAIConstants.AI_DETAILED_DESC);
                    break;
                default:
                    // Handle unrecognized style if needed
                    break;
            }

            String message = null;
            try {
                message = generateMessage(openAIConfig, stringBuilder.toString());
            } catch (IOException e) {
                log.error("Error occurred while generating summary", e);
            }

            if (message != null) {
                int lastIndex = message.lastIndexOf(".");
                String output = message.substring(0, lastIndex + 1);
                try {
                    writeResponse(response, output);
                } catch (IOException e) {
                    log.error("Error occurred while generating response for summary", e);
                }
            }
        }));


        Optional.ofNullable(keyword).filter(k -> k.equalsIgnoreCase("true"))
                .ifPresent(k -> Optional.ofNullable(prompt)
                        .ifPresent(p -> {
                            StringBuilder keyPrompt = new StringBuilder(p);
                            keyPrompt.insert(0, OpenAIConstants.AI_KEYWORDS);
                            String message = null;
                            try {
                                message = generateMessage(openAIConfig, keyPrompt.toString());
                            } catch (IOException e) {
                                log.error("Error occurred while generating keyword",e);
                            }
                            try {
                                writeResponse(response, message);
                            } catch (IOException e) {
                                log.error("Error occurred while writing response for keyword",e);
                            }
                        }));
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
