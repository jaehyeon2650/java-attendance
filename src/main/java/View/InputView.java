package View;

import constants.SelectionOption;
import java.time.LocalDateTime;
import java.util.Scanner;
import util.Convertor;

public class InputView {

    public SelectionOption getMenu(){
        String output = "오늘은 "+ Convertor.dateFormattingForInput(LocalDateTime.now())+"입니다. 기능을 선택해 주세요";
        System.out.println(output);
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. 출석 확인");
        System.out.println("2. 출석 수정");
        System.out.println("3. 크루별 출석 기록 확인");
        System.out.println("4. 제적 위험자 확인");
        System.out.println("Q. 종료");
        return SelectionOption.getSelectOption(scanner.nextLine());
    }

    public String getName() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public LocalDateTime getAttendanceTime() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("등교 시간을 입력해 주세요.");
        return Convertor.changeToDate(scanner.nextLine());
    }

    public String getEditName() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public LocalDateTime getEditAttendanceTime() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        String date = scanner.nextLine();
        System.out.println("언제로 변경하겠습니까?");
        String time = scanner.nextLine();
        return Convertor.editDayOfMonth(date,time);
    }
}
