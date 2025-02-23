package constants;

import java.util.Arrays;

public enum SelectionOption {
    ADD_ATTENDANCE("1"),
    EDIT_ATTENDANCE("2"),
    GET_ATTENDANCE_HISTORY("3"),
    CHECK_ABSENCE_USERS("4"),
    QUIT("Q");

    private final String option;

    SelectionOption(String option) {
        this.option = option;
    }

    public static SelectionOption getSelectOption(String selection) {
        return Arrays.stream(SelectionOption.values())
                .filter(select -> select.option.equals(selection))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(String.format("[ERROR] %s은 잘못된 입력입니다.",selection)));
    }

}
