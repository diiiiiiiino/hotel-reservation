package com.dino.hotel.api.hotel.command.application.service;

import com.dino.hotel.api.common.exception.NotFoundException;
import com.dino.hotel.api.hotel.command.application.dto.HotelUpdateDto;
import com.dino.hotel.api.hotel.command.domain.Hotel;
import com.dino.hotel.api.hotel.command.domain.exception.HotelNotFoundException;
import com.dino.hotel.api.hotel.command.domain.repository.HotelRepository;
import com.dino.hotel.api.util.VerifyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HotelUpdateService {

    private final HotelRepository hotelRepository;

    @Transactional
    public void update(HotelUpdateDto hotelUpdateDto) {
        VerifyUtil.verifyNull(hotelUpdateDto, "hotelUpdateDto");

        Hotel hotel = hotelRepository.findById(hotelUpdateDto.getId())
                .orElseThrow(() -> new HotelNotFoundException("hotel not found"));

        hotel.update(hotelUpdateDto.getAddress(), hotelUpdateDto.getName());
    }
}
