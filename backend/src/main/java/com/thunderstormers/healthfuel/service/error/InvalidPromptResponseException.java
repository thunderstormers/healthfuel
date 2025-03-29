package com.thunderstormers.healthfuel.service.error;

import com.thunderstormers.healthfuel.error.BaseException;

public class InvalidPromptResponseException extends BaseException {

    public InvalidPromptResponseException(String message) {
        super(message);
    }

}
