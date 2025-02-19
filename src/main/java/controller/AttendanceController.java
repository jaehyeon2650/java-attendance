package controller;

import View.InputView;
import View.OutputVIew;
import domain.Crews;
import domain.History;
import dto.HistoriesDto;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class AttendanceController {

    private final OutputVIew outputVIew;
    private final InputView inputView;
    private final Crews crews;

    public AttendanceController(OutputVIew outputVIew, InputView inputView, Crews crews) {
        this.outputVIew = outputVIew;
        this.inputView = inputView;
        this.crews = crews;
    }

    public void start() {
        String answer;
        do{
            answer = inputView.getMenu();
            if(answer.equals("1")){
                addAttendance();
            }
            if(answer.equals("2")){
                editAttendance();
            }
            if(answer.equals("3")){
                getAllAttendance();
            }
            if(answer.equals("4")){

            }
        }while (!answer.equals("Q"));
    }

    private void addAttendance(){
        String name = inputView.getName();
        LocalDateTime time = inputView.getAttendanceTime();
        crews.addHistory(name,time);
        String historyResult = crews.getHistoryResult(name, time);
        outputVIew.printAttendanceConfirmation(time,historyResult);
    }

    private void editAttendance(){
        String editName = inputView.getEditName();
        LocalDateTime editHistory = inputView.getEditAttendanceTime();
        LocalDateTime beforeHistory = crews.getHistory(editName, editHistory);
        String beforeResult = crews.getHistoryResult(editName,editHistory);
        String editResult = crews.editHistory(editName, editHistory);
        outputVIew.printEditAttendance(beforeHistory,beforeResult,editHistory,editResult);
    }

    private void getAllAttendance(){
        String username = inputView.getName();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime newDate = LocalDateTime.of(2024, 12, now.getDayOfMonth(), now.getHour(), now.getMinute());
        List<History> beforeHistory = crews.getBeforeHistory(username, newDate);
        Map<String, Integer> attendanceAllResult = crews.getAttendanceAllResult(username, newDate);
        String classifyAbsenceLevel = crews.getClassifyAbsenceLevel(username, newDate);
        HistoriesDto historiesDto = HistoriesDto.of(username,beforeHistory,attendanceAllResult,classifyAbsenceLevel);
        outputVIew.printHistories(historiesDto);
    }

}
