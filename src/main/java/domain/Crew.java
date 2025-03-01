package domain;

import static domain.AbsencePenalty.LATE_TO_ABSENCE_THRESHOLD;
import static domain.AttendanceResult.ABSENCE;
import static domain.AttendanceResult.LATE;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import util.Convertor;

public class Crew implements Comparable<Crew> {
    private final String userName;
    private final AttendanceHistories attendanceHistories;

    public Crew(String userName, List<LocalDateTime> histories, LocalDate standard) {
        validateName(userName);
        this.userName = userName;
        this.attendanceHistories = new AttendanceHistories(histories, standard);
    }

    public void validateName(String userName) {
        if (userName.length() >= 5) {
            throw new IllegalArgumentException("이름은 네 글자 이하여야 합니다.");
        }
    }

    public void addAttendance(LocalDateTime history) {
        attendanceHistories.recordAttendance(history);
    }

    public List<AttendanceHistory> getBeforeHistories(LocalDateTime standard) {
        return attendanceHistories.getSortedAttendanceHistories(standard);
    }

    public boolean isHighAbsenceLevel(LocalDateTime standardTime) {
        return !getClassifyAbsenceLevel(standardTime).equals((AbsencePenalty.NORMAL));
    }

    public void editHistory(LocalDateTime attendanceTime) {
        attendanceHistories.editAttendanceHistory(attendanceTime);
    }

    public String getHistoryResult(LocalDateTime attendanceTime) {
        return attendanceHistories.getAttendanceHistoryResult(attendanceTime);
    }

    public LocalDateTime getHistoryDate(LocalDateTime time) {
        return attendanceHistories.getAttendanceHistory(time);
    }

    public AbsencePenalty getClassifyAbsenceLevel(LocalDateTime time) {
        return attendanceHistories.classifyAbsenceLevel(time);
    }

    public AttendanceRecord getAttendanceAllResult(LocalDateTime time) {
        return attendanceHistories.countAttendanceResult(time);
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public int compareTo(Crew o) {
        LocalDateTime standard = Convertor.changeStandardLocalDateTime(LocalDateTime.now());
        Map<AttendanceResult, Integer> thisAttendanceResult = getAttendanceResultMap(standard, this);
        Map<AttendanceResult, Integer> otherAttendanceResult = getAttendanceResultMap(standard, o);
        int result = compareAttendanceResult(thisAttendanceResult, otherAttendanceResult);
        if (result == 0) {
            return this.userName.compareTo(o.userName);
        }
        return result;
    }

    private int compareAttendanceResult(Map<AttendanceResult, Integer> thisAttendanceResult,
                                        Map<AttendanceResult, Integer> otherAttendanceResult) {
        int myAbsenceCount = calculateAbsenceCount(thisAttendanceResult);
        int otherAbsenceCount = calculateAbsenceCount(otherAttendanceResult);

        return Integer.compare(otherAbsenceCount, myAbsenceCount);
    }

    private Map<AttendanceResult, Integer> getAttendanceResultMap(LocalDateTime standard, Crew crew) {
        AttendanceRecord attendanceAllResult = crew.getAttendanceAllResult(standard);
        return attendanceAllResult.getTotalAttendanceRecord();
    }

    private int calculateAbsenceCount(Map<AttendanceResult, Integer> thisAttendanceResult) {
        int myAbsenceCount = thisAttendanceResult.getOrDefault(LATE, 0);
        myAbsenceCount += thisAttendanceResult.getOrDefault(ABSENCE, 0) * LATE_TO_ABSENCE_THRESHOLD;
        return myAbsenceCount;
    }
}
