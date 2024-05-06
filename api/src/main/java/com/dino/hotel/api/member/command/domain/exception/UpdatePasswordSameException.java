package com.dino.hotel.api.member.command.domain.exception;


import com.dino.hotel.api.common.exception.ApplicationException;
import com.dino.hotel.api.common.http.response.ErrorCode;

/**
 * 기존 비밀번호화 변경할 비밀번호가 동일한 예외
 */
public class UpdatePasswordSameException extends ApplicationException {
    public UpdatePasswordSameException(String message) {
        super(message, ErrorCode.UPDATE_PASSWORD_SAME);
    }
}
