package domain;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AttendanceAnalyze {
    private final int lateCount;
    private final int absenceCount;
    private final int attendanceCount;

    public AttendanceAnalyze(final List<AttendanceHistory> histories) {
        Map<AttendanceResult, Integer> statics = histories.stream().collect(Collectors.groupingBy(
                AttendanceHistory::getAttendanceResult,
                () -> new EnumMap<>(AttendanceResult.class),
                Collectors.summingInt((e) -> 1)
        ));
        this.lateCount = statics.getOrDefault(AttendanceResult.LATE,0);
        this.absenceCount = statics.getOrDefault(AttendanceResult.ABSENCE,0);
        this.attendanceCount = statics.getOrDefault(AttendanceResult.ATTENDANCE,0);
    }

    public int getLateCount() {
        return lateCount;
    }

    public int getAbsenceCount() {
        return absenceCount;
    }

    public int getAttendanceCount() {
        return attendanceCount;
    }

    public AttendanceStatus getAttendanceStatus(){
        return AttendanceStatus.findAttendanceStatus(lateCount,absenceCount);
    }

    public boolean isExpulsionTarget(){
        AttendanceStatus attendanceStatus = getAttendanceStatus();
        return !attendanceStatus.equals(AttendanceStatus.NORMAL);
    }
}
