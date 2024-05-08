package com.dino.hotel.api.hotel.command.domain.repository;

import com.dino.hotel.api.helper.BaseRepositoryTest;
import com.dino.hotel.api.helper.builder.RoomTypeBuilder;
import com.dino.hotel.api.hotel.command.domain.RoomType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class RoomTypeRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Test
    @DisplayName("RoomType 엔티티 저장")
    void createHotelEntitySave(){
        RoomType roomType = RoomTypeBuilder.builder().build();

        RoomType save = roomTypeRepository.save(roomType);

        assertThat(save).isNotNull();
        assertThat(save.getId()).isNotNull();
    }
}
