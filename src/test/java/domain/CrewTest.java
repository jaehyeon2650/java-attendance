package domain;

import java.time.LocalDate;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewTest {

    @Test
    @DisplayName("이름 5글자 이상이면 예외가 발생한다.")
    void crewNameLengthTest() {
        Assertions.assertThatThrownBy(() -> new Crew("aaaaaa", List.of(), LocalDate.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 크루 이름은 최대 5글자입니다.");
    }

}
