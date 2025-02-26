package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AttendanceHistory {
    private final LocalDateTime attendanceTime;
    private final String AttendanceResult;

    public AttendanceHistory(LocalDateTime attendanceTime) {
        Validator.validateAttendanceTime(attendanceTime);
        this.attendanceTime = attendanceTime;
        this.AttendanceResult = findAttendanceResult(attendanceTime);

    }

    private String findAttendanceResult(LocalDateTime attendanceDate) {
        DayOfWeek dayOfWeek = attendanceDate.getDayOfWeek();
        LocalTime attendanceTime = attendanceDate.toLocalTime();
        if (dayOfWeek == DayOfWeek.MONDAY) {
            if (attendanceTime.isAfter(LocalTime.of(13, 30))) {
                return "결석";
            }
            if (attendanceTime.isAfter(LocalTime.of(13, 5))) {
                return "지각";
            }
            return "출석";
        }

        if (attendanceTime.isAfter(LocalTime.of(10, 30))) {
            return "결석";
        }
        if (attendanceTime.isAfter(LocalTime.of(10, 5))) {
            return "지각";
        }
        return "출석";
    }

    public String getAttendanceResult() {
        return AttendanceResult;
    }

    public boolean isSameDate(LocalDateTime standard) {
        LocalDate standardDate = standard.toLocalDate();
        LocalDate attendanceDate = attendanceTime.toLocalDate();
        return attendanceDate.isEqual(standardDate);
    }

    private static class Validator {
        public static void validateAttendanceTime(LocalDateTime attendanceTime) {
            if (attendanceTime.isBefore(LocalDateTime.of(attendanceTime.getYear(), attendanceTime.getMonth(),
                    attendanceTime.getDayOfMonth(), 8, 0)) || attendanceTime.isAfter(
                    LocalDateTime.of(attendanceTime.getYear(), attendanceTime.getMonth(),
                            attendanceTime.getDayOfMonth(), 23, 0))) {
                throw new IllegalArgumentException("[ERROR] 캠퍼스 운영 시간은 08:00 ~ 23:00 입니다.");
            }
        }
    }
}
