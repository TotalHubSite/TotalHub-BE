package com.totalhubsite.backend.domain.member.exception;

import com.totalhubsite.backend.global.exception.ApplicationException;
import com.totalhubsite.backend.global.exception.ErrorCode;

public class PermissionDeniedException extends ApplicationException {

    private static final ErrorCode ERROR_CODE = ErrorCode.PERMISSION_DENIED;

    public PermissionDeniedException() {
        super(ERROR_CODE);
    }
}
