package com.dino.hotel.api.room.presentation.controller;

import com.dino.hotel.api.helper.BaseControllerTest;
import com.dino.hotel.api.helper.dto.TestRoomDto;
import com.dino.hotel.api.hotel.command.domain.exception.HotelNotFoundException;
import com.dino.hotel.api.room.command.application.service.RoomAddService;
import com.dino.hotel.api.room.command.application.service.RoomRemoveService;
import com.dino.hotel.api.room.command.domain.exception.RoomNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = AdminRoomController.class)
@ActiveProfiles("test")
public class AdminRoomControllerTest extends BaseControllerTest {

    @MockBean
    private RoomAddService roomAddService;

    @MockBean
    private RoomRemoveService roomRemoveService;

    @WithMockUser
    @Test
    @DisplayName("Room 추가 시 hotelId가 null인 경우")
    void whenRoomAddThenInternalServerErrorCauseHotelIdNull() throws Exception {
        Long hotelId = null;
        TestRoomDto roomDto = TestRoomDto.of(1L, 1, 102, "102호");

        mvcPerformPostRoom(hotelId, roomDto, status().isInternalServerError());
    }

    @WithMockUser
    @ParameterizedTest
    @ValueSource(longs = {0, -1})
    @DisplayName("Room 추가 시 hotelId가 양수가 아닌 경우")
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

    @WithMockUser
    @Test
    @DisplayName("Room 삭제 시 HotelId가 빈 문자열인 경우")
    void whenRoomRemoveThenInternalServerErrorCauseHotelIdNull() throws Exception {
        Long hotelId = null;
        Long roomId = 1L;

        mvcPerformDeleteRoom(hotelId, roomId, status().isInternalServerError());
    }

    @WithMockUser
    @ParameterizedTest
    @ValueSource(longs = {0, -1})
    @DisplayName("Room 삭제 시 HotelId가 음수인 경우")
    void whenRoomRemoveThenBadRequestErrorCauseHotelIdNegative(Long hotelId) throws Exception {
        Long roomId = 1L;

        mvcPerformDeleteRoom(hotelId, roomId, status().isBadRequest());
    }

    @WithMockUser
    @Test
    @DisplayName("Room 삭제 시 RoomId가 빈 문자열인 경우")
    void whenRoomRemoveThenInternalServerErrorCauseRoomIdNull() throws Exception {
        Long hotelId = 1L;
        Long roomId = null;

        mvcPerformDeleteRoom(hotelId, roomId, status().isInternalServerError());
    }

    @WithMockUser
    @ParameterizedTest
    @ValueSource(longs = {0, -1})
    @DisplayName("Room 삭제 시 RoomId가 양수가 아닌 경우")
    void whenRoomRemoveThenBadRequestCauseRoomIdNegative(Long roomId) throws Exception {
        Long hotelId = 1L;

        mvcPerformDeleteRoom(hotelId, roomId, status().isBadRequest());
    }

    @WithMockUser
    @Test
    @DisplayName("Room 삭제 시 Hotel이 존재하지 않을 경우")
    void whenRoomRemoveThenNotFoundCauseHotelNotFound() throws Exception {
        Long hotelId = 1L;
        Long roomId = 1L;

        doThrow(new HotelNotFoundException("Hotel not found"))
                .when(roomRemoveService).remove(anyLong(), anyLong());

        mvcPerformDeleteRoom(hotelId, roomId, status().isNotFound());
    }

    @WithMockUser
    @Test
    @DisplayName("Room 삭제 시 삭제 대상 Room이 존재하지 않을 경우")
    void whenRoomRemoveThenNotFoundCauseRoomNotFound() throws Exception {
        Long hotelId = 1L;
        Long roomId = 1L;

        doThrow(new RoomNotFoundException("Room not found"))
                .when(roomRemoveService).remove(anyLong(), anyLong());

        mvcPerformDeleteRoom(hotelId, roomId, status().isNotFound());
    }

    private void mvcPerformPostRoom(Long hotelId, TestRoomDto roomDto, ResultMatcher resultMatcher) throws Exception {
        mvcPerform(post("/admin/hotels/" + (hotelId == null ? " " : hotelId) + "/rooms"), roomDto)
                .andExpect(resultMatcher)
                .andDo(print());
    }

    private void mvcPerformDeleteRoom(Long hotelId, Long roomId, ResultMatcher resultMatcher) throws Exception {
        mvcPerform(delete("/admin/hotels/" + (hotelId == null ? " " : hotelId) + "/rooms/" + (roomId == null ? " " : roomId)), null)
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
