import configure.Configure;
import controller.AttendanceController;

public class Main {
    public static void main(String[] args) {
        Configure configure = new Configure();
        AttendanceController attendanceController = configure.attendanceController();
        attendanceController.start();
    }
}
