package com.meeplelog.backend.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Test
    @DisplayName("User.of()로 사용자를 생성한다")
    void of_success() {
        // when
        User user = User.of("테스트유저", "testUser", "password123", "http://profile.url");

        // then
        assertThat(user).isNotNull();
        assertThat(user.getName()).isEqualTo("테스트유저");
        assertThat(user.getUsername()).isEqualTo("testUser");
        assertThat(user.getPassword()).isEqualTo("password123");
        assertThat(user.getImageUrl()).isEqualTo("http://profile.url");
    }

    @Test
    @DisplayName("User.of()로 사용자를 생성한다")
    void forTest_success() {
        // when
        User user = User.forTest(1L,"테스트유저", "testUser", "password123", "http://profile.url");

        // then
        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getName()).isEqualTo("테스트유저");
        assertThat(user.getUsername()).isEqualTo("testUser");
        assertThat(user.getPassword()).isEqualTo("password123");
        assertThat(user.getImageUrl()).isEqualTo("http://profile.url");
    }

    @Test
    @DisplayName("이미지 URL 없이 사용자를 생성할 수 있다")
    void of_withoutImageUrl() {
        // when
        User user = User.of("테스트유저", "testUser", "password123", null);

        // then
        assertThat(user).isNotNull();
        assertThat(user.getName()).isEqualTo("테스트유저");
        assertThat(user.getUsername()).isEqualTo("testUser");
        assertThat(user.getImageUrl()).isNull();
    }

    @Test
    @DisplayName("사용자는 이름, 아이디, 비밀번호를 가진다")
    void hasRequiredFields() {
        // when
        User user = User.of("테스트", "test", "pass", null);

        // then
        assertThat(user.getName()).isNotNull();
        assertThat(user.getUsername()).isNotNull();
        assertThat(user.getPassword()).isNotNull();
    }
}
