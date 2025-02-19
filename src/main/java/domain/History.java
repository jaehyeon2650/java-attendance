package domain;

import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;

import java.time.LocalDateTime;

public class History implements Comparable<History> {
    private final LocalDateTime attendanceTime;
    private final String attendanceResult;

    public History(LocalDateTime attendanceTime) {
        validateHistory(attendanceTime);
        this.attendanceTime = attendanceTime;
        attendanceResult = getAttendanceResult(attendanceTime);
    }

    public String getAttendanceResult(LocalDateTime attendanceTime) {
        if (attendanceTime.getDayOfWeek() == MONDAY) {
            if (attendanceTime.isAfter(LocalDateTime.of(attendanceTime.getYear(), attendanceTime.getMonth(),
                    attendanceTime.getDayOfMonth(),
                    13, 30))) {
                return "결석";
            }
            if (attendanceTime.isAfter(LocalDateTime.of(attendanceTime.getYear(), attendanceTime.getMonth(),
                    attendanceTime.getDayOfMonth(),
                    13, 05))) {
                return "지각";
            }
            return "출석";
        }
        if (attendanceTime.isAfter(
                LocalDateTime.of(attendanceTime.getYear(), attendanceTime.getMonth(), attendanceTime.getDayOfMonth(),
                        10, 30))) {
            return "결석";
        }
        if (attendanceTime.isAfter(
                LocalDateTime.of(attendanceTime.getYear(), attendanceTime.getMonth(), attendanceTime.getDayOfMonth(),
                        10, 05))) {
            return "지각";
        }
        return "출석";
    }

    private void validateHistory(LocalDateTime attendanceTime) {
        if ((attendanceTime.getDayOfWeek() == SATURDAY) || (attendanceTime.getDayOfWeek() == SUNDAY)) {
            throw new IllegalArgumentException(
                    String.format("%d월 %d일 %s은 등교일이 아닙니다.",
                            attendanceTime.getMonthValue(),
                            attendanceTime.getDayOfMonth(), "토요일"

                    )
            );
        }
        if (attendanceTime.getMonthValue() == 12 && attendanceTime.getDayOfMonth() == 25) {
            throw new IllegalArgumentException(
                    String.format("%d월 %d일 %s은 등교일이 아닙니다.",
                            attendanceTime.getMonthValue(),
                            attendanceTime.getDayOfMonth(), "일요일"

                    )
            );
        }
    }

    public LocalDateTime getAttendanceTime() {
        return attendanceTime;
    }

    public String getAttendanceResult() {
        return attendanceResult;
    }

    public boolean isBeforeHistory(LocalDateTime time){
        LocalDateTime standardTime = LocalDateTime.of(time.getYear(),time.getMonthValue(),time.getDayOfMonth(),0,0);
        return attendanceTime.isBefore(standardTime);
    }

    @Override
    public int compareTo(History o) {
        return this.attendanceTime.compareTo(o.attendanceTime);
    }
}
