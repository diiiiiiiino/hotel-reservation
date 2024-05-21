package com.dino.hotel.api.hotel.command.application.service;

import com.dino.hotel.api.common.exception.CustomIllegalArgumentException;
import com.dino.hotel.api.common.exception.CustomNullPointerException;
import com.dino.hotel.api.helper.builder.HotelBuilder;
import com.dino.hotel.api.hotel.command.domain.Address;
import com.dino.hotel.api.hotel.command.domain.Hotel;
import com.dino.hotel.api.hotel.command.domain.exception.HotelNotFoundException;
import com.dino.hotel.api.hotel.command.domain.repository.HotelRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HotelRemoveServiceTest {

    private HotelRemoveService hotelRemoveService;

    @Mock
    private HotelRepository hotelRepository;

    @BeforeEach
    void init(){
        hotelRemoveService = new HotelRemoveService(hotelRepository);
    }

    @Test
    @DisplayName("삭제할 Hotel ID가 존재하지 않을 경우")
    void whenHotelRemoveThrowNotFoundExceptionCauseHotelNotFound() {
        Long id = 1L;

        when(hotelRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(id, HotelNotFoundException.class);
    }

    @Test
    @DisplayName("삭제할 Hotel의 ID가 null인 경우")
    void whenHotelRemoveThenIllegalArgumentExceptionCauseIdIsNull() {
        assertThatThrownBy(null, CustomNullPointerException.class);
    }

    @Test
    @DisplayName("삭제할 Hotel의 ID가 null인 경우")
    void whenHotelRemoveThenIllegalArgumentExceptionCauseIdIsNegative() {
        assertThatThrownBy(-1L, CustomIllegalArgumentException.class);
    }

    private void assertThatThrownBy(Long id, Class<? extends Exception> exceptionClass){
        Assertions.assertThatThrownBy(() -> hotelRemoveService.remove(id))
                .isInstanceOf(exceptionClass);
    }
}
