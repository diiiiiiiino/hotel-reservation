package com.dino.hotel.api.reservation.command.application.service;

import com.dino.hotel.api.helper.builder.HotelBuilder;
import com.dino.hotel.api.helper.builder.MemberCreateHelperBuilder;
import com.dino.hotel.api.helper.builder.ReservationDtoBuilder;
import com.dino.hotel.api.hotel.command.domain.Hotel;
import com.dino.hotel.api.member.command.domain.Member;
import com.dino.hotel.api.reservation.command.application.dto.ReservationDto;
import com.dino.hotel.api.reservation.command.application.service.ReservationCreateService;
import com.dino.hotel.api.reservation.command.domain.RoomTypeInventory;
import com.dino.hotel.api.reservation.command.domain.RoomTypeInventoryId;
import jakarta.persistence.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
public class RoomTypeInventoryConcurrencyTest {

    @Autowired
    private ReservationCreateService reservationCreateService;

    @PersistenceUnit
    private EntityManagerFactory emf;

    Member init(){
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Hotel hotel = HotelBuilder.builder().build();
        entityManager.persist(hotel);

        RoomTypeInventoryId roomTypeInventoryId = RoomTypeInventoryId.of(hotel.getId(), 1L, LocalDateTime.of(2024, 05, 11, 0, 0));

        RoomTypeInventory roomTypeInventory = RoomTypeInventory.of(roomTypeInventoryId, hotel, 100, 98);

        RoomTypeInventoryId roomTypeInventoryId2 = RoomTypeInventoryId.of(hotel.getId(), 1L, LocalDateTime.of(2024, 05, 12, 0, 0));

        RoomTypeInventory roomTypeInventory2 = RoomTypeInventory.of(roomTypeInventoryId2, hotel, 100, 98);

        entityManager.persist(roomTypeInventory);
        entityManager.persist(roomTypeInventory2);

        Member member = MemberCreateHelperBuilder.builder().build();
        entityManager.persist(member);

        transaction.commit();

        entityManager.close();

        return member;
    }

    @Test
    @DisplayName("예약 낙관적 락 처리")
    void reserveOptimisticLock() throws InterruptedException {
        Member member = init();

        ReservationDto reservationDto = ReservationDtoBuilder.builder()
                .start(LocalDateTime.of(2024, 05, 11, 0, 0, 0))
                .end(LocalDateTime.of(2024, 05, 12, 0, 0, 0))
                .build();

        int numberOfThreads = 2;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        AtomicInteger atomicInteger = new AtomicInteger();

        Runnable runnable = () -> {
            try {
                reservationCreateService.create(member, reservationDto);
                atomicInteger.incrementAndGet();
            } catch (ObjectOptimisticLockingFailureException e){
                System.out.println("에러 발생");
            }
        };

        Future<?> submit = executorService.submit(runnable);
        Future<?> submit2 = executorService.submit(runnable);

        try {
            submit.get();
            submit2.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        assertThat(atomicInteger.get()).isEqualTo(1);
    }
}
