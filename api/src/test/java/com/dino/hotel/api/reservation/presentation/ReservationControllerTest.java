package com.dino.hotel.api.reservation.presentation;

import com.dino.hotel.api.helper.BaseControllerTest;
import com.dino.hotel.api.helper.builder.TestReservationDtoBuilder;
import com.dino.hotel.api.helper.dto.TestReservationDto;
import com.dino.hotel.api.hotel.command.domain.exception.HotelNotFoundException;
import com.dino.hotel.api.reservation.command.application.service.ReservationCreateService;
import com.dino.hotel.api.reservation.presentaion.controller.ReservationController;
import com.dino.hotel.api.security.WithMockMember;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

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
    @DisplayName("Hotel이 존재하지 않는 경우")
    void whenReserveThenNotFoundCauseHotelNotFound() throws Exception {
        TestReservationDto reservationDto = TestReservationDtoBuilder.builder().build();

        doThrow(new HotelNotFoundException("Hotel not found"))
                .when(reservationCreateService).create(any(), any());

        mvcPerform(post("/member/reservation"), reservationDto)
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}
