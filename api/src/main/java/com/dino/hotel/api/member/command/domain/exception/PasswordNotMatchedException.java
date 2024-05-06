package com.dino.hotel.api.member.command.domain.exception;

import com.dino.hotel.api.common.exception.ApplicationException;
import com.dino.hotel.api.common.http.response.ErrorCode;

/**
 * 비밀번호 불일치 예외
 */
public class PasswordNotMatchedException extends ApplicationException {
    public PasswordNotMatchedException(String message) {
        super(message, ErrorCode.PASSWORD_NOT_MATCHED);
    }
}
