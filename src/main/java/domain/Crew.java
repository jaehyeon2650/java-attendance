package domain;

import java.time.LocalDateTime;

public class Crew {
    private final String username;
    private final AttendanceHistories attendanceHistories;

    public Crew(String username) {
        Validator.validateNameLength(username);
        this.username = username;
        this.attendanceHistories = new AttendanceHistories();
    }

    public String addAttendanceHistory(LocalDateTime attendanceTime) {
        return attendanceHistories.addAttendanceHistory(attendanceTime);
    }

    public static class Validator {
        public static void validateNameLength(String username) {
            if (username.length() > 5) {
                throw new IllegalArgumentException("[ERROR] 크루 이름은 최대 5글자입니다.");
            }
        }
    }
}
