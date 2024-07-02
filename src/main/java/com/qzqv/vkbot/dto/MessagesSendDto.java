package com.qzqv.vkbot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Builder
@Getter
@Setter
@JsonPropertyOrder(alphabetic = true)
@EqualsAndHashCode(exclude = "randomId")
public class MessagesSendDto implements Serializable, Cloneable {

    @JsonProperty(value = "user_id")
    Long userId;
    @JsonProperty(value = "random_id")
    Long randomId;
    @JsonProperty(value = "peer_id")
    Long peerId;
    @JsonProperty(value = "chat_id")
    Long chatId;
    @JsonProperty(value = "user_ids")
    List<Long> userIds;
    @JsonProperty(value = "message")
    String message;
    @JsonProperty(value = "group_id")
    Long groupId;

    @Override
    public MessagesSendDto clone() {
        try {
            return (MessagesSendDto) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
