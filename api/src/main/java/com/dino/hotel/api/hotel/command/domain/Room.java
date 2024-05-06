package com.dino.hotel.api.hotel.command.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Hotel hotel;

    private String roomTypeId;
    private Integer floor;
    private Integer number;
    private String name;
    private boolean isAvailable;

    private Room(Hotel hotel,
                 String roomTypeId,
                 Integer floor,
                 Integer number,
                 String name,
                 boolean isAvailable) {
        this.hotel = hotel;
        this.roomTypeId = roomTypeId;
        this.floor = floor;
        this.number = number;
        this.name = name;
        this.isAvailable = isAvailable;
    }

    public static Room of(Hotel hotel,
                          String roomTypeId,
                          Integer floor,
                          Integer number,
                          String name,
                          boolean isAvailable){
        return new Room(hotel, roomTypeId, floor, number, name, isAvailable);
    }

    void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
}
