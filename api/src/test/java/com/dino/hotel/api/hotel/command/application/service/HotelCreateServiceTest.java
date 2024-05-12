package com.dino.hotel.api.hotel.command.application.service;

import com.dino.hotel.api.common.exception.CustomNullPointerException;
import com.dino.hotel.api.helper.builder.AddressBuilder;
import com.dino.hotel.api.helper.builder.HotelBuilder;
import com.dino.hotel.api.helper.builder.RoomBuilder;
import com.dino.hotel.api.hotel.command.application.dto.HotelDto;
import com.dino.hotel.api.hotel.command.domain.Address;
import com.dino.hotel.api.hotel.command.domain.Hotel;
import com.dino.hotel.api.hotel.command.domain.Room;
import com.dino.hotel.api.hotel.command.domain.repository.HotelRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HotelCreateServiceTest {

    private HotelCreateService hotelCreateService;
    private HotelRepository hotelRepository;

    public HotelCreateServiceTest() {
        this.hotelRepository = mock(HotelRepository.class);
        this.hotelCreateService = new HotelCreateService(hotelRepository);
    }

    @Test
    @DisplayName("Hotel 생성 시 HotelDto가 null일 때")
    void createHotelThenHotelDtoIsNull() {
        HotelDto hotelDto = null;

        assertThatThrownBy(() -> hotelCreateService.create(hotelDto))
                .isInstanceOf(CustomNullPointerException.class);
    }

    @Test
    @DisplayName("Hotel만 생성")
    void whenCreateHotelThenSuccess() {
        Address address = AddressBuilder.builder().build();
        String name = "5성호텔";
        List<Room> rooms = new ArrayList<>();

        HotelDto hotelDto = HotelDto.of(address, name, rooms);

        Hotel hotel = HotelBuilder.builder()
                .id(1L)
                .build();

        when(hotelRepository.save(any(Hotel.class))).thenReturn(hotel);

        Long hotelId = hotelCreateService.create(hotelDto);

        assertThat(hotelId).isEqualTo(1L);
    }
}
