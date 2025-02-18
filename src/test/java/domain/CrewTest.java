package domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class CrewTest {

    @Test
    public void validateName() {
        String userName = "가나다라마";
        List<LocalDateTime> histories = new ArrayList<>();

        assertThatThrownBy(() -> new Crew(userName, histories))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이름은 네 글자 이하여야 합니다.");
    }

}
