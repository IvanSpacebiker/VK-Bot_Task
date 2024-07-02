package com.qzqv.vkbot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qzqv.vkbot.config.VkBotProperties;
import com.qzqv.vkbot.dto.MessagesSendDto;
import com.qzqv.vkbot.util.exception.MessageSenderException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class VkUriCreator {
    private final VkBotProperties vkBotProperties;
    private final ObjectMapper objectMapper;

    public URI createUri(MessagesSendDto dto) {
        try {
            MultiValueMap<String, String> map = objectMapper.convertValue(dto, LinkedMultiValueMap.class);
            return UriComponentsBuilder.fromHttpUrl("https://api.vk.com/method/messages.send")
                    .queryParam("access_token", vkBotProperties.getAccessToken())
                    .queryParam("v", vkBotProperties.getVersion())
                    .queryParams(map)
                    .build()
                    .toUri();
        } catch (ClassCastException e) {
            throw new MessageSenderException(e);
        }
    }
}
