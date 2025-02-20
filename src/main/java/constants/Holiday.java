package constants;

import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import util.Convertor;

public enum Holiday {
    CHRISTMAS(12, 25),
    NEW_YEARS_DAY(1, 1);

    private final int month;
    private final int day;

    Holiday(int month, int day) {
        this.month = month;
        this.day = day;
    }

    public static void validate(LocalDateTime time) {
        validateWeekend(time);
        validateHoliday(time);
    }

    public static boolean isHoliday(LocalDate time) {
        boolean isHoliday = Arrays.stream(Holiday.values())
                .anyMatch(holiday -> holiday.month == time.getMonthValue() && holiday.day == time.getDayOfMonth());
        boolean isWeekend = time.getDayOfWeek() == SATURDAY || time.getDayOfWeek() == SUNDAY;
        return isHoliday || isWeekend;
    }


    private static void validateWeekend(LocalDateTime time) {
        if ((time.getDayOfWeek() == SATURDAY) || (time.getDayOfWeek() == SUNDAY)) {
            throw new IllegalArgumentException(
                    String.format("[ERROR] %s은 등교일이 아닙니다.", Convertor.dateFormattingForInput(time))
            );
        }
    }

    private static void validateHoliday(LocalDateTime time) {
        boolean isHoliday = Arrays.stream(Holiday.values())
                .anyMatch(holiday -> holiday.month == time.getMonthValue() && holiday.day == time.getDayOfMonth());
        if (isHoliday) {
            throw new IllegalArgumentException(
                    String.format("[ERROR] %s은 등교일이 아닙니다.", Convertor.dateFormattingForInput(time))
            );
        }
    }


}
