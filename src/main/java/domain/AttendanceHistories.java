package domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AttendanceHistories {
    private final List<AttendanceHistory> attendanceHistories;

    public AttendanceHistories() {
        this.attendanceHistories = new ArrayList<>();
    }

    public String addAttendanceHistory(LocalDateTime attendanceTime) {
        Validator.validateAddAttendanceHistory(attendanceHistories, attendanceTime);
        AttendanceHistory newAttendanceHistory = new AttendanceHistory(attendanceTime);
        attendanceHistories.add(newAttendanceHistory);
        return newAttendanceHistory.getAttendanceResult();
    }

    public static class Validator {
        public static void validateAddAttendanceHistory(List<AttendanceHistory> attendanceHistories,
                                                        LocalDateTime localDateTime) {
            boolean check = attendanceHistories.stream()
                    .anyMatch(attendanceHistory -> attendanceHistory.isSameDate(localDateTime));
            if (check) {
                throw new IllegalArgumentException("[ERROR] 오늘 이미 출석을 하셨습니다. 수정 메뉴로 이동해주세요!");
            }
        }
    }
}
