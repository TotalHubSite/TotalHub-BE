package com.totalhubsite.backend.global.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class ApplicationException extends RuntimeException{

    private final ErrorCode errorCode;

    protected ApplicationException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    protected ApplicationException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        if (super.getMessage() == null) {
            return errorCode.getMessage();
        }

        return super.getMessage();
    }


}
