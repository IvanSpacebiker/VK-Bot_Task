package com.qzqv.vkbot.service;

import com.qzqv.vkbot.config.VkBotProperties;
import com.qzqv.vkbot.dto.CallbackDto;
import com.qzqv.vkbot.dto.CallbackObjectDto;
import com.qzqv.vkbot.dto.CallbackMessageDto;
import com.qzqv.vkbot.dto.MessagesSendDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CallbackServiceImpl implements CallbackService {
    private final VkBotProperties vkBotProperties;
    private final MessageSenderService<MessagesSendDto> messageSenderService;

    @Override
    public String handleCallback(CallbackDto callbackDto) {
        switch (Objects.requireNonNull(callbackDto.getType())) {
            case message_new: {
                validateSecret(callbackDto);
                handleMessageNew(callbackDto);
                return "ok";
            }
            case confirmation: {
                return vkBotProperties.getVerification();
            }
            default: {
                throw new UnsupportedOperationException("Service support only 'message_new' callback type");
            }
        }
    }

    private void validateSecret(CallbackDto callbackDto) {
        if (!vkBotProperties.getSecret().equals(callbackDto.getSecret())) {
            throw new InvalidParameterException("Invalid secret");
        }
    }

    private void handleMessageNew(CallbackDto callbackDto) {
        CallbackObjectDto objectDto = callbackDto.getObject();
        CallbackMessageDto callbackMessageDto = objectDto.getMessage();
        MessagesSendDto dto = MessagesSendDto.builder()
                .peerId(callbackMessageDto.getPeer_id())
                .message("Вы сказали: ".concat(callbackMessageDto.getText()))
                .groupId(callbackDto.getGroupId())
                .build();
        messageSenderService.sendMessage(dto);
    }

}
