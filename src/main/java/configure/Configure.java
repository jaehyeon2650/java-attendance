package configure;

import controller.AttendanceController;
import view.InputView;
import view.OutputVIew;

public class Configure {
    private static AttendanceController attendanceController;
    private static OutputVIew outputVIew;
    private static InputView inputView;

    public AttendanceController attendanceController() {
        if (attendanceController == null) {
            return new AttendanceController(outputVIew(), inputView());
        }
        return attendanceController;
    }

    private OutputVIew outputVIew() {
        if (outputVIew == null) {
            outputVIew = new OutputVIew();
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
