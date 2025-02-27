package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewsTest {

    @Test
    @DisplayName("크루들")
    void addAttendanceHistory() {
        // given
        List<String> names = List.of("a", "b", "c");
        Crews crews = new Crews(names);
        String username = "a";
        LocalDateTime time = LocalDateTime.of(2024, 12, 24, 10, 31);
        // when
        String result = crews.addAttendanceHistory(username, time);
        // then
        assertThat(result).isEqualTo("결석");
    }

    @Test
    @DisplayName("크루 존재 확인 메서드 테스트(회원이 존재)")
    void hasCrewTestWhenHasCrew() {
        // given
        List<String> names = List.of("a", "b", "c");
        Crews crews = new Crews(names);
        String username = "a";
        // when & then
        assertThatNoException().isThrownBy(() -> crews.validateHasCrew(username));
    }

    @Test
    @DisplayName("크루 존재 확인 메서드 테스트(회원이 존재하지 않음)")
    void hasCrewTestWhenHasCrew_Exception() {
        // given
        List<String> names = List.of("a", "b", "c");
        Crews crews = new Crews(names);
        String username = "d";
        // when & then
        assertThatThrownBy(() -> crews.validateHasCrew(username))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 등록되지 않은 닉네임입니다.");
    }
}
