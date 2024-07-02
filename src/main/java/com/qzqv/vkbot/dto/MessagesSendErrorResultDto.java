package com.qzqv.vkbot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class MessagesSendErrorResultDto {
    @JsonProperty(value = "error_code")
    Long errorCode;
    @JsonProperty(value = "error_msg")
    String errorMsg;
}
