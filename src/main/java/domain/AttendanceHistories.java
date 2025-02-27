package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class AttendanceHistories {
    private final List<AttendanceHistory> attendanceHistories;

    public AttendanceHistories(List<LocalDateTime> histories) {
        this.attendanceHistories = histories.stream().map(AttendanceHistory::new).collect(Collectors.toList());
    }

    public String addAttendanceHistory(LocalDateTime attendanceTime) {
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

    public String editAttendanceHistory(LocalDateTime editTime){
        AttendanceHistory findBeforeHistory = findAttendanceHistoryByDate(editTime.toLocalDate());
        attendanceHistories.remove(findBeforeHistory);
        return addAttendanceHistory(editTime);
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
