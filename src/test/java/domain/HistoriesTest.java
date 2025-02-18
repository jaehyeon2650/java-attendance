package domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class HistoriesTest {

    // 지각, 결석, 정상 개수 출력
    @Test
    void historiesTest() {
        List<LocalDateTime> historiesTimes = List.of(LocalDateTime.of(2025, 2, 18, 9, 0),
                LocalDateTime.of(2025, 2, 18, 10, 6),
                LocalDateTime.of(2025, 2, 18, 10, 35));
        Histories histories = new Histories(historiesTimes);
        Map<String, Integer> result = histories.getAttendanceResultCount();
        Assertions.assertThat(result.get("지각")).isEqualTo(1);
        Assertions.assertThat(result.get("결석")).isEqualTo(1);
        Assertions.assertThat(result.get("정상")).isEqualTo(1);
    }


    @Test
    void classifyAbsenceLevel() {
        List<LocalDateTime> historiesTimes = List.of(LocalDateTime.of(2025, 2, 18, 10, 35),
                LocalDateTime.of(2025, 2, 18, 10, 35),
                LocalDateTime.of(2025, 2, 18, 10, 35));
        Histories histories = new Histories(historiesTimes);
        String result = histories.classifyAbsenceLevel();

        Assertions.assertThat(result).isEqualTo("면담 대상자");
    }

    
    @Test
    void classifyAbsenceLevel2() {
        List<LocalDateTime> historiesTimes = List.of(LocalDateTime.of(2025, 2, 14, 10, 9),
                LocalDateTime.of(2025, 2, 11, 10, 9),
                LocalDateTime.of(2025, 2, 12, 10, 9),
                LocalDateTime.of(2025, 2, 18, 10, 40)

        );
        Histories histories = new Histories(historiesTimes);
        String result = histories.classifyAbsenceLevel();

        Assertions.assertThat(result).isEqualTo("경고 대상자");
    }

}
