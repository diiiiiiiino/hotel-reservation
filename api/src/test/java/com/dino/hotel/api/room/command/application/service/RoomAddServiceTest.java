package com.dino.hotel.api.room.command.application.service;

import com.dino.hotel.api.common.exception.CustomIllegalArgumentException;
import com.dino.hotel.api.common.exception.CustomNullPointerException;
import com.dino.hotel.api.helper.builder.HotelBuilder;
import com.dino.hotel.api.hotel.command.application.mapper.RoomDtoToRoomMapper;
import com.dino.hotel.api.hotel.command.domain.Hotel;
import com.dino.hotel.api.hotel.command.domain.exception.HotelNotFoundException;
import com.dino.hotel.api.hotel.command.domain.repository.HotelRepository;
import com.dino.hotel.api.room.command.application.dto.RoomDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoomAddServiceTest {

    @Mock
    private HotelRepository hotelRepository;
    private RoomAddService roomAddService;
    private RoomDtoToRoomMapper roomDtoToRoomMapper;

    @BeforeEach
    void init(){
        this.roomDtoToRoomMapper = new RoomDtoToRoomMapper();
        this.roomAddService = new RoomAddService(hotelRepository, roomDtoToRoomMapper);
    }

    @Test
    @DisplayName("HotelDto가 null인 경우")
    void hotelDtoIsNull() {
        Long hotelId = 1L;
        RoomDto roomDto = null;

        assertThatThrownBy(() -> roomAddService.add(hotelId, roomDto))
                .isInstanceOf(CustomNullPointerException.class);
    }

    @Test
    @DisplayName("Hotel ID가 null인 경우")
    void hotelIdIsNull() {
        Long hotelId = null;
        RoomDto roomDto = RoomDto.of(1L ,1, 102, "102호");

        assertThatThrownBy(() -> roomAddService.add(hotelId, roomDto))
                .isInstanceOf(CustomNullPointerException.class);
    }

    @Test
    @DisplayName("Hotel ID가 음수인 경우")
    void hotelIdIsNegative() {
        Long hotelId = -1L;
        RoomDto roomDto = RoomDto.of(1L ,1, 102, "102호");

        assertThatThrownBy(() -> roomAddService.add(hotelId, roomDto))
                .isInstanceOf(CustomIllegalArgumentException.class);
    }

    @Test
    @DisplayName("Hotel이 조회되지 않을 경우")
    void hotelNotFound() {
        Long hotelId = 1L;
        RoomDto roomDto = RoomDto.of(1L, 1, 102, "102호");

        when(hotelRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> roomAddService.add(hotelId, roomDto))
                .isInstanceOf(HotelNotFoundException.class);
    }

    @Test
    @DisplayName("Room 추가 정상")
    void hotelAddSuccess() {
        Long hotelId = 1L;
        RoomDto roomDto = RoomDto.of(1L, 1, 102, "102호");

        Hotel hotel = HotelBuilder.builder().build();

        when(hotelRepository.findById(anyLong())).thenReturn(Optional.of(hotel));

        roomAddService.add(hotelId, roomDto);

        assertThat(hotel.getRooms()).hasSize(2);
    }
}
