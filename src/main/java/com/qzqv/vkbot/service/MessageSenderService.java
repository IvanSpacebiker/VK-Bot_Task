package com.qzqv.vkbot.service;

public interface MessageSenderService<T> {
    void sendMessage(T message);
}
