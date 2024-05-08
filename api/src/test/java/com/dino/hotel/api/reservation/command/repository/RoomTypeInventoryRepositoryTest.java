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

        RoomType roomType = RoomTypeBuilder.builder().build();
        roomTypeRepository.save(roomType);

        RoomTypeInventoryId roomTypeInventoryId = RoomTypeInventoryId.of(hotel.getId(), roomType.getId(), LocalDateTime.now());

        RoomTypeInventory roomTypeInventory = RoomTypeInventory.of(roomTypeInventoryId, hotel, 100, 80);

        roomTypeInventoryRepository.save(roomTypeInventory);

        flushAndClear();
    }
}
