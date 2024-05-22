package com.dino.hotel.api.helper.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TestRoomDto {
    private Long roomTypeId;
    private Integer floor;
    private Integer number;
    private String name;

    public static TestRoomDto of(
            Long roomTypeId,
            Integer floor,
            Integer number,
            String name){

        return new TestRoomDto(roomTypeId, floor, number, name);
    }
}
