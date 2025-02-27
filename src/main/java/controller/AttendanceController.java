package controller;

import domain.AttendanceHistory;
import domain.AttendanceHistory.AttendanceValidator;
import domain.AttendanceResult;
import domain.Crews;
import dto.EditResponseDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
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
        if(selection.equals("1")){
            LocalDate now = LocalDate.now();
            LocalDate changedDate = LocalDate.of(2024,12,now.getDayOfMonth());
            AttendanceValidator.validateAttendanceDate(changedDate);
            String username = inputView.getUsernameForAddHistory();
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
        }
    }

    public Crews initializeCrews(){
        Map<String, List<LocalDateTime>> histories = FileReader.getCrewsAttendanceHistories();
        LocalDate now = LocalDate.now();
        LocalDate changed = LocalDate.of(2024,12,now.getDayOfMonth());
        return new Crews(histories,changed);
    }
}
