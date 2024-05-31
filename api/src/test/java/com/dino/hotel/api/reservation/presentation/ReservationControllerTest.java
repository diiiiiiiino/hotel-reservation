package com.dino.hotel.api.reservation.presentation;

import com.dino.hotel.api.helper.BaseControllerTest;
import com.dino.hotel.api.helper.builder.TestReservationDtoBuilder;
import com.dino.hotel.api.helper.dto.TestReservationDto;
import com.dino.hotel.api.hotel.command.domain.exception.HotelNotFoundException;
import com.dino.hotel.api.reservation.command.application.service.ReservationCreateService;
import com.dino.hotel.api.reservation.command.domain.exception.NoRoomsAvailableForReservation;
import com.dino.hotel.api.reservation.command.domain.exception.NotFoundRoomsAvailableForReservation;
import com.dino.hotel.api.reservation.presentaion.controller.ReservationController;
import com.dino.hotel.api.room.command.domain.exception.RoomNotFoundException;
import com.dino.hotel.api.security.WithMockMember;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = ReservationController.class)
@ActiveProfiles("test")
public class ReservationControllerTest extends BaseControllerTest {

    @MockBean
    private ReservationCreateService reservationCreateService;

    @WithMockMember
    @Test
    @DisplayName("ReservationDto가 null인 경우")
    void whenReserveThenInternalServerErrorCauseReservationDtoNull() throws Exception {
        TestReservationDto reservationDto = null;

        mvcPerform(post("/member/reservation"), reservationDto)
                .andExpect(status().isInternalServerError())
                .andDo(print());
    }

    @WithMockMember
    @ParameterizedTest
    @MethodSource("reservationDtoMethodSource")
    @DisplayName("ReservationDto가 유효한 값을 가지지 않을 경우")
    void whenReserveThenBadRequestCauseReservationDtoInvalid(TestReservationDto reservationDto) throws Exception {
        mvcPerform(post("/member/reservation"), reservationDto)
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @WithMockMember
    @Test
    @DisplayName("Hotel이 존재하지 않는 경우")
    void whenReserveThenNotFoundCauseHotelNotFound() throws Exception {
        LocalDateTime now = LocalDateTime.now().plusMinutes(1);
        TestReservationDto reservationDto = TestReservationDtoBuilder.builder().start(now).end(now.plusDays(1)).build();

        doThrow(new HotelNotFoundException("Hotel not found"))
                .when(reservationCreateService).create(any(), any());

        mvcPerform(post("/member/reservation"), reservationDto)
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @WithMockMember
    @Test
    @DisplayName("Room이 존재하지 않는 경우")
    void whenReserveThenNotFoundCauseRoomNotFound() throws Exception {
        LocalDateTime now = LocalDateTime.now().plusMinutes(1);
        TestReservationDto reservationDto = TestReservationDtoBuilder.builder().start(now).end(now.plusDays(1)).build();

        doThrow(new RoomNotFoundException("Room not found"))
                .when(reservationCreateService).create(any(), any());

        mvcPerform(post("/member/reservation"), reservationDto)
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @WithMockMember
    @Test
    @DisplayName("예약 가능한 Room이 없을 경우")
    void whenReserveThenNotFoundCauseAvailableForReservationRoomNotFound() throws Exception {
        LocalDateTime now = LocalDateTime.now().plusMinutes(1);
        TestReservationDto reservationDto = TestReservationDtoBuilder.builder().start(now).end(now.plusDays(1)).build();

        doThrow(new NotFoundRoomsAvailableForReservation("Not found rooms available for reservation"))
                .when(reservationCreateService).create(any(), any());

        mvcPerform(post("/member/reservation"), reservationDto)
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @WithMockMember
    @Test
    @DisplayName("예약하려는 Room의 수량이 부족한 경우")
    void whenReserveThenNotFoundCauseNoRoomsAvailableForReservation() throws Exception {
        LocalDateTime now = LocalDateTime.now().plusMinutes(1);
        TestReservationDto reservationDto = TestReservationDtoBuilder.builder().start(now).end(now.plusDays(1)).build();

        doThrow(new NoRoomsAvailableForReservation("There are no rooms available for reservation"))
                .when(reservationCreateService).create(any(), any());

        mvcPerform(post("/member/reservation"), reservationDto)
                .andExpect(status().isConflict())
                .andDo(print());
    }

    public static Stream<Arguments> reservationDtoMethodSource(){
        return Stream.of(
                Arguments.of(TestReservationDtoBuilder.builder().hotelId(null).build()),
                Arguments.of(TestReservationDtoBuilder.builder().hotelId(-1L).build()),
                Arguments.of(TestReservationDtoBuilder.builder().roomId(null).build()),
                Arguments.of(TestReservationDtoBuilder.builder().roomId(-1L).build()),
                Arguments.of(TestReservationDtoBuilder.builder().roomTypeId(null).build()),
                Arguments.of(TestReservationDtoBuilder.builder().roomTypeId(-1L).build()),
                Arguments.of(TestReservationDtoBuilder.builder().start(null).build()),
                Arguments.of(TestReservationDtoBuilder.builder().start(LocalDateTime.now().minusNanos(1)).end(LocalDateTime.now()).build()),
                Arguments.of(TestReservationDtoBuilder.builder().end(null).build()),
                Arguments.of(TestReservationDtoBuilder.builder().start(LocalDateTime.now()).end(LocalDateTime.now().minusNanos(-1)).build()),
                Arguments.of(TestReservationDtoBuilder.builder().numberOfRoomsToReserve(null).build()),
                Arguments.of(TestReservationDtoBuilder.builder().numberOfRoomsToReserve(-1).build())
        );
    }
}
