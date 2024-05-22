package com.dino.hotel.api.room.command.domain.exception;

import com.dino.hotel.api.common.exception.ApplicationException;
import com.dino.hotel.api.common.http.response.ErrorCode;

public class RoomNotFoundException extends ApplicationException {
    public RoomNotFoundException(String message) {
        super(message, ErrorCode.ROOM_NOT_FOUND);
    }
}
