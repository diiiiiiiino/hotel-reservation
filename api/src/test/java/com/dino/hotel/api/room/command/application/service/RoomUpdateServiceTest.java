package com.dino.hotel.api.room.command.application.service;

import com.dino.hotel.api.common.exception.CustomIllegalArgumentException;
import com.dino.hotel.api.common.exception.CustomNullPointerException;
import com.dino.hotel.api.helper.builder.HotelBuilder;
import com.dino.hotel.api.hotel.command.domain.Hotel;
import com.dino.hotel.api.hotel.command.domain.Room;
import com.dino.hotel.api.hotel.command.domain.RoomType;
import com.dino.hotel.api.hotel.command.domain.exception.HotelNotFoundException;
import com.dino.hotel.api.hotel.command.domain.repository.HotelRepository;
import com.dino.hotel.api.room.command.application.dto.RoomUpdateDto;
import com.dino.hotel.api.room.command.domain.exception.RoomNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoomUpdateServiceTest {
    @Mock
    private HotelRepository hotelRepository;
    private RoomUpdateService roomUpdateService;

    @BeforeEach
    void init(){
        roomUpdateService = new RoomUpdateService(hotelRepository);
    }

    @Test
    @DisplayName("HotelId가 null인 경우")
    void whenRoomUpdateThenCaseHotelIdIsNull() {
        Long hotelId = null;
        Long roomId = 1L;
        RoomUpdateDto roomUpdateDto = RoomUpdateDto.of(1L, 2, 201, "201호", true);

        assertThatThrownBy(() -> roomUpdateService.update(hotelId, roomId, roomUpdateDto))
                .isInstanceOf(CustomNullPointerException.class);
    }

    @Test
    @DisplayName("HotelId가 음수인 경우")
    void whenRoomUpdateThenCaseHotelIdIsNegative() {
        Long hotelId = -1L;
        Long roomId = 1L;
        RoomUpdateDto roomUpdateDto = RoomUpdateDto.of(1L, 2, 201, "201호", true);

        assertThatThrownBy(() -> roomUpdateService.update(hotelId, roomId, roomUpdateDto))
                .isInstanceOf(CustomIllegalArgumentException.class);
    }

    @Test
    @DisplayName("RoomId가 null인 경우")
    void whenRoomUpdateThenCaseRoomIdIsNull() {
        Long hotelId = 1L;
        Long roomId = null;
        RoomUpdateDto roomUpdateDto = RoomUpdateDto.of(1L, 2, 201, "201호", true);

        assertThatThrownBy(() -> roomUpdateService.update(hotelId, roomId, roomUpdateDto))
                .isInstanceOf(CustomNullPointerException.class);
    }

    @Test
    @DisplayName("RoomId가 음수인 경우")
    void whenRoomUpdateThenCaseRoomIdIsNegative() {
        Long hotelId = 1L;
        Long roomId = -1L;
        RoomUpdateDto roomUpdateDto = RoomUpdateDto.of(1L, 2, 201, "201호", true);

        assertThatThrownBy(() -> roomUpdateService.update(hotelId, roomId, roomUpdateDto))
                .isInstanceOf(CustomIllegalArgumentException.class);
    }

    @Test
    @DisplayName("Hotel이 존재하지 않을 경우")
    void whenRoomUpdateThenHotelNotFoundEx() {
        Long hotelId = 1L;
        Long roomId = 1L;
        RoomUpdateDto roomUpdateDto = RoomUpdateDto.of(1L, 2, 201, "201호", true);

        when(hotelRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> roomUpdateService.update(hotelId, roomId, roomUpdateDto))
                .isInstanceOf(HotelNotFoundException.class);
    }

    @Test
    @DisplayName("수정할 Room이 존재하지 않을 경우")
    void whenRoomUpdateThenRoomNotFoundEx() {
        Long hotelId = 1L;
        Long roomId = 2L;
        RoomUpdateDto roomUpdateDto = RoomUpdateDto.of(1L, 2, 201, "201호", true);

        List<Function<Hotel, Room>> functions = List.of((hotel) -> Room.of(1L, hotel, RoomType.of(1L), 1, 101, "101호", true));

        Hotel hotel = HotelBuilder.builder().rooms(functions).build();

        when(hotelRepository.findById(anyLong())).thenReturn(Optional.of(hotel));

        assertThatThrownBy(() -> roomUpdateService.update(hotelId, roomId, roomUpdateDto))
                .isInstanceOf(RoomNotFoundException.class);
    }

    @Test
    @DisplayName("HotelUpdateDto가 null인 경우")
    void whenRoomUpdateThenNullPointerExCauseHotelUpdateDtoNull() {
        Long hotelId = 1L;
        Long roomId = 1L;
        RoomUpdateDto roomUpdateDto = null;

        assertThatThrownBy(() -> roomUpdateService.update(hotelId, roomId, roomUpdateDto))
                .isInstanceOf(CustomNullPointerException.class);
    }

    @Test
    @DisplayName("Room 수정")
    void whenRoomUpdateThenSuccess() {
        Long hotelId = 1L;
        Long roomId = 1L;
        RoomUpdateDto roomUpdateDto = RoomUpdateDto.of(2L, 2, 201, "201호", true);

        List<Function<Hotel, Room>> functions = List.of((hotel) -> Room.of(1L, hotel, RoomType.of(1L), 1, 101, "101호", true));

        Hotel hotel = HotelBuilder.builder().rooms(functions).build();

        when(hotelRepository.findById(anyLong())).thenReturn(Optional.of(hotel));

        roomUpdateService.update(hotelId, roomId, roomUpdateDto);

        Room room = hotel.getRoom(1L);

        assertThat(room).isNotNull();
        assertThat(room.getRoomTypeId()).isEqualTo(2);
        assertThat(room.getFloor()).isEqualTo(2);
        assertThat(room.getNumber()).isEqualTo(201);
        assertThat(room.getName()).isEqualTo("201호");
        assertThat(room.isAvailable()).isEqualTo(true);
    }
}
