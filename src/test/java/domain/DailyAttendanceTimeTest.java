package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class DailyAttendanceTimeTest {

    @ParameterizedTest
    @DisplayName("출석 가능한지 확인하는 로직 테스트")
    @MethodSource
    void canAttendanceTest(LocalDate attendanceDate, boolean expected) {
        // when
        boolean canAttendance = DailyAttendanceTime.canAttendance(attendanceDate);
        // then
        assertThat(canAttendance).isEqualTo(expected);
    }

    public static Stream<Arguments> canAttendanceTest() {
        return Stream.of(
                Arguments.of(LocalDate.of(2024, 12, 25), false),
                Arguments.of(LocalDate.of(2024, 12, 21), false),
                Arguments.of(LocalDate.of(2024, 12, 22), false),
                Arguments.of(LocalDate.of(2024, 12, 23), true),
                Arguments.of(LocalDate.of(2024, 12, 24), true)
        );
    }

    @ParameterizedTest
    @DisplayName("요일 별로 출석 결과를 확인하는 로직 테스트")
    @MethodSource
    void getDailyAttendanceResultTest(LocalDateTime attendanceTime, AttendanceResult expected) {
        // when
        AttendanceResult result = DailyAttendanceTime.getDailyAttendanceResult(attendanceTime);
        // then
        assertThat(result).isEqualTo(expected);
    }

    public static Stream<Arguments> getDailyAttendanceResultTest() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(2024, 12, 23, 12, 0), AttendanceResult.ATTENDANCE),
                Arguments.of(LocalDateTime.of(2024, 12, 23, 13, 0), AttendanceResult.ATTENDANCE),
                Arguments.of(LocalDateTime.of(2024, 12, 23, 13, 5), AttendanceResult.ATTENDANCE),
                Arguments.of(LocalDateTime.of(2024, 12, 23, 13, 7), AttendanceResult.LATE),
                Arguments.of(LocalDateTime.of(2024, 12, 23, 13, 30), AttendanceResult.LATE),
                Arguments.of(LocalDateTime.of(2024, 12, 23, 13, 35), AttendanceResult.ABSENCE),
                Arguments.of(LocalDateTime.of(2024, 12, 23, 12, 0), AttendanceResult.ATTENDANCE),
                Arguments.of(LocalDateTime.of(2024, 12, 24, 10, 0), AttendanceResult.ATTENDANCE),
                Arguments.of(LocalDateTime.of(2024, 12, 24, 10, 5), AttendanceResult.ATTENDANCE),
                Arguments.of(LocalDateTime.of(2024, 12, 24, 10, 7), AttendanceResult.LATE),
                Arguments.of(LocalDateTime.of(2024, 12, 24, 10, 30), AttendanceResult.LATE),
                Arguments.of(LocalDateTime.of(2024, 12, 24, 10, 35), AttendanceResult.ABSENCE)
        );
    }

    @ParameterizedTest
    @DisplayName("요일 별로 출석 결과를 확인하는 로직 테스트(예외)")
    @MethodSource
    void getDailyAttendanceResultExceptionTest(LocalDateTime attendanceTime) {
        assertThatThrownBy(() -> DailyAttendanceTime.getDailyAttendanceResult(attendanceTime))
                .isInstanceOf(IllegalArgumentException.class);
    }

    public static Stream<Arguments> getDailyAttendanceResultExceptionTest() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(2024, 12, 25, 12, 0)),
                Arguments.of(LocalDateTime.of(2024, 12, 21, 13, 0)),
                Arguments.of(LocalDateTime.of(2024, 12, 22, 13, 5)),
                Arguments.of(LocalDateTime.of(2024, 12, 23, 7, 0)),
                Arguments.of(LocalDateTime.of(2024, 12, 23, 23, 30))
        );
    }
}