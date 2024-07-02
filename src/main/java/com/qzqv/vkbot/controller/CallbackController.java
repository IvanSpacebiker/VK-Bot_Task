package com.qzqv.vkbot.controller;

import com.qzqv.vkbot.dto.CallbackDto;
import com.qzqv.vkbot.service.CallbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class CallbackController {
    private final CallbackService callbackService;

    @PostMapping(value = "/callbacks")
    @ResponseBody
    public ResponseEntity<String> handleCallback(@RequestBody CallbackDto callbackDto) {
        return new ResponseEntity<>(callbackService.handleCallback(callbackDto), HttpStatus.OK);
    }
}
