package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewsTest {

    private Crews crews;

    @BeforeEach
    void beforeEach(){
        Map<String,List<LocalDateTime>> histories = Map.of(
                "a",List.of(),
                "b",List.of(),
                "c",List.of()
        );
        crews = new Crews(histories, LocalDate.of(2024,12,1));
    }

    @Test
    @DisplayName("크루들")
    void addAttendanceHistory() {
        // given
        String username = "a";
        LocalDateTime time = LocalDateTime.of(2024, 12, 24, 10, 31);
        // when
        AttendanceResult result = crews.addAttendanceHistory(username, time);
        // then
        assertThat(result).isEqualTo(AttendanceResult.ABSENCE);
    }

    @Test
    @DisplayName("크루 존재 확인 메서드 테스트(회원이 존재)")
    void hasCrewTestWhenHasCrew() {
        // given
        String username = "a";
        // when & then
        assertThatNoException().isThrownBy(() -> crews.validateHasCrew(username));
    }

    @Test
    @DisplayName("크루 존재 확인 메서드 테스트(회원이 존재하지 않음)")
    void hasCrewTestWhenHasCrew_Exception() {
        // given
        String username = "d";
        // when & then
        assertThatThrownBy(() -> crews.validateHasCrew(username))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 등록되지 않은 닉네임입니다.");
    }

    @Test
    @DisplayName("출석 수정 기능 테스트")
    void editAttendanceTest(){
        // given
        crews.addAttendanceHistory("a",LocalDateTime.of(2024, 12, 24, 10, 31));
        LocalDateTime editTime = LocalDateTime.of(2024, 12, 24, 10, 0);
        // when
        AttendanceResult result = crews.editAttendanceHistory("a",editTime);
        // then
        assertThat(result).isEqualTo(AttendanceResult.ATTENDANCE);
    }

    @Test
    @DisplayName("특정 날짜 출석 조회 기능 테스트")
    void getAttendanceHistory(){
        // given
        crews.addAttendanceHistory("a",LocalDateTime.of(2024, 12, 24, 10, 31));
        crews.addAttendanceHistory("b",LocalDateTime.of(2024, 12, 23, 13, 31));
        crews.addAttendanceHistory("c",LocalDateTime.of(2024, 12, 27, 13, 31));
        LocalDate localDate = LocalDate.of(2024,12,24);
        AttendanceHistory expected = new AttendanceHistory(LocalDateTime.of(2024, 12, 24, 10, 31));
        // when
        AttendanceHistory findHistory = crews.findAttendanceHistory("a",localDate);
        // then
        assertThat(findHistory).isEqualTo(expected);
    }

    @Test
    @DisplayName("특정 날짜 출석 조회 기능 예외 테스트")
    void getAttendanceHistory_Exception(){
        // given
        crews.addAttendanceHistory("a",LocalDateTime.of(2024, 12, 24, 10, 31));
        crews.addAttendanceHistory("b",LocalDateTime.of(2024, 12, 23, 13, 31));
        crews.addAttendanceHistory("c",LocalDateTime.of(2024, 12, 27, 13, 31));
        LocalDate localDate = LocalDate.of(2024,12,25);
        // when & then
        assertThatThrownBy(()->crews.findAttendanceHistory("a",localDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 기록이 존재하지 않습니다.");

    }
}
