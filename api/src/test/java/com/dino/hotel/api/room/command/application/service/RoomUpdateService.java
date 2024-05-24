package com.dino.hotel.api.room.command.application.service;

import com.dino.hotel.api.hotel.command.domain.Hotel;
import com.dino.hotel.api.hotel.command.domain.exception.HotelNotFoundException;
import com.dino.hotel.api.hotel.command.domain.repository.HotelRepository;
import com.dino.hotel.api.room.command.application.dto.RoomUpdateDto;
import com.dino.hotel.api.util.VerifyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoomUpdateService {
    private final HotelRepository hotelRepository;

    @Transactional
    public void update(Long hotelId, Long roomId, RoomUpdateDto roomUpdateDto){
        VerifyUtil.verifyPositive(hotelId, "hotelId");
        VerifyUtil.verifyPositive(roomId, "roomId");
        VerifyUtil.verifyNull(roomUpdateDto, "roomUpdateDto");

        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new HotelNotFoundException("Hotel not found"));

        hotel.updateRoom(roomId, roomUpdateDto);
    }
}
