package com.qzqv.vkbot.service;

import com.qzqv.vkbot.dto.CallbackDto;

public interface CallbackService {
    String handleCallback(CallbackDto callbackDto);
}
