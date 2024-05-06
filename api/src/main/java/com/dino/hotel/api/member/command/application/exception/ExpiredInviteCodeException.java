package com.dino.hotel.api.member.command.application.exception;

import com.dino.hotel.api.common.exception.ApplicationException;
import com.dino.hotel.api.common.http.response.ErrorCode;

/**
 * 초대코드 만료 예외
 */
public class ExpiredInviteCodeException extends ApplicationException {
    public ExpiredInviteCodeException(String message) {
        super(message, ErrorCode.INVITE_CODE_EXPIRED);
    }
}
