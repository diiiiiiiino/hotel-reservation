package com.dino.hotel.api.hotel.command.domain;

import com.dino.hotel.api.common.exception.CustomIllegalArgumentException;
import com.dino.hotel.api.common.exception.CustomNullPointerException;
import com.dino.hotel.api.helper.builder.AddressBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class HotelTest {

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("호텔 생성 시 호텔명 누락")
    public void whenHotelCreateThenNameIsNull(String name){
        Address address = AddressBuilder.builder().build();

        assertThatThrownBy(() -> Hotel.of(name, address))
                .isInstanceOf(CustomIllegalArgumentException.class);
    }

    @Test
    @DisplayName("호텔 생성 시 주소 누락")
    public void whenHotelCreateThenAddressIsNull(){
        assertThatThrownBy(() -> Hotel.of("5성호텔", null))
                .isInstanceOf(CustomNullPointerException.class);
    }

    @Test
    @DisplayName("Null인 Room이 추가 될 때")
    void whenAddRoomThenNull() {
        Address address = AddressBuilder.builder().build();
        Hotel hotel = Hotel.of("5성호텔", address);

        assertThatThrownBy(() -> hotel.addRoom(null))
                .isInstanceOf(CustomNullPointerException.class);
    }

    @Test
    @DisplayName("Null인 Room을 삭제 하려고 할때")
    void whenDeleteRoomThenNull() {
        Address address = AddressBuilder.builder().build();
        Hotel hotel = Hotel.of("5성호텔", address);

        assertThatThrownBy(() -> hotel.removeRoom(null))
                .isInstanceOf(CustomNullPointerException.class);
    }

    @ParameterizedTest
    @MethodSource("whenCreateAddressThenArgumentExceptionSource")
    @DisplayName("주소 생성 시 유효하지 않은 값 입력 시 예외 발생")
    void whenCreateAddressThenArgumentException(String address1, String address2, String zipNo) {
        assertThatThrownBy(() -> Address.of(address1, address2, zipNo))
                .isInstanceOf(CustomIllegalArgumentException.class);
    }

    static Stream<Arguments> whenCreateAddressThenArgumentExceptionSource(){
        return Stream.of(
                Arguments.of(null, "동대문", "123456"),
                Arguments.of("", "동대문", "123456"),
                Arguments.of("서울시", null, "123456"),
                Arguments.of("서울시", "", "123456"),
                Arguments.of("서울시", "동대문", null),
                Arguments.of("서울시", "동대문", "")
        );
    }
}
