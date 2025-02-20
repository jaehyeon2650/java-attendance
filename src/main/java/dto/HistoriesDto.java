package dto;

import domain.AbsenceLevel;
import domain.History;
import java.util.List;
import java.util.Map;

public record HistoriesDto(
        String username,
        List<HistoryDto> histories,
        Map<String, Integer> attendanceAllResult,
        String classifyAbsenceLevel
) {

    public static HistoriesDto of(String username, List<History> histories, Map<String, Integer> attendanceAllResult,
                                  AbsenceLevel classifyAbsenceLevel) {
        List<HistoryDto> historyDtoList = histories.stream().map(history -> {
            return new HistoryDto(history.getAttendanceTime(), history.getAttendanceResult().getResult());
        }).toList();
        return new HistoriesDto(username, historyDtoList, attendanceAllResult, classifyAbsenceLevel.getLevel());
    }
}
