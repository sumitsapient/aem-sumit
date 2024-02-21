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

    @AttributeDefinition(name = "Messages Author Role", description = "The role of the messages author. One of system, user, assistant, or function.", type = AttributeType.STRING)
    String role() default "user";

    @AttributeDefinition(name = "Model ID", description = "ID of the model to use.", type = AttributeType.STRING)
    String model() default "gpt-3.5-turbo";

    @AttributeDefinition(name = "Image Generation API", description = "Endpoint used for generating images.", type = AttributeType.STRING)
    String imageGenerationAPI() default "https://api.openai.com/v1/images/generations";

    @AttributeDefinition(name = "API Key", description = "Chat GPT API Key", type = AttributeType.STRING)
    String apiKey() default "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";
}
