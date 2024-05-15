package com.dino.hotel.api.hotel.presentation.controller;

import com.dino.hotel.api.common.exception.CustomNullPointerException;
import com.dino.hotel.api.helper.BaseControllerTest;
import com.dino.hotel.api.helper.builder.AddressBuilder;
import com.dino.hotel.api.hotel.command.application.dto.HotelDto;
import com.dino.hotel.api.hotel.command.application.dto.RoomDto;
import com.dino.hotel.api.hotel.command.application.service.HotelCreateService;
import com.dino.hotel.api.hotel.command.domain.Address;
import com.dino.hotel.api.infra.security.SecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
public class AdminHotelControllerTest extends BaseControllerTest {

    @MockBean
    private HotelCreateService hotelCreateService;

    @BeforeEach
    public void init() throws Exception {
        login("admin", "qwer1234!@");
    }

    @Test
    @DisplayName("Hotel 생성 시 요청 바디 유효성 에러")
    void whenHotelCreateThenInvalidRequestCauseHttpBodyNull() throws Exception {
        Address address = AddressBuilder.builder().build();
        String name = "5성호텔";
        List<RoomDto> rooms = List.of(RoomDto.of(1L, 1, 101, "101호"));

        HotelDto hotelDto = HotelDto.of(address, name, rooms);

        doThrow(new CustomNullPointerException("hotelDto is null"))
                .when(hotelCreateService).create(any(HotelDto.class));

        mvcPerform(post("/admin/hotel"), hotelDto)
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Hotel 생성 시 HotelDto rooms가 비어있는 경우")
    void whenHotelCreateThenInvalidRequestCauseEmptyRooms() throws Exception {
        Address address = AddressBuilder.builder().build();
        String name = "5성호텔";
        List<RoomDto> rooms = List.of();

        HotelDto hotelDto = HotelDto.of(address, name, rooms);

        mvcPerform(post("/admin/hotel"), hotelDto)
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Hotel 생성")
    void whenHotelCreateThenSuccess() throws Exception {
        Address address = AddressBuilder.builder().build();
        String name = "5성호텔";
        List<RoomDto> rooms = List.of(RoomDto.of(1L, 1, 101, "101호"));

        HotelDto hotelDto = HotelDto.of(address, name, rooms);

        mvcPerform(post("/admin/hotel"), hotelDto)
                .andExpect(status().isOk());
    }
}
