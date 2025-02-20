package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HistoriesTest {

    @DisplayName("출석 결과 카운팅 오늘 날짜 제외 테스트")
    @Test
    void historiesTest() {
        List<LocalDateTime> historiesTimes = List.of(LocalDateTime.of(2024, 12, 16, 9, 0),
                LocalDateTime.of(2024, 12, 17, 10, 6),
                LocalDateTime.of(2024, 12, 18, 10, 35));

        Histories histories = new Histories(historiesTimes, LocalDate.of(2024, 12, 18));
        LocalDateTime standard = LocalDateTime.of(2024, 12, 18, 10, 35);
        Map<String, Integer> result = histories.getAttendanceResultCount(standard);
        assertThat(result.get("지각")).isEqualTo(1);
        assertThat(result.get("결석")).isEqualTo(10);
        assertThat(result.get("출석")).isEqualTo(1);
    }

    @DisplayName("출석 결과 카운팅에 따른 면담 대상자 테스트")
    @Test
    void classifyAbsenceLevel() {
        List<LocalDateTime> historiesTimes = List.of(LocalDateTime.of(2024, 12, 18, 10, 35),
                LocalDateTime.of(2024, 12, 18, 10, 35),
                LocalDateTime.of(2024, 12, 18, 10, 35));
        Histories histories = new Histories(historiesTimes, LocalDate.of(2024, 12, 19));
        LocalDateTime standard = LocalDateTime.of(2024, 12, 19, 10, 35);
        String result = histories.classifyAbsenceLevel(standard);

        assertThat(result).isEqualTo("제적");
    }


    @Test
    void classifyAbsenceLevel2() {
        List<LocalDateTime> historiesTimes = List.of(LocalDateTime.of(2024, 12, 13, 10, 9),
                LocalDateTime.of(2024, 12, 11, 10, 9),
                LocalDateTime.of(2024, 12, 12, 10, 9),
                LocalDateTime.of(2024, 12, 18, 10, 40)

        );
        Histories histories = new Histories(historiesTimes, LocalDate.of(2024, 12, 19));
        LocalDateTime standard = LocalDateTime.of(2024, 12, 19, 10, 40);
        String result = histories.classifyAbsenceLevel(standard);

        assertThat(result).isEqualTo("제적");
    }

    @Test
    void hasHistory() {
        List<LocalDateTime> historiesTimes = List.of(LocalDateTime.of(2024, 12, 13, 10, 9),
                LocalDateTime.of(2024, 12, 11, 10, 9),
                LocalDateTime.of(2024, 12, 12, 10, 9),
                LocalDateTime.of(2024, 12, 18, 10, 40)

        );
        Histories histories = new Histories(historiesTimes, LocalDate.of(2024, 12, 18));
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
        Histories histories = new Histories(historiesTimes, LocalDate.of(2024, 12, 18));
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
        Histories histories = new Histories(historiesTimes, LocalDate.of(2024, 12, 17));
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
        Histories histories = new Histories(historiesTimes, LocalDate.of(2024, 12, 18));
        LocalDateTime time = LocalDateTime.of(2024, 12, 18, 11, 50);
        assertThatThrownBy(() -> histories.deleteHistory(time))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 날짜 출석 기록이 없습니다.");
    }

    @Test
    void editHistory() {
        List<LocalDateTime> historiesTimes = List.of(LocalDateTime.of(2024, 12, 13, 10, 9),
                LocalDateTime.of(2024, 12, 11, 10, 9),
                LocalDateTime.of(2024, 12, 12, 10, 9),
                LocalDateTime.of(2024, 12, 17, 10, 40)

        );
        Histories histories = new Histories(historiesTimes, LocalDate.of(2024, 12, 18));
        LocalDateTime time = LocalDateTime.of(2024, 12, 17, 11, 50);
        histories.editHistory(time);
        LocalDateTime standard = LocalDateTime.of(2024, 12, 18, 11, 50);
        List<History> historiesResult = histories.getHistories(standard);
        List<LocalDateTime> dateList = historiesResult.stream().map(History::getAttendanceTime).toList();

        boolean editContain = dateList.contains(time);
        assertThat(editContain).isEqualTo(true);
    }

    @DisplayName("출석 기록이 있으면 예외를 발생한다.")
    @Test
    void addHistory1() {
        List<LocalDateTime> historiesTimes = List.of(LocalDateTime.of(2024, 12, 13, 10, 9),
                LocalDateTime.of(2024, 12, 11, 10, 9),
                LocalDateTime.of(2024, 12, 12, 10, 9),
                LocalDateTime.of(2024, 12, 17, 10, 40)

        );
        Histories histories = new Histories(historiesTimes, LocalDate.of(2024, 12, 18));
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 17, 10, 40);
        assertThatThrownBy(() -> histories.addHistory(attendanceTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이미 출석하셨습니다.");
    }

    @Test
    void addHistory2() {
        List<LocalDateTime> historiesTimes = List.of(LocalDateTime.of(2024, 12, 13, 10, 9),
                LocalDateTime.of(2024, 12, 11, 10, 9),
                LocalDateTime.of(2024, 12, 12, 10, 9),
                LocalDateTime.of(2024, 12, 17, 10, 40)

        );
        Histories histories = new Histories(historiesTimes, LocalDate.of(2024, 12, 18));
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 18, 10, 40);
        histories.addHistory(attendanceTime);

        assertThat(histories.hasHistory(attendanceTime)).isTrue();
    }

    @DisplayName("출석 확인 - 지각 테스트")
    @Test
    void getTodayHistoryResult() {

        String expected = "지각";

        List<LocalDateTime> historiesTimes = List.of(LocalDateTime.of(2024, 12, 13, 10, 9),
                LocalDateTime.of(2024, 12, 11, 10, 9),
                LocalDateTime.of(2024, 12, 12, 10, 9),
                LocalDateTime.of(2024, 12, 17, 10, 40)

        );
        Histories histories = new Histories(historiesTimes, LocalDate.of(2024, 12, 18));
        LocalDateTime todayTime = LocalDateTime.of(2024, 12, 12, 10, 40);
        assertThat(histories.getHistoryResult(todayTime)).isEqualTo("지각");
    }

    @DisplayName("출석 확인 - 지각 테스트")
    @Test
    void getTodayHistoryResult2() {

        List<LocalDateTime> historiesTimes = List.of(LocalDateTime.of(2024, 12, 13, 10, 9),
                LocalDateTime.of(2024, 12, 11, 10, 9),
                LocalDateTime.of(2024, 12, 12, 10, 9),
                LocalDateTime.of(2024, 12, 17, 10, 40)

        );
        Histories histories = new Histories(historiesTimes, LocalDate.of(2024, 12, 18));
        LocalDateTime todayTime = LocalDateTime.of(2024, 12, 18, 10, 40);
        assertThatThrownBy(() -> histories.getHistoryResult(todayTime))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
