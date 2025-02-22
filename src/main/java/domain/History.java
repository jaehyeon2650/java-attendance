package domain;

import java.time.LocalDateTime;

public class History implements Comparable<History> {
    private final LocalDateTime attendanceTime;
    private final AttendanceResult attendanceResult;

    public final static int ABSENT_DEFAULT_HOUR = 23;
    public final static int ABSENT_DEFAULT_MINUTE = 59;

    public History(LocalDateTime attendanceTime) {
        this.attendanceTime = attendanceTime;
        this.attendanceResult = DailyAttendanceTime.getDailyAttendanceResult(attendanceTime);
    }

    public LocalDateTime getAttendanceTime() {
        return attendanceTime;
    }

    public String getAttendanceResult() {
        return attendanceResult.getResult();
    }

    public boolean isBeforeHistory(LocalDateTime time) {
        LocalDateTime standardTime = LocalDateTime.of(time.getYear(), time.getMonthValue(), time.getDayOfMonth(), 0, 0);
        return attendanceTime.isBefore(standardTime);
    }

    @Override
    public int compareTo(History o) {
        return this.attendanceTime.compareTo(o.attendanceTime);
    }

}
