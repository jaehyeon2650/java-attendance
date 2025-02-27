package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Crew {
    private final String username;
    private final AttendanceHistories attendanceHistories;

    public Crew(String username, List<LocalDateTime> histories, LocalDate standardDate) {
        Validator.validateNameLength(username);
        this.username = username;
        this.attendanceHistories = new AttendanceHistories(histories,standardDate);
    }

    public String addAttendanceHistory(LocalDateTime attendanceTime) {
        return attendanceHistories.addAttendanceHistory(attendanceTime);
    }

    public String editAttendanceHistory(LocalDateTime editAttendanceTime){
        return attendanceHistories.editAttendanceHistory(editAttendanceTime);
    }

    public boolean isSameName(String username) {
        return this.username.equals(username);
    }

    public static class Validator {
        public static void validateNameLength(String username) {
            if (username.length() > 5) {
                throw new IllegalArgumentException("[ERROR] 크루 이름은 최대 5글자입니다.");
            }
        }
    }

}
