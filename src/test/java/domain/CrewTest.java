package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class CrewTest {

    @Test
    public void validateName() {
        String userName = "가나다라마";
        List<LocalDateTime> histories = new ArrayList<>();

        assertThatThrownBy(() -> new Crew(userName, histories))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이름은 네 글자 이하여야 합니다.");
    }

    @Test
    public void checkAttendance() {
        String userName = "쿠키";
        LocalDateTime history = LocalDateTime.of(2024, 12, 20, 9, 50);
        Crew crew = new Crew(userName, List.of(history));
        crew.checkAttendance(history);
        assertThat(crew.checkAttendance(history)).isTrue();
    }


}
