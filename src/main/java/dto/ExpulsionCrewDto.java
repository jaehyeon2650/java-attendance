package dto;

import domain.AttendanceAnalyze;

public record ExpulsionCrewDto(
        String username,
        int lateCount,
        int absenceCount,
        String result
) {
    public static ExpulsionCrewDto of(String username, AttendanceAnalyze analyze) {
        return new ExpulsionCrewDto(username, analyze.getLateCount(), analyze.getAbsenceCount(),
                analyze.getAttendanceStatus().getStatus());
    }
}
