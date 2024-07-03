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
    private static final int TEXT_MAX_LENGTH = 1000;
    private final RestTemplate restTemplate;
    private final VkUriCreator vkUriCreator;

    @Override
    public void sendMessage(MessagesSendDto message) {
        List<MessagesSendDto> messages = parseIfRequired(message);
        messages.forEach(this::sendInternal);
    }

    private List<MessagesSendDto> parseIfRequired(MessagesSendDto dto) {
        String originalMessage = dto.getMessage();
        List<MessagesSendDto> result = new ArrayList<>();
        int start = 0;
        while (start < originalMessage.length()) {
            int end = Math.min(start + TEXT_MAX_LENGTH, originalMessage.length());

            if (end < originalMessage.length() && originalMessage.charAt(end) != ' ') {
                int lastSpace = originalMessage.lastIndexOf(' ', end);
                if (lastSpace > start) {
                    end = lastSpace;
                }
            }

            result.add(copyWithNewMessage(dto, originalMessage.substring(start, end).trim()));
            start = end + 1;
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
