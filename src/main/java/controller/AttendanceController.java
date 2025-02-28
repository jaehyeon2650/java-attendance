package controller;

import domain.AttendanceAnalyze;
import domain.AttendanceHistory;
import domain.AttendanceHistory.AttendanceValidator;
import domain.AttendanceResult;
import domain.Crews;
import dto.AttendanceHistoriesDto;
import dto.AttendanceHistoryDto;
import dto.EditResponseDto;
import dto.ExpulsionCrewsDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import util.DateFormatter;
import util.FileReader;
import view.InputView;
import view.OutputView;

public class AttendanceController {

    private final OutputView outputView;
    private final InputView inputView;

    public AttendanceController(OutputView outputView, InputView inputView) {
        this.outputView = outputView;
        this.inputView = inputView;
    }

    public void start() {
        Crews crews = initializeCrews();
        String selection = inputView.getSelection();
        while(!selection.equals("Q")){
            try{
                if(selection.equals("1")){
                    LocalDate now = DateFormatter.getTodayDate();
                    AttendanceValidator.validateAttendanceDate(now);
                    String username = inputView.getUsername();
                    crews.validateHasCrew(username);
                    LocalDateTime attendanceTime = inputView.getAttendanceTime();
                    AttendanceResult result = crews.addAttendanceHistory(username, attendanceTime);
                    outputView.printAddAttendanceResult(attendanceTime,result.getResult());
                }else if(selection.equals("2")){
                    String username = inputView.getUsernameForEditHistory();
                    crews.validateHasCrew(username);
                    LocalDate attendDate = inputView.getEditDay();
                    AttendanceValidator.validateAttendanceDate(attendDate);
                    AttendanceHistory beforeHistory = crews.findAttendanceHistory(username, attendDate);
                    LocalTime attendTime = inputView.getEditTime();
                    LocalDateTime attendanceTime = LocalDateTime.of(attendDate,attendTime);
                    AttendanceResult result = crews.editAttendanceHistory(username, attendanceTime);
                    outputView.printEditResult(EditResponseDto.of(beforeHistory,attendanceTime,result));
                }else if(selection.equals("3")) {
                    String username = inputView.getUsername();
                    LocalDate now = DateFormatter.getTodayDate();
                    List<AttendanceHistory> attendanceHistories = crews.findAttendanceHistories(username, now);
                    AttendanceAnalyze attendanceAnalyze = crews.getAttendanceAnalyze(username, now);
                    outputView.printAttendanceHistories(
                            AttendanceHistoriesDto.of(username, attendanceHistories, attendanceAnalyze));
                } else if(selection.equals("4")){
                    LocalDate now = DateFormatter.getTodayDate();
                    Map<String, AttendanceAnalyze> expulsionCrews = crews.findExpulsionCrews(now);
                    outputView.printExpulsionCrews(ExpulsionCrewsDto.from(expulsionCrews));
                }
            }catch(IllegalArgumentException e){
                outputView.printErrorMessage(e.getMessage());
            }finally {
                selection = inputView.getSelection();
            }
        }
    }

    public Crews initializeCrews(){
        Map<String, List<LocalDateTime>> histories = FileReader.getCrewsAttendanceHistories();
        LocalDate now = LocalDate.now();
        LocalDate changed = LocalDate.of(2024,12,now.getDayOfMonth());
        return new Crews(histories,changed);
    }
}
