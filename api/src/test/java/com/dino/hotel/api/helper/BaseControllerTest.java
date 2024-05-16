package com.dino.hotel.api.helper;

import com.dino.hotel.api.login.command.application.dto.LoginRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@AutoConfigureMockMvc
public abstract class BaseControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    private ObjectMapper objectMapper;
    protected String token;

    public BaseControllerTest() {
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    protected String writeValueAsString(Object data) throws JsonProcessingException {
        return objectMapper.writeValueAsString(data);
    }

    protected void login(String loginId, String password) throws Exception {
        LoginRequest loginRequest = LoginRequest.builder()
                .loginId(loginId)
                .password(password)
                .build();

        MvcResult mvcResult = mockMvc.perform(post("/login")
                        .content(writeValueAsString(loginRequest)))
                .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();

        token = response.getHeader(HttpHeaders.AUTHORIZATION);
    }

    protected ResultActions mvcPerform(MockHttpServletRequestBuilder builder, Object object) throws Exception {
        if(object != null){
            builder.content(writeValueAsString(object))
                    .with(SecurityMockMvcRequestPostProcessors.csrf());
        }

        return mockMvc.perform(builder
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
