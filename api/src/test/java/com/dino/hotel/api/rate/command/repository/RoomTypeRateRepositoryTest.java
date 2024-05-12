package com.dino.hotel.api.rate.command.repository;

import com.dino.hotel.api.helper.BaseRepositoryTest;
import com.dino.hotel.api.helper.builder.HotelBuilder;
import com.dino.hotel.api.hotel.command.domain.Hotel;
import com.dino.hotel.api.hotel.command.domain.repository.HotelRepository;
import com.dino.hotel.api.rate.command.domain.RoomTypeRate;
import com.dino.hotel.api.rate.command.domain.RoomTypeRateId;
import com.dino.hotel.api.rate.command.domain.repository.RoomTypeRateRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class RoomTypeRateRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private RoomTypeRateRepository roomTypeRateRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Test
    @DisplayName("RoomTypeRate 엔티티 저장")
    void createRoomTypeRate() {
        Hotel hotel = HotelBuilder.builder().build();
        hotelRepository.save(hotel);

        RoomTypeRateId roomTypeRateId = RoomTypeRateId.of(hotel.getId(), LocalDateTime.of(2024, 05, 10, 10, 0, 0));
        RoomTypeRate roomTypeRate = RoomTypeRate.of(roomTypeRateId, hotel, 100);

        roomTypeRateRepository.save(roomTypeRate);

        flushAndClear();
        roomTypeRate = roomTypeRateRepository.findById(roomTypeRateId).get();

        assertThat(roomTypeRate).isNotNull();
        assertThat(roomTypeRate.getHotel()).isNotNull();
    }
}
