package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Crew {
    private final String userName;
    private final Histories histories;

    public Crew(String userName, List<LocalDateTime> histories, LocalDate standard) {
        validateName(userName);
        this.userName = userName;
        this.histories = new Histories(histories, standard);
    }

    public void validateName(String userName) {
        if (userName.length() >= 5) {
            throw new IllegalArgumentException("이름은 네 글자 이하여야 합니다.");
        }
    }

    public void addAttendance(LocalDateTime history) {
        histories.addHistory(history);
    }

    public List<AttendanceHistory> getBeforeHistories(LocalDateTime standard) {
        return histories.getSortedHistories(standard);
    }

    public String getHistoryResult(LocalDateTime attendanceTime) {
        return histories.getHistoryResult(attendanceTime);
    }

    public String getUserName() {
        return userName;
    }

    public void editHistory(LocalDateTime attendanceTime) {
        histories.editHistory(attendanceTime);
    }

    public LocalDateTime getHistoryDate(LocalDateTime time) {
        return histories.getHistory(time);
    }

    public AbsencePenalty getClassifyAbsenceLevel(LocalDateTime time) {
        return histories.classifyAbsenceLevel(time);
    }

    public Map<String, Integer> getAttendanceAllResult(LocalDateTime time) {
        return histories.getAttendanceResultCount(time);
    }
}
