package domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Crews {
    private final List<Crew> crews;

    public Crews(Map<String, List<LocalDateTime>> allAttendanceHistories) {
        this.crews = allAttendanceHistories.entrySet().stream()
                .map(entry -> new Crew(entry.getKey(), entry.getValue()))
                .toList();
    }

    public void validateHasCrew(String username) {
        findCrewByUsername(username);
    }

    public String addAttendanceHistory(String username, LocalDateTime attendanceTime) {
        Crew crew = findCrewByUsername(username);
        return crew.addAttendanceHistory(attendanceTime);
    }

    private Crew findCrewByUsername(String username) {
        return crews.stream().filter(crew -> crew.isSameName(username))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다."));
    }
}
