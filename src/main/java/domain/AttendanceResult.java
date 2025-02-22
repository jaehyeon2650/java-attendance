package domain;

public enum AttendanceResult {
    ABSENCE("결석"),
    LATE("지각"),
    ATTENDANCE("출석");

    public static final int LATE_STANDARD_MINUTES = 5;
    public static final int ABSENCE_STANDARD_MINUTES = 30;
    private final String result;

    AttendanceResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }
}
