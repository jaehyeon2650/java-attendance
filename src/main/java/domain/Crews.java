package domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Crews {
    private final List<Crew> crews;

    public Crews(Map<String, List<LocalDateTime>> histories) {
        crews = new ArrayList<>();
        for (String username : histories.keySet()) {
            Crew crew = new Crew(username,histories.get(username));
            crews.add(crew);
        }
    }

    public List<LocalDateTime> getBeforeHistory(String username, LocalDateTime standard){
        Crew findCrew = crews.stream()
                .filter(crew -> crew.getUserName().equals(username)).findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않은 크루입니다."));
        return findCrew.getBeforeHistories(standard);
    }

    public void addHistory(String username,LocalDateTime attendanceTime){
        Crew findCrew = crews.stream()
                .filter(crew -> crew.getUserName().equals(username)).findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않은 크루입니다."));
        findCrew.addAttendance(attendanceTime);
    }
}
