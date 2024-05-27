package com.dino.hotel.api.reservation.command.application.service;

import com.dino.hotel.api.common.exception.CustomNullPointerException;
import com.dino.hotel.api.helper.builder.HotelBuilder;
import com.dino.hotel.api.hotel.command.domain.Hotel;
import com.dino.hotel.api.hotel.command.domain.Room;
import com.dino.hotel.api.hotel.command.domain.RoomType;
import com.dino.hotel.api.hotel.command.domain.exception.HotelNotFoundException;
import com.dino.hotel.api.hotel.command.domain.repository.HotelRepository;
import com.dino.hotel.api.reservation.command.domain.exception.NoRoomsAvailableForReservation;
import com.dino.hotel.api.reservation.command.application.dto.ReservationDto;
import com.dino.hotel.api.reservation.command.domain.RoomTypeInventory;
import com.dino.hotel.api.reservation.command.domain.RoomTypeInventoryId;
import com.dino.hotel.api.reservation.command.domain.repository.RoomTypeInventoryRepository;
import com.dino.hotel.api.room.command.domain.exception.RoomNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private RoomTypeInventoryRepository roomTypeInventoryRepository;

    private ReservationCreateService reservationCreateService;

    @BeforeEach
    void init(){
        reservationCreateService = new ReservationCreateService(hotelRepository, roomTypeInventoryRepository);
    }

    @Test
    @DisplayName("ReservationDto가 null인 경우")
    void ReservationDtoNull() {
        ReservationDto reservationDto = null;

        assertThatThrownBy(() -> reservationCreateService.create(reservationDto))
                .isInstanceOf(CustomNullPointerException.class);
    }

    @Test
    @DisplayName("Hotel이 조회되지 않는 경우")
    void hotelNotFound() {
        Long hotelId = 1L;
        Long roomId = 1L;
        Long roomTypeId = 1L;
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusDays(2);
        Integer numberOfRoomsToReserve = 1;

        ReservationDto reservationDto = ReservationDto.of(hotelId, roomId, roomTypeId, start, end, numberOfRoomsToReserve);

        when(hotelRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reservationCreateService.create(reservationDto))
                .isInstanceOf(HotelNotFoundException.class);
    }

    @Test
    @DisplayName("Room이 조회되지 않는 경우")
    void roomNotFound() {
        Long hotelId = 1L;
        Long roomId = 1L;
        Long roomTypeId = 1L;
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusDays(2);
        Integer numberOfRoomsToReserve = 1;

        ReservationDto reservationDto = ReservationDto.of(hotelId, roomId, roomTypeId, start, end, numberOfRoomsToReserve);

        List<Function<Hotel, Room>> functions = List.of(hotel -> Room.of(2L, hotel, RoomType.of(1L), 1, 1, "101", true));
        Hotel hotel = HotelBuilder.builder()
                .id(1L)
                .rooms(functions).build();

        when(hotelRepository.findById(anyLong())).thenReturn(Optional.of(hotel));

        assertThatThrownBy(() -> reservationCreateService.create(reservationDto))
                .isInstanceOf(RoomNotFoundException.class);
    }

    @Test
    @DisplayName("Room의 잔여 수량이 부족한 경우")
    void roomNotEnoughQuantity() {
        Long hotelId = 1L;
        Long roomId = 1L;
        Long roomTypeId = 1L;
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusDays(2);
        Integer numberOfRoomsToReserve = 1;

        ReservationDto reservationDto = ReservationDto.of(hotelId, roomId, roomTypeId, start, end, numberOfRoomsToReserve);

        List<Function<Hotel, Room>> functions = List.of(hotel -> Room.of(1L, hotel, RoomType.of(1L), 1, 1, "101", true));
        Hotel hotel = HotelBuilder.builder()
                .id(1L)
                .rooms(functions).build();

        List<RoomTypeInventory> inventories = List.of(
                RoomTypeInventory.of(
                        RoomTypeInventoryId.of(1L, 1L, LocalDateTime.of(2024, 5, 27, 10, 0, 0)),
                        hotel,
                        100,
                        100
                )
        );

        when(hotelRepository.findById(anyLong())).thenReturn(Optional.of(hotel));
        when(roomTypeInventoryRepository.findAllByStartAndEnd(anyLong(), anyLong(), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(inventories);

        assertThatThrownBy(() -> reservationCreateService.create(reservationDto))
                .isInstanceOf(NoRoomsAvailableForReservation.class);
    }
}
