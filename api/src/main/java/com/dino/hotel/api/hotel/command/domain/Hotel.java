package com.dino.hotel.api.hotel.command.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Room> rooms = new ArrayList<>();

    @Embedded
    private Address address;
    private String name;

    private Hotel(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    public static Hotel of(String name, Address address){
        return new Hotel(name, address);
    }

    public void addRoom(Room room){
        this.rooms.add(room);
        room.setHotel(this);
    }
}
