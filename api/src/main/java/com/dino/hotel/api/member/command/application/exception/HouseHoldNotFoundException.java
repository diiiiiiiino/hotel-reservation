package com.dino.hotel.api.member.command.application.exception;

import com.dino.hotel.api.common.exception.ApplicationException;
import com.dino.hotel.api.common.http.response.ErrorCode;

/**
 * 세대 미조회 예외
 */
public class HouseHoldNotFoundException extends ApplicationException {
    public HouseHoldNotFoundException(String message) {
        super(message, ErrorCode.HOUSEHOLD_NOT_FOUND);
    }
}
