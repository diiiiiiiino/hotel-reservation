package com.dino.hotel.api.hotel.command.domain;

import com.dino.hotel.api.util.VerifyUtil;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    protected Room(Long id,
                 Hotel hotel,
                 RoomType roomType,
                 Integer floor,
                 Integer number,
                 String name,
                 boolean isAvailable) {
        this(hotel, roomType, floor, number, name, isAvailable);
        setId(id);
    }

    private Room(Hotel hotel,
                 RoomType roomType,
                 Integer floor,
                 Integer number,
                 String name,
                 boolean isAvailable) {
        setHotel(hotel);
        setRoomType(roomType);
        setFloor(floor);
        setNumber(number);
        setName(name);
        setAvailable(isAvailable);
    }

    public static Room of(Long id,
                          Hotel hotel,
                          RoomType roomType,
                          Integer floor,
                          Integer number,
                          String name,
                          boolean isAvailable){
        return new Room(id, hotel, roomType, floor, number, name, isAvailable);
    }

    public static Room of(Hotel hotel,
                          RoomType roomType,
                          Integer floor,
                          Integer number,
                          String name,
                          boolean isAvailable){
        return new Room(hotel, roomType, floor, number, name, isAvailable);
    }

    public boolean equalId(Long roomId){
        VerifyUtil.verifyPositiveOrZero(roomId, "roomId");

        return this.id.equals(roomId);
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

    private void setId(Long id) {
        this.id = id;
    }

    private void setHotel(Hotel hotel) {
        VerifyUtil.verifyNull(hotel, "hotel");
        this.hotel = hotel;
    }

    private void setRoomType(RoomType roomType) {
        VerifyUtil.verifyNull(roomType, "roomType");
        this.roomType = roomType;
    }

    private void setFloor(Integer floor) {
        VerifyUtil.verifyNegative(floor, "floor");
        this.floor = floor;
    }

    private void setNumber(Integer number) {
        VerifyUtil.verifyNegative(number, "number");
        this.number = number;
    }

    private void setName(String name) {
        VerifyUtil.verifyText(name, "name");
        this.name = name;
    }

    private void setAvailable(boolean available) {
        isAvailable = available;
    }
}
