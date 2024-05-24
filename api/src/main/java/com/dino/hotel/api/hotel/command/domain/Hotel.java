package com.dino.hotel.api.hotel.command.domain;

import com.dino.hotel.api.room.command.application.dto.RoomUpdateDto;
import com.dino.hotel.api.room.command.domain.exception.RoomNotFoundException;
import com.dino.hotel.api.util.VerifyUtil;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    private void setRooms(List<Function<Hotel, Room>> functions){
        VerifyUtil.verifyCollection(functions, "hotelFunctions");

        List<Room> rooms = new ArrayList<>();
        for(Function<Hotel, Room> function : functions){
            rooms.add(function.apply(this));
        }

        this.rooms = rooms;
    }

    public void addRoom(Room room){
        VerifyUtil.verifyNull(room, "hotelRoom");
        this.rooms.add(room);
    }

    public void removeRoom(Long roomId){
        VerifyUtil.verifyPositive(roomId, "roomId");

        Optional<Room> optionalRoom = getRoom(roomId);

        if(optionalRoom.isEmpty()){
            throw new RoomNotFoundException("Room not found");
        } else {
            Room room = optionalRoom.get();
            this.rooms.remove(room);
        }
    }

    public void updateRoom(Long roomId, RoomUpdateDto roomUpdateDto){
        VerifyUtil.verifyPositive(roomId, "roomId");
        VerifyUtil.verifyNull(roomUpdateDto, "roomUpdateDto");

        Optional<Room> optionalRoom = getRoom(roomId);

        if(optionalRoom.isEmpty()){
            throw new RoomNotFoundException("Room not found");
        } else {
            Room room = optionalRoom.get();
            room.update(roomUpdateDto);
        }
    }

    public void update(Address address, String name) {
        VerifyUtil.verifyNull(address, "address");
        VerifyUtil.verifyText(name, "name");

        this.address = address;
        this.name = name;
    }

    public boolean hasRoom(Long roomId) {
        VerifyUtil.verifyPositive(roomId, "roomId");

        if(rooms.isEmpty()){
            return false;
        }

        return rooms.stream()
                .anyMatch(room -> room.equalId(roomId));
    }

    public Optional<Room> getRoom(Long roomId){
        if(!hasRoom(roomId))
            throw new RoomNotFoundException("Room not found");

        return rooms.stream()
                .filter(r -> r.equalId(roomId))
                .findFirst();
    }
}
