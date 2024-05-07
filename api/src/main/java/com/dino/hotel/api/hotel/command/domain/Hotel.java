package com.dino.hotel.api.hotel.command.domain;

import com.dino.hotel.api.util.VerifyUtil;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
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
        VerifyUtil.verifyText(name, "hotelName");
        VerifyUtil.verifyNull(address, "hotelAddress");

        return new Hotel(name, address);
    }

    public void addRoom(Room room){
        VerifyUtil.verifyNull(room, "hotelRoom");
        this.rooms.add(room);
    }

    public void removeRoom(Room room){
        VerifyUtil.verifyNull(room, "hotelRoom");
        this.rooms.remove(room);
    }
}
