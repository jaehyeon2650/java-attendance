package view;

import domain.AttendanceStatus;
import dto.AttendanceHistoriesDto;
import dto.AttendanceHistoryDto;
import dto.EditResponseDto;
import dto.ExpulsionCrewDto;
import dto.ExpulsionCrewsDto;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class OutputView {
    public void printAddAttendanceResult(LocalDateTime time, String result) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M월 d일 EEEE HH:mm");
        String format = formatter.format(time);
        System.out.printf("%s (%s)\n", format, result);
    }

    public void printEditResult(EditResponseDto editResponseDto) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("M월 d일 EEEE");
        String beforeDate = dateFormatter.format(editResponseDto.beforeAttendanceDate());
        Optional<LocalTime> beforeTimeOptional = editResponseDto.beforeAttendanceTime();
        String beforeTime = "--:--";
        if (beforeTimeOptional.isPresent()) {
            beforeTime = getTime(beforeTimeOptional.get());
        }
        LocalDateTime afterAttendanceTime = editResponseDto.afterAttendanceTime();
        String afterTime = getTime(afterAttendanceTime.toLocalTime());
        String beforeResult = editResponseDto.beforeResult();
        String afterResult = editResponseDto.afterResult();
        System.out.printf("%s %s (%s) -> %s (%s) 수정 완료!\n", beforeDate, beforeTime, beforeResult, afterTime,
                afterResult);
    }

    public void printAttendanceHistories(AttendanceHistoriesDto historiesDto) {
        System.out.printf("이번 달 %s의 출석 기록입니다.\n", historiesDto.username());
        System.out.println();
        List<AttendanceHistoryDto> histories = historiesDto.histories();
        for (AttendanceHistoryDto history : histories) {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("M월 d일 EEEE");
            String date = dateFormatter.format(history.attendanceDate());
            String time = "--:--";
            if (history.attendanceTime().isPresent()) {
                time = getTime(history.attendanceTime().get());
            }
            System.out.printf("%s %s (%s)\n", date, time, history.result());
        }
        System.out.println();
        System.out.printf("출석: %d회\n", historiesDto.attendanceCount());
        System.out.printf("지각: %d회\n", historiesDto.lateCount());
        System.out.printf("결석: %d회\n", historiesDto.absenceCount());
        System.out.println();

        if (!historiesDto.status().equals(AttendanceStatus.NORMAL.getStatus())) {
            System.out.printf("%s 대상자입니다.", historiesDto.status());
        }
    }

    public void printExpulsionCrews(ExpulsionCrewsDto expulsionCrewsDto){
        System.out.println("제적 위험자 조회 결과");
        List<ExpulsionCrewDto> crews = expulsionCrewsDto.crews();
        crews.forEach(crew->{
            System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)\n",crew.username(),crew.absenceCount(),crew.lateCount(),crew.result());
        });
        System.out.println();
    }

    private String getTime(LocalTime beforeTime) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        return timeFormatter.format(beforeTime);
    }
}
