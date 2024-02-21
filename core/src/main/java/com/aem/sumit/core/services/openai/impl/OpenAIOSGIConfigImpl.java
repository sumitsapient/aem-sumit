package com.aem.sumit.core.services.openai.impl;

import com.aem.sumit.core.services.openai.OpenAIConfig;
import com.aem.sumit.core.services.openai.OpenAIOSGIConfig;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;

@Component(immediate = true,service = OpenAIConfig.class)
@Designate(ocd= OpenAIOSGIConfig.class)
public class OpenAIOSGIConfigImpl implements OpenAIConfig{

    private String host;

    private String uri;

    private String secret;

    private String role;

    private String model;

    private String imageGenerateAPI;


    @Activate
    protected void activate(OpenAIOSGIConfig config) {
        host = config.hostName();
        uri = config.uriType();
        secret = config.apiKey();
        role = config.role();
        model = config.role();
        imageGenerateAPI = config.imageGenerationAPI();
    }

    @Override
    public String getHostName() {
        return host;
    }

    @Override
    public String getUriType() {
        return uri;
    }

    @Override
    public String getApiKey() {
        return secret;
    }

    @Override
    public String getRole() { return role; }

    @Override
    public String getModel() { return model; }

    @Override
    public String getImageGenerationAPI() { return imageGenerateAPI;}
}
