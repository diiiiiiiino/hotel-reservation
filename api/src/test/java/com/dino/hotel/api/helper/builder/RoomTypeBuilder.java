package com.dino.hotel.api.helper.builder;

import com.dino.hotel.api.hotel.command.domain.RoomType;

public class RoomTypeBuilder {
    private String name = "디럭스";
    private String description = "오션뷰";

    public static RoomTypeBuilder builder(){
        return new RoomTypeBuilder();
    }

    public RoomTypeBuilder name(String name) {
        this.name = name;
        return this;
    }

    public RoomTypeBuilder description(String description) {
        this.description = description;
        return this;
    }

    public RoomType build(){
        return RoomType.of(name, description);
    }
}
