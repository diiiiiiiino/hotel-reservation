package com.dino.hotel.api.member.command.domain.exception;

import com.dino.hotel.api.common.exception.ApplicationException;
import com.dino.hotel.api.common.http.response.ErrorCode;

/**
 * 비밀번호 정책 위반 예외
 */
public class PasswordOutOfConditionException extends ApplicationException {
    public PasswordOutOfConditionException(String message) {
        super(message, ErrorCode.PASSWORD_OUT_OF_CONDITION);
    }
}
