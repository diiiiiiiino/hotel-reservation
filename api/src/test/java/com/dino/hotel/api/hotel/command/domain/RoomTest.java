package com.dino.hotel.api.hotel.command.domain;

import com.dino.hotel.api.common.exception.CustomIllegalArgumentException;
import com.dino.hotel.api.common.exception.CustomNullPointerException;
import com.dino.hotel.api.helper.builder.HotelBuilder;
import com.dino.hotel.api.helper.builder.RoomTypeBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RoomTest {

    @Test
    @DisplayName("Room 생성 시 호텔이 null인 경우")
    void whenCreateRoomThenHotelNull() {
        RoomType roomType = RoomTypeBuilder.builder().build();
        Integer floor = 1;
        Integer number = 101;
        String name = "101호";
        Hotel hotel = null;

        assertThatThrownBy(() -> Room.of(hotel, roomType, floor, number, name, true))
                .isInstanceOf(CustomNullPointerException.class);
    }

    @Test
    @DisplayName("Room 생성 시 호텔이 RoomType이 null인 경우")
    void whenCreateRoomThenRoomTypeNull() {
        Hotel hotel = HotelBuilder.builder().build();
        Integer floor = 1;
        Integer number = 101;
        String name = "101호";
        RoomType roomType = null;

        assertThatThrownBy(() -> Room.of(hotel, roomType, floor, number, name, true))
                .isInstanceOf(CustomNullPointerException.class);
    }

    @Test
    @DisplayName("Room 생성 시 호텔이 층수가 null인 경우")
    void whenCreateRoomThenFloorNull() {
        Hotel hotel = HotelBuilder.builder().build();
        RoomType roomType = RoomType.of("디럭스룸", "오션뷰");
        Integer number = 101;
        String name = "101호";
        Integer floor = null;

        assertThatThrownBy(() -> Room.of(hotel, roomType, floor, number, name, true))
                .isInstanceOf(CustomNullPointerException.class);
    }

    @Test
    @DisplayName("Room 생성 시 호텔이 층수가 음수인 경우")
    void whenCreateRoomThenFloorNegative() {
        Hotel hotel = HotelBuilder.builder().build();
        RoomType roomType = RoomType.of("디럭스룸", "오션뷰");
        Integer number = 101;
        String name = "101호";
        Integer floor = -1;

        assertThatThrownBy(() -> Room.of(hotel, roomType, floor, number, name, true))
                .isInstanceOf(CustomIllegalArgumentException.class);
    }

    @Test
    @DisplayName("Room 생성 시 호수가 null인 경우")
    void whenCreateRoomThenNumberNull() {
        Hotel hotel = HotelBuilder.builder().build();
        RoomType roomType = RoomTypeBuilder.builder().build();
        Integer floor = 1;
        String name = "101호";
        Integer number = null;

        assertThatThrownBy(() -> Room.of(hotel, roomType, floor, number, name, true))
                .isInstanceOf(CustomNullPointerException.class);
    }

    @Test
    @DisplayName("Room 생성 시 호수가 음수인 경우")
    void whenCreateRoomThenNumberNegative() {
        Hotel hotel = HotelBuilder.builder().build();
        RoomType roomType = RoomTypeBuilder.builder().build();
        Integer floor = 1;
        String name = "101호";
        Integer number = -101;

        assertThatThrownBy(() -> Room.of(hotel, roomType, floor, number, name, true))
                .isInstanceOf(CustomIllegalArgumentException.class);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Room 생성 시 이름이 유효하지 않은 값인 경우")
    void whenCreateRoomThenNameInvalid(String name) {
        Hotel hotel = HotelBuilder.builder().build();
        RoomType roomType = RoomTypeBuilder.builder().build();
        Integer floor = 1;
        Integer number = 101;

        assertThatThrownBy(() -> Room.of(hotel, roomType, floor, number, name, true))
                .isInstanceOf(CustomIllegalArgumentException.class);
    }
}
