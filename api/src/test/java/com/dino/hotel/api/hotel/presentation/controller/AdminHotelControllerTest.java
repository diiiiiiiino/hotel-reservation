package com.dino.hotel.api.hotel.presentation.controller;

import com.dino.hotel.api.common.exception.CustomNullPointerException;
import com.dino.hotel.api.helper.BaseControllerTest;
import com.dino.hotel.api.helper.builder.AddressBuilder;
import com.dino.hotel.api.helper.dto.TestHotelDto;
import com.dino.hotel.api.hotel.command.application.dto.HotelDto;
import com.dino.hotel.api.hotel.command.application.dto.RoomDto;
import com.dino.hotel.api.hotel.command.application.service.HotelCreateService;
import com.dino.hotel.api.hotel.command.domain.Address;
import com.dino.hotel.api.hotel.controller.AdminHotelController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = AdminHotelController.class)
@ActiveProfiles("test")
public class AdminHotelControllerTest extends BaseControllerTest {

    @MockBean
    private HotelCreateService hotelCreateService;

    @WithMockUser
    @Test
    @DisplayName("Hotel 생성 시 요청 바디 유효성 에러")
    void whenHotelCreateThenInvalidRequestCauseHttpBodyNull() throws Exception {
        Address address = AddressBuilder.builder().build();
        String name = "5성호텔";
        List<RoomDto> rooms = List.of(RoomDto.of(1L, 1, 101, "101호"));

        doThrow(new CustomNullPointerException("hotelDto is null"))
                .when(hotelCreateService).create(any(HotelDto.class));

        mvcPerformPostHotel(rooms, address, name, status().isBadRequest());
    }

    @WithMockUser
    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Hotel 생성 시 HotelDto rooms가 null 또는 비어있는 경우")
    void whenHotelCreateThenInvalidRequestCauseEmptyRooms(List<RoomDto> rooms) throws Exception {
        Address address = AddressBuilder.builder().build();
        String name = "5성호텔";

        mvcPerformPostHotel(rooms, address, name, status().isBadRequest());
    }

    @WithMockUser
    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Hotel 생성 시 HotelDto name이 null 또는 비어있는 경우")
    void whenHotelCreateThenInvalidRequestCauseInvalidName(String name) throws Exception {
        Address address = AddressBuilder.builder().build();
        List<RoomDto> rooms = List.of(RoomDto.of(1L, 1, 101, "101호"));

        mvcPerformPostHotel(rooms, address, name, status().isBadRequest());
    }

    @WithMockUser
    @Test
    @DisplayName("Hotel 생성")
    void whenHotelCreateThenSuccess() throws Exception {
        Address address = AddressBuilder.builder().build();
        String name = "5성호텔";
        List<RoomDto> rooms = List.of(RoomDto.of(1L, 1, 101, "101호"));

        mvcPerformPostHotel(rooms, address, name, status().isOk());
    }

    private void mvcPerformPostHotel(List<RoomDto> rooms, Address address, String name, ResultMatcher resultMatcher) throws Exception {
        TestHotelDto hotelDto = TestHotelDto.of(address, name, rooms);

        mvcPerform(post("/admin/hotel"), hotelDto)
                .andExpect(resultMatcher)
                .andDo(print());
    }
}
