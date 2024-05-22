package com.dino.hotel.api.hotel.command.application.mapper;

import com.dino.hotel.api.hotel.command.application.dto.HotelDto;
import com.dino.hotel.api.hotel.command.domain.Hotel;
import com.dino.hotel.api.hotel.command.domain.Room;
import com.dino.hotel.api.util.VerifyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class HotelDtoToHotelMapper implements Function<HotelDto, Hotel> {

    private final RoomDtoToRoomMapper roomDtoToRoomMapper;

    @Override
    public Hotel apply(HotelDto hotelDto) {
        VerifyUtil.verifyNull(hotelDto, "hotelDto");

        List<Function<Hotel, Room>> functions = hotelDto.getRooms().stream()
                .map(roomDto -> (Function<Hotel, Room>) (hotel -> roomDtoToRoomMapper.apply(hotel, roomDto)))
                .collect(Collectors.toList());

        return Hotel.of(hotelDto.getName(), hotelDto.getAddress(), functions);
    }
}
