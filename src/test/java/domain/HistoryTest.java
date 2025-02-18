package domain;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

public class HistoryTest {

    @Test
    void history() {
        LocalDateTime time = LocalDateTime.of(2025, 2, 18, 10, 7);
        History history = new History(time);
        String result = history.getAttendanceResult();
        assertThat(result).isEqualTo("지각");
    }

    @Test
    void validateHistory() {
        LocalDateTime time = LocalDateTime.of(2025, 2, 15, 10, 7);
        assertThatThrownBy(() -> new History(time))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("등교일이 아닙니다.");
    }


}
