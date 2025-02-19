package util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

public class Convertor {
    private final static String DELIMITER = ":";

    public static LocalDateTime changeToDate(String input) {
        Validator.validateDate(input.trim());
        String[] words = input.split(":");
        int hour = Integer.parseInt(words[0].trim());
        int minutes = Integer.parseInt(words[1].trim());
        LocalDateTime now = LocalDateTime.now();
        return LocalDateTime.of(2024, 12, now.getDayOfMonth(), hour, minutes);
    }

    public static String dateFormattingForOutput(LocalDateTime localDateTime){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM월 dd일 E요일 HH:mm", Locale.KOREAN);
        Date dateAttendanceTime = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return dateFormat.format(dateAttendanceTime);

    }

    public static String dateFormattingForInput(LocalDateTime localDateTime){
        LocalDateTime changedDate = LocalDateTime.of(2024,12,localDateTime.getDayOfMonth(),localDateTime.getHour(),localDateTime.getMinute());

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM월 dd일 E요일", Locale.KOREAN);
        Date dateAttendanceTime = Date.from(changedDate.atZone(ZoneId.systemDefault()).toInstant());
        return dateFormat.format(dateAttendanceTime);
    }

    public static String timeFormattingForOutput(LocalDateTime localDateTime){
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.KOREAN);
        Date dateAttendanceTime = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return dateFormat.format(dateAttendanceTime);
    }

    public static LocalDateTime editDayOfMonth(String date, String time){
        Validator.validateDate(time.trim());
        Validator.validateNumber(date.trim());
        String[] words = time.split(":");
        int hour = Integer.parseInt(words[0].trim());
        int minutes = Integer.parseInt(words[1].trim());
        int day = Integer.parseInt(date);
        return LocalDateTime.of(2024,12,day,hour,minutes);
    }


    static class Validator {
        private final static String TIME_FORMAT = "^(?:[01]\\d|2[0-3])\\s*:\\s*[0-5]\\d$";

        public static void validateDate(String input) {
            if (!input.matches(TIME_FORMAT)) {
                throw new IllegalArgumentException("[ERROR] 00:00 형식으로 입력해주세요.");
            }
        }

        public static void validateNumber(String input){
            if(!input.matches("\\d+")){
                throw new IllegalArgumentException("[ERROR]  숫자를 입력해주세요.");
            }
        }
    }


}
