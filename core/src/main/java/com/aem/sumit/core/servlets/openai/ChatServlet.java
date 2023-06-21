package com.aem.sumit.core.servlets.openai;

import com.aem.sumit.core.chatgpt.ChatGptRequest;
import com.aem.sumit.core.chatgpt.ChatGptResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.logging.Logger;

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
public class ChatServlet extends SlingSafeMethodsServlet {
    private static final Logger LOGGER = Logger.getLogger(ChatServlet.class.getName());

    //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
    private static final String CHATGPT_API_ENDPOINT = "https://api.openai.com/v1/chat/completions";

    private static final HttpClient client = HttpClients.createDefault();
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException, IOException {
        String prompt = request.getParameter("prompt");
        String message = generateMessage(prompt);
        response.getWriter().write(message);
    }
    private static String generateMessage(String prompt) throws IOException {

        // Generate the chat message using ChatGPT API
        String requestBody = MAPPER.writeValueAsString(new ChatGptRequest(prompt,"gpt-3.5-turbo","user"));
        HttpPost request = new HttpPost(CHATGPT_API_ENDPOINT);
        request.addHeader("Authorization", "Bearer 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");
        request.addHeader("Content-Type", "application/json");
        request.setEntity(new StringEntity(requestBody));
        HttpResponse response = client.execute(request);
        String message = null;

        if (response.getStatusLine().getStatusCode() != 200) {
            message = "Sorry! ChatGPT server is busy. Please try after some time";
        }
        else {
            ChatGptResponse chatGptResponse = MAPPER.readValue(EntityUtils.toString(response.getEntity()), ChatGptResponse.class);
            message = chatGptResponse.getChoices().get(0).getMessage().getContent();
        }

        return message;

    }

    public static void main(String[] args) {
        try {
            System.out.println(generateMessage("What is Adobe AEM"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
