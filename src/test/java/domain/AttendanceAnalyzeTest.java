package domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceAnalyzeTest {
    @Test
    @DisplayName("최종 결과 반환 테스트")
    void getAttendanceAnalyzeTest(){
        // given
        AttendanceHistory attendanceHistory1 = new AttendanceHistory(LocalDateTime.of(2024,12,23,13,6));
        AttendanceHistory attendanceHistory2 = new AttendanceHistory(LocalDateTime.of(2024,12,24,10,36));
        AttendanceHistory attendanceHistory3 = new AttendanceHistory(LocalDateTime.of(2024,12,26,10,36));
        AttendanceHistory attendanceHistory4 = new AttendanceHistory(LocalDateTime.of(2024,12,27,10,6));
        AttendanceHistory attendanceHistory5 = new AttendanceHistory(LocalDateTime.of(2024,12,30,13,4));
        List<AttendanceHistory> histories = List.of(attendanceHistory1,attendanceHistory2,attendanceHistory3,attendanceHistory4,attendanceHistory5);
        // when
        AttendanceAnalyze attendanceAnalyze = new AttendanceAnalyze(histories);
        // then
        assertThat(attendanceAnalyze.getLateCount()).isEqualTo(2);
        assertThat(attendanceAnalyze.getAbsenceCount()).isEqualTo(2);
        assertThat(attendanceAnalyze.getAttendanceCount()).isEqualTo(1);
        assertThat(attendanceAnalyze.getAttendanceStatus()).isEqualTo(AttendanceStatus.WARNING);

    }
}