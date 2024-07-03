package com.qzqv.vkbot.service;

import com.qzqv.vkbot.config.VkBotProperties;
import com.qzqv.vkbot.dto.CallbackDto;
import com.qzqv.vkbot.dto.CallbackMessageDto;
import com.qzqv.vkbot.dto.CallbackObjectDto;
import com.qzqv.vkbot.dto.MessagesSendDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.security.InvalidParameterException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class CallbackServiceTest {
    private static final String SECRET = "secret123";
    @Autowired
    private MessageSenderService messageSenderService;
    @Autowired
    private CallbackService callbackService;

    @Configuration
    static class ContextConfiguration {
        @Bean
        public VkBotProperties properties() {
            VkBotProperties properties = new VkBotProperties();
            properties.setSecret(SECRET);
            return properties;
        }

        @Bean
        public MessageSenderService messageSenderService() {
            return Mockito.mock(MessageSenderService.class);
        }

        @Bean
        public CallbackService callbackService(VkBotProperties properties,
                                               MessageSenderService<MessagesSendDto> messageSenderService) {
            return new CallbackServiceImpl(properties, messageSenderService);
        }
    }

    @Test(expected = NullPointerException.class)
    public void handleCallbackWithoutDto() {
        callbackService.handleCallback(null);
    }

    @Test(expected = InvalidParameterException.class)
    public void handleCallbackWithoutSecret() {
        CallbackDto empty = CallbackDto.builder().build();
        callbackService.handleCallback(empty);
    }

    @Test(expected = InvalidParameterException.class)
    public void handleCallbackWithInvalidSecret() {
        CallbackDto invalid = CallbackDto.builder()
                .secret("")
                .build();
        callbackService.handleCallback(invalid);
    }

    @Test(expected = NullPointerException.class)
    public void handleUnsupportedTypeCallback() {
        CallbackDto unsupported = CallbackDto.builder()
                .secret(SECRET)
                .type(null)
                .build();
        callbackService.handleCallback(unsupported);
    }

    @Test
    public void handleValidCallback() {
        CallbackMessageDto callbackMessageDto = CallbackMessageDto.builder()
                .id(0)
                .date(1)
                .peer_id(2)
                .from_id(3)
                .text("Some text")
                .build();
        CallbackObjectDto callbackObjectDto = CallbackObjectDto.builder()
                .message(callbackMessageDto)
                .build();
        CallbackDto validDto = CallbackDto.builder()
                .secret(SECRET)
                .type(CallbackDto.CallbackType.message_new)
                .object(callbackObjectDto)
                .build();
        callbackService.handleCallback(validDto);
        verify(messageSenderService, times(1)).sendMessage(any());
    }
}
