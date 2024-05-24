package com.dino.hotel.api.hotel.command.application.mapper;

import com.dino.hotel.api.hotel.command.application.dto.HotelDto;
import com.dino.hotel.api.hotel.command.domain.Hotel;
import com.dino.hotel.api.room.command.application.dto.RoomDto;
import com.dino.hotel.api.util.VerifyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class HotelToHotelDtoMapper implements Function<Hotel, HotelDto> {

    private final RoomToRoomDtoMapper roomToRoomDtoMapper;

    @Override
    public HotelDto apply(Hotel hotel) {
        VerifyUtil.verifyNull(hotel, "hotel");

        List<RoomDto> roomDtos = hotel.getRooms().stream()
                .map(roomToRoomDtoMapper::apply)
                .collect(Collectors.toList());

        return HotelDto.of(hotel.getAddress(), hotel.getName(), roomDtos);
    }
}
