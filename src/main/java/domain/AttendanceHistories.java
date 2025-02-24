package domain;

import static domain.AttendanceResult.ABSENCE;
import static domain.AttendanceResult.LATE;
import static domain.AttendanceHistory.ABSENT_DEFAULT_HOUR;
import static domain.AttendanceHistory.ABSENT_DEFAULT_MINUTE;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AttendanceHistories {
    private final List<AttendanceHistory> histories;

    public AttendanceHistories(List<LocalDateTime> histories, LocalDate standard) {
        List<LocalDateTime> copy = new ArrayList<>(histories);
        int day = standard.getDayOfMonth();
        for (int i = 1; i < day; i++) {
            addAbsenceHistory(standard, i, copy);
        }
        this.histories = copy.stream().map(AttendanceHistory::new).collect(Collectors.toList());
    }

    public AttendanceRecord countAttendanceResult(LocalDateTime standard) {
        Map<AttendanceResult, Integer> results = new HashMap<>();
        histories.stream().filter(history -> history.isBeforeHistory(standard))
                .forEach(history -> {
                    AttendanceResult attendanceResult = history.getAttendanceResult();
                    results.put(attendanceResult, results.getOrDefault(attendanceResult, 0) + 1);
                });

        return new AttendanceRecord(results);
    }

    public AbsencePenalty classifyAbsenceLevel(LocalDateTime standard) {
        AttendanceRecord attendanceRecord = countAttendanceResult(standard);
        Map<AttendanceResult, Integer> results = attendanceRecord.getTotalAttendanceRecord();
        int absentCount = results.getOrDefault(ABSENCE, 0);
        int lateCount = results.getOrDefault(LATE, 0);
        return AbsencePenalty.findAbsenceLevel(absentCount, lateCount);
    }

    public boolean hasAttendanceHistory(LocalDateTime time) {
        return histories.stream()
                .anyMatch(history -> (history.getAttendanceTime().getDayOfMonth() == time.getDayOfMonth()) &&
                        (history.getAttendanceTime().getMonthValue() == time.getMonthValue()));
    }

    public void deleteAttendanceHistory(LocalDateTime time) {
        AttendanceHistory findAttendanceHistory = histories.stream()
                .filter(history -> (history.getAttendanceTime().getDayOfMonth() == time.getDayOfMonth()) &&
                        (history.getAttendanceTime().getMonthValue() == time.getMonthValue())).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 날짜 출석 기록이 없습니다."));
        histories.remove(findAttendanceHistory);
    }

    public List<AttendanceHistory> getSortedAttendanceHistories(LocalDateTime standard) {
        return histories.stream()
                .filter(history -> history.isBeforeHistory(standard))
                .sorted()
                .toList();
    }

    public void editAttendanceHistory(LocalDateTime time) {
        deleteAttendanceHistory(time);
        histories.add(new AttendanceHistory(time));
    }

    public void recordAttendance(LocalDateTime time) {
        if (hasAttendanceHistory(time)) {
            throw new IllegalArgumentException("[ERROR] 이미 출석하셨습니다.");
        }
        histories.add(new AttendanceHistory(time));
    }

    public LocalDateTime getAttendanceHistory(LocalDateTime time) {
        AttendanceHistory findAttendanceHistory = histories.stream()
                .filter(history -> (history.getAttendanceTime().getDayOfMonth() == time.getDayOfMonth()) &&
                        (history.getAttendanceTime().getMonthValue() == time.getMonthValue())).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 날짜 출석 기록이 없습니다."));
        return findAttendanceHistory.getAttendanceTime();
    }

    public String getAttendanceHistoryResult(LocalDateTime time) {
        AttendanceHistory findAttendanceHistory = histories.stream()
                .filter(history -> (history.getAttendanceTime().getDayOfMonth() == time.getDayOfMonth()) &&
                        (history.getAttendanceTime().getMonthValue() == time.getMonthValue())).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 날짜 출석 기록이 없습니다."));
        AttendanceResult attendanceResult = findAttendanceHistory.getAttendanceResult();
        return attendanceResult.getResult();
    }

    private void addAbsenceHistory(LocalDate standard, int day, List<LocalDateTime> copy) {
        LocalDate time = LocalDate.of(standard.getYear(), standard.getMonthValue(), day);
        if (DailyAttendanceTime.canAttendance(time) && !checkHasAttendanceTime(copy, time)) {
            copy.add(LocalDateTime.of(standard.getYear(), standard.getMonthValue(), time.getDayOfMonth(),
                    ABSENT_DEFAULT_HOUR,
                    ABSENT_DEFAULT_MINUTE));
        }
    }

    private boolean checkHasAttendanceTime(List<LocalDateTime> histories, LocalDate standard) {
        return histories.stream()
                .map(LocalDateTime::toLocalDate)
                .anyMatch(date -> date.equals(standard));
    }
}
