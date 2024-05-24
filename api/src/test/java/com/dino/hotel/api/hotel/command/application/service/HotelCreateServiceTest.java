package com.dino.hotel.api.hotel.command.application.service;

import com.dino.hotel.api.common.exception.CustomNullPointerException;
import com.dino.hotel.api.helper.builder.AddressBuilder;
import com.dino.hotel.api.helper.builder.HotelBuilder;
import com.dino.hotel.api.hotel.command.application.dto.HotelDto;
import com.dino.hotel.api.hotel.command.application.mapper.HotelDtoToHotelMapper;
import com.dino.hotel.api.hotel.command.application.mapper.RoomDtoToRoomMapper;
import com.dino.hotel.api.hotel.command.domain.Address;
import com.dino.hotel.api.hotel.command.domain.Hotel;
import com.dino.hotel.api.hotel.command.domain.repository.HotelRepository;
import com.dino.hotel.api.room.command.application.dto.RoomDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HotelCreateServiceTest {

    private HotelCreateService hotelCreateService;
    private HotelDtoToHotelMapper hotelDtoToHotelMapper;
    private RoomDtoToRoomMapper roomDtoToRoomMapper;
    private HotelRepository hotelRepository;

    public HotelCreateServiceTest() {
        this.hotelRepository = mock(HotelRepository.class);
        this.roomDtoToRoomMapper = new RoomDtoToRoomMapper();
        this.hotelDtoToHotelMapper = new HotelDtoToHotelMapper(roomDtoToRoomMapper);
        this.hotelCreateService = new HotelCreateService(hotelRepository, hotelDtoToHotelMapper);
    }

    @Test
    @DisplayName("Hotel 생성 시 HotelDto가 null일 때")
    void createHotelThenHotelDtoIsNull() {
        HotelDto hotelDto = null;

        assertThatThrownBy(() -> hotelCreateService.create(hotelDto))
                .isInstanceOf(CustomNullPointerException.class);
    }

    @Test
    @DisplayName("Hotel 생성")
    void whenCreateHotelThenSuccess() {
        Address address = AddressBuilder.builder().build();
        String name = "5성호텔";
        List<RoomDto> rooms = List.of(RoomDto.of(1L, 1, 101, "101호"));

        HotelDto hotelDto = HotelDto.of(address, name, rooms);

        Hotel hotel = HotelBuilder.builder()
                .id(1L)
                .build();

        when(hotelRepository.save(any(Hotel.class))).thenReturn(hotel);

        Long hotelId = hotelCreateService.create(hotelDto);

        assertThat(hotelId).isEqualTo(1L);
    }
}
