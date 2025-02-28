package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class CrewTest {

    @Test
    @DisplayName("이름 5글자 이상이면 예외가 발생한다.")
    void crewNameLengthTest() {
        assertThatThrownBy(() -> new Crew("aaaaaa", List.of(), LocalDate.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 크루 이름은 최대 5글자입니다.");
    }

    @ParameterizedTest
    @CsvSource({"5,false", "6,false", "7,true", "8,true"})
    @DisplayName("제적 대상자인지 확인하는 메서드 테스트")
    void isExpulsionTargetTest(int day, boolean expected) {
        // given
        List<LocalDateTime> histories = List.of(LocalDateTime.of(2024, 12, 2, 13, 0),
                LocalDateTime.of(2024, 12, 3, 10, 0),
                LocalDateTime.of(2024, 12, 4, 10, 0));
        Crew crew = new Crew("a", histories, LocalDate.of(2024, 12, day));
        LocalDate standard = LocalDate.of(2024, 12, day);
        // when
        boolean isExpulsionTarget = crew.isExpulsionTarget(standard);
        // then
        assertThat(isExpulsionTarget).isEqualTo(expected);
    }
}
