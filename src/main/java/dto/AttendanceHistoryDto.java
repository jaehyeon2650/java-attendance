package dto;

import domain.AttendanceHistory;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public record AttendanceHistoryDto(
        LocalDate attendanceDate,
        Optional<LocalTime> attendanceTime,
        String result
) {
    public static AttendanceHistoryDto from(AttendanceHistory history) {
        return new AttendanceHistoryDto(history.getAttendanceDate(), history.getAttendanceTime(),
                history.getAttendanceResult().getResult());
    }
}
