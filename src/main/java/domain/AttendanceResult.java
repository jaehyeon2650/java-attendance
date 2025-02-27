package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public enum AttendanceResult {
    ATTENDANCE("출석"),
    LATE("지각"),
    ABSENCE("결석");

    private final String result;

    AttendanceResult(String result) {
        this.result = result;
    }

    public static AttendanceResult findAttendanceResult(LocalDate attendanceDate, LocalTime attendanceTime) {
        DayOfWeek dayOfWeek = attendanceDate.getDayOfWeek();
        if (attendanceTime == null) {
            return ABSENCE;
        }
        return getAttendanceResult(attendanceTime, dayOfWeek);
    }

    private static AttendanceResult getAttendanceResult(LocalTime attendanceTime, DayOfWeek dayOfWeek) {
        if (dayOfWeek == DayOfWeek.MONDAY) {
            return getMondayAttendanceResult(attendanceTime);
        }
        return getDefaultAttendanceResult(attendanceTime);
    }

    private static AttendanceResult getMondayAttendanceResult(LocalTime attendanceTime) {
        if (attendanceTime.isAfter(LocalTime.of(13, 30))) {
            return ABSENCE;
        }
        if (attendanceTime.isAfter(LocalTime.of(13, 5))) {
            return LATE;
        }
        return ATTENDANCE;
    }

    private static AttendanceResult getDefaultAttendanceResult(LocalTime attendanceTime) {
        if (attendanceTime.isAfter(LocalTime.of(10, 30))) {
            return ABSENCE;
        }
        if (attendanceTime.isAfter(LocalTime.of(10, 5))) {
            return LATE;
        }
        return ATTENDANCE;
    }

    public String getResult() {
        return result;
    }
}