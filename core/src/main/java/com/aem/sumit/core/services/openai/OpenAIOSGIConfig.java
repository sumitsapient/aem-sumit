package com.aem.sumit.core.services.openai;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "OpenAI API Configuration", description = "OpenAI Client API Configuration")
public @interface OpenAIOSGIConfig {

    @AttributeDefinition(name = "API Host Name", description = "API host name, e.g. https://example.com", type = AttributeType.STRING)
    String hostName() default "https://api.openai.com";

    @AttributeDefinition(name = "API URI Type Path", description = "API URI type path, e.g. /services/int/v2", type = AttributeType.STRING)
    String uriType() default "/v1/chat/completions";

    @AttributeDefinition(name = "API Key", description = "Chat GPT API Key", type = AttributeType.STRING)
    String apiKey() default "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";
}
