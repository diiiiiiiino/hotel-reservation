package com.dino.hotel.api.rate.command;

import com.dino.hotel.api.common.exception.CustomIllegalArgumentException;
import com.dino.hotel.api.common.exception.CustomNullPointerException;
import com.dino.hotel.api.helper.builder.RoomBuilder;
import com.dino.hotel.api.hotel.command.domain.Address;
import com.dino.hotel.api.hotel.command.domain.Hotel;
import com.dino.hotel.api.hotel.command.domain.Room;
import com.dino.hotel.api.rate.command.domain.RoomTypeRate;
import com.dino.hotel.api.rate.command.domain.RoomTypeRateId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RoomTypeRateTest {

    @ParameterizedTest
    @MethodSource("whenCreateRoomTypeRateIdThenExceptionSource")
    @DisplayName("RoomTypeRateId 생성 시 유효하지 않은 값 입력 시 예외 발생")
    void whenCreateRoomTypeRateIdThenException(Long hotelId, LocalDateTime date, Class clazz) {
        assertThatThrownBy(() -> RoomTypeRateId.of(hotelId, date))
                .isInstanceOf(clazz);
    }

    @ParameterizedTest
    @MethodSource("whenCreateRoomTypeRateThenExceptionSource")
    @DisplayName("RoomTypeRate 생성 시 유효하지 않은 값 입력 시 예외 발생")
    void whenCreateRoomTypeRateThenException(RoomTypeRateId id, Hotel hotel, Integer rate, Class clazz) {
        assertThatThrownBy(() -> RoomTypeRate.of(id, hotel, rate))
                .isInstanceOf(clazz);
    }

    static Stream<Arguments> whenCreateRoomTypeRateIdThenExceptionSource(){
        return Stream.of(
                Arguments.of(null, LocalDateTime.now(), CustomNullPointerException.class),
                Arguments.of(-1L, LocalDateTime.now(), CustomIllegalArgumentException.class),
                Arguments.of(1L, null, CustomNullPointerException.class)
        );
    }

    static Stream<Arguments> whenCreateRoomTypeRateThenExceptionSource(){
        RoomTypeRateId id = RoomTypeRateId.of(1L, LocalDateTime.now());
        List<Function<Hotel, Room>> functions = List.of(hotel -> RoomBuilder.builder().hotel(hotel).build());
        Hotel hotel = Hotel.of("5성호텔", Address.of("서울시", "동대문구", "123456"), functions);

        return Stream.of(
                Arguments.of(null, hotel, 1, CustomNullPointerException.class),
                Arguments.of(id, null, 1, CustomNullPointerException.class),
                Arguments.of(id, hotel, null, CustomNullPointerException.class),
                Arguments.of(id, hotel, -1, CustomIllegalArgumentException.class)
        );
    }
}
