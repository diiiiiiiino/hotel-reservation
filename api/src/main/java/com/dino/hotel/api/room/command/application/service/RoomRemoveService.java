package com.dino.hotel.api.room.command.application.service;

import com.dino.hotel.api.hotel.command.domain.Hotel;
import com.dino.hotel.api.hotel.command.domain.exception.HotelNotFoundException;
import com.dino.hotel.api.hotel.command.domain.repository.HotelRepository;
import com.dino.hotel.api.util.VerifyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomRemoveService {
    private final HotelRepository hotelRepository;

    public void remove(Long hotelId, Long roomId) {
        VerifyUtil.verifyPositiveOrZero(hotelId, "hotelId");
        VerifyUtil.verifyPositiveOrZero(roomId, "roomId");

        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new HotelNotFoundException("Hotel not found"));

        hotel.removeRoom(roomId);
    }
}
