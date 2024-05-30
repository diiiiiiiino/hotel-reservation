package com.dino.hotel.api.reservation.command.application.service;

import com.dino.hotel.api.common.exception.CustomNullPointerException;
import com.dino.hotel.api.helper.builder.HotelBuilder;
import com.dino.hotel.api.helper.builder.MemberCreateHelperBuilder;
import com.dino.hotel.api.helper.builder.ReservationDtoBuilder;
import com.dino.hotel.api.hotel.command.domain.Hotel;
import com.dino.hotel.api.hotel.command.domain.Room;
import com.dino.hotel.api.hotel.command.domain.RoomType;
import com.dino.hotel.api.hotel.command.domain.exception.HotelNotFoundException;
import com.dino.hotel.api.hotel.command.domain.repository.HotelRepository;
import com.dino.hotel.api.member.command.domain.Member;
import com.dino.hotel.api.reservation.command.application.dto.ReservationDto;
import com.dino.hotel.api.reservation.command.domain.RoomTypeInventory;
import com.dino.hotel.api.reservation.command.domain.RoomTypeInventoryId;
import com.dino.hotel.api.reservation.command.domain.exception.NoRoomsAvailableForReservation;
import com.dino.hotel.api.reservation.command.domain.exception.NotFoundRoomsAvailableForReservation;
import com.dino.hotel.api.reservation.command.domain.repository.ReservationRepository;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReservationCreateServiceTest {

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private RoomTypeInventoryRepository roomTypeInventoryRepository;

    @Mock
    private ReservationRepository reservationRepository;

    private ReservationCreateService reservationCreateService;

    @BeforeEach
    void init(){
        reservationCreateService = new ReservationCreateService(hotelRepository, roomTypeInventoryRepository, reservationRepository);
    }

    @Test
    @DisplayName("ReservationDto가 null인 경우")
    void ReservationDtoNull() {
        Member member = MemberCreateHelperBuilder.builder().build();
        ReservationDto reservationDto = null;

        assertThatThrownBy(() -> reservationCreateService.create(member, reservationDto))
                .isInstanceOf(CustomNullPointerException.class);
    }

    @Test
    @DisplayName("Hotel이 조회되지 않는 경우")
    void hotelNotFound() {
        Member member = MemberCreateHelperBuilder.builder().build();
        ReservationDto reservationDto = ReservationDtoBuilder.builder().build();

        when(hotelRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reservationCreateService.create(member, reservationDto))
                .isInstanceOf(HotelNotFoundException.class);
    }

    @Test
    @DisplayName("Room이 조회되지 않는 경우")
    void roomNotFound() {
        Member member = MemberCreateHelperBuilder.builder().build();
        ReservationDto reservationDto = ReservationDtoBuilder.builder().build();

        List<Function<Hotel, Room>> functions = List.of(hotel -> Room.of(2L, hotel, RoomType.of(1L), 1, 1, "101", true));
        Hotel hotel = HotelBuilder.builder()
                .id(1L)
                .rooms(functions).build();

        when(hotelRepository.findById(anyLong())).thenReturn(Optional.of(hotel));

        assertThatThrownBy(() -> reservationCreateService.create(member, reservationDto))
                .isInstanceOf(RoomNotFoundException.class);
    }

    @Test
    @DisplayName("Room의 잔여 수량이 부족한 경우")
    void roomNotEnoughQuantity() {
        Member member = MemberCreateHelperBuilder.builder().build();
        ReservationDto reservationDto = ReservationDtoBuilder.builder()
                .numberOfRoomsToReserve(2)
                .build();

        List<Function<Hotel, Room>> functions = List.of(hotel -> Room.of(1L, hotel, RoomType.of(1L), 1, 1, "101", true));
        Hotel hotel = HotelBuilder.builder()
                .id(1L)
                .rooms(functions).build();

        List<RoomTypeInventory> inventories = List.of(
                RoomTypeInventory.of(
                        RoomTypeInventoryId.of(1L, 1L, LocalDateTime.of(2024, 5, 27, 15, 0, 0)),
                        hotel,
                        100,
                        98
                ),
                RoomTypeInventory.of(
                        RoomTypeInventoryId.of(1L, 1L, LocalDateTime.of(2024, 5, 28, 10, 0, 0)),
                        hotel,
                        100,
                        98
                ),
                RoomTypeInventory.of(
                        RoomTypeInventoryId.of(1L, 1L, LocalDateTime.of(2024, 5, 29, 11, 0, 0)),
                        hotel,
                        100,
                        99
                )
        );

        when(hotelRepository.findById(anyLong())).thenReturn(Optional.of(hotel));
        when(roomTypeInventoryRepository.findAllByStartAndEnd(anyLong(), anyLong(), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(inventories);

        assertThatThrownBy(() -> reservationCreateService.create(member, reservationDto))
                .isInstanceOf(NoRoomsAvailableForReservation.class);
    }

    @Test
    @DisplayName("예약 시 객실의 데이터 수가 조회된 기간만큼 조회 되지 않을때")
    void roomTypeInventoryNotFoundPeriodSearch() {
        Member member = MemberCreateHelperBuilder.builder().build();
        ReservationDto reservationDto = ReservationDtoBuilder.builder().build();

        List<Function<Hotel, Room>> functions = List.of(hotel -> Room.of(1L, hotel, RoomType.of(1L), 1, 1, "101", true));
        Hotel hotel = HotelBuilder.builder()
                .id(1L)
                .rooms(functions).build();

        List<RoomTypeInventory> inventories = List.of(
                RoomTypeInventory.of(
                        RoomTypeInventoryId.of(1L, 1L, LocalDateTime.of(2024, 5, 27, 10, 0, 0)),
                        hotel,
                        100,
                        99
                )
        );

        when(hotelRepository.findById(anyLong())).thenReturn(Optional.of(hotel));
        when(roomTypeInventoryRepository.findAllByStartAndEnd(anyLong(), anyLong(), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(inventories);

        assertThatThrownBy(() -> reservationCreateService.create(member, reservationDto))
                .isInstanceOf(NotFoundRoomsAvailableForReservation.class);
    }

    @Test
    @DisplayName("예약 성공")
    void reservationSuccess() {
        Member member = MemberCreateHelperBuilder.builder().build();
        ReservationDto reservationDto = ReservationDtoBuilder.builder().build();

        List<Function<Hotel, Room>> functions = List.of(hotel -> Room.of(1L, hotel, RoomType.of(1L), 1, 1, "101", true));
        Hotel hotel = HotelBuilder.builder()
                .id(1L)
                .rooms(functions).build();

        List<RoomTypeInventory> inventories = List.of(
                RoomTypeInventory.of(
                        RoomTypeInventoryId.of(1L, 1L, LocalDateTime.of(2024, 5, 27, 15, 0, 0)),
                        hotel,
                        100,
                        98
                ),
                RoomTypeInventory.of(
                        RoomTypeInventoryId.of(1L, 1L, LocalDateTime.of(2024, 5, 28, 10, 0, 0)),
                        hotel,
                        100,
                        98
                ),
                RoomTypeInventory.of(
                        RoomTypeInventoryId.of(1L, 1L, LocalDateTime.of(2024, 5, 29, 11, 0, 0)),
                        hotel,
                        100,
                        99
                )
        );

        when(hotelRepository.findById(anyLong())).thenReturn(Optional.of(hotel));
        when(roomTypeInventoryRepository.findAllByStartAndEnd(anyLong(), anyLong(), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(inventories);

        reservationCreateService.create(member, reservationDto);

        assertThat(inventories.get(0).getTotalReserved()).isEqualTo(99);
        assertThat(inventories.get(1).getTotalReserved()).isEqualTo(99);
        assertThat(inventories.get(2).getTotalReserved()).isEqualTo(100);
    }
}
