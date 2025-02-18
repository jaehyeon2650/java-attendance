package domain;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Histories {
    private final List<History> histories;

    public Histories(List<LocalDateTime> histories) {
        this.histories = histories.stream().map(History::new).collect(Collectors.toList());
    }

    public Map<String, Integer> getAttendanceResultCount() {
        Map<String, Integer> results = new HashMap<>();
        histories.forEach(history -> {
            String result = history.getAttendanceResult();
            results.put(result, results.getOrDefault(result, 0) + 1);
        });
        return results;
    }

    public String classifyAbsenceLevel() {
        Map<String, Integer> results = getAttendanceResultCount();
        int absentCount = results.getOrDefault("결석", 0);
        int lateCount = results.getOrDefault("지각", 0);
        absentCount += (lateCount / 3);

        if (absentCount > 5) {
            return "제적 대상자";
        }
        if (absentCount >= 3) {
            return "면담 대상자";
        }
        if (absentCount >= 2) {
            return "경고 대상자";
        }
        return "정상 대상자";
    }
}
