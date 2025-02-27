package constant;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;

public enum Holiday {
    CHRISTMAS(12, 25);

    private final int month;
    private final int day;

    Holiday(int month, int day) {
        this.month = month;
        this.day = day;
    }

    public static boolean isHoliday(LocalDate localDate) {
        boolean isHoliday = Arrays.stream(Holiday.values())
                .anyMatch(holiday -> holiday.month == localDate.getMonthValue()
                        && holiday.day == localDate.getDayOfMonth());
        return isHoliday || isWeekend(localDate);
    }

    private static boolean isWeekend(LocalDate localDate) {
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }
}
