package View;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

public class OutputVIew {


    public void printAttendanceConfirmation(LocalDateTime attendanceTime, String attendanceResult) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM월 dd일 E요일 HH:mm", Locale.KOREAN);
        Date dateAttendanceTime = Date.from(attendanceTime.atZone(ZoneId.systemDefault()).toInstant());
        String formattedOutput = dateFormat.format(dateAttendanceTime);
        formattedOutput += " (" + attendanceResult + ")";
        System.out.println(formattedOutput);
    }
}

