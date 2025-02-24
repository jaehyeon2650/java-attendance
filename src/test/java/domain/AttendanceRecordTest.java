package domain;

import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@TestInstance(Lifecycle.PER_CLASS)
class AttendanceRecordTest {

    @ParameterizedTest
    @MethodSource
    @DisplayName("패널티 계산 로직 테스트")
    void calculateAbsencePenaltyTest(int attendanceCount, int absenceCount, int lateCount, AbsencePenalty expected) {
        // given
        Map<AttendanceResult, Integer> record = new EnumMap<>(AttendanceResult.class);
        record.put(AttendanceResult.ATTENDANCE, attendanceCount);
        record.put(AttendanceResult.LATE, lateCount);
        record.put(AttendanceResult.ABSENCE, absenceCount);
        AttendanceRecord attendanceRecord = new AttendanceRecord(record);
        // when
        AbsencePenalty absencePenalty = attendanceRecord.calculateAbsencePenalty();
        // then
        Assertions.assertThat(absencePenalty).isEqualTo(expected);
    }

    private static Stream<Arguments> calculateAbsencePenaltyTest() {
        return Stream.of(
                Arguments.of(2, 0, 0, AbsencePenalty.NORMAL),
                Arguments.of(2, 1, 0, AbsencePenalty.NORMAL),
                Arguments.of(2, 2, 0, AbsencePenalty.WARNING),
                Arguments.of(2, 3, 0, AbsencePenalty.MEETING),
                Arguments.of(2, 3, 3, AbsencePenalty.MEETING),
                Arguments.of(2, 4, 3, AbsencePenalty.MEETING),
                Arguments.of(2, 5, 4, AbsencePenalty.OUT),
                Arguments.of(2, 6, 0, AbsencePenalty.OUT)
        );
    }
}