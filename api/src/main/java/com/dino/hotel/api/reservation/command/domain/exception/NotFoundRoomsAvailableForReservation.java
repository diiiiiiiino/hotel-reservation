package com.dino.hotel.api.reservation.command.domain.exception;

import com.dino.hotel.api.common.exception.ApplicationException;
import com.dino.hotel.api.common.http.response.ErrorCode;

import java.util.Map;

public class NotFoundRoomsAvailableForReservation extends ApplicationException {
    public NotFoundRoomsAvailableForReservation(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public NotFoundRoomsAvailableForReservation(String message, ErrorCode errorCode, Map<String, String> detail) {
        super(message, errorCode, detail);
    }
}
