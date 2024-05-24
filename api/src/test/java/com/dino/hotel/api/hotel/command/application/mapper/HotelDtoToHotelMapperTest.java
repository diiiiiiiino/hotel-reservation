package com.dino.hotel.api.hotel.command.application.mapper;

import com.dino.hotel.api.common.exception.CustomNullPointerException;
import com.dino.hotel.api.helper.builder.AddressBuilder;
import com.dino.hotel.api.hotel.command.application.dto.HotelDto;
import com.dino.hotel.api.hotel.command.domain.Address;
import com.dino.hotel.api.hotel.command.domain.Hotel;
import com.dino.hotel.api.room.command.application.dto.RoomDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HotelDtoToHotelMapperTest {

    private HotelDtoToHotelMapper hotelDtoToHotelMapper;
    private RoomDtoToRoomMapper roomDtoToRoomMapper;

    public HotelDtoToHotelMapperTest() {
        this.roomDtoToRoomMapper = new RoomDtoToRoomMapper();
        this.hotelDtoToHotelMapper = new HotelDtoToHotelMapper(roomDtoToRoomMapper);
    }

    @Test
    @DisplayName("HotelDto -> Hotel 엔티티 변환 시 HotelDto가 null인 경우")
    void caseHotelIsNullWhenTranslateHotelDtoToHotelThenNullPointException() {
        Assertions.assertThatThrownBy(() -> hotelDtoToHotelMapper.apply(null))
                .isInstanceOf(CustomNullPointerException.class);
    }

    @Test
    @DisplayName("HotelDto -> Hotel 엔티티 변환")
    void whenTranslateHotelDtoToHotelThenSuccess() {
        Address address = AddressBuilder.builder().build();

        HotelDto hotelDto = HotelDto.of(address, "5성호텔", List.of(RoomDto.of(1L, 1, 101, "101호")));

        Hotel hotel = hotelDtoToHotelMapper.apply(hotelDto);

        assertThat(hotel).isNotNull();
        assertThat(hotel.getName()).isEqualTo("5성호텔");
        assertThat(hotel.getRooms()).hasSize(1);

        Address hotelAddress = hotel.getAddress();
        assertThat(hotelAddress.getAddress1()).isEqualTo("서울시");
        assertThat(hotelAddress.getAddress2()).isEqualTo("동대문");
        assertThat(hotelAddress.getZipNo()).isEqualTo("123456");
    }
}
