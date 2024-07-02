package com.qzqv.vkbot.service;

import com.qzqv.vkbot.dto.MessagesSendDto;
import com.qzqv.vkbot.dto.MessagesSendErrorResultDto;
import com.qzqv.vkbot.dto.MessagesSendResultDto;
import com.qzqv.vkbot.util.exception.MessageSenderException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MessageSenderServiceImpl implements MessageSenderService<MessagesSendDto> {
    private static final int TEXT_MAX_LENGTH = 4096;
    private final RestTemplate restTemplate;
    private final VkUriCreator vkUriCreator;

    @Override
    public void sendMessage(MessagesSendDto message) {
        List<MessagesSendDto> messages = parseIfRequired(message);
        messages.forEach(this::sendInternal);
    }

    private List<MessagesSendDto> parseIfRequired(MessagesSendDto dto) {
        String originalMessage = dto.getMessage();
        int capacity = originalMessage.length() / TEXT_MAX_LENGTH + 1;
        ArrayList<MessagesSendDto> result = new ArrayList<>(capacity);
        for (int i = 0; i < capacity; i++) {
            int beginIndex = i * TEXT_MAX_LENGTH;
            int endIndex = Math.min((i + 1) * TEXT_MAX_LENGTH, originalMessage.length());
            result.add(copyWithNewMessage(dto, originalMessage.substring(beginIndex, endIndex)));
        }
        return result;
    }

    private static MessagesSendDto copyWithNewMessage(MessagesSendDto dto, String message) {
        MessagesSendDto copy = dto.clone();
        copy.setMessage(message);
        return copy;
    }

    private void sendInternal(MessagesSendDto message) {
        message.setRandomId((long) message.hashCode());
        URI uri = vkUriCreator.createUri(message);
        ResponseEntity<MessagesSendResultDto> responseEntity = restTemplate
                .postForEntity(uri, null, MessagesSendResultDto.class);
        validateResponse(responseEntity);
    }

    private void validateResponse(ResponseEntity<MessagesSendResultDto> responseEntity) {
        MessagesSendErrorResultDto error = Objects.requireNonNull(responseEntity.getBody()).getError();
        if (error != null && error.getErrorCode() != null) {
            throw new MessageSenderException(error.getErrorMsg());
        }
    }

}
