package dto;

public record AbsenceCrewDto(
        String username,
        int attendanceCount,
        int lateCount,
        int absenceCount,
        String classifyAbsenceLevel
) {
}
