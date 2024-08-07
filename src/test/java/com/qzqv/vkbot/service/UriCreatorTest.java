package com.qzqv.vkbot.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qzqv.vkbot.config.VkBotProperties;
import com.qzqv.vkbot.dto.MessagesSendDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.net.URI;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class UriCreatorTest {

    @Autowired
    private VkUriCreator vkUriCreator;

    @Configuration
    static class ContextConfiguration {
        @Bean
        public VkBotProperties vkBotProperties() {
            VkBotProperties properties = new VkBotProperties();
            properties.setAccessToken("access123");
            properties.setVersion("1.0");
            return properties;
        }

        @Bean
        public ObjectMapper objectMapper() {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            return mapper;
        }

        @Bean
        public VkUriCreator vkUriCreator(VkBotProperties properties,
                                         ObjectMapper mapper) {
            return new VkUriCreator(properties, mapper);
        }
    }

    @Test
    public void createUriWithParams() {
        String string = "https://api.vk.com/method/messages.send?access_token=access123&v=1.0&group_id=1&message=msg&user_id=2";
        MessagesSendDto dto = MessagesSendDto.builder()
                .userId(2L)
                .message("msg")
                .groupId(1L)
                .build();
        URI uri = vkUriCreator.createUri(dto);
        assertEquals(string, uri.toString());
    }

    @Test
    public void createUriWithoutParams() {
        String string = "https://api.vk.com/method/messages.send?access_token=access123&v=1.0";
        MessagesSendDto dto = MessagesSendDto.builder().build();
        URI uri = vkUriCreator.createUri(dto);
        assertEquals(string, uri.toString());
    }
}
