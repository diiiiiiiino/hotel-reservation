package com.dino.hotel.api.member.presentation.controller;

import com.dino.hotel.api.authority.command.application.exception.AuthorityNotFoundException;
import com.dino.hotel.api.common.exception.ValidationCode;
import com.dino.hotel.api.common.exception.ValidationError;
import com.dino.hotel.api.common.exception.ValidationErrorException;
import com.dino.hotel.api.helper.BaseControllerTest;
import com.dino.hotel.api.member.command.application.dto.AdminCreateRequest;
import com.dino.hotel.api.member.command.application.exception.MemberNotFoundException;
import com.dino.hotel.api.member.command.application.service.AdminChangeService;
import com.dino.hotel.api.member.command.application.service.AdminCreateService;
import com.dino.hotel.api.member.command.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
public class AdminControllerTest extends BaseControllerTest {

    @MockBean
    private AdminCreateService adminCreateService;

    @MockBean
    private AdminChangeService adminChangeService;


    @BeforeEach
    void beforeEach() throws Exception {
        login("admin", "qwer1234!@");
    }

    @DisplayName("관리자 생성 요청이 비정상적일 때")
    @Test
    void createAdminWithMissMemberCreateRequest() throws Exception {
        List<ValidationError> errors = new ArrayList<>();
        errors.add(ValidationError.of("memberLoginId", ValidationCode.NO_TEXT.getValue()));
        errors.add(ValidationError.of("memberPassword", ValidationCode.LENGTH.getValue()));

        AdminCreateRequest adminCreateRequest = AdminCreateRequest.of(null, "qwer1234!@", "홍길동", "01012345678");

        doThrow(new ValidationErrorException("Request has invalid values", errors))
                .when(adminCreateService).create(any(AdminCreateRequest.class));

        mvcPerform(post("/admin"), adminCreateRequest)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isNotEmpty());
    }

    @DisplayName("관리자 생성")
    @Test
    void createAdminSuccess() throws Exception {
        AdminCreateRequest adminCreateRequest = AdminCreateRequest.of("loginId", "qwer1234!@", "홍길동", "01012345678");

        mvcPerform(post("/admin"), adminCreateRequest)
                .andExpect(status().isOk());
    }

    @DisplayName("관리자로 변경하려는 회원이 존재하지 않을 경우")
    @Test
    void targetMemberNotFound() throws Exception {
        doThrow(new MemberNotFoundException("Target member not found"))
                .when(adminChangeService).change(any(Member.class), anyLong());

        mvcPerform(patch("/admin/1"), null)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").isNotEmpty());
    }

    @DisplayName("변경하려는 권한이 비활성화 된 경우")
    @Test
    void changeAuthorityIsNonActive() throws Exception {
        doThrow(new AuthorityNotFoundException("Authority not found"))
                .when(adminChangeService).change(any(Member.class), anyLong());

        mvcPerform(patch("/admin/1"), null)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").isNotEmpty());
    }

    @DisplayName("관리자 권한 변경")
    @Test
    void changeAuthority() throws Exception {
        mvcPerform(patch("/admin/1"), null)
                .andExpect(status().isOk());
    }
}
