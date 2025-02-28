import config.Config;
import controller.AttendanceController;
import java.io.FileNotFoundException;
import view.InputView;
import view.OutputView;

public class Main {
    public static void main(String[] args)  {
        Config config = new Config();
        AttendanceController attendanceController = config.attendanceController();
        attendanceController.start();
    }
}
