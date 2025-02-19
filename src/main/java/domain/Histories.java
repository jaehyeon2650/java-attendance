package domain;

import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;

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

    public Map<String, Integer> getAttendanceResultCount(LocalDateTime standard) {
        int day = standard.getDayOfMonth();
        Map<String, Integer> results = new HashMap<>();
        histories.stream().filter(history -> history.isBeforeHistory(standard))
                .forEach(history -> {
            String result = history.getAttendanceResult();
            results.put(result, results.getOrDefault(result, 0) + 1);
        });
        for (int i = 1; i < day; i++) {
            LocalDateTime time = LocalDateTime.of(standard.getYear(), standard.getMonthValue(), i, 0, 0);
            if (!(time.getDayOfWeek() == SATURDAY) && !(time.getDayOfWeek() == SUNDAY) && !((time.getMonthValue() == 12
                    && time.getDayOfMonth() == 25)) && !hasHistory(time)) {
                results.put("결석", results.getOrDefault("결석", 0) + 1);
            }
        }
        return results;
    }

    public String classifyAbsenceLevel(LocalDateTime standard) {
        Map<String, Integer> results = getAttendanceResultCount(standard);
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

    public boolean hasHistory(LocalDateTime time) {
        return histories.stream()
                .anyMatch(history -> (history.getAttendanceTime().getDayOfMonth() == time.getDayOfMonth()) &&
                        (history.getAttendanceTime().getMonthValue() == time.getMonthValue()));
    }

    public void deleteHistory(LocalDateTime time) {
        History findHistory = histories.stream()
                .filter(history -> (history.getAttendanceTime().getDayOfMonth() == time.getDayOfMonth()) &&
                        (history.getAttendanceTime().getMonthValue() == time.getMonthValue())).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 날짜 출석 기록이 없습니다."));
        histories.remove(findHistory);
    }

    public List<LocalDateTime> getHistories() {
        return histories.stream()
                .map(History::getAttendanceTime)
                .toList();
    }

    public void editHistory(LocalDateTime time) {
        deleteHistory(time);
        histories.add(new History(time));
    }

    public void addHistory(LocalDateTime time) {
        if (hasHistory(time)) {
            throw new IllegalArgumentException("[ERROR] 이미 출석하셨습니다.");
        }
        histories.add(new History(time));
    }

}
