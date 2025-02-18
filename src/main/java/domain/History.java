package domain;

import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;

import java.time.LocalDateTime;

public class History {
    //ToDO 출석 수정 - 삭제 or 수정
    private final LocalDateTime attendanceTime;

    public History(LocalDateTime attendanceTime) {
        validateHistory(attendanceTime);
        this.attendanceTime = attendanceTime;
    }

    public String getAttendanceResult() {
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
            return "정상";
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
        return "정상";
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

}
