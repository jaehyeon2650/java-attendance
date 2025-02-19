package domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewsTest {

    private Crews crews;

    @BeforeEach
    void beforeEach(){
        String username1 = "쿠키";
        String username2 = "빙봉";
        String username3 = "빙티";
        List<LocalDateTime> historiesTimes1 = List.of(LocalDateTime.of(2024, 12, 4, 9, 59),
                LocalDateTime.of(2024, 12, 3, 9, 59),
                LocalDateTime.of(2024, 12, 2, 9, 59));
        List<LocalDateTime> historiesTimes2 = List.of(LocalDateTime.of(2024, 12, 4, 10, 35),
                LocalDateTime.of(2024, 12, 3, 10, 35),
                LocalDateTime.of(2024, 12, 2, 9, 59));
        List<LocalDateTime> historiesTimes3 = List.of();
        Map<String,List<LocalDateTime>> crews = new HashMap<>();
        crews.put(username1,historiesTimes1);
        crews.put(username2,historiesTimes2);
        crews.put(username3,historiesTimes3);
        this.crews=new Crews(crews, LocalDate.of(2024,12,5));
    }

    @Test
    public void getCrewHistory(){
        // given
        LocalDateTime standard = LocalDateTime.of(2024, 12, 19, 10, 35);
        List<LocalDateTime> expected = List.of(LocalDateTime.of(2024, 12, 2, 9, 59),
                LocalDateTime.of(2024, 12, 3, 9, 59),
                LocalDateTime.of(2024, 12, 4, 9, 59));
        // when
        List<History> result = crews.getBeforeHistory("쿠키",standard);
        List<LocalDateTime> resultTime = result.stream().map(History::getAttendanceTime).toList();
        assertThat(resultTime).isEqualTo(expected);
    }

    @DisplayName("조회 정렬 테스트")
    @Test
    public void getCrewHistory2(){
        // given
        LocalDateTime standard = LocalDateTime.of(2024, 12, 19, 10, 35);
        // when
        List<History> result = crews.getBeforeHistory("빙봉",standard);
        List<LocalDateTime> resultTime = result.stream().map(History::getAttendanceTime).toList();
        List<LocalDateTime> expected = List.of(LocalDateTime.of(2024, 12, 2, 9, 59),
                LocalDateTime.of(2024, 12, 3, 10, 35),
                LocalDateTime.of(2024, 12, 4, 10, 35));
        assertThat(resultTime).isEqualTo(expected);
    }

    @DisplayName("회원 출석 확인 테스트")
    @Test
    void attendanceTest(){
        String username = "빙봉";
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 19, 10, 35);
        crews.addHistory(username,attendanceTime);
        LocalDateTime standardTime = LocalDateTime.of(2024, 12, 20, 10, 35);
        List<History> result= crews.getBeforeHistory(username, standardTime);
        List<LocalDateTime> resultTime = result.stream().map(History::getAttendanceTime).toList();
        boolean check = resultTime.contains(attendanceTime);
        assertThat(check).isTrue();
    }

    @Test
    public void getHighAbsenceLevelCrews(){
        List<Crew> absenceLevelCrews = crews.getHighAbsenceLevelCrews(LocalDateTime.of(2024,12,5,11,0));
        assertThat(absenceLevelCrews.size()).isEqualTo(2);
        List<String> usernames = absenceLevelCrews.stream().map(Crew::getUserName).toList();
        assertThat(usernames.contains("빙봉")).isTrue();
        assertThat(usernames.contains("빙티")).isTrue();
    }
}
