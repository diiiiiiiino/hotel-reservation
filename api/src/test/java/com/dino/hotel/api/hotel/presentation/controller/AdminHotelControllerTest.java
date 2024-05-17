package com.dino.hotel.api.hotel.presentation.controller;

import com.dino.hotel.api.helper.BaseControllerTest;
import com.dino.hotel.api.helper.builder.AddressBuilder;
import com.dino.hotel.api.helper.dto.TestHotelDto;
import com.dino.hotel.api.helper.dto.TestHotelUpdateDto;
import com.dino.hotel.api.hotel.command.application.dto.HotelUpdateDto;
import com.dino.hotel.api.hotel.command.application.dto.RoomDto;
import com.dino.hotel.api.hotel.command.application.service.HotelCreateService;
import com.dino.hotel.api.hotel.command.application.service.HotelUpdateService;
import com.dino.hotel.api.hotel.command.domain.Address;
import com.dino.hotel.api.hotel.command.domain.exception.HotelNotFoundException;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = AdminHotelController.class)
@ActiveProfiles("test")
public class AdminHotelControllerTest extends BaseControllerTest {

    @MockBean
    private HotelCreateService hotelCreateService;

    @MockBean
    private HotelUpdateService hotelUpdateService;

    @WithMockUser
    @Test
    @DisplayName("Hotel 생성 시 요청 바디 유효성 에러")
    void whenHotelCreateThenInvalidRequestCauseHttpBodyNull() throws Exception {
        mvcPerform(post("/admin/hotel"), null)
                .andExpect(status().isInternalServerError())
                .andDo(print());
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

    @WithMockUser
    @Test
    @DisplayName("Hotel 수정 시 요청 바디가 null인 경우")
    void whenHotelUpdateThenInvalidRequestCauseHttpBodyNull() throws Exception {
        mvcPerform(patch("/admin/hotel"), null)
                .andExpect(status().isInternalServerError())
                .andDo(print());
    }

    @WithMockUser
    @Test
    @DisplayName("Hotel 수정 시 Hotel이 존재하지 않는 경우")
    void whenHotelUpdateThenNotFoundEx() throws Exception {
        Long id = 1L;
        Address address = AddressBuilder.builder().build();
        String name = "5성호텔";

        doThrow(new HotelNotFoundException("Hotel not found"))
                .when(hotelUpdateService).update(any(HotelUpdateDto.class));

        mvcPerformPatchHotel(id, address, name, status().isNotFound());
    }

    @WithMockUser
    @Test
    @DisplayName("Hotel 수정 시 id가 음수인 경우")
    void whenHotelUpdateThenInvalidRequestCauseIdNegative() throws Exception {
        Long id = -1L;
        Address address = AddressBuilder.builder().build();
        String name = "5성호텔";

        mvcPerformPatchHotel(id, address, name, status().isBadRequest());
    }

    @WithMockUser
    @Test
    @DisplayName("Hotel 수정 시 id가 null인 경우")
    void whenHotelUpdateThenInvalidRequestCauseIdNull() throws Exception {
        Long id = null;
        Address address = AddressBuilder.builder().build();
        String name = "5성호텔";

        mvcPerformPatchHotel(id, address, name, status().isBadRequest());
    }

    @WithMockUser
    @Test
    @DisplayName("Hotel 수정 시 address가 null인 경우")
    void whenHotelUpdateThenNullPointerExCauseAddressIsNull() throws Exception {
        Long id = 1L;
        Address address = null;
        String name = "5성호텔";

        mvcPerformPatchHotel(id, address, name, status().isBadRequest());
    }

    @WithMockUser
    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Hotel 수정 시 name이 null 또는 비어있는 경우")
    void whenHotelUpdateThenNullPointerExCauseInvalidName(String name) throws Exception {
        Long id = 1L;
        Address address = AddressBuilder.builder().build();

        mvcPerformPatchHotel(id, address, name, status().isBadRequest());
    }

    @WithMockUser
    @Test
    @DisplayName("Hotel 수정")
    void whenHotelUpdateSuccess() throws Exception {
        Long id = 1L;
        Address address = Address.of("경기도", "의정부시", "222222");
        String name = "4성호텔";

        mvcPerformPatchHotel(id, address, name, status().isOk());
    }

    private void mvcPerformPostHotel(List<RoomDto> rooms, Address address, String name, ResultMatcher resultMatcher) throws Exception {
        TestHotelDto hotelDto = TestHotelDto.of(address, name, rooms);

        mvcPerform(post("/admin/hotel"), hotelDto)
                .andExpect(resultMatcher)
                .andDo(print());
    }

    private void mvcPerformPatchHotel(Long id, Address address, String name, ResultMatcher resultMatcher) throws Exception {
        TestHotelUpdateDto hotelDto = TestHotelUpdateDto.of(id, address, name);

        mvcPerform(patch("/admin/hotel"), hotelDto)
                .andExpect(resultMatcher)
                .andDo(print());
    }
}
