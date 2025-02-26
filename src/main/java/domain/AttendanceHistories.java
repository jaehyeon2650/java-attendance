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
        AttendanceHistory newAttendanceHistory = new AttendanceHistory(attendanceTime);
        attendanceHistories.add(newAttendanceHistory);
        return newAttendanceHistory.getAttendanceResult();
    }
}
