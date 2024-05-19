package com.dino.hotel.api.hotel.command.domain;

import com.dino.hotel.api.util.VerifyUtil;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Room {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Hotel hotel;

    @ManyToOne(fetch = FetchType.LAZY)
    private RoomType roomType;

    private Integer floor;
    private Integer number;
    private String name;
    private boolean isAvailable;

    private Room(Hotel hotel,
                 RoomType roomType,
                 Integer floor,
                 Integer number,
                 String name,
                 boolean isAvailable) {
        this.hotel = hotel;
        this.roomType = roomType;
        this.floor = floor;
        this.number = number;
        this.name = name;
        this.isAvailable = isAvailable;
    }

    public static Room of(Hotel hotel,
                          RoomType roomType,
                          Integer floor,
                          Integer number,
                          String name,
                          boolean isAvailable){
        VerifyUtil.verifyNull(hotel, "hotel");
        VerifyUtil.verifyNull(roomType, "roomType");
        VerifyUtil.verifyNegative(floor, "floor");
        VerifyUtil.verifyNegative(number, "number");
        VerifyUtil.verifyText(name, "name");

        return new Room(hotel, roomType, floor, number, name, isAvailable);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return isAvailable == room.isAvailable && Objects.equals(id, room.id) && Objects.equals(floor, room.floor) && Objects.equals(number, room.number) && Objects.equals(name, room.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, floor, number, name, isAvailable);
    }
}
