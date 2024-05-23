package com.dino.hotel.api.hotel.command.domain;

import com.dino.hotel.api.util.VerifyUtil;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class RoomType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    private RoomType(Long id){
        this.id = id;
    }

    private RoomType(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public static RoomType of(String name, String description){
        VerifyUtil.verifyText(name, "name");
        VerifyUtil.verifyText(description, "description");

        return new RoomType(name, description);
    }

    public static RoomType of(Long id){
        VerifyUtil.verifyPositiveOrZero(id, "id");

        return new RoomType(id);
    }
}
