package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Crews {
    private final List<Crew> crews;

    public Crews(Map<String, List<LocalDateTime>> histories, LocalDate standard) {
        crews = new ArrayList<>();
        for (String username : histories.keySet()) {
            Crew crew = new Crew(username, histories.get(username), standard);
            crews.add(crew);
        }
    }

    public List<AttendanceHistory> getBeforeAttendanceHistories(String username, LocalDateTime standard) {
        Crew findCrew = findCrew(username);
        return findCrew.getBeforeHistories(standard);
    }

    public void recordAttendance(String username, LocalDateTime attendanceTime) {
        Crew findCrew = findCrew(username);
        findCrew.addAttendance(attendanceTime);
    }

    public String getRecordAttendanceResult(String username, LocalDateTime attendanceTime) {
        Crew findCrew = findCrew(username);
        return findCrew.getHistoryResult(attendanceTime);
    }

    public LocalDateTime getAttendanceHistory(String username, LocalDateTime localDateTime) {
        Crew findCrew = findCrew(username);
        return findCrew.getHistoryDate(localDateTime);
    }

    public AbsencePenalty getClassifyAbsenceLevel(String username, LocalDateTime localDateTime) {
        Crew findCrew = findCrew(username);
        return findCrew.getClassifyAbsenceLevel(localDateTime);
    }

    public String editAttendanceHistory(String username, LocalDateTime localDateTime) {
        Crew findCrew = findCrew(username);
        findCrew.editHistory(localDateTime);
        return findCrew.getHistoryResult(localDateTime);
    }

    public Map<String, Integer> getAttendanceAllResult(String username, LocalDateTime localDateTime) {
        Crew findCrew = findCrew(username);
        return findCrew.getAttendanceAllResult(localDateTime);
    }

    public List<Crew> getHighAbsenceLevelCrews(LocalDateTime localDateTime) {
        return crews.stream()
                .filter(crew -> !crew.getClassifyAbsenceLevel(localDateTime).equals(AbsencePenalty.NORMAL)).toList();
    }

    private Crew findCrew(String username) {
        return crews.stream()
                .filter(crew -> crew.getUserName().equals(username)).findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않은 크루입니다."));
    }
}
