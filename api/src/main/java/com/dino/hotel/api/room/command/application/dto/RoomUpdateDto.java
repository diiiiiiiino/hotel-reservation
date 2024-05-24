package com.dino.hotel.api.room.command.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RoomUpdateDto {
    private Long roomTypeId;
    private Integer floor;
    private Integer number;
    private String name;
    private boolean isAvailable;

    public static RoomUpdateDto of(Long roomTypeId,
                          Integer floor,
                          Integer number,
                          String name,
                          boolean isAvailable){
        return new RoomUpdateDto(roomTypeId, floor, number, name, isAvailable);
    }
}
