package com.dino.hotel.api.room.presentation.controller;

import com.dino.hotel.api.helper.BaseControllerTest;
import com.dino.hotel.api.helper.dto.TestRoomDto;
import com.dino.hotel.api.room.command.application.service.RoomAddService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = AdminRoomController.class)
@ActiveProfiles("test")
public class AdminRoomControllerTest extends BaseControllerTest {

    @MockBean
    private RoomAddService roomAddService;

    @WithMockUser
    @Test
    @DisplayName("Room 추가 시 hotelId가 null인 경우")
    void whenRoomAddThenInternalServerErrorCauseHotelIdNull() throws Exception {
        Long hotelId = null;
        TestRoomDto roomDto = TestRoomDto.of(1L, 1, 102, "102호");

        mvcPerformPostRoom(hotelId, roomDto, status().isInternalServerError());
    }

    @WithMockUser
    @Test
    @DisplayName("Room 추가 시 hotelId가 음수인 경우")
    void whenRoomAddThenBadRequestCauseHotelIdNegative() throws Exception {
        Long hotelId = -1L;
        TestRoomDto roomDto = TestRoomDto.of(1L, 1, 102, "102호");

        mvcPerformPostRoom(hotelId, roomDto, status().isBadRequest());
    }

    @WithMockUser
    @Test
    @DisplayName("Room 추가 시 RoomDto가 null인 경우")
    void whenRoomAddThenInternalServerErrorCauseRoomDtoIsNull() throws Exception {
        Long hotelId = 1L;
        TestRoomDto roomDto = null;

        mvcPerformPostRoom(hotelId, roomDto, status().isInternalServerError());
    }

    @WithMockUser
    @ParameterizedTest
    @MethodSource("roomAddBadRequestSource")
    @DisplayName("Room 추가 시 RoomDto가 유효하지 않은 값인 경우")
    void whenRoomAddThenBadRequestCauseRoomDtoHasInvalidValue(TestRoomDto roomDto) throws Exception {
        Long hotelId = 1L;

        mvcPerformPostRoom(hotelId, roomDto, status().isBadRequest());
    }

    @WithMockUser
    @Test
    @DisplayName("Room 추가")
    void whenRoomAddThenSuccess() throws Exception {
        Long hotelId = 1L;
        TestRoomDto roomDto = TestRoomDto.of(1L, 1, 102, "102호");

        mvcPerformPostRoom(hotelId, roomDto, status().isOk());
    }

    private void mvcPerformPostRoom(Long hotelId, TestRoomDto roomDto, ResultMatcher resultMatcher) throws Exception {
        mvcPerform(post("/admin/hotels/" + (hotelId == null ? " " : hotelId) + "/rooms"), roomDto)
                .andExpect(resultMatcher)
                .andDo(print());
    }

    static Stream<Arguments> roomAddBadRequestSource(){
        return Stream.of(
                Arguments.of(TestRoomDto.of(null, 1 , 102, "102호")),
                Arguments.of(TestRoomDto.of(-1L, 1 , 102, "102호")),
                Arguments.of(TestRoomDto.of(1L, null , 102, "102호")),
                Arguments.of(TestRoomDto.of(1L, -1 , 102, "102호")),
                Arguments.of(TestRoomDto.of(1L, 1 , null, "102호")),
                Arguments.of(TestRoomDto.of(1L, 1 , -102, "102호")),
                Arguments.of(TestRoomDto.of(1L, 1 , 102, null)),
                Arguments.of(TestRoomDto.of(1L, 1 , 102, ""))
        );
    }
}
