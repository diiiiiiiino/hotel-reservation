package com.dino.hotel.api.hotel.command.application.service;

import com.dino.hotel.api.hotel.command.domain.Hotel;
import com.dino.hotel.api.hotel.command.domain.exception.HotelNotFoundException;
import com.dino.hotel.api.hotel.command.domain.repository.HotelRepository;
import com.dino.hotel.api.util.VerifyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HotelRemoveService {
    private final HotelRepository hotelRepository;

    @Transactional
    public void remove(Long hotelId){
        VerifyUtil.verifyNegative(hotelId, "hotelId");

        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new HotelNotFoundException("Hotel not found"));

        hotelRepository.delete(hotel);
    }

}
