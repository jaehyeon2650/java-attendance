package controller;

import View.InputView;
import View.OutputVIew;
import domain.Crews;
import java.time.LocalDateTime;

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
        String name = inputView.getName();
        LocalDateTime time = inputView.getAttendanceTime();
        crews.addHistory(name,time);

    }

}
