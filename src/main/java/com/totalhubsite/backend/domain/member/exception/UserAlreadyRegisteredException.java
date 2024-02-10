package com.totalhubsite.backend.domain.member.exception;

import com.totalhubsite.backend.global.exception.ApplicationException;
import com.totalhubsite.backend.global.exception.ErrorCode;

public class UserAlreadyRegisteredException extends ApplicationException {
    
    private static final ErrorCode ERROR_CODE = ErrorCode.USER_ALREADY_REGISTERED;

    public UserAlreadyRegisteredException() {
        super(ERROR_CODE);
    }

}
