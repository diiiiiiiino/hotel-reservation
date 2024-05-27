package com.dino.hotel.api.reservation.command.domain.exception;

import com.dino.hotel.api.common.exception.ApplicationException;
import com.dino.hotel.api.common.http.response.ErrorCode;

import java.util.Map;

public class NoRoomsAvailableForReservation extends ApplicationException {
    public NoRoomsAvailableForReservation(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public NoRoomsAvailableForReservation(String message, ErrorCode errorCode, Map<String, String> detail) {
        super(message, errorCode, detail);
    }
}
