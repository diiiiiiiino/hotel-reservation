package com.dino.hotel.api.room.command.application.service;

import com.dino.hotel.api.hotel.command.application.dto.RoomDto;
import com.dino.hotel.api.hotel.command.application.mapper.RoomDtoToRoomMapper;
import com.dino.hotel.api.hotel.command.domain.Hotel;
import com.dino.hotel.api.hotel.command.domain.exception.HotelNotFoundException;
import com.dino.hotel.api.hotel.command.domain.repository.HotelRepository;
import com.dino.hotel.api.util.VerifyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoomAddService {

    private final HotelRepository hotelRepository;
    private final RoomDtoToRoomMapper roomDtoToRoomMapper;

    @Transactional
    public void add(Long hotelId, RoomDto roomDto) {
        VerifyUtil.verifyNegative(hotelId, "hotelId");
        VerifyUtil.verifyNull(roomDto, "roomDto");

        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new HotelNotFoundException("Hotel not found"));

        hotel.addRoom(roomDtoToRoomMapper.apply(hotel, roomDto));
    }
}
