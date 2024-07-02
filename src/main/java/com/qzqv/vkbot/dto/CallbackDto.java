package com.qzqv.vkbot.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CallbackDto {
    @JsonProperty(value = "type")
    private CallbackType type;
    @JsonProperty(value = "object")
    private CallbackObjectDto object;
    @JsonProperty(value = "group_id")
    private Long groupId;
    @JsonProperty(value = "secret")
    private String secret;
    @JsonProperty(value = "event_id")
    private String eventId;

    public enum CallbackType {
        message_new, confirmation
    }
}
