package View;

import java.time.LocalDateTime;
import java.util.Scanner;
import util.Convertor;

public class InputView {
    public String getName() {
        Scanner scan = new Scanner(System.in);
        System.out.println("닉네임을 입력해 주세요.");
        return scan.nextLine();
    }

    public LocalDateTime getAttendanceTime() {
        Scanner scan = new Scanner(System.in);
        System.out.println("등교 시간을 입력해 주세요.");
        return Convertor.changeToDate(scan.nextLine());
    }
}
