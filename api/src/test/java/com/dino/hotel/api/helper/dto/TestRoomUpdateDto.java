package com.dino.hotel.api.helper.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TestRoomUpdateDto {
    private Long roomTypeId;
    private Integer floor;
    private Integer number;
    private String name;
    private boolean isAvailable;

    public static TestRoomUpdateDto of(Long roomTypeId,
                                       Integer floor,
                                       Integer number,
                                       String name,
                                       boolean isAvailable){
        return new TestRoomUpdateDto(roomTypeId, floor, number, name, isAvailable);
    }
}
