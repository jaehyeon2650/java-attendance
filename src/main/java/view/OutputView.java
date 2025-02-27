package view;

import dto.EditResponseDto;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

    private String getTime(LocalTime beforeTime) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        return timeFormatter.format(beforeTime);

    }
}
