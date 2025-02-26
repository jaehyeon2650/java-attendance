package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AttendanceHistoryTest {

    @ParameterizedTest
    @MethodSource
    @DisplayName("출석 시간(08:00~23:00)을 벗어난 시간을 하면 예외가 발생한다.")
    void createAttendanceHistory_exception(LocalDateTime time) {
        assertThatThrownBy(() -> new AttendanceHistory(time))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 캠퍼스 운영 시간은 08:00 ~ 23:00 입니다.");
    }

    public static Stream<Arguments> createAttendanceHistory_exception() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(2024, 12, 19, 7, 59)),
                Arguments.of(LocalDateTime.of(2024, 12, 19, 6, 59)),
                Arguments.of(LocalDateTime.of(2024, 12, 19, 5, 59)),
                Arguments.of(LocalDateTime.of(2024, 12, 19, 4, 59)),
                Arguments.of(LocalDateTime.of(2024, 12, 19, 23, 59)),
                Arguments.of(LocalDateTime.of(2024, 12, 19, 23, 1))
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("정상적인 시간일 경우 출석 기록을 남긴다.")
    void createAttendanceHistory_success(LocalDateTime time) {
        assertThatNoException().isThrownBy(() -> new AttendanceHistory(time));
    }

    public static Stream<Arguments> createAttendanceHistory_success() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(2024, 12, 19, 8, 0)),
                Arguments.of(LocalDateTime.of(2024, 12, 19, 8, 59)),
                Arguments.of(LocalDateTime.of(2024, 12, 19, 9, 59)),
                Arguments.of(LocalDateTime.of(2024, 12, 19, 20, 59)),
                Arguments.of(LocalDateTime.of(2024, 12, 19, 22, 59)),
                Arguments.of(LocalDateTime.of(2024, 12, 19, 23, 0))
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("정상적인 시간인 경우 지각, 결석, 출석 상태를 결정한다.(월요일인 경우)")
    void makeAttendanceResultWhenMonday(LocalDateTime attendanceTime, String expected) {
        // when
        AttendanceHistory attendanceHistory = new AttendanceHistory(attendanceTime);
        String result = attendanceHistory.getAttendanceResult();
        // then
        assertThat(result).isEqualTo(expected);
    }

    public static Stream<Arguments> makeAttendanceResultWhenMonday() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(2024, 12, 23, 12, 50), "출석"),
                Arguments.of(LocalDateTime.of(2024, 12, 23, 13, 0), "출석"),
                Arguments.of(LocalDateTime.of(2024, 12, 23, 13, 5), "출석"),
                Arguments.of(LocalDateTime.of(2024, 12, 23, 13, 6), "지각"),
                Arguments.of(LocalDateTime.of(2024, 12, 23, 13, 30), "지각"),
                Arguments.of(LocalDateTime.of(2024, 12, 23, 13, 31), "결석")
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("정상적인 시간인 경우 지각, 결석, 출석 상태를 결정한다.(월요일이 아닌 경우)")
    void makeAttendanceResultWhenNotMonday(LocalDateTime attendanceTime, String expected) {
        // when
        AttendanceHistory attendanceHistory = new AttendanceHistory(attendanceTime);
        String result = attendanceHistory.getAttendanceResult();
        // then
        assertThat(result).isEqualTo(expected);
    }

    public static Stream<Arguments> makeAttendanceResultWhenNotMonday() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(2024, 12, 24, 9, 50), "출석"),
                Arguments.of(LocalDateTime.of(2024, 12, 24, 10, 0), "출석"),
                Arguments.of(LocalDateTime.of(2024, 12, 24, 10, 5), "출석"),
                Arguments.of(LocalDateTime.of(2024, 12, 24, 10, 6), "지각"),
                Arguments.of(LocalDateTime.of(2024, 12, 24, 10, 30), "지각"),
                Arguments.of(LocalDateTime.of(2024, 12, 24, 10, 31), "결석")
        );
    }

    @Test
    @DisplayName("같은 날짜 출헉인지 확인한다.")
    void isSameDateTest() {
        // given
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 23, 10, 0, 0);
        AttendanceHistory attendanceHistory = new AttendanceHistory(attendanceTime);
        LocalDateTime standardTime1 = LocalDateTime.of(2024, 12, 23, 23, 0, 0);
        LocalDateTime standardTime2 = LocalDateTime.of(2024, 12, 22, 23, 0, 0);
        // when
        boolean sameDate1 = attendanceHistory.isSameDate(standardTime1);
        boolean sameDate2 = attendanceHistory.isSameDate(standardTime2);
        // then
        assertThat(sameDate1).isTrue();
        assertThat(sameDate2).isFalse();
    }
}
