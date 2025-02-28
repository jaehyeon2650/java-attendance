package domain;

import constant.Holiday;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AttendanceHistories {
    private final List<AttendanceHistory> attendanceHistories;

    public AttendanceHistories(final List<LocalDateTime> histories, LocalDate standardDate) {
        List<AttendanceHistory> allHistories = new ArrayList<>();
        for (int i = 1; i < standardDate.getDayOfMonth(); i++) {
            LocalDate localDate = LocalDate.of(standardDate.getYear(), standardDate.getMonth(), i);
            createAttendance(histories, localDate, allHistories);
        }
        this.attendanceHistories = allHistories;
    }

    public AttendanceResult addAttendanceHistory(LocalDateTime attendanceTime) {
        Validator.validateAddAttendanceHistory(attendanceHistories, attendanceTime);
        AttendanceHistory newAttendanceHistory = new AttendanceHistory(attendanceTime);
        attendanceHistories.add(newAttendanceHistory);
        return newAttendanceHistory.getAttendanceResult();
    }

    public AttendanceHistory findAttendanceHistoryByDate(LocalDate standardDate) {
        return attendanceHistories.stream().filter(history -> history.isSameDate(standardDate))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 기록이 존재하지 않습니다."));
    }

    public AttendanceResult editAttendanceHistory(LocalDateTime editTime) {
        AttendanceHistory findBeforeHistory = findAttendanceHistoryByDate(editTime.toLocalDate());
        attendanceHistories.remove(findBeforeHistory);
        return addAttendanceHistory(editTime);
    }

    public List<AttendanceHistory> getBeforeAttendanceHistories(LocalDate localDate) {
        return attendanceHistories.stream()
                .filter(attendanceHistory -> attendanceHistory.isBeforeAttendanceHistory(localDate))
                .sorted()
                .toList();
    }

    public AttendanceAnalyze getAttendanceAnalyze(LocalDate standard) {
        return new AttendanceAnalyze(getBeforeAttendanceHistories(standard));
    }

    private void createAttendance(List<LocalDateTime> histories, LocalDate localDate,
                                  List<AttendanceHistory> allHistories) {
        if (!Holiday.isHoliday(localDate)) {
            Optional<LocalDateTime> date = histories.stream().filter(history -> {
                        LocalDate historyDate = history.toLocalDate();
                        return historyDate.isEqual(localDate);
                    })
                    .findAny();
            allHistories.add(makeAttendanceHistory(date, localDate));
        }
    }

    private AttendanceHistory makeAttendanceHistory(Optional<LocalDateTime> date, LocalDate standardDate) {
        return date.map(AttendanceHistory::new)
                .orElseGet(() -> new AttendanceHistory(standardDate, null));

    }


    public static class Validator {
        public static void validateAddAttendanceHistory(List<AttendanceHistory> attendanceHistories,
                                                        LocalDateTime localDateTime) {
            boolean check = attendanceHistories.stream()
                    .anyMatch(attendanceHistory -> attendanceHistory.isSameDate(localDateTime.toLocalDate()));
            if (check) {
                throw new IllegalArgumentException("[ERROR] 오늘 이미 출석을 하셨습니다. 수정 메뉴로 이동해주세요!");
            }
        }
    }
}
