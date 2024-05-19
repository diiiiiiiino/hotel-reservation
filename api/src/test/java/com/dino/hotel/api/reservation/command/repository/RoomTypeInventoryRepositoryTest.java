package com.dino.hotel.api.reservation.command.repository;

import com.dino.hotel.api.helper.BaseRepositoryTest;
import com.dino.hotel.api.helper.builder.HotelBuilder;
import com.dino.hotel.api.helper.builder.RoomTypeBuilder;
import com.dino.hotel.api.hotel.command.domain.Hotel;
import com.dino.hotel.api.hotel.command.domain.RoomType;
import com.dino.hotel.api.hotel.command.domain.repository.HotelRepository;
import com.dino.hotel.api.hotel.command.domain.repository.RoomTypeRepository;
import com.dino.hotel.api.reservation.command.domain.RoomTypeInventory;
import com.dino.hotel.api.reservation.command.domain.RoomTypeInventoryId;
import com.dino.hotel.api.reservation.command.domain.repository.RoomTypeInventoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class RoomTypeInventoryRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private RoomTypeInventoryRepository roomTypeInventoryRepository;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Test
    @DisplayName("RoomTypeInventory 엔티티 저장")
    void createRoomTypeInventory() {
        Hotel hotel = HotelBuilder.builder().build();
        hotelRepository.save(hotel);

        RoomTypeInventoryId roomTypeInventoryId = RoomTypeInventoryId.of(hotel.getId(), 1L, LocalDateTime.of(2024, 05, 11, 10, 0, 0));

        RoomTypeInventory roomTypeInventory = RoomTypeInventory.of(roomTypeInventoryId, hotel, 100, 80);

        roomTypeInventoryRepository.save(roomTypeInventory);

        flushAndClear();

        roomTypeInventory = roomTypeInventoryRepository.findById(roomTypeInventoryId).get();

        assertThat(roomTypeInventory).isNotNull();
    }
}
