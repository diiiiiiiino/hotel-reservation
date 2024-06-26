package com.dino.hotel.api.login.presentation;

import com.dino.hotel.api.helper.BaseControllerTest;
import com.dino.hotel.api.login.command.application.dto.LoginRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
public class LoginControllerTest extends BaseControllerTest {

    @DisplayName("로그인 실패")
    @Test
    void loginFail() throws Exception {
        LoginRequest loginRequest = LoginRequest.builder()
                .loginId("abcde")
                .password("qwer12345")
                .build();

        mockMvc.perform(post("/login")
                        .content(writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("로그인 성공")
    @Test
    void loginSuccess() throws Exception {
        LoginRequest loginRequest = LoginRequest.builder()
                .loginId("abcde")
                .password("qwer1234!@")
                .build();

        MvcResult mvcResult = mockMvc.perform(post("/login")
                        .content(writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();

        String token = response.getHeader(HttpHeaders.AUTHORIZATION);
        System.out.println(token);
    }
}
