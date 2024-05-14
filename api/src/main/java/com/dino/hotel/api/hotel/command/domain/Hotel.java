package com.dino.hotel.api.hotel.command.domain;

import com.dino.hotel.api.util.VerifyUtil;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

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

    private Hotel(String name, Address address, List<Function<Hotel, Room>> functions) {
        this.name = name;
        this.address = address;
        setRooms(functions);
    }

    private Hotel(Long id, String name, Address address, List<Function<Hotel, Room>> functions) {
        this(name, address, functions);
        this.id = id;
    }

    public static Hotel of(String name, Address address, List<Function<Hotel, Room>> functions){
        VerifyUtil.verifyText(name, "hotelName");
        VerifyUtil.verifyNull(address, "hotelAddress");
        VerifyUtil.verifyCollection(functions, "hotelFunctions");

        return new Hotel(name, address, functions);
    }

    public static Hotel of(Long id, String name, Address address, List<Function<Hotel, Room>> functions){
        VerifyUtil.verifyText(name, "hotelName");
        VerifyUtil.verifyNull(address, "hotelAddress");
        VerifyUtil.verifyCollection(functions, "hotelFunctions");

        return new Hotel(id, name, address, functions);
    }

    public void addRoom(Room room){
        VerifyUtil.verifyNull(room, "hotelRoom");
        this.rooms.add(room);
    }

    public void removeRoom(Room room){
        VerifyUtil.verifyNull(room, "hotelRoom");
        this.rooms.remove(room);
    }

    private void setRooms(List<Function<Hotel, Room>> functions){
        VerifyUtil.verifyCollection(functions, "hotelFunctions");

        List<Room> rooms = new ArrayList<>();
        for(Function<Hotel, Room> function : functions){
            rooms.add(function.apply(this));
        }

        this.rooms = rooms;
    }
}
