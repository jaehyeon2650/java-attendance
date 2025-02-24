package dto;

import domain.AbsencePenalty;
import domain.AttendanceHistory;
import domain.AttendanceRecord;
import domain.AttendanceResult;
import java.util.List;
import java.util.Map;

public record HistoriesDto(
        String username,
        List<HistoryDto> histories,
        int attendanceCount,
        int lateCount,
        int absenceCount,
        String classifyAbsenceLevel
) {

    public static HistoriesDto of(String username, List<AttendanceHistory> histories, AttendanceRecord attendanceRecord) {
        List<HistoryDto> historyDtoList = histories.stream().map(history -> {
            return new HistoryDto(history.getAttendanceTime(), history.getAttendanceResult().getResult());
        }).toList();
        Map<AttendanceResult, Integer> result = attendanceRecord.getTotalAttendanceRecord();
        return new HistoriesDto(username, historyDtoList, result.getOrDefault(AttendanceResult.ATTENDANCE,0),
                result.getOrDefault(AttendanceResult.LATE,0),result.getOrDefault(AttendanceResult.ABSENCE,0)
                , attendanceRecord.calculateAbsencePenalty().getLevel());
    }
}
