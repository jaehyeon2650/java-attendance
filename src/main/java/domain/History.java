package domain;

import java.time.LocalDateTime;

public class History implements Comparable<History> {
    private final LocalDateTime attendanceTime;
    private final AttendanceResult attendanceResult;

    public final static int ABSENT_DEFAULT_HOUR = 23;
    public final static int ABSENT_DEFAULT_MINUTE = 59;
    private final static int START_TIME = 8;
    private final static int END_TIME = 23;

    public History(LocalDateTime attendanceTime) {
        validateHistory(attendanceTime);
        this.attendanceTime = attendanceTime;
        attendanceResult = getAttendanceResult(attendanceTime);
    }

    public AttendanceResult getAttendanceResult(LocalDateTime attendanceTime) {
        return AttendanceResult.findAttendanceResult(attendanceTime);
    }


    public LocalDateTime getAttendanceTime() {
        return attendanceTime;
    }

    public AttendanceResult getAttendanceResult() {
        return attendanceResult;
    }

    public boolean isBeforeHistory(LocalDateTime time) {
        LocalDateTime standardTime = LocalDateTime.of(time.getYear(), time.getMonthValue(), time.getDayOfMonth(), 0, 0);
        return attendanceTime.isBefore(standardTime);
    }

    @Override
    public int compareTo(History o) {
        return this.attendanceTime.compareTo(o.attendanceTime);
    }

    private void validateHistory(LocalDateTime attendanceTime) {
        Holiday.validate(attendanceTime);
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
