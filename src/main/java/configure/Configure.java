package configure;

import controller.AttendanceController;
import view.InputView;
import view.OutputView;

public class Configure {
    private static AttendanceController attendanceController;
    private static OutputView outputVIew;
    private static InputView inputView;

    public AttendanceController attendanceController() {
        if (attendanceController == null) {
            return new AttendanceController(outputVIew(), inputView());
        }
        return attendanceController;
    }

    private OutputView outputVIew() {
        if (outputVIew == null) {
            outputVIew = new OutputView();
        }
        return outputVIew;
    }

    private InputView inputView() {
        if (inputView == null) {
            inputView = new InputView();
        }
        return inputView;
    }

}
