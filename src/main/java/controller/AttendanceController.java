package controller;

import domain.Crews;
import java.time.LocalDateTime;
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
            String username = inputView.getUsernameForAddHistory();
            crews.validateHasCrew(username);
            LocalDateTime attendanceTime = inputView.getAttendanceTime();
            String result = crews.addAttendanceHistory(username, attendanceTime);
            outputView.printAddAttendanceResult(attendanceTime,result);
        }
    }

    public Crews initializeCrews(){
        Map<String, List<LocalDateTime>> histories = FileReader.getCrewsAttendanceHistories();
        return new Crews(histories);
    }
}
