package com.dino.hotel.api.hotel.command.domain;

import com.dino.hotel.api.common.exception.CustomIllegalArgumentException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RoomTypeTest {

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("RoomType 생성 시 이름이 유효하지 않은 값인 경우")
    void whenCreateRoomThenNameInvalid(String name) {
        assertThatThrownBy(() -> RoomType.of(name, "오션뷰"))
                .isInstanceOf(CustomIllegalArgumentException.class);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("RoomType 생성 시 설명이 유효하지 않은 값인 경우")
    void whenCreateRoomThenDescriptionInvalid(String description) {
        assertThatThrownBy(() -> RoomType.of("디럭스", description))
                .isInstanceOf(CustomIllegalArgumentException.class);
    }
}
