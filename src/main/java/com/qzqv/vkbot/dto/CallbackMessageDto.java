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
public class CallbackMessageDto {
    private long date;
    private long from_id;
    private int id;
    private int out;
    private int version;
    private List<Object> attachments;
    private int conversation_message_id;
    private List<Object> fwd_messages;
    private boolean important;
    private boolean is_hidden;
    private long peer_id;
    private int random_id;
    private String text;
}
