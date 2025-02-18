package domain;

import java.time.LocalDateTime;
import java.util.List;

public class Crew {
    private final String userName;
    private final Histories histories;

    public Crew(String userName, List<LocalDateTime> histories) {
        validateName(userName);
        this.userName = userName;
        this.histories = new Histories(histories);
    }

    public void validateName(String userName) {
        if (userName.length() >= 5) {
            throw new IllegalArgumentException("이름은 네 글자 이하여야 합니다.");
        }
    }

    public boolean checkAttendance(LocalDateTime history) {
        return histories.hasHistory(history);
    }

    public void addAttendance(LocalDateTime history) {
        histories.addHistory(history);
    }


}
