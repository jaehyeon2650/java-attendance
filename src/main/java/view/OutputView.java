package view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OutputView {
    public void printAddAttendanceResult(LocalDateTime time,String result){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M월 d일 EEEE HH:mm");
        String format = formatter.format(time);
        System.out.printf("%s (%s)\n",format,result);
    }
}
