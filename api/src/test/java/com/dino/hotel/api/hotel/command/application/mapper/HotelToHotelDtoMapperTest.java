package com.dino.hotel.api.hotel.command.application.mapper;

import com.dino.hotel.api.common.exception.CustomNullPointerException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HotelToHotelDtoMapperTest {

    private HotelToHotelDtoMapper hotelToHotelDtoMapper;
    private RoomToRoomDtoMapper roomToRoomDtoMapper;

    public HotelToHotelDtoMapperTest() {
        this.roomToRoomDtoMapper = new RoomToRoomDtoMapper();
        this.hotelToHotelDtoMapper = new HotelToHotelDtoMapper(roomToRoomDtoMapper);
    }

    @Test
    @DisplayName("Hotel 엔티티 -> HotelDto 변환 시 Hotel이 null인 경우")
    void caseHotelIsNullWhenTranslateHotelToHotelDtoThenNullPointException() {
        Assertions.assertThatThrownBy(() -> hotelToHotelDtoMapper.apply(null))
                .isInstanceOf(CustomNullPointerException.class);
    }
}
