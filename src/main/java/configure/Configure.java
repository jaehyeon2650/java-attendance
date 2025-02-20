package configure;

import controller.AttendanceController;
import domain.Crews;
import java.time.LocalDate;
import util.CsvReader;
import view.InputView;
import view.OutputVIew;

public class Configure {
    private static AttendanceController attendanceController;
    private static OutputVIew outputVIew;
    private static InputView inputView;
    private static Crews crews;

    public AttendanceController attendanceController() {
        if (attendanceController == null) {
            return new AttendanceController(outputVIew(), inputView(), crews());
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

    private Crews crews() {
        if (crews == null) {
            LocalDate now = LocalDate.now();
            crews = new Crews(CsvReader.loadAttendanceData(), LocalDate.of(2024, 12, now.getDayOfMonth()));
        }
        return crews;
    }

}
