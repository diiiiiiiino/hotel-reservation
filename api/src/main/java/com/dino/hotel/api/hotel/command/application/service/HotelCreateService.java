package com.dino.hotel.api.hotel.command.application.service;

import com.dino.hotel.api.hotel.command.application.dto.HotelDto;
import com.dino.hotel.api.hotel.command.domain.Hotel;
import com.dino.hotel.api.hotel.command.domain.repository.HotelRepository;
import com.dino.hotel.api.util.VerifyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HotelCreateService {
    private final HotelRepository hotelRepository;

    @Transactional
    public Long create(HotelDto hotelDto) {
        VerifyUtil.verifyNull(hotelDto, "hotelDto");

        Hotel hotel = hotelRepository.save(getOf(hotelDto));

        return hotel.getId();
    }

    private Hotel getOf(HotelDto hotelDto) {
        return Hotel.of(hotelDto.getName(), hotelDto.getAddress());
    }
}
