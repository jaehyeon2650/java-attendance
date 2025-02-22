package domain;

import java.time.LocalDateTime;

public class AttendanceHistory implements Comparable<AttendanceHistory> {
    public final static int ABSENT_DEFAULT_HOUR = 23;
    public final static int ABSENT_DEFAULT_MINUTE = 59;
    private final LocalDateTime attendanceTime;
    private final AttendanceResult attendanceResult;

    public AttendanceHistory(LocalDateTime attendanceTime) {
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
    public int compareTo(AttendanceHistory o) {
        return this.attendanceTime.compareTo(o.attendanceTime);
    }

}
