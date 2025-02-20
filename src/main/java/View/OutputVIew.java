package View;

import dto.AbsenceCrewDto;
import dto.AbsenceCrewsDto;
import dto.HistoriesDto;
import dto.HistoryDto;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import util.Convertor;

public class OutputVIew {

    private static final String LINE_SEPARATOR = System.lineSeparator();

    public void printAttendanceConfirmation(LocalDateTime attendanceTime, String attendanceResult) {
        String time = Convertor.dateFormattingForOutput(attendanceTime);
        System.out.printf("%s (%s)\n",time,attendanceResult);
    }

    public void printEditAttendance(LocalDateTime before, String beforeResult, LocalDateTime edit,
                                    String editResult) {
        String beforeTime = Convertor.dateFormattingForOutput(before);
        String editTime = Convertor.timeFormattingForOutput(edit);
        System.out.printf("%s (%s) -> %s (%s) 수정 완료!\n",beforeTime,beforeResult,editTime,editResult);
    }

    public void printHistories(HistoriesDto historiesDto){
        printAttendanceHistory(historiesDto);
        printAttendanceAllResult(historiesDto);
        printUserAttendanceResult(historiesDto);
    }

    public void printDangerous(AbsenceCrewsDto crewsDto){
        System.out.println("제적 위험자 조회 결과");
        List<AbsenceCrewDto> crews = crewsDto.getCrews();
        crews.forEach(crew->{
            Map<String, Integer> results = crew.getResults();
            System.out.printf("- %s: 결석 %d회,지각 %d회 (%s)\n",crew.getUsername(),
                    results.getOrDefault("결석",0),
                    results.getOrDefault("지각",0),
                    crew.getClassifyAbsenceLevel());
        });
        System.out.print(LINE_SEPARATOR);
    }

    private void printAttendanceHistory(HistoriesDto historiesDto) {
        System.out.printf("이번 달 %s의 출석 기록입니다.\n", historiesDto.username());
        for (HistoryDto history : historiesDto.histories()) {
            String time = Convertor.dateFormattingForOutput(history.time());
            System.out.printf("%s (%s)\n",time,history.attendanceResult());
        }
    }

    private void printUserAttendanceResult(HistoriesDto historiesDto) {
        String classifyResult = historiesDto.classifyAbsenceLevel();
        if(!classifyResult .equals("정상")){
            System.out.printf("%s 대상자 입니다.\n",classifyResult);
        }
        System.out.print(LINE_SEPARATOR);
    }

    private void printAttendanceAllResult(HistoriesDto historiesDto) {
        Map<String, Integer> result = historiesDto.attendanceAllResult();
        System.out.print(LINE_SEPARATOR);
        System.out.printf("출석: %d회\n", result.getOrDefault("출석", 0));
        System.out.printf("지각: %d회\n", result.getOrDefault("지각", 0));
        System.out.printf("결석: %d회\n", result.getOrDefault("결석", 0));
        System.out.print(LINE_SEPARATOR);
    }
}

