package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AbsencePenaltyTest {

    @ParameterizedTest
    @MethodSource
    @DisplayName("제적 위험자 상태 결과 도출 테스트")
    void findAbsenceLevelTest(int absentCount, int lateCount, AbsencePenalty expected) {
        // when
        AbsencePenalty result = AbsencePenalty.findAbsenceLevel(absentCount, lateCount);
        // then
        assertThat(result).isEqualTo(expected);
    }

    private static Stream<Arguments> findAbsenceLevelTest() {
        return Stream.of(
                Arguments.of(0, 0, AbsencePenalty.NORMAL),
                Arguments.of(1, 0, AbsencePenalty.NORMAL),
                Arguments.of(1, 1, AbsencePenalty.NORMAL),
                Arguments.of(1, 2, AbsencePenalty.NORMAL),
                Arguments.of(1, 3, AbsencePenalty.WARNING),
                Arguments.of(2, 0, AbsencePenalty.WARNING),
                Arguments.of(2, 3, AbsencePenalty.MEETING),
                Arguments.of(3, 0, AbsencePenalty.MEETING),
                Arguments.of(4, 0, AbsencePenalty.MEETING),
                Arguments.of(5, 0, AbsencePenalty.MEETING),
                Arguments.of(5, 3, AbsencePenalty.OUT),
                Arguments.of(5, 4, AbsencePenalty.OUT),
                Arguments.of(6, 3, AbsencePenalty.OUT)
        );
    }
}