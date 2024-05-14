package com.dino.hotel.api.hotel.command.application.mapper;

import com.dino.hotel.api.hotel.command.application.dto.RoomDto;
import com.dino.hotel.api.hotel.command.domain.Hotel;
import com.dino.hotel.api.hotel.command.domain.Room;
import com.dino.hotel.api.hotel.command.domain.RoomType;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
public class RoomDtoToRoomMapper implements BiFunction<Hotel, RoomDto, Room> {
    @Override
    public Room apply(Hotel hotel, RoomDto roomDto) {
        return Room.of(hotel, RoomType.of(roomDto.getRoomTypeId()), roomDto.getFloor(), roomDto.getNumber(), roomDto.getName(), true);
    }
}
