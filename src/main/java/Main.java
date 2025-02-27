import controller.AttendanceController;
import java.io.FileNotFoundException;
import util.FileReader;
import view.InputView;
import view.OutputView;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        AttendanceController attendanceController = new AttendanceController(new OutputView(),new InputView());
        attendanceController.start();
    }
}
