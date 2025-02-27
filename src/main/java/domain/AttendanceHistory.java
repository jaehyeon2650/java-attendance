package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class AttendanceHistory {
    private final LocalDate attendanceDate;
    private final LocalTime attendanceTime;
    private final String AttendanceResult;

    public AttendanceHistory(LocalDateTime attendanceTime) {
        Validator.validateAttendanceTime(attendanceTime.toLocalTime());
        this.attendanceDate = attendanceTime.toLocalDate();
        this.attendanceTime = attendanceTime.toLocalTime();
        this.AttendanceResult = findAttendanceResult(this.attendanceDate, this.attendanceTime);
    }

    public AttendanceHistory(LocalDate attendanceDate,LocalTime attendanceTime){
        Validator.validateAttendanceTime(attendanceTime);
        this.attendanceDate = attendanceDate;
        this.attendanceTime = attendanceTime;
        this.AttendanceResult = findAttendanceResult(this.attendanceDate, this.attendanceTime);
    }

    private String findAttendanceResult(LocalDate attendanceDate,LocalTime attendanceTime) {
        DayOfWeek dayOfWeek = attendanceDate.getDayOfWeek();
        if(attendanceTime == null){
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

    public String getAttendanceResult() {
        return AttendanceResult;
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

    private static class Validator {
        public static void validateAttendanceTime(LocalTime attendanceTime) {
            if (attendanceTime !=null && (attendanceTime.isBefore(LocalTime.of( 8, 0)) || attendanceTime.isAfter(
                    LocalTime.of(23, 0)))) {
                throw new IllegalArgumentException("[ERROR] 캠퍼스 운영 시간은 08:00 ~ 23:00 입니다.");
            }
        }
    }
}
