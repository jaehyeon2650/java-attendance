package View;

import java.time.LocalDateTime;
import util.Convertor;

public class OutputVIew {

    public void printAttendanceConfirmation(LocalDateTime attendanceTime, String attendanceResult) {
        String formattedOutput = Convertor.dateFormattingForOutput(attendanceTime);
        formattedOutput += " (" + attendanceResult + ")";
        System.out.println(formattedOutput);
    }

    public void printEditAttendance(LocalDateTime before, String beforeResult, LocalDateTime editTime,
                                    String editResult) {
        String formattedOutput = Convertor.dateFormattingForOutput(before);
        formattedOutput += " (" + beforeResult + ") -> ";
        String timeFormatOutput = Convertor.timeFormattingForOutput(editTime);
        timeFormatOutput += "(" + editResult + ") 수정 완료!";
        System.out.println(formattedOutput + timeFormatOutput);
    }
}

