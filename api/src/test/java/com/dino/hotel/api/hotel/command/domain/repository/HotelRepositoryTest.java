package com.dino.hotel.api.hotel.command.domain.repository;

import com.dino.hotel.api.helper.BaseRepositoryTest;
import com.dino.hotel.api.helper.builder.HotelBuilder;
import com.dino.hotel.api.helper.builder.RoomBuilder;
import com.dino.hotel.api.helper.builder.RoomTypeBuilder;
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

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Test
    @DisplayName("Hotel 엔티티 저장")
    void createHotelEntitySave(){
        Hotel hotel = HotelBuilder.builder().build();

        Hotel saveHotel = hotelRepository.save(hotel);

        assertThat(saveHotel).isNotNull();
    }

    @Test
    @DisplayName("Hotel 엔티티에 Room 엔티티 추가")
    void createHotelEntityAddRoom() {
        Hotel hotel = HotelBuilder.builder().build();
        RoomType roomType = saveRoomType();
        Room room = RoomBuilder.builder()
                .hotel(hotel)
                .roomType(roomType)
                .build();

        hotel.addRoom(room);

        hotelRepository.save(hotel);

        hotel = hotelRepository.findById(hotel.getId()).get();

        List<Room> rooms = hotel.getRooms();

        assertThat(hotel).isNotNull();
        assertThat(rooms).hasSize(1);
    }

    @Test
    @DisplayName("Hotel 엔티티에 Room 엔티티 삭제")
    void createHotelEntityRemoveRoom() {
        Hotel hotel = HotelBuilder.builder().build();
        RoomType roomType = saveRoomType();
        Room room = RoomBuilder.builder()
                .hotel(hotel)
                .roomType(roomType)
                .build();

        hotel.addRoom(room);

        hotelRepository.save(hotel);

        hotel.removeRoom(room);

        flushAndClear();

        hotel = hotelRepository.findById(hotel.getId()).get();

        List<Room> rooms = hotel.getRooms();
        assertThat(rooms).isEmpty();
    }


    private RoomType saveRoomType(){
        RoomType roomType = RoomTypeBuilder.builder().build();
        return roomTypeRepository.save(roomType);
    }
}
