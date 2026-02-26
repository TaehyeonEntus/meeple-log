package com.meeplelog.backend.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GameTest {

    @Test
    @DisplayName("Game.of()로 게임을 생성한다")
    void of_success() {
        // when
        Game game = Game.of("스플렌더", "http://image.url");

        // then
        assertThat(game).isNotNull();
        assertThat(game.getName()).isEqualTo("스플렌더");
        assertThat(game.getImageUrl()).isEqualTo("http://image.url");
        assertThat(game.getCategories()).isEmpty();
    }

    @Test
    @DisplayName("Game.forTest()로 게임을 생성한다")
    void forTest_success() {
        // when
        Game game = Game.forTest(1L,"스플렌더", "http://image.url");

        // then
        assertThat(game).isNotNull();
        assertThat(game.getId()).isEqualTo(1L);
        assertThat(game.getName()).isEqualTo("스플렌더");
        assertThat(game.getImageUrl()).isEqualTo("http://image.url");
        assertThat(game.getCategories()).isEmpty();
    }


    @Test
    @DisplayName("이미지 URL 없이 게임을 생성할 수 있다")
    void of_withoutImageUrl() {
        // when
        Game game = Game.of("카탄", null);

        // then
        assertThat(game).isNotNull();
        assertThat(game.getName()).isEqualTo("카탄");
        assertThat(game.getImageUrl()).isNull();
    }

    @Test
    @DisplayName("게임에 카테고리를 추가한다")
    void addCategory_success() {
        // given
        Game game = Game.of("스플렌더", "http://image.url");
        Category category = Category.of("전략게임", "깊은 사고가 필요한 게임");

        // when
        game.addCategory(category);

        // then
        assertThat(game.getCategories()).hasSize(1);
        assertThat(game.getCategories().getFirst().getGame()).isEqualTo(game);
        assertThat(game.getCategories().getFirst().getCategory()).isEqualTo(category);
    }

    @Test
    @DisplayName("게임에 여러 카테고리를 추가할 수 있다")
    void addCategory_multiple() {
        // given
        Game game = Game.of("스플렌더", "http://image.url");
        Category category1 = Category.of("전략게임", "깊은 사고가 필요한 게임");
        Category category2 = Category.of("엔진빌딩", "자원을 모아 발전시키는 게임");

        // when
        game.addCategory(category1);
        game.addCategory(category2);

        // then
        assertThat(game.getCategories()).hasSize(2);
    }
}
