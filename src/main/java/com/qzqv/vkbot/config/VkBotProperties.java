package com.qzqv.vkbot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Configuration
@Data
@PropertySource("bot.properties")
public class VkBotProperties {
    @Value("${vk.group.access-token}")
    private String accessToken;
    @Value("${vk.group.id}")
    private String groupId;
    @Value("${vk.api.version}")
    private String version;
    @Value("${vk.api.secret}")
    private String secret;
    @Value("${vk.api.verification}")
    private String verification;
}


