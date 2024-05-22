package com.dino.hotel.api.hotel.command.domain.exception;


import com.dino.hotel.api.common.exception.ApplicationException;
import com.dino.hotel.api.common.http.response.ErrorCode;

/**
 * Hotel 미조회 예외
 */
public class HotelNotFoundException extends ApplicationException {
    public HotelNotFoundException(String message) {
        super(message, ErrorCode.HOTEL_NOT_FOUND);
    }
}
