package com.dino.hotel.api.hotel.command.domain.repository;

import com.dino.hotel.api.helper.BaseRepositoryTest;
import com.dino.hotel.api.helper.builder.HotelBuilder;
import com.dino.hotel.api.helper.builder.RoomBuilder;
import com.dino.hotel.api.hotel.command.domain.Hotel;
import com.dino.hotel.api.hotel.command.domain.Room;
import com.dino.hotel.api.hotel.command.domain.RoomType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HotelRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private HotelRepository hotelRepository;

    @Test
    @DisplayName("Hotel 엔티티 저장")
    void createHotelEntitySave(){
        Hotel hotel = HotelBuilder.builder()
                .build();

        Hotel saveHotel = hotelRepository.save(hotel);

        assertThat(saveHotel).isNotNull();
    }

    @Test
    @DisplayName("Hotel 엔티티에 Room 엔티티 추가")
    void createHotelEntityAddRoom() {
        Hotel hotel = HotelBuilder.builder().build();

        Room room = RoomBuilder.builder()
                .hotel(hotel)
                .roomType(RoomType.of(2L))
                .build();

        hotel.addRoom(room);

        hotelRepository.save(hotel);

        hotel = hotelRepository.findById(hotel.getId()).get();

        List<Room> rooms = hotel.getRooms();

        assertThat(hotel).isNotNull();
        assertThat(rooms).hasSize(2);
    }

    @Test
    @DisplayName("Hotel 엔티티에 Room 엔티티 삭제")
    void createHotelEntityRemoveRoom() {
        Hotel hotel = HotelBuilder.builder().build();

        Room room = RoomBuilder.builder()
                .hotel(hotel)
                .roomType(RoomType.of(2L))
                .build();

        hotel.addRoom(room);

        hotelRepository.save(hotel);

        hotel.removeRoom(room.getId());

        flushAndClear();

        hotel = hotelRepository.findById(hotel.getId()).get();

        List<Room> rooms = hotel.getRooms();
        assertThat(rooms).hasSize(1);
    }
}
