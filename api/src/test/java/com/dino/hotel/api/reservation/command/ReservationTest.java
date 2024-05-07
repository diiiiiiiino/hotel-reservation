package com.dino.hotel.api.reservation.command;

import com.dino.hotel.api.common.exception.CustomIllegalArgumentException;
import com.dino.hotel.api.common.exception.CustomNullPointerException;
import com.dino.hotel.api.helper.builder.HotelBuilder;
import com.dino.hotel.api.helper.builder.MemberCreateHelperBuilder;
import com.dino.hotel.api.hotel.command.domain.Hotel;
import com.dino.hotel.api.member.command.domain.Member;
import com.dino.hotel.api.reservation.command.domain.Reservation;
import com.dino.hotel.api.reservation.command.domain.ReservationStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.stream.Stream;

public class ReservationTest {

    @ParameterizedTest
    @MethodSource("whenCreateReservationThenExceptionSource")
    @DisplayName("예약 생성 시 유효하지 않은 값이 입력될 때 예외 발생")
    void whenCreateReservationThenException(Hotel hotel,
                                            Member member,
                                            String roomTypeId,
                                            LocalDateTime startDate,
                                            LocalDateTime endDate,
                                            ReservationStatus status,
                                            Class<Exception> clazz) {
        Assertions.assertThatThrownBy(() -> Reservation.of(hotel, member, roomTypeId, startDate, endDate, status))
                .isInstanceOf(clazz);
    }

    @Test
    @DisplayName("예약 생성 시 예약 기간이 유효하지 않을 경우")
    void whenCreateReservationByInvalidPeriodThenException() {
        Hotel hotel = HotelBuilder.builder().build();
        Member member = MemberCreateHelperBuilder.builder().build();
        String roomTypeId = "123456";
        LocalDateTime startDate = LocalDateTime.of(2024, 5, 8, 12, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 5, 6, 12, 0, 0);
        ReservationStatus status = ReservationStatus.PENDING;

        Assertions.assertThatThrownBy(() -> Reservation.of(hotel, member, roomTypeId, startDate, endDate, status))
                .isInstanceOf(CustomIllegalArgumentException.class);
    }

    public static Stream<Arguments> whenCreateReservationThenExceptionSource(){
        Hotel hotel = HotelBuilder.builder().build();
        Member member = MemberCreateHelperBuilder.builder().build();
        String roomTypeId = "123456";
        LocalDateTime startDate = LocalDateTime.of(2024, 5, 7, 12, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 5, 9, 12, 0, 0);
        ReservationStatus status = ReservationStatus.PENDING;

        return Stream.of(
                Arguments.of(null, member, roomTypeId, startDate, endDate, status, CustomNullPointerException.class),
                Arguments.of(hotel, null, roomTypeId, startDate, endDate, status, CustomNullPointerException.class),
                Arguments.of(hotel, member, null, startDate, endDate, status, CustomIllegalArgumentException.class),
                Arguments.of(hotel, member, roomTypeId, null, endDate, status, CustomNullPointerException.class),
                Arguments.of(hotel, member, roomTypeId, startDate, null, status, CustomNullPointerException.class),
                Arguments.of(hotel, member, roomTypeId, startDate, endDate, null, CustomNullPointerException.class),
                Arguments.of(hotel, member, "", startDate, endDate, status, CustomIllegalArgumentException.class)
        );
    }
}
