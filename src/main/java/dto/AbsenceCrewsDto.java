package dto;

import domain.AttendanceRecord;
import domain.AttendanceResult;
import domain.Crew;
import java.util.List;
import java.util.Map;

public class AbsenceCrewsDto {
    private final List<AbsenceCrewDto> crews;

    public AbsenceCrewsDto(Map<Crew, AttendanceRecord> record) {
        List<AbsenceCrewDto> crews = record.keySet().stream().map(crew -> {
            AttendanceRecord attendanceRecord = record.get(crew);
            String crewName = crew.getUserName();
            Map<AttendanceResult, Integer> result = attendanceRecord.getTotalAttendanceRecord();
            int attendanceCount = result.getOrDefault(AttendanceResult.ATTENDANCE, 0);
            int lateCount = result.getOrDefault(AttendanceResult.LATE, 0);
            int absenceCount = result.getOrDefault(AttendanceResult.ABSENCE, 0);
            String classifyAbsenceLevel = attendanceRecord.calculateAbsencePenalty().getLevel();
            return new AbsenceCrewDto(crewName, attendanceCount, lateCount, absenceCount, classifyAbsenceLevel);
        }).toList();
        this.crews = crews;
    }

    public List<AbsenceCrewDto> getCrews() {
        return crews;
    }
}
