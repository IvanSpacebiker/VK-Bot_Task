package com.qzqv.vkbot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MessagesSendResultDto {
    @JsonProperty(value = "peer_id")
    Long peerId;
    @JsonProperty(value = "message_id")
    Long messageId;
    MessagesSendErrorResultDto error;
}
