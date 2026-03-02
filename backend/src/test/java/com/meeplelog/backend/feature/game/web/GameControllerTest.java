package com.meeplelog.backend.feature.game.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meeplelog.backend.RestDocsConfig;
import com.meeplelog.backend.TestSecurityConfig;
import com.meeplelog.backend.domain.Game;
import com.meeplelog.backend.feature.game.usecase.AddGameUseCase;
import com.meeplelog.backend.feature.game.web.dto.AddGameRequest;
import com.meeplelog.backend.security.JwtAuthenticationFilter;
import com.meeplelog.backend.security.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        value = GameController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = {
                        SecurityConfig.class,
                        JwtAuthenticationFilter.class
                }
        )
)
@AutoConfigureRestDocs
@Import(
        {
                RestDocsConfig.class,
                TestSecurityConfig.class
        }
)
class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AddGameUseCase addGameUseCase;

    @Test
    @DisplayName("게임 추가 API 문서화")
    void addGame() throws Exception {
        // given
        AddGameRequest request = new AddGameRequest("Catan", "http://image.url");
        Game game = Game.of("Catan", "http://image.url");
        given(addGameUseCase.addGame(any(AddGameRequest.class))).willReturn(game);

        // when
        ResultActions result = this.mockMvc.perform(
                post("/game/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );

        // then
        result.andExpect(status().isOk())
                .andDo(document("game-add",
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("게임 이름"),
                                fieldWithPath("imageUrl").type(JsonFieldType.STRING).description("게임 이미지 URL")
                        ),
                        responseFields(
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지")
                        )
                ));
    }
}
