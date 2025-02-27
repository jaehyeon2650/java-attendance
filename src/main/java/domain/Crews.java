package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Crews {
    private final List<Crew> crews;

    public Crews(Map<String, List<LocalDateTime>> allAttendanceHistories, LocalDate standardDate) {
        this.crews = allAttendanceHistories.entrySet().stream()
                .map(entry -> new Crew(entry.getKey(), entry.getValue(),standardDate))
                .toList();
    }

    public void validateHasCrew(String username) {
        findCrewByUsername(username);
    }

    public String addAttendanceHistory(String username, LocalDateTime attendanceTime) {
        Crew crew = findCrewByUsername(username);
        return crew.addAttendanceHistory(attendanceTime);
    }

    public String editAttendanceHistory(String username, LocalDateTime editTime){
        Crew crew = findCrewByUsername(username);
        return crew.editAttendanceHistory(editTime);
    }

    public AttendanceHistory findAttendanceHistory(String username, LocalDate standardTime) {
        Crew crew = findCrewByUsername(username);
        return crew.findAttendanceHistory(standardTime);
    }

    private Crew findCrewByUsername(String username) {
        return crews.stream().filter(crew -> crew.isSameName(username))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다."));
    }
}
