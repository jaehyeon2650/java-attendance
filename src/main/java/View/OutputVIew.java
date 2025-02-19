package View;

import dto.HistoriesDto;
import dto.HistoryDto;
import java.time.LocalDateTime;
import java.util.Map;
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

    public void printHistories(HistoriesDto historiesDto){
        System.out.printf("이번 달 %s의 출석 기록입니다.\n",historiesDto.username());
        for (HistoryDto history : historiesDto.histories()) {
            String formattedOutput = Convertor.dateFormattingForOutput(history.time());
            formattedOutput += " (" + history.attendanceResult()+ ")";
            System.out.println(formattedOutput);
        }
        Map<String, Integer> result = historiesDto.attendanceAllResult();
        System.out.println();
        System.out.printf("출석: %d회\n",result.getOrDefault("출석",0));
        System.out.printf("지각: %d회\n",result.getOrDefault("지각",0));
        System.out.printf("결석: %d회\n",result.getOrDefault("결석",0));
        String classifyResult =historiesDto.classifyAbsenceLevel();
        System.out.println();
        if(!classifyResult .equals("정상 대상자")){
            System.out.printf("%s입니다.\n",classifyResult);
        }
        System.out.println();
    }
}

