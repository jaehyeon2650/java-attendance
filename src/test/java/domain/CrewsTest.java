package domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewsTest {

    private Crews crews;

    @BeforeEach
    void beforeEach(){
        String username1 = "쿠기";
        String username2 = "빙봉";
        List<LocalDateTime> historiesTimes1 = List.of(LocalDateTime.of(2024, 12, 18, 10, 35),
                LocalDateTime.of(2024, 12, 18, 10, 35),
                LocalDateTime.of(2024, 12, 18, 10, 35));
        List<LocalDateTime> historiesTimes2 = List.of(LocalDateTime.of(2024, 12, 18, 10, 35),
                LocalDateTime.of(2024, 12, 18, 10, 35),
                LocalDateTime.of(2024, 12, 18, 10, 35));
        Map<String,List<LocalDateTime>> crews = new HashMap<>();
        crews.put(username1,historiesTimes1);
        crews.put(username2,historiesTimes2);
        this.crews=new Crews(crews);
    }

    @Test
    public void getCrewHistory(){
        // given
        LocalDateTime standard = LocalDateTime.of(2024, 12, 19, 10, 35);
        List<LocalDateTime> expected = List.of(LocalDateTime.of(2024, 12, 18, 10, 35),
                LocalDateTime.of(2024, 12, 18, 10, 35),
                LocalDateTime.of(2024, 12, 18, 10, 35));
        // when
        List<LocalDateTime> result = crews.getBeforeHistory("빙봉",standard);
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("조회 정렬 테스트")
    @Test
    public void getCrewHistory2(){
        // given
        LocalDateTime standard = LocalDateTime.of(2024, 12, 19, 10, 35);
        // when
        List<LocalDateTime> result = crews.getBeforeHistory("빙봉",standard);
        List<LocalDateTime> expected = List.of(LocalDateTime.of(2024, 12, 13, 10, 35),
                LocalDateTime.of(2024, 12, 16, 10, 35),
                LocalDateTime.of(2024, 12, 17, 10, 35));
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("회원 출석 확인 테스트")
    @Test
    void attendanceTest(){
        String username = "빙봉";
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 19, 10, 35);
        crews.addHistory(username,attendanceTime);
        LocalDateTime standardTime = LocalDateTime.of(2024, 12, 20, 10, 35);
        List<LocalDateTime> beforeHistory = crews.getBeforeHistory(username, standardTime);
        boolean check = beforeHistory.contains(attendanceTime);
        assertThat(check).isTrue();
    }
}
