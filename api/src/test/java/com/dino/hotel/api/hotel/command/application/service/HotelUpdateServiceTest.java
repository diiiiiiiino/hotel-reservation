package com.dino.hotel.api.hotel.command.application.service;

import com.dino.hotel.api.common.exception.CustomNullPointerException;
import com.dino.hotel.api.common.exception.NotFoundException;
import com.dino.hotel.api.helper.builder.HotelBuilder;
import com.dino.hotel.api.hotel.command.application.dto.HotelUpdateDto;
import com.dino.hotel.api.hotel.command.domain.Address;
import com.dino.hotel.api.hotel.command.domain.Hotel;
import com.dino.hotel.api.hotel.command.domain.exception.HotelNotFoundException;
import com.dino.hotel.api.hotel.command.domain.repository.HotelRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HotelUpdateServiceTest {

    private HotelUpdateService hotelUpdateService;
    private HotelRepository hotelRepository;

    @BeforeEach
    void setUp(){
        this.hotelRepository = mock(HotelRepository.class);
        this.hotelUpdateService = new HotelUpdateService(hotelRepository);
    }

    @Test
    @DisplayName("수정할 Hotel ID가 존재하지 않을 경우")
    void whenHotelUpdateThrowNotFoundExceptionCauseHotelNotFound() {
        Long id = 1L;
        Address address = Address.of("경기도", "광주시", "333333");
        String name = "4성급호텔";

        HotelUpdateDto hotelUpdateDto = HotelUpdateDto.of(address, name);

        when(hotelRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(id, hotelUpdateDto, HotelNotFoundException.class);
    }

    @Test
    @DisplayName("수정할 Hotel의 정보가 null인 경우")
    void whenHotelUpdateThenIllegalArgumentExceptionCauseDtoIsNull() {
        assertThatThrownBy(1L, null, CustomNullPointerException.class);
    }

    @Test
    @DisplayName("Hotel 정보 수정 성공")
    void whenHotelUpdateSuccess() {
        Long id = 1L;
        Address address = Address.of("경기도", "광주시", "333333");
        String name = "4성급호텔";

        HotelUpdateDto hotelUpdateDto = HotelUpdateDto.of(address, name);

        Hotel hotel = HotelBuilder.builder().build();

        when(hotelRepository.findById(anyLong())).thenReturn(Optional.of(hotel));

        hotelUpdateService.update(id, hotelUpdateDto);

        Address address2 = hotel.getAddress();
        assertThat(hotel.getName()).isEqualTo("4성급호텔");
        assertThat(address2.getAddress1()).isEqualTo("경기도");
        assertThat(address2.getAddress2()).isEqualTo("광주시");
        assertThat(address2.getZipNo()).isEqualTo("333333");
    }

    private void assertThatThrownBy(Long id, HotelUpdateDto hotelUpdateDto, Class<? extends Exception> exceptionClass){
        Assertions.assertThatThrownBy(() -> hotelUpdateService.update(id, hotelUpdateDto))
                .isInstanceOf(exceptionClass);
    }
}
