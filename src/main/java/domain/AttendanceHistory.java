package domain;

import java.time.LocalDateTime;

public class AttendanceHistory {
    private final LocalDateTime attendanceTime;

    public AttendanceHistory(LocalDateTime attendanceTime) {
        Validator.validateAttendanceTime(attendanceTime);
        this.attendanceTime = attendanceTime;
    }

    private static class Validator {
        public static void validateAttendanceTime(LocalDateTime attendanceTime) {
            if (attendanceTime.isBefore(LocalDateTime.of(attendanceTime.getYear(), attendanceTime.getMonth(),
                    attendanceTime.getDayOfMonth(), 8, 0)) || attendanceTime.isAfter(
                    LocalDateTime.of(attendanceTime.getYear(), attendanceTime.getMonth(),
                            attendanceTime.getDayOfMonth(), 23, 0))) {
                throw new IllegalArgumentException("[ERROR] 캠퍼스 운영 시간은 08:00 ~ 23:00 입니다.");
            }
        }
    }
}
