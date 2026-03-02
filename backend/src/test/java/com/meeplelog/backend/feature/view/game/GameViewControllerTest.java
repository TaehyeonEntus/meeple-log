package com.meeplelog.backend.feature.view.game;

import com.meeplelog.backend.RestDocsConfig;
import com.meeplelog.backend.TestSecurityConfig;
import com.meeplelog.backend.feature.game.dto.GameDetail;
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
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        value = GameViewController.class,
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
class GameViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetGameResponseUseCase getGameResponseUseCase;

    @Test
    @DisplayName("게임 상세 정보 조회 API 문서화")
    void getGameResponse() throws Exception {
        // given
        Long gameId = 1L;
        GameDetail gameDetail = new GameDetail(gameId, "Catan", "http://image.url");
        GameResponse gameResponse = new GameResponse(gameDetail);
        given(getGameResponseUseCase.getGameResponse(gameId)).willReturn(gameResponse);

        // when
        ResultActions result = this.mockMvc.perform(
                get("/game/{gameId}", gameId)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(document("game-view-get",
                        responseFields(
                                fieldWithPath("gameDetail.id").type(JsonFieldType.NUMBER).description("게임 ID"),
                                fieldWithPath("gameDetail.name").type(JsonFieldType.STRING).description("게임 이름"),
                                fieldWithPath("gameDetail.imageUrl").type(JsonFieldType.STRING).description("게임 이미지 URL")
                        )
                ));
    }
}
