package domain;

import static java.time.DayOfWeek.MONDAY;

import java.time.LocalDateTime;

public enum AttendanceResult {
    MONDAY_ABSENCE("결석", 13, 30),
    MONDAY_LATE("지각", 13, 5),
    ABSENCE("결석", 10, 30),
    LATE("지각", 10, 5),
    ATTENDANCE("출석", 12, 0);

    private final String result;
    private final int hour;
    private final int mintues;

    AttendanceResult(String result, int hour, int mintues) {
        this.result = result;
        this.hour = hour;
        this.mintues = mintues;
    }


    public static AttendanceResult findAttendanceResult(LocalDateTime localDateTime) {
        if (localDateTime.getDayOfWeek() == MONDAY) {
            return getMondayResult(localDateTime);
        }
        return getOtherDayResult(localDateTime);
    }

    private static AttendanceResult getOtherDayResult(LocalDateTime localDateTime) {
        if (localDateTime.isAfter(
                LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth(),
                        ABSENCE.hour, ABSENCE.mintues))) {
            return ABSENCE;
        }
        if (localDateTime.isAfter(
                LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth(),
                        LATE.hour, LATE.mintues))) {
            return LATE;
        }
        return ATTENDANCE;
    }

    private static AttendanceResult getMondayResult(LocalDateTime localDateTime) {
        if (localDateTime.isAfter(LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth(),
                localDateTime.getDayOfMonth(),
                MONDAY_ABSENCE.hour, MONDAY_ABSENCE.mintues))) {
            return MONDAY_ABSENCE;
        }
        if (localDateTime.isAfter(LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth(),
                localDateTime.getDayOfMonth(),
                MONDAY_LATE.hour, MONDAY_ABSENCE.mintues))) {
            return MONDAY_LATE;
        }
        return ATTENDANCE;
    }

    public String getResult() {
        return result;
    }
}
