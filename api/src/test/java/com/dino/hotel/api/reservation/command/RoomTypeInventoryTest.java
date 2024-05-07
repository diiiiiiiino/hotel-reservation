package com.dino.hotel.api.reservation.command;

import com.dino.hotel.api.common.exception.CustomIllegalArgumentException;
import com.dino.hotel.api.common.exception.CustomNullPointerException;
import com.dino.hotel.api.helper.builder.HotelBuilder;
import com.dino.hotel.api.helper.builder.RoomTypeInventoryIdBuilder;
import com.dino.hotel.api.hotel.command.domain.Hotel;
import com.dino.hotel.api.reservation.command.domain.RoomTypeInventory;
import com.dino.hotel.api.reservation.command.domain.RoomTypeInventoryId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class RoomTypeInventoryTest {

    @ParameterizedTest
    @MethodSource("whenCreateRoomTypeInventoryThenExceptionSource")
    @DisplayName("예약 생성 시 유효하지 않은 값이 입력될 때 예외 발생")
    void whenCreateRoomTypeInventoryThenException(RoomTypeInventoryId id,
                                            Hotel hotel,
                                            Integer totalInventory,
                                            Integer totalReserved,
                                            Class<Exception> exceptionClass) {
        Assertions.assertThatThrownBy(() -> RoomTypeInventory.of(id, hotel, totalInventory, totalReserved))
                .isInstanceOf(exceptionClass);
    }

    public static Stream<Arguments> whenCreateRoomTypeInventoryThenExceptionSource(){
        RoomTypeInventoryId id = RoomTypeInventoryIdBuilder.builder().build();
        Hotel hotel = HotelBuilder.builder().build();
        Integer totalInventory = 100;
        Integer totalReserved = 80;

        return Stream.of(
                Arguments.of(null, hotel, totalInventory, totalReserved, CustomNullPointerException.class),
                Arguments.of(id, null, totalInventory, totalReserved, CustomNullPointerException.class),
                Arguments.of(id, hotel, null, totalReserved, CustomNullPointerException.class),
                Arguments.of(id, hotel, totalInventory, null, CustomNullPointerException.class),
                Arguments.of(id, hotel, -totalInventory, totalReserved, CustomIllegalArgumentException.class),
                Arguments.of(id, hotel, totalInventory, -totalReserved, CustomIllegalArgumentException.class)
        );
    }
}
