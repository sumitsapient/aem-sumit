package com.aem.sumit.core.services.openai.impl;

import java.io.IOException;


import com.aem.sumit.core.chatgpt.SummaryBean;
import com.aem.sumit.core.services.openai.APIInvoker;
import com.aem.sumit.core.services.openai.ChatGptHttpClientFactory;
import com.aem.sumit.core.services.openai.JSONConverter;
import com.aem.sumit.core.utils.StringObjectResponseHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component(service = APIInvoker.class)
public class ChatGptAPIInvokerImpl implements APIInvoker {

    private static final StringObjectResponseHandler HANDLER = new StringObjectResponseHandler();

    @Reference
    private ChatGptHttpClientFactory httpClientFactory;

    @Reference
    private JSONConverter jsonConverter;

    ObjectMapper Obj = new ObjectMapper();

    @Override
    public String invokeAPI(String bodyText, int maxTokens) {
        String responseString = StringUtils.EMPTY;
        try {
            responseString = httpClientFactory.getExecutor()
                    .execute(httpClientFactory.post().bodyString(generatePromot(bodyText, maxTokens), ContentType.APPLICATION_JSON))
                    .handleResponse(HANDLER);
        } catch (IOException e) {
            log.error("Error occured while processing request {}", e.getMessage());
        }
        log.debug("API Request Response {}", responseString);
        return responseString;

    }

    private String generatePromot(String bodyText, int maxTokens) {
        SummaryBean bodyBean = new SummaryBean();
        if(maxTokens != 0) {
            bodyBean.setMaxTokens(maxTokens);
        }
        bodyBean.setPrompt(bodyText);
        return jsonConverter.convertToJsonString(bodyBean);
    }
}