package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class HistoriesTest {

    // 지각, 결석, 정상 개수 출력
    @Test
    void historiesTest() {
        List<LocalDateTime> historiesTimes = List.of(LocalDateTime.of(2024, 12, 18, 9, 0),
                LocalDateTime.of(2024, 12, 18, 10, 6),
                LocalDateTime.of(2024, 12, 18, 10, 35));
        Histories histories = new Histories(historiesTimes);
        Map<String, Integer> result = histories.getAttendanceResultCount();
        assertThat(result.get("지각")).isEqualTo(1);
        assertThat(result.get("결석")).isEqualTo(1);
        assertThat(result.get("정상")).isEqualTo(1);
    }


    @Test
    void classifyAbsenceLevel() {
        List<LocalDateTime> historiesTimes = List.of(LocalDateTime.of(2024, 12, 18, 10, 35),
                LocalDateTime.of(2024, 12, 18, 10, 35),
                LocalDateTime.of(2024, 12, 18, 10, 35));
        Histories histories = new Histories(historiesTimes);
        String result = histories.classifyAbsenceLevel();

        assertThat(result).isEqualTo("면담 대상자");
    }


    @Test
    void classifyAbsenceLevel2() {
        List<LocalDateTime> historiesTimes = List.of(LocalDateTime.of(2024, 12, 13, 10, 9),
                LocalDateTime.of(2024, 12, 11, 10, 9),
                LocalDateTime.of(2024, 12, 12, 10, 9),
                LocalDateTime.of(2024, 12, 18, 10, 40)

        );
        Histories histories = new Histories(historiesTimes);
        String result = histories.classifyAbsenceLevel();

        assertThat(result).isEqualTo("경고 대상자");
    }

    @Test
    void hasHistory() {
        List<LocalDateTime> historiesTimes = List.of(LocalDateTime.of(2024, 12, 13, 10, 9),
                LocalDateTime.of(2024, 12, 11, 10, 9),
                LocalDateTime.of(2024, 12, 12, 10, 9),
                LocalDateTime.of(2024, 12, 18, 10, 40)

        );
        Histories histories = new Histories(historiesTimes);
        LocalDateTime time = LocalDateTime.of(2025, 12, 18, 11, 50);
        boolean check = histories.hasHistory(time);

        assertThat(check).isTrue();
    }

    @Test
    void hasHistory2() {
        List<LocalDateTime> historiesTimes = List.of(LocalDateTime.of(2024, 12, 13, 10, 9),
                LocalDateTime.of(2024, 12, 11, 10, 9),
                LocalDateTime.of(2024, 12, 12, 10, 9),
                LocalDateTime.of(2024, 12, 17, 10, 40)

        );
        Histories histories = new Histories(historiesTimes);
        LocalDateTime time = LocalDateTime.of(2024, 12, 18, 11, 50);
        boolean check = histories.hasHistory(time);

        assertThat(check).isFalse();
    }

    @Test
    void deleteHistory1() {
        List<LocalDateTime> historiesTimes = List.of(LocalDateTime.of(2024, 12, 13, 10, 9),
                LocalDateTime.of(2024, 12, 11, 10, 9),
                LocalDateTime.of(2024, 12, 12, 10, 9),
                LocalDateTime.of(2024, 12, 17, 10, 40)

        );
        Histories histories = new Histories(historiesTimes);
        LocalDateTime time = LocalDateTime.of(2024, 12, 17, 11, 50);
        histories.deleteHistory(time);

        boolean check = histories.hasHistory(time);
        assertThat(check).isFalse();
    }

    @Test
    void deleteHistory2() {
        List<LocalDateTime> historiesTimes = List.of(LocalDateTime.of(2024, 12, 13, 10, 9),
                LocalDateTime.of(2024, 12, 11, 10, 9),
                LocalDateTime.of(2024, 12, 12, 10, 9),
                LocalDateTime.of(2024, 12, 17, 10, 40)

        );
        Histories histories = new Histories(historiesTimes);
        LocalDateTime time = LocalDateTime.of(2024, 12, 18, 11, 50);
        assertThatThrownBy(() -> histories.deleteHistory(time))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 날짜 출석 기록이 없습니다.");
    }
}
