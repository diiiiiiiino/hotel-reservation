package com.dino.hotel.api.authority.command.application.exception;

import com.dino.hotel.api.common.exception.ApplicationException;
import com.dino.hotel.api.common.http.response.ErrorCode;

/**
 * 권한 미조회 예외
 */
public class AuthorityNotFoundException extends ApplicationException {
    public AuthorityNotFoundException(String message) {
        super(message, ErrorCode.AUTHORITY_NOT_FOUND);
    }
}
