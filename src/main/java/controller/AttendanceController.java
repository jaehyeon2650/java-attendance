package controller;

import View.InputView;
import View.OutputVIew;
import java.time.LocalDateTime;

public class AttendanceController {

    private final OutputVIew outputVIew;
    private final InputView inputView;

    public AttendanceController(OutputVIew outputVIew, InputView inputView) {
        this.outputVIew = outputVIew;
        this.inputView = inputView;
    }

    public void start() {
        String name = inputView.getName();
        LocalDateTime time = inputView.getAttendanceTime();

    }
}
