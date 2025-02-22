package domain;

import static domain.AttendanceResult.ABSENCE;
import static domain.AttendanceResult.ABSENCE_STANDARD_MINUTES;
import static domain.AttendanceResult.ATTENDANCE;
import static domain.AttendanceResult.LATE;
import static domain.AttendanceResult.LATE_STANDARD_MINUTES;
import static domain.History.ABSENT_DEFAULT_HOUR;
import static domain.History.ABSENT_DEFAULT_MINUTE;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import util.Convertor;

public enum DailyAttendanceTime {
    MONDAY(LocalTime.of(13, 0), DayOfWeek.MONDAY),
    TUESDAY(LocalTime.of(10, 0), DayOfWeek.TUESDAY),
    WEDNESDAY(LocalTime.of(10, 0), DayOfWeek.WEDNESDAY),
    THURSDAY(LocalTime.of(10, 0), DayOfWeek.THURSDAY),
    FRIDAY(LocalTime.of(10, 0), DayOfWeek.FRIDAY);

    private final static int START_TIME = 8;
    private final static int END_TIME = 23;
    private final LocalTime attendanceTime;
    private final DayOfWeek dayOfWeek;

    DailyAttendanceTime(LocalTime attendanceTime, DayOfWeek dayOfWeek) {
        this.attendanceTime = attendanceTime;
        this.dayOfWeek = dayOfWeek;
    }

    public static AttendanceResult getDailyAttendanceResult(LocalDateTime attendanceTime) {
        Holiday.validateHoliday(attendanceTime);
        validateOpeningHours(attendanceTime);
        DailyAttendanceTime dailyAttendanceTime = findDailyAttendanceTime(attendanceTime);
        return getAttendanceResult(dailyAttendanceTime, attendanceTime);
    }

    public static boolean canAttendance(LocalDate attendanceDate) {
        boolean canAttendance = Arrays.stream(DailyAttendanceTime.values())
                .anyMatch(dailyAttendanceTime -> dailyAttendanceTime.dayOfWeek == attendanceDate.getDayOfWeek());
        return canAttendance && !Holiday.isHoliday(attendanceDate);
    }

    private static DailyAttendanceTime findDailyAttendanceTime(LocalDateTime attendanceTime) {
        return Arrays.stream(DailyAttendanceTime.values())
                .filter(day -> attendanceTime.getDayOfWeek() == day.dayOfWeek)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("[ERROR] %s은 등교일이 아닙니다.", Convertor.dateFormattingForInput(attendanceTime))
                ));
    }

    private static AttendanceResult getAttendanceResult(DailyAttendanceTime dailyAttendanceTime,
                                                        LocalDateTime attendanceTime) {
        LocalTime todayAttendanceTime = dailyAttendanceTime.attendanceTime;
        LocalTime attendanceLocalTime = attendanceTime.toLocalTime();
        if (attendanceLocalTime.isAfter(todayAttendanceTime.plusMinutes(ABSENCE_STANDARD_MINUTES))) {
            return ABSENCE;
        }
        if (attendanceLocalTime.isAfter(todayAttendanceTime.plusMinutes(LATE_STANDARD_MINUTES))) {
            return LATE;
        }
        return ATTENDANCE;
    }

    private static void validateOpeningHours(LocalDateTime attendanceTime) {
        if (!(attendanceTime.getHour() == ABSENT_DEFAULT_HOUR && attendanceTime.getMinute() == ABSENT_DEFAULT_MINUTE)
                && (attendanceTime.isBefore(LocalDateTime.of(attendanceTime.getYear(), attendanceTime.getMonthValue(),
                attendanceTime.getDayOfMonth(), START_TIME, 0)) ||
                (attendanceTime.isAfter(LocalDateTime.of(attendanceTime.getYear(), attendanceTime.getMonthValue(),
                        attendanceTime.getDayOfMonth(), END_TIME, 0)))
        )) {
            throw new IllegalArgumentException("[ERROR] 캠퍼스 운영 시간은 08:00~23:00 입니다.");
        }
    }
}
