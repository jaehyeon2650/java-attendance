package dto;

import java.util.Collections;
import java.util.List;

public class AbsenceCrewsDto {
    private final List<AbsenceCrewDto> crews;

    public AbsenceCrewsDto(List<AbsenceCrewDto> crews) {
        Collections.sort(crews);
        this.crews = crews;
    }

    public List<AbsenceCrewDto> getCrews() {
        return crews;
    }
}
