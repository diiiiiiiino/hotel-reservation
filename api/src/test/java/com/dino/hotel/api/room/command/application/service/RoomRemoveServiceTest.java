package com.dino.hotel.api.room.command.application.service;

import com.dino.hotel.api.common.exception.CustomIllegalArgumentException;
import com.dino.hotel.api.common.exception.CustomNullPointerException;
import com.dino.hotel.api.helper.builder.HotelBuilder;
import com.dino.hotel.api.hotel.command.domain.Hotel;
import com.dino.hotel.api.hotel.command.domain.Room;
import com.dino.hotel.api.hotel.command.domain.RoomType;
import com.dino.hotel.api.hotel.command.domain.exception.HotelNotFoundException;
import com.dino.hotel.api.hotel.command.domain.repository.HotelRepository;
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
public class RoomRemoveServiceTest {

    private RoomRemoveService roomRemoveService;

    @Mock
    private HotelRepository hotelRepository;

    @BeforeEach
    void init(){
        roomRemoveService = new RoomRemoveService(hotelRepository);
    }

    @Test
    @DisplayName("HotelId가 null인 경우")
    void whenRoomRemoveThenCaseHotelIdIsNull() {
        Long hotelId = null;
        Long roomId = 1L;

        assertThatThrownBy(() -> roomRemoveService.remove(hotelId, roomId))
                .isInstanceOf(CustomNullPointerException.class);
    }

    @Test
    @DisplayName("HotelId가 음수인 경우")
    void whenRoomRemoveThenCaseHotelIdIsNegative() {
        Long hotelId = -1L;
        Long roomId = 1L;

        assertThatThrownBy(() -> roomRemoveService.remove(hotelId, roomId))
                .isInstanceOf(CustomIllegalArgumentException.class);
    }

    @Test
    @DisplayName("RoomId가 null인 경우")
    void whenRoomRemoveThenCaseRoomIdIsNull() {
        Long hotelId = 1L;
        Long roomId = null;

        assertThatThrownBy(() -> roomRemoveService.remove(hotelId, roomId))
                .isInstanceOf(CustomNullPointerException.class);
    }

    @Test
    @DisplayName("RoomId가 음수인 경우")
    void whenRoomRemoveThenCaseRoomIdIsNegative() {
        Long hotelId = 1L;
        Long roomId = -1L;

        assertThatThrownBy(() -> roomRemoveService.remove(hotelId, roomId))
                .isInstanceOf(CustomIllegalArgumentException.class);
    }

    @Test
    @DisplayName("Hotel이 존재하지 않을 경우")
    void whenRoomRemoveThenHotelNotFoundEx() {
        Long hotelId = 1L;
        Long roomId = 1L;

        when(hotelRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> roomRemoveService.remove(hotelId, roomId))
                .isInstanceOf(HotelNotFoundException.class);
    }

    @Test
    @DisplayName("삭제할 Room이 존재하지 않을 경우")
    void whenRoomRemoveThenRoomNotFoundEx() {
        Long hotelId = 1L;
        Long roomId = 2L;

        List<Function<Hotel, Room>> functions = List.of((hotel) -> Room.of(1L, hotel, RoomType.of(1L), 1, 101, "101호", true));

        Hotel hotel = HotelBuilder.builder().rooms(functions).build();

        when(hotelRepository.findById(anyLong())).thenReturn(Optional.of(hotel));

        assertThatThrownBy(() -> roomRemoveService.remove(hotelId, roomId))
                .isInstanceOf(RoomNotFoundException.class);
    }

    @Test
    @DisplayName("Room 삭제")
    void whenRoomRemoveSuccess() {
        Long hotelId = 1L;
        Long roomId = 2L;

        List<Function<Hotel, Room>> functions = List.of((hotel) -> Room.of(1L, hotel, RoomType.of(1L), 1, 101, "101호", true),
                (hotel) -> Room.of(2L, hotel, RoomType.of(1L), 1, 102, "102호", true));

        Hotel hotel = HotelBuilder.builder().rooms(functions).build();

        when(hotelRepository.findById(anyLong())).thenReturn(Optional.of(hotel));

        roomRemoveService.remove(hotelId, roomId);

        assertThat(hotel.hasRoom(2L)).isFalse();
        assertThat(hotel.getRooms()).hasSize(1);
    }
}
