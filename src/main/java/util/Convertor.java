package util;

import java.time.LocalDateTime;

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

    static class Validator {
        private final static String TIME_FORMAT = "^(?:[01]\\d|2[0-3])\\s*:\\s*[0-5]\\d$";

        public static void validateDate(String input) {
            if (!input.matches(TIME_FORMAT)) {
                throw new IllegalArgumentException("[ERROR] 00:00 형식으로 입력해주세요.");
            }
        }
    }
}
