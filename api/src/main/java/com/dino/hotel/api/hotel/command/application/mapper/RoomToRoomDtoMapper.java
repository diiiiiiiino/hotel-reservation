package com.dino.hotel.api.hotel.command.application.mapper;

import com.dino.hotel.api.hotel.command.application.dto.RoomDto;
import com.dino.hotel.api.hotel.command.domain.Room;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RoomToRoomDtoMapper implements Function<Room, RoomDto> {
    @Override
    public RoomDto apply(Room room) {
        return RoomDto.of(room.getRoomType().getId(), room.getFloor(), room.getNumber(), room.getName());
    }
}
