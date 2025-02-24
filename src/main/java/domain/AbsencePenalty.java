package domain;

import java.util.Arrays;
import java.util.Comparator;

public enum AbsencePenalty {
    OUT("제적", 6),
    MEETING("면담", 3),
    WARNING("경고", 2),
    NORMAL("정상", 0);

    public static final int LATE_TO_ABSENCE_THRESHOLD = 3;

    private final String level;
    private final int standard;

    AbsencePenalty(String level, int standard) {
        this.level = level;
        this.standard = standard;
    }

    public static AbsencePenalty findAbsenceLevel(final int absentCount, final int lateCount) {
        int calculatedAbsentCount = absentCount + (lateCount / LATE_TO_ABSENCE_THRESHOLD);

        return Arrays.stream(AbsencePenalty.values())
                .sorted(Comparator.comparingInt((AbsencePenalty absencePenalty) -> absencePenalty.standard).reversed())
                .filter(penalty -> penalty.exceedsStandard(calculatedAbsentCount))
                .findFirst()
                .orElse(NORMAL);
    }

    private boolean exceedsStandard(int count) {
        return count >= standard;
    }

    public String getLevel() {
        return level;
    }
}
