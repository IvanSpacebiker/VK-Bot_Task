package com.qzqv.vkbot.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CallbackClientInfoDto {
    private List<String> button_actions;
    private boolean keyboard;
    private boolean inline_keyboard;
    private boolean carousel;
    private int lang_id;
}
