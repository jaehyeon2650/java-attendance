package domain;

import static domain.AttendanceResult.ABSENCE;
import static domain.AttendanceResult.ATTENDANCE;
import static domain.AttendanceResult.LATE;

import java.util.EnumMap;
import java.util.Map;

public class AttendanceRecord {
    private final int attendanceCount;
    private final int lateCount;
    private final int absenceCount;

    public AttendanceRecord(Map<AttendanceResult, Integer> record) {
        this.attendanceCount = record.getOrDefault(ATTENDANCE, 0);
        this.lateCount = record.getOrDefault(LATE, 0);
        this.absenceCount = record.getOrDefault(ABSENCE, 0);
    }

    public AbsencePenalty calculateAbsencePenalty() {
        return AbsencePenalty.findAbsenceLevel(absenceCount, lateCount);
    }

    public Map<AttendanceResult, Integer> getTotalAttendanceRecord() {
        Map<AttendanceResult, Integer> result = new EnumMap<>(AttendanceResult.class);
        result.put(ATTENDANCE, attendanceCount);
        result.put(LATE, lateCount);
        result.put(ABSENCE, absenceCount);
        return result;
    }
}
