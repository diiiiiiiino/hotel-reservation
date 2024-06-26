package com.dino.hotel.api.login.command.domain;

import com.dino.hotel.api.helper.builder.MemberCreateHelperBuilder;
import com.dino.hotel.api.member.command.domain.Member;
import com.dino.hotel.api.member.command.domain.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static com.dino.hotel.api.helper.util.JpaUtils.flushAndClear;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class LoginRecordRepositoryTest {
    @Autowired
    LoginRecordRepository loginRecordRepository;

    @Autowired
    MemberRepository memberRepository;

    @PersistenceContext
    EntityManager entityManager;

    @DisplayName("로그인 기록 저장")
    @Test
    void loginHistoryCreate() {
        Member member = MemberCreateHelperBuilder.builder().build();
        memberRepository.save(member);

        LoginRecord loginRecord = LoginRecord.builder(member, LocalDateTime.of(2023, 7, 28, 12, 23, 59)).build();
        loginRecord = loginRecordRepository.save(loginRecord);

        flushAndClear(entityManager);

        assertThat(loginRecord).isNotNull();
        assertThat(loginRecord.getLoginTime()).isEqualTo(LocalDateTime.of(2023, 7, 28, 12, 23, 59));
    }
}
