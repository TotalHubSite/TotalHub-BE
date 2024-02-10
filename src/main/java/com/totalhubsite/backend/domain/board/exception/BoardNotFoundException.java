package com.totalhubsite.backend.domain.board.exception;

import com.totalhubsite.backend.global.exception.ApplicationException;
import com.totalhubsite.backend.global.exception.ErrorCode;

public class BoardNotFoundException extends ApplicationException {

    private static final ErrorCode ERROR_CODE = ErrorCode.BOARD_NOT_FOUND;

    public BoardNotFoundException() {
        super(ERROR_CODE);
    }
}
