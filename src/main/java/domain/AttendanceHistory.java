package domain;

import constant.Holiday;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

public class AttendanceHistory {
    private final LocalDate attendanceDate;
    private final LocalTime attendanceTime;
    private final String AttendanceResult;

    public AttendanceHistory(LocalDateTime attendanceTime) {
        AttendanceValidator.validateAttendanceTime(attendanceTime.toLocalTime());
        AttendanceValidator.validateAttendanceDate(attendanceTime.toLocalDate());
        this.attendanceDate = attendanceTime.toLocalDate();
        this.attendanceTime = attendanceTime.toLocalTime();
        this.AttendanceResult = findAttendanceResult(this.attendanceDate, this.attendanceTime);
    }

    public AttendanceHistory(LocalDate attendanceDate, LocalTime attendanceTime) {
        AttendanceValidator.validateAttendanceTime(attendanceTime);
        AttendanceValidator.validateAttendanceDate(attendanceDate);
        this.attendanceDate = attendanceDate;
        this.attendanceTime = attendanceTime;
        this.AttendanceResult = findAttendanceResult(this.attendanceDate, this.attendanceTime);
    }

    private String findAttendanceResult(LocalDate attendanceDate, LocalTime attendanceTime) {
        DayOfWeek dayOfWeek = attendanceDate.getDayOfWeek();
        if (attendanceTime == null) {
            return "결석";
        }
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

    public boolean isSameDate(LocalDate standardDate) {
        return attendanceDate.isEqual(standardDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AttendanceHistory that = (AttendanceHistory) o;
        return Objects.equals(attendanceDate, that.attendanceDate) && Objects.equals(attendanceTime,
                that.attendanceTime) && Objects.equals(AttendanceResult, that.AttendanceResult);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attendanceDate, attendanceTime, AttendanceResult);
    }

    public static class AttendanceValidator {
        private static void validateAttendanceTime(LocalTime attendanceTime) {
            if (attendanceTime != null && (attendanceTime.isBefore(LocalTime.of(8, 0)) || attendanceTime.isAfter(
                    LocalTime.of(23, 0)))) {
                throw new IllegalArgumentException("[ERROR] 캠퍼스 운영 시간은 08:00 ~ 23:00 입니다.");
            }
        }

        public static void validateAttendanceDate(LocalDate localDate) {
            if (Holiday.isHoliday(localDate)) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M월 d일 EEEE", Locale.KOREAN);
                String dateFormat = formatter.format(localDate);
                String errorMessage = String.format("[ERROR] %S은 등교일이 아닙니다.", dateFormat);
                throw new IllegalArgumentException(errorMessage);
            }
        }
    }

    public String getAttendanceResult() {
        return AttendanceResult;
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public Optional<LocalTime> getAttendanceTime() {
        return Optional.ofNullable(attendanceTime);
    }
}
